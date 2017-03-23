package com.example.concussionapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import zephyr.android.HxMBT.BTClient;
import zephyr.android.HxMBT.ZephyrProtocol;

import static com.example.concussionapp.R.id.Connect;
//import android.view.View.OnClickListener;

public class Chronometer_Heart_Rate_Activity extends Activity {

    private static final  String TAG = "ChronometerActivity";
    Chronometer chronometer;

    //for sensor:
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;
    private final int HEART_RATE = 0x100;
 //   private final int INSTANT_SPEED = 0x101;
    private String maxHR;       //max and min heart rates taken from get extra
    private String minHR;
    int maxHeart;
    int minHeart;
    int exerciseTime;         //time in minutes, will start count down clock w/ this value
    //end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer__heart__rate_);

        Intent intent = getIntent(); //get the intent from the mainActivity to link them

 //       Bundle bundle = intent.getExtras();
        maxHeart = intent.getIntExtra("maxHeartRate",120); //grab max and min HR from previous activity
        minHeart = intent.getIntExtra("minHeartRate",60);
        exerciseTime = intent.getIntExtra("exerciseTime", 9);

        Log.i(TAG, "Max heart rate entered:  " + maxHeart);
        Log.i(TAG, "Min heart rate entered:  " + minHeart);
        Log.i(TAG, "Exercise time entered by user:  " + exerciseTime);
 /*       if(maxHR != null) {
            maxHeartRate = Integer.parseInt(maxHR);
        } else {
            maxHeartRate = 120;
        }
        if(minHR != null) {
            minHeartRate = Integer.parseInt(minHR);
        } else {
            minHeartRate = 90;
        }
*/

        Button StartButton;
        Button StopButton;
        Button ResetButton;

        chronometer = (Chronometer) findViewById(R.id.chronometer);

        StartButton= (Button) findViewById(R.id.start_Button);
        StartButton.setOnClickListener(mStartListener);

        StopButton= (Button) findViewById(R.id.stop_Button);
        StopButton.setOnClickListener(mStopListener);

        ResetButton = (Button) findViewById(R.id.reset_Button);
        ResetButton.setOnClickListener(mResetListener);

        /*BIPINS CODE FOR onCreate STARTS HERE AND ENDS AT END OF onCreate()*/
         /*Sending a message to android that we are going to initiate a pairing request*/
        IntentFilter filter = new IntentFilter("android.bluetooth.device.action.PAIRING_REQUEST");
        /*Registering a new BTBroadcast receiver from the Main Activity context with pairing request event*/
        this.getApplicationContext().registerReceiver(new BTBroadcastReceiver(), filter);
        // Registering the BTBondReceiver in the application that the status of the receiver has changed to Paired
        IntentFilter filter2 = new IntentFilter("android.bluetooth.device.action.BOND_STATE_CHANGED");
        this.getApplicationContext().registerReceiver(new BTBondReceiver(), filter2);

