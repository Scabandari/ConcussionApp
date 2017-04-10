package com.example.concussionapp;
//test
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class Chronometer_Heart_Rate_Activity extends AppCompatActivity {

    private final ToneGenerator tone = new ToneGenerator(AudioManager.STREAM_NOTIFICATION,200);
    private static final String TAG = "ChronometerActivity";


    //for sensor:
    BluetoothAdapter adapter = null;
    BTClient _bt;
    ZephyrProtocol _protocol;
    NewConnectedListener _NConnListener;

    private BroadcastReceiver receiver;
    private final int HEART_RATE = 0x100;
  //  private String maxHR;       //max and min heart rates taken from get extra
 //   private String minHR;
    private int maxHeart;
    private int minHeart;
    private int exerciseTime;         //time in minutes, will start count down clock w/ this value

    private List<Integer> HRSamples; //gets a sample every 3 second
    private List<Integer> SamplesPerMinute;  //gets avg of HRSamples
    private boolean grabSample;      //Sample HR every 3 seconds, every 3 sec this should be reset

    //this Handler object will be used in obtaining HR samples, it needs an int delay in Millis
 //   private Handler handleForToasts;
    private Handler handleForMinutes;
    private Handler handle;
//    private int delayForToastMsg;
    private int delayForMinutes;
    private int delay;
    private String dataFromSurvey; //sent from activity setup via intent
    private String HRStringdata;  //this is just to put the data to log to see what we're getting
    private int heartRateData;  // was declared below but i want my my handle to have access to
    // this value
    private boolean showToast;
    private boolean canStartTimer;
    private boolean fromPause; // if the CountDownTimer is being started again after a pause
   // private String allDataReadyForEmail;  // = dataFromSurvey + minuteAverage from heart rate

    TextView countDownTime;
    private CountDownTimer countDownT;
    private long millisLeft; //the number of millis left for CountDownTimer
    private long timeForToast;
    private long timeForSound;

    private boolean connected;

    private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer__heart__rate_);

        currentApiVersion = Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {
            getWindow().getDecorView().setSystemUiVisibility(flags);

            //Code handling the press of volume up and down, without it
            //when pressed, navigation bar show up and doesnt hide

            final View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
            {
                @Override
                public void onSystemUiVisibilityChange(int visibility)
                {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                    {
                        decorView.setSystemUiVisibility(flags);
                    }
                }

            });
        }








        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        if (toolbar != null)
        {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        connected = false;
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



        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter4 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        this.registerReceiver(receiver, filter3);
        this.registerReceiver(receiver, filter4);


  //      allDataReadyForEmail = "";
        //not sure if i need to initialize millisLeft
        countDownTime = (TextView) findViewById(R.id.countDownTimer);


   //     Log.i(TAG, "Received data from setup activity: " +  dataFromSurvey);
  //      Log.i(TAG, "Received data from setup activity: " + dataFromSurvey);
//        Log.i(TAG, "Received data from setup activity: " + dataFromSurvey);
        fromPause = false;
        canStartTimer = true;
        //       Bundle bundle = intent.getExtras();
        Intent intent = getIntent();
        maxHeart = intent.getIntExtra("maxHeartRate", 120); //grab max and min HR from previous activity
        minHeart = intent.getIntExtra("minHeartRate", 60);
        exerciseTime = intent.getIntExtra("exerciseTime", 9);
        exerciseTime *= 60000; // 1 min * 60 sec * 1000 milli/sed
     //   millisLeft = exerciseTime;
        showToast = false;

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

        StartButton = (Button) findViewById(R.id.start_Button);
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
        String ErrorText = "Not Connected to HxM ! !";
        tv.setText(ErrorText);

        Button btnConnect = (Button) findViewById(Connect);
        if (btnConnect != null) {

            btnConnect.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    if (!connected) {
                      //  String BhMacID = "00:07:80:0E:B1:0C";
                        String BhMacID = SharedData.MAC;
                        //if the timer is already running
                        if (!canStartTimer) {
                            showToast = true; //allow show toast msg
                        }
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
					/*Reset the global variables*/
                        TextView tv1 = (EditText) findViewById(R.id.ActualHeartRate);
                        tv1.setText("000");
                        showToast = false;
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

                        if(!canStartTimer) {
                            countDownT.cancel();
                            canStartTimer = true;
                            fromPause = true;
                            showToast = false;
                        }

                    }
                }
            });
        }

        timeForToast = System.currentTimeMillis() + 7000;
        timeForSound = System.currentTimeMillis() + 7000;


    } // onCreate() ENDS HERE

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()){
            case R.id.Switch1:
                //what happens when menu item is pressed

                AlertDialog.Builder aboutAlert = new AlertDialog.Builder(this);
                aboutAlert.setMessage(" NoCussion is a tool designed for any member of the medical team who is treating athletes recovering from a concussion.  The application gives information on concussion and tips for a better recovery process. NoCussion guide the athletes throughout their recovery. The user will have her/his heart beat monitor during their training and will be asked to rate their concussion symptom after a physical effort. All data will be sent to the athlete’s care provider by email.  NoCussion facilitates the communication between the athlete and the care provider during the recovery process.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("About Us").create();
                aboutAlert.show();
                return true;
            case R.id.Switch2:

                AlertDialog.Builder helpAlert = new AlertDialog.Builder(this);
                helpAlert.setMessage("This is your Exercise page: In here, you can connect your sensor or disconnect it. Once you are ready to train just press on the start button and you are on the go. If you need to stop for any reason you can use the stop button to do so or you can even reset your timer at anytime.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setTitle("Help").create();
                helpAlert.show();
                return true;
            case R.id.Switch3:
                Intent logout = new Intent(Chronometer_Heart_Rate_Activity.this, MainActivity.class);
                startActivity(logout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onStart() {
        super.onStart();
    //    showToast = true;

    }

    protected void onStop() {

        showToast = false;
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

    protected void onPause() {

        showToast = false;
        if(countDownT != null) {
            try {
                countDownT.cancel();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        canStartTimer = true;
        fromPause = true;
        super.onPause();
    }

  /*  protected void onResume() {
        super.onResume();
        countDownT= new CountDownTimer(millisLeft, 1000) {
            //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
            @Override
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
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
                Intent newIntent = new Intent(getApplicationContext(), PostSurveyy.class);
                newIntent.putExtra("heartData", minuteAvg);
                startActivity(newIntent);
            }
        };
        countDownT.start();

        canStartTimer = false;

    }
*/

    View.OnClickListener mStopListener= new View.OnClickListener() {
        public void onClick(View v) {

            //!canStartTimer means the timer is running, when paused this should be false
            if(!canStartTimer) {
                countDownT.cancel();
                canStartTimer = true;
                fromPause = true;
                showToast = false;
            }
        }
    };

    View.OnClickListener mResetListener  = new View.OnClickListener() {
        public void onClick(View v) {
            if (connected) {
                SamplesPerMinute.clear();
                showToast = true;
                //this if executed if start button pressed from a condition of being paused
                //starts with amount of time = millisLeft
                if (canStartTimer && fromPause) {

                    countDownT = new CountDownTimer(exerciseTime, 1000) {
                        //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millisLeft = millisUntilFinished;
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
                            //       minuteAvg += "]";
                            //         SharedData.data += "Average heart rate for each minute of exercise time: \n" +
                            //            minuteAvg;
                            SharedData.dataArray[1] = "Average heart rate for each minute of exercise time: \n[" +
                                    minuteAvg + "]\n\nHow they rated their symptoms on a scale from 1-5. 1 not at all, 5 yes a lot.\n\n";
                            for (int i = 0; i < 5; i++) { //just for testing
                                Log.i(TAG, "Finished. Averages are" + minuteAvg);
                            }
                            Intent newIntent = new Intent(getApplicationContext(), Questionaire.class);
                            startActivity(newIntent);
                        }
                    };
                    countDownT.start();

                    canStartTimer = false;
                }
                //if the timer is already running
                else if (!canStartTimer) {
                    countDownT.cancel();

                    countDownT = new CountDownTimer(exerciseTime, 1000) {
                        //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millisLeft = millisUntilFinished;
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

                            SharedData.dataArray[1] = "Average heart rate for each minute of exercise time: \n[" +
                                    minuteAvg + "]\n\nHow they rated their symptoms on a scale from 1-5. 1 not at all, 5 yes a lot.\n\n";

                            Intent newIntent = new Intent(getApplicationContext(), Questionaire.class);
                            startActivity(newIntent);
                        }
                    };
                    countDownT.start();

                    canStartTimer = false;
                }
            }
        }
    };

    View.OnClickListener mStartListener = new View.OnClickListener() {
        public void onClick(View v) {

            if (connected) {
                showToast = true;
                //this if executed if start button pressed from a condition of being paused
                //starts with amount of time = millisLeft
                if (canStartTimer && fromPause) {
                    Log.i(TAG, "STARTED TIMER FROM ON PAUSE");
                    Log.i(TAG, "STARTED TIMER FROM ON PAUSE");
                    Log.i(TAG, "STARTED TIMER FROM ON PAUSE");
                    countDownT = new CountDownTimer(millisLeft, 1000) {

                        //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millisLeft = millisUntilFinished;
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

                            SharedData.dataArray[1] = "Average heart rate for each minute of exercise time: \n[" +
                                    minuteAvg + "]\n\nHow they rated their symptoms on a scale from 1-5. 1 not at all, 5 yes a lot.\n\n";
                            Intent newIntent = new Intent(getApplicationContext(), Questionaire.class);

                            startActivity(newIntent);
                        }
                    };
                    countDownT.start();

                    canStartTimer = false;
                }
                //this else if executes when the timer is first started, ie not from a paused condition
                else if (canStartTimer) {
                    showToast = true;
                    countDownT = new CountDownTimer(exerciseTime, 1000) {
                        //ref from here: http://androidbite.blogspot.ca/2012/11/android-count-down-timer-example.html
                        @Override
                        public void onTick(long millisUntilFinished) {
                            millisLeft = millisUntilFinished;
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
                            for (int i = 0; i < 5; i++) { //just for testing
                                Log.i(TAG, "Finished. Averages are" + minuteAvg);
                            }

                            SharedData.dataArray[1] = "Average heart rate for each minute of exercise time: \n[" +
                                    minuteAvg + "]\n\nHow they rated their symptoms on a scale from 1-5. 1 not at all, 5 yes a lot.\n\n";
                            Intent newIntent = new Intent(getApplicationContext(), Questionaire.class);
                            startActivity(newIntent);
                        }
                    };
                    countDownT.start();

                    canStartTimer = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "You must be connected to the sensor", Toast.LENGTH_LONG).show();
            }
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

            if (msg.what == HEART_RATE) {
                String HeartRatetext = msg.getData().getString("HeartRate");
                heartRateData = parseInt(HeartRatetext);
                //grabSample is set to True every 3 sec in handle's  run() definition
               //!canStartTimer means clock is running
                if (grabSample && heartRateData != 0 && !canStartTimer) {
                    grabSample = false;
                    HRSamples.add(heartRateData);
                    HRStringdata += " " + String.valueOf(heartRateData);
                    Log.i(TAG, "Logging HR data: " + HRStringdata);
                }
                if (heartRateData > maxHeart && System.currentTimeMillis() > timeForToast) {
                    Log.i(TAG, " Heart rate data is above max");
               //     if(showToast) {
                        Toast.makeText(getApplicationContext(), "Heart rate too high.", Toast.LENGTH_LONG).show();
                        if(System.currentTimeMillis() > timeForSound) {
                            //play sound
                            tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                            timeForSound = System.currentTimeMillis() + 10000;
                        }
                        timeForToast = System.currentTimeMillis() + 5000;
             //       }

                } else if (heartRateData < minHeart && System.currentTimeMillis() > timeForToast) {
                    Log.i(TAG, " Heart rate data is below max");
                //    if(showToast) {
                    if(System.currentTimeMillis() > timeForSound) {
                        //play sound
                        tone.startTone(ToneGenerator.TONE_PROP_BEEP);
                        timeForSound = System.currentTimeMillis() + 10000;
                    }
                        Toast.makeText(getApplicationContext(), "Heart rate too low.", Toast.LENGTH_LONG).show();
                        timeForToast = System.currentTimeMillis() + 5000;
             //       }

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