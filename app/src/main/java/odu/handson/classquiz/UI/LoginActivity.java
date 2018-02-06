package odu.handson.classquiz.UI;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


import odu.handson.classquiz.R;
import odu.handson.classquiz.utility.LoginServiceCaller;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Button login_button;
    String midas_id, password;
    EditText midasId, Password;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        // TODO: Move this to where you establish a user session
        logUser();


        System.out.println("data in saved state "+savedInstanceState);

        setContentView(R.layout.activity_login);
        sharedPreferences=getPreferences(Context.MODE_PRIVATE);
        midasId = (EditText) findViewById(R.id.midasId);
        Password = (EditText) findViewById(R.id.Password);
        final Switch rememberMe= (Switch) findViewById(R.id.mySwitch);
        if(!sharedPreferences.getString("midasId","0").equals("0") && !sharedPreferences.getString("password","0").equals("0"))
        {
            midasId.setText(sharedPreferences.getString("midasId","0"));
            Password.setText(sharedPreferences.getString("password","0"));
        }
        System.out.println(sharedPreferences.getString("rememberMe","0"));
        if(sharedPreferences.getString("rememberMe","0").equals("true"))
            rememberMe.setChecked(true);
        login_button = (Button) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

////remove 3 lines
//                Intent in = new Intent(getApplicationContext(), DisplayquizActivity.class);
//                in.putExtra("userId","7030");
//                startActivity(in);


                midas_id = midasId.getText().toString();
                password = Password.getText().toString();
                getQuizDetails(midas_id, password);
                //        android:text="Wingking1++"        android:text="gatki001"
                if(rememberMe.isChecked())
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("midasId",midas_id);
                    editor.putString("password",password);
                    editor.putString("rememberMe","true");
                    editor.commit();
                    System.out.println(sharedPreferences.getString("userId","0")+"--"+sharedPreferences.getString("password","0"));
                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    System.out.println(sharedPreferences.getString("midasId","0")+"--"+sharedPreferences.getString("password","0"));
                }
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                midasId = (EditText) findViewById(R.id.midasId);
                Password = (EditText) findViewById(R.id.Password);
                midas_id = midasId.getText().toString();
                password = Password.getText().toString();
                if(isChecked)
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("midasId",midas_id);
                    editor.putString("password",password);
                    editor.putString("rememberMe","true");
                    editor.commit();
                    System.out.println(sharedPreferences.getString("userId","0")+"--"+sharedPreferences.getString("password","0"));

                }
                else
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    System.out.println(sharedPreferences.getString("midasId","0")+"--"+sharedPreferences.getString("password","0"));
                }
            }
        });
        System.out.println(sharedPreferences.getString("userId","0")+"--"+sharedPreferences.getString("password","0"));
    }


    private void logUser() {
        // TODO: Use the current user's information
        // You can call any combination of these three methods
        Crashlytics.setUserIdentifier("12345");
        Crashlytics.setUserEmail("user@fabric.io");
        Crashlytics.setUserName("Test User");

    }

    public void getQuizDetails(String midas_id, String password) {
        //System.out.println("responseeeee"+ LoginServiceCaller.oduCall("https://my.odu.edu",null));
        if(isNetworkAvailable())
            new RequestService().execute(midas_id, password);
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Network Unavailable");
            builder.setMessage("Your device is currently unable to establish a network connection.\nTurn on cellular data or use Wi-Fi to access data.");
            builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });

            builder.show();
        }


    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    class RequestService extends AsyncTask<String, Integer, String> {
        public ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
         dialog.setMessage("Connecting...");
         dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return LoginServiceCaller.oduCall("https://itsapps.odu.edu/auth/", params[0], params[1]);
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(" MY requests, Response from odu: " + result);
            new RequestShibbolethService().execute(result);
            dialog.cancel();
        }
    }
    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Do you want to exit the app? ");
        builder.setPositiveButton("Stay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(LoginActivity.this);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        builder.show();
    }
    class RequestShibbolethService extends AsyncTask<String, Integer, String> {
        public ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Authenticating...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return LoginServiceCaller.shibbolethCall("https://shibboleth.odu.edu/idp/profile/SAML2/SOAP/ECP", params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

            System.out.println(" MY requests, Response from shibboleth: " + result);
            if (result.equals("200")) {
                System.out.println("success login");
                new RequestCsService().execute();

            } else if (result.equals("401")) {
                builder.setTitle("Alert");
                builder.setMessage("Invalid Username/Password");
                System.out.println("invalid user/password");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {} });
                builder.show();
            } else {
                builder.setTitle("Alert");
                builder.setMessage("error try again");
                System.out.println("error try again");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {} });
                builder.show();
            }

            dialog.cancel();
        }
    }

    class RequestCsService extends AsyncTask<String, Integer, String> {
        public ProgressDialog dialog=new ProgressDialog(LoginActivity.this);
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Signing...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return LoginServiceCaller.csCall("https://qav2.cs.odu.edu/mobile/login.php");
        }

        @Override
        protected void onPostExecute(String result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

            System.out.println(" MY requests, Response from cs: " + result);
            try {
                JSONObject json = new JSONObject(result);
                if (json.getString("statusCode").equals("200")) {
                    JSONObject innerobj=new JSONObject(json.getString("data"));
//                    MyApplication myApplication= (MyApplication) getApplicationContext();
//                    myApplication.setUserId(innerobj.getString("userId"));
                    sharedPreferences=getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("userId",innerobj.getString("userId"));
                    editor.commit();
                    System.out.println("into pref "+innerobj.getString("userId"));
                    System.out.println(sharedPreferences.getString("userId","0"));
                    Intent in = new Intent(getApplicationContext(), DisplayquizActivity.class);
                    in.putExtra("userId",innerobj.getString("userId"));

                    startActivity(in);
                }
                else
                {
                    builder.setTitle("Alert");
                    builder.setMessage("Invalid User");
                    System.out.println("Invalid User");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {} });
                    builder.show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        dialog.cancel();
        }

    }



}