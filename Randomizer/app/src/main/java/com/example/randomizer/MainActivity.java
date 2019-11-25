package com.example.randomizer;


/** Jani L - September 2019.
 *
 * Main idea is to generate code from button and send it to the email address from user input and show
 * notification in the process.
 *
 * The program asks user to fill email address on specific fields and confirm them. If the following
 * fields do not match toast appears and asks user to check spelling.
 *
 * At this point we should click button "Generate code" to generate secret 6 digit code
 * (numbers and letters including) that appears on password field below "Generate code" button.
 *
 * If the email input fields match user can proceed to the next page by clicking "Next" button.
 * Program shows animated circle that sends message and also toasts user input.
 * Example "Sending authentication code to: MyEmailAddress2019@gmail.com".
 *
 * User lands on the last page that shows text field: "Email and notification has been sent".
 * There will be also notification that tells code has been sent from the program.
 *
 * If you find anything that could be improved or done better, please contact me at git.laakso@gmail.com.
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//My imports
//Notifications
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;

//Random
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //ClASS VARIABLES

    //Declaring Notifications
    public static final int NOTIFICATION_ID = 222;
    public static final String CHANNEL_ID = "Using only one channel that shows up when email fields match";
    public static final String CHANNEL_NAME = "Channel name";
    NotificationManagerCompat notificationManagerCompat;
    Notification notification;

    //Declaring generated myRandom that we can call by generatedString
    private String generatedString;
    //Declaring EditText
    private EditText confirmEField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the views
        //Notifications
        createNotificationChannel();
        notification = createNotification();

        notificationManagerCompat = NotificationManagerCompat.from(this);
        //Send email part. Reading confirmEField
        confirmEField = (EditText) findViewById(R.id.confirmEField);
    }

    //Creating channel for notifications
    private void createNotificationChannel() {
        //Check the SDK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Let's register our notification channel and set the priority level that is HIGH at the moment
            NotificationChannel notificationChannel = new NotificationChannel
                    (CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            //Enable lights & vibration
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            //Define the lights color
            notificationChannel.setLightColor(Color.BLUE);
            //Define vibration pattern
            notificationChannel.setVibrationPattern(new long[] { 500, 250, 250, 500 });
            //Define does our text appear on locked screen. At the moment it does
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }


    //Declaring what does our notification display
    private Notification createNotification() {

        NotificationCompat.Builder myBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        //Show notification title / Using "Big text" because without
        // "big text" our body section would disappear after certain point / also we do set small icon on display
        // and use priority level that is HIGH
        myBuilder.setContentTitle("Authenticator 2000")
        .setStyle(new NotificationCompat.BigTextStyle().bigText
                ("Your authentication code has been sent to your email from MyApp to your email "))
        .setSmallIcon(R.drawable.bell)
        .setPriority(NotificationCompat.PRIORITY_HIGH);
        return myBuilder.build();
    }

    //Listening gRandom button and setting up our "random"
    public void gRandom(View view) {
        TextView fieldsOfDigits = findViewById(R.id.cAppearance);
        //Declaring myRandom
        String listOfChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder list = new StringBuilder();
        Random myRandom = new Random();
        while (list.length() < 6) { // Maximum length
            int indexOfChar = (int) (myRandom.nextFloat() * listOfChars.length());
            list.append(listOfChars.charAt(indexOfChar));
        }

        //Using
        generatedString = list.toString();

        //Let's test if our button works
        Log.i("ReadID", "Click recorded from generate code button");
        fieldsOfDigits.setText(generatedString);
    }

    public void confirmAndProceed(View view) {
        //Let's read users input field one and two
        EditText rFieldOne = findViewById(R.id.eField);
        EditText rFieldTwo = findViewById(R.id.confirmEField);

        //Declaring our strings
        String cEmailFieldOne = rFieldOne.getText().toString();
        String cEmailFieldTwo = rFieldTwo.getText().toString();
        String rEmail = confirmEField.getText().toString();

        //Handle toasts
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast;

        //Handle logic
        if (cEmailFieldOne.equals(cEmailFieldTwo)) {
            CharSequence text = "Sending authentication to: " + rEmail;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            //Declaring the part when notification shows up
            notificationManagerCompat.notify(NOTIFICATION_ID, notification);

            //Declaring subject message and our authentication code.
            SendMail sm = new SendMail(this, rEmail, "Your authentication code",
                    "You requested authentication code from myAuthenticator 2000." +
                            " \n Your authentication code is " + "'' " + generatedString + " ''");

            //Executing sendmail to send email
            sm.execute();
            //Forward user to the 2nd page
            goToSecondActivity();
        } else {
            toast = Toast.makeText(context, "Emails do not match", duration);
            toast.show();
        }
    }

    public void goToSecondActivity() {
        Intent myIntent = new Intent(this, SecondActivity.class);
        startActivity(myIntent);
    }
}