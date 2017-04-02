package com.example.concussionapp;
//test
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import zephyr.android.HxMBT.BTClient;
import zephyr.android.HxMBT.ZephyrProtocol;

import static com.example.concussionapp.R.id.Connect;
import static java.lang.Integer.parseInt;
//import android.view.View.OnClickListener;

public class Chronometer_Heart_Rate_Activity extends Activity {

    private static final String TAG = "ChronometerActivity";
    //   Chronometer chronometer;

    //for sensor:
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;
    private final int HEART_RATE = 0x100;
    //   private final int INSTANT_SPEED = 0x101;
    private String maxHR;       //max and min heart rates taken from get extra
    private String minHR;
    private int maxHeart;
    private int minHeart;
    private int exerciseTime;         //time in minutes, will start count down clock w/ this value

    private List<Integer> HRSamples; //gets a sample every 3 second
    private List<Integer> SamplesPerMinute;  //gets avg of HRSamples
    private boolean grabSample;      //Sample HR every 3 seconds, every 3 sec this should be reset

    //this Handler object will be used in obtaining HR samples, it needs an int delay in Millis
    private Handler handleForMinutes;
    private Handler handle;
    private int delayForMinutes;
    private int delay;
    private String HRStringdata;  //this is just to put the data to log to see what we're getting
    private int heartRateData;  // was declared below but i want my my handle to have access to
    // this value

