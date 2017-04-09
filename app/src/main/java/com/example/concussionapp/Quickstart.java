package com.example.concussionapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import zephyr.android.HxMBT.BTClient;
import zephyr.android.HxMBT.ZephyrProtocol;

import static com.example.concussionapp.R.id.Connect;

public class Quickstart extends AppCompatActivity {

    private BroadcastReceiver receiver;

    private static final String TAG = "Quickstart";
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;
    private final int HEART_RATE = 0x100;
    private boolean connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quickstart);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                    connected = true;
                    Log.i(TAG, "Sensor Connected");
                    Log.i(TAG, "Sensor Connected");
                    Log.i(TAG, "Sensor Connected");
                    Log.i(TAG, "Sensor Connected");
                } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                    connected = false;
                    Log.i(TAG, "Sensor Dis-Connected");
                    Log.i(TAG, "Sensor Dis-Connected");
                    Log.i(TAG, "Sensor Dis-Connected");
                    Log.i(TAG, "Sensor Dis-Connected");
                }
            }
        };

        connected = false;

        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter4 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(receiver, filter3);
        this.registerReceiver(receiver, filter4);


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
                    //if there's no connection so this, else do nothing
                    if(!connected) {
                   // String BhMacID = "00:07:80:0E:B1:0C";
                        String BhMacID = SharedData.MAC;

                        //if the timer is already running
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
                    tv1.setText("000" + "BPM");


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
                    if (connected) {
                        // TODO Auto-generated method stub
					/*Reset the global variables*/
                        TextView tv1 = (EditText) findViewById(R.id.ActualHeartRate);
                        tv1.setText("000");

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
                }
            });
        }

    }



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

            if (msg.what == HEART_RATE) {
                String HeartRatetext = msg.getData().getString("HeartRate");

                tv = (EditText) findViewById(R.id.ActualHeartRate);
                System.out.println("Heart Rate Info is " + HeartRatetext);
                if (tv != null) {
                    tv.setText(HeartRatetext);
                }

            }
        }

    };

    @Override
    protected void onStop() {

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

        unregisterReceiver(receiver);
        super.onStop();
    }

    public void Done(View view)
    {

        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}