        //Obtaining the handle to act on the CONNECT button
        TextView tv = (TextView) findViewById(R.id.connectionStatus);
        String ErrorText  = "Not Connected to HxM ! !";
        tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(Connect);
        if (btnConnect != null)
        {
            btnConnect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String BhMacID = "00:07:80:0E:B1:0C";

                    adapter = BluetoothAdapter.getDefaultAdapter();

                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

                    if (pairedDevices.size() > 0)
                    {
                        for (BluetoothDevice device : pairedDevices)
                        {
                            if (device.getName().startsWith("HXM"))
                            {
                                BluetoothDevice btDevice = device;
                                BhMacID = btDevice.getAddress();
                                break;

                            }
                        }

                    }

                    //BhMacID = btDevice.getAddress();
                    BluetoothDevice Device = adapter.getRemoteDevice(BhMacID);
                    String DeviceName = Device.getName();
                    _bt = new BTClient(adapter, BhMacID);
                    _NConnListener = new NewConnectedListener(Newhandler,Newhandler);
                    _bt.addConnectedEventListener(_NConnListener);

                    TextView tv1 = (EditText)findViewById(R.id.ActualHeartRate);
                    tv1.setText("000");

               //     tv1 = (EditText)findViewById(R.id.labelInstantSpeed);
                 //   tv1.setText("0.0");

               //     ((TextView) findViewById(R.id.MACaddr)).setText(BhMacID);

                    //tv1 = 	(EditText)findViewById(R.id.labelSkinTemp);
                    //tv1.setText("0.0");

                    //tv1 = 	(EditText)findViewById(R.id.labelPosture);
                    //tv1.setText("000");

                    //tv1 = 	(EditText)findViewById(R.id.labelPeakAcc);
                    //tv1.setText("0.0");
                    if(_bt.IsConnected())
                    {
                        _bt.start();
                        TextView tv = (TextView) findViewById(R.id.connectionStatus);
                        String ErrorText  = "Connected to HxM "+DeviceName;
                        tv.setText(ErrorText);

                        //Reset all the values to 0s

                    }
                    else
                    {
                        TextView tv = (TextView) findViewById(R.id.connectionStatus);
                        String ErrorText  = "Unable to Connect !";
                        tv.setText(ErrorText);
                    }
                }
            });
        }
        /*Obtaining the handle to act on the DISCONNECT button*/
        Button btnDisconnect = (Button) findViewById(R.id.Disconnect);
        if (btnDisconnect != null)
        {
            btnDisconnect.setOnClickListener(new View.OnClickListener() {
                @Override
				/*Functionality to act if the button DISCONNECT is touched*/
                public void onClick(View v) {
                    // TODO Auto-generated method stub
					/*Reset the global variables*/
                    TextView tv = (TextView) findViewById(R.id.connectionStatus);
                    String ErrorText  = "Disconnected from HxM!";
                    tv.setText(ErrorText);

					/*This disconnects listener from acting on received messages*/
                    _bt.removeConnectedEventListener(_NConnListener);
					/*Close the communication with the device & throw an exception if failure*/
                    _bt.Close();

                }
            });
        }

    } // onCreate() ENDS HERE

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            chronometer.start();
        }
    };
    View.OnClickListener mStopListener = new View.OnClickListener() {
        public void onClick(View v) {
            chronometer.stop();
        }
    };
    View.OnClickListener mResetListener = new View.OnClickListener()
    {
        public void onClick(View v) {chronometer.setBase(SystemClock.elapsedRealtime());}
    };


    /*BIPINS CODE STARTS HERE AGAIN AND ENDS @END OF ACTIVITY
     */
    private class BTBondReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle b = intent.getExtras();
            BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("Bond state", "BOND_STATED = " + device.getBondState());
        }
    }
    private class BTBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("BTIntent", intent.getAction());
            Bundle b = intent.getExtras();
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.DEVICE").toString());
            Log.d("BTIntent", b.get("android.bluetooth.device.extra.PAIRING_VARIANT").toString());
            try {
                BluetoothDevice device = adapter.getRemoteDevice(b.get("android.bluetooth.device.extra.DEVICE").toString());
                Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[] {String.class} );
                byte[] pin = (byte[])m.invoke(device, "1234");
                m = device.getClass().getMethod("setPin", new Class [] {pin.getClass()});
                Object result = m.invoke(device, pin);
                Log.d("BTTest", result.toString());
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    final Handler Newhandler = new Handler(){
        public void handleMessage(Message msg)
        {
            TextView tv;
            if(msg.what == HEART_RATE)
            {
                    String HeartRatetext = msg.getData().getString("HeartRate");
                    tv = (EditText)findViewById(R.id.ActualHeartRate);
                    System.out.println("Heart Rate Info is "+ HeartRatetext);
                    if (tv != null)tv.setText(HeartRatetext);

            }
        }

    };
}