    TextView countDownTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer__heart__rate_);


        countDownTime = (TextView) findViewById(R.id.countDownTimer);
        Intent intent = getIntent(); //get the intent from the mainActivity to link them

        //       Bundle bundle = intent.getExtras();
        maxHeart = intent.getIntExtra("maxHeartRate", 120); //grab max and min HR from previous activity
        minHeart = intent.getIntExtra("minHeartRate", 60);
        exerciseTime = intent.getIntExtra("exerciseTime", 9);
        exerciseTime *= 60000; // 1 min * 60 sec * 1000 milli/sed


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

        SamplesPerMinute = new ArrayList<>();
        delayForMinutes = 60000;
        handleForMinutes = new Handler();
        handleForMinutes.postDelayed(new Runnable() {
            public void run() {

                if (!HRSamples.isEmpty()) {
                    int avgPerMin = 0;
                    for (Integer i : HRSamples) {
                        avgPerMin += i;
                    }
                    avgPerMin /= HRSamples.size();
                    SamplesPerMinute.add(avgPerMin);
                    HRSamples.clear();
                    HRStringdata = "";
                }
                handleForMinutes.postDelayed(this, delayForMinutes);
            }
        }, delayForMinutes);


        delay = 3000; //3000 millis gives a delay of 3 sec, hr samples 20 per minute
        HRSamples = new ArrayList<>(); //in
        grabSample = false;
        handle = new Handler();
        handle.postDelayed(new Runnable() {
            public void run() {
                grabSample = true;
                handle.postDelayed(this, delay);
            }
        }, delay);


        Button StartButton;
        Button StopButton;
        Button ResetButton;

        //   chronometer = (Chronometer) findViewById(R.id.chronometer);

        StartButton = (Button) findViewById(R.id.start_Button);
        StartButton.setOnClickListener(mStartListener);

    /*    StopButton= (Button) findViewById(R.id.stop_Button);
        StopButton.setOnClickListener(mStopListener);

        ResetButton = (Button) findViewById(R.id.reset_Button);
        ResetButton.setOnClickListener(mResetListener);
*/
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
        String ErrorText = "Not Connected to HxM ! !";
        tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(Connect);
        if (btnConnect != null) {
            btnConnect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String BhMacID = "00:07:80:0E:B1:0C";

                    adapter = BluetoothAdapter.getDefaultAdapter();

                    Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            if (device.getName().startsWith("HXM")) {
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
                    _NConnListener = new NewConnectedListener(Newhandler, Newhandler);
                    _bt.addConnectedEventListener(_NConnListener);

                    TextView tv1 = (EditText) findViewById(R.id.ActualHeartRate);
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
                    if (_bt.IsConnected()) {
                        _bt.start();
                        TextView tv = (TextView) findViewById(R.id.connectionStatus);
                        String ErrorText = "Connected to HxM " + DeviceName;
                        tv.setText(ErrorText);

                        //Reset all the values to 0s
                    } else {
                        TextView tv = (TextView) findViewById(R.id.connectionStatus);
                        String ErrorText = "Unable to Connect !";
                        tv.setText(ErrorText);
                    }
                }
            });
        }
        /*Obtaining the handle to act on the DISCONNECT button*/
        Button btnDisconnect = (Button) findViewById(R.id.Disconnect);
        if (btnDisconnect != null) {
            btnDisconnect.setOnClickListener(new View.OnClickListener() {
                @Override
				/*Functionality to act if the button DISCONNECT is touched*/
                public void onClick(View v) {
                    // TODO Auto-generated method stub
					/*Reset the global variables*/
                    TextView tv = (TextView) findViewById(R.id.connectionStatus);
                    String ErrorText = "Disconnected from HxM!";
                    tv.setText(ErrorText);

					/*This disconnects listener from acting on received messages*/
                    try {
                        _bt.removeConnectedEventListener(_NConnListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
					/*Close the communication with the device & throw an exception if failure*/
                    try {
                        _bt.Close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        }

    } // onCreate() ENDS HERE

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {
            new CountDownTimer(exerciseTime, 1000) {
                //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
                @Override
                public void onTick(long millisUntilFinished) {
                    //ref: http://stackoverflow.com/questions/17620641/countdowntimer-in-minutes-and-seconds
                    countDownTime.setText("" + String.format("%2d:%02d", (millisUntilFinished / 60000) % 60, (millisUntilFinished / 1000) % 60));
                }

                @Override
                public void onFinish() {
                    String minuteAvg = "";
                    for (Integer i : SamplesPerMinute) {
                        minuteAvg += " " + String.valueOf(i);
                    }
                    countDownTime.setText("Done");
                    for (int i = 0; i < 15; i++) { //just for testing
                        Log.i(TAG, "Finished. Averages are" + minuteAvg);
                    }
                    Intent newIntent = new Intent(getApplicationContext(), SecondQuestionaire.class);
                    newIntent.putExtra("heartData", minuteAvg);
                    startActivity(newIntent);
                }
            }.start();
        }
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
                Method m = BluetoothDevice.class.getMethod("convertPinToBytes", new Class[]{String.class});
                byte[] pin = (byte[]) m.invoke(device, "1234");
                m = device.getClass().getMethod("setPin", new Class[]{pin.getClass()});
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


    final Handler Newhandler = new Handler() {
        public void handleMessage(Message msg) {
            TextView tv;
            //           int heartRateData;                             CHANGE HERE
            if (msg.what == HEART_RATE) {
                String HeartRatetext = msg.getData().getString("HeartRate");
                heartRateData = parseInt(HeartRatetext);
                //grabSample is set to True every 3 sec in handle's  run() definition
                if (grabSample && heartRateData != 0) {
                    grabSample = false;
                    HRSamples.add(heartRateData);
                    HRStringdata += " " + String.valueOf(heartRateData);
                    Log.i(TAG, HRStringdata);
                }
                if (heartRateData > maxHeart) {
                    Log.i(TAG, " Heart rate data is above max");
                    Toast.makeText(getApplicationContext(), "Heart rate too high.", Toast.LENGTH_LONG).show();

                } else if (heartRateData < minHeart) {
                    Log.i(TAG, " Heart rate data is below max");
                    Toast.makeText(getApplicationContext(), "Heart rate too low.", Toast.LENGTH_LONG).show();

                }
                tv = (EditText) findViewById(R.id.ActualHeartRate);
                System.out.println("Heart Rate Info is " + HeartRatetext);
                if (tv != null) {
                    tv.setText(HeartRatetext);
                }

            }
        }

    };
}