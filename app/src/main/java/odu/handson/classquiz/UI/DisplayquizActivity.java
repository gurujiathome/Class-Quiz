package odu.handson.classquiz.UI;

import android.Manifest;
import android.app.AlertDialog;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import odu.handson.classquiz.R;
import odu.handson.classquiz.adapter.ListAdapter;
import odu.handson.classquiz.model.QuizDetails;
import odu.handson.classquiz.model.ResponseObject;
import odu.handson.classquiz.utility.ServiceCaller;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DisplayquizActivity extends AppCompatActivity {
    List<QuizDetails> complquiz_details;
    List<QuizDetails> uncomplquiz_details;
    public String userId;
    ListView listView;
    ListView listViewComp;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayquiz);
        complquiz_details=new ArrayList<QuizDetails>();
        uncomplquiz_details=new ArrayList<QuizDetails>();
       // Toast.makeText(getApplicationContext(),""+getIntent().getStringExtra("userId"),Toast.LENGTH_SHORT).show();

//        userId=getIntent().getStringExtra("userId");
//        new RequestQuizService().execute(getIntent().getStringExtra("userId"));
        listView= (ListView) findViewById(R.id.quizList);
        listViewComp= (ListView) findViewById(R.id.quizListCompleted);
//        TextView textView = new TextView(getApplicationContext());
//        TextView textView1=new TextView(getApplicationContext());
//        textView.setText("Available Quiz(s)");
//        textView.setTextColor(Color.BLACK);
//        textView1.setText("Completed Quiz(s)");
//        textView1.setTextColor(Color.BLACK);
//        listView.addHeaderView(textView);
//        listViewComp.addHeaderView(textView1);
    }

    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(in);
    }
@Override
protected void onResume()
{
    super.onResume();
 //   Toast.makeText(getApplicationContext(),"in Resume",Toast.LENGTH_SHORT).show();
    userId=getIntent().getStringExtra("userId");
  // sharedPreferences=getPreferences(Context.MODE_PRIVATE);
//    userId=sharedPreferences.getString("userId","0");
    System.out.println("userid in pref --"+getPreferences(Context.MODE_PRIVATE).getString("userId","0"));
    complquiz_details=new ArrayList<QuizDetails>();
    uncomplquiz_details=new ArrayList<QuizDetails>();

    if(isNetworkAvailable())
        new RequestQuizService().execute(getIntent().getStringExtra("userId"));
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

    class RequestQuizService extends AsyncTask<String, Integer, String> {
        public ProgressDialog dialog=new ProgressDialog(DisplayquizActivity.this);
        @Override
        protected void onPreExecute() {
        dialog.setMessage("loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return ServiceCaller.startGetService("https://qav2.cs.odu.edu/mobile/get_quizzes.php","userId",params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(" MY requests, Response from odu: " + result);
            ObjectMapper mapper=new ObjectMapper();
            try {
                ResponseObject resObj=mapper.readValue(result,ResponseObject.class);
                System.out.println(resObj.toString()+resObj.getStatusCode()+resObj.getData().getQuizzes());
               if( resObj.getStatusCode()==200)
               {
                   List<QuizDetails> qdl=resObj.getData().getQuizzes();
                   HashMap<Integer, QuizDetails> hashMap = new HashMap<Integer, QuizDetails>();
                   for(QuizDetails qd:qdl) {
                       hashMap.put(qd.getQuizId(), qd);
                   }
                    //System.out.println(hashMap.size());
                   qdl.clear();
                  for(int key:hashMap.keySet())
                      qdl.add(hashMap.get(key));
                   for(QuizDetails qd:qdl)
                   {
                       System.out.println("quiz status"+qd.getStatus());
                        //System.out.println(qd.equals(qd));
                       if(qd.getStatus().equals("COMPLETED"))
                       {
                           complquiz_details.add(qd);
                       }
                       else if(qd.getStatus().equals("ENABLED"))
                           uncomplquiz_details.add(qd);
                   }
               }

                ListAdapter mListAdapter=new ListAdapter(getApplicationContext(),complquiz_details);
                ListAdapter mListAdapterun=new ListAdapter(getApplicationContext(),uncomplquiz_details);

                listView.setAdapter(mListAdapterun);
                listViewComp.setAdapter(mListAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                      //  Toast.makeText(getApplicationContext(),""+uncomplquiz_details.get(position-1).getQuizId(),Toast.LENGTH_LONG).show();
                        AlertDialog.Builder alert = new AlertDialog.Builder(DisplayquizActivity.this);
                        alert.setTitle("Alert");
                        alert.setMessage("Do you want to start quiz '"+uncomplquiz_details.get(position).getName()+"'?");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

//                                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                                if (ActivityCompat.checkSelfPermission(DisplayquizActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DisplayquizActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                    // TODO: Consider calling
//                                    //    ActivityCompat#requestPermissions
//                                    // here to request the missing permissions, and then overriding
//                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                    //                                          int[] grantResults)
//                                    // to handle the case where the user grants the permission. See the documentation
//                                    // for ActivityCompat#requestPermissions for more details.
//                                    return;
//                                }
//                                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                                double longitude = location.getLongitude();
//                                double latitude = location.getLatitude();
//                                System.out.println("coordinates"+longitude+"----"+latitude);
//                                calculateDistance(longitude,latitude,-76.303752,36.887363);
                                Intent in=new Intent(getApplicationContext(),DisplayquestionActivity.class);
                                in.putExtra("userId",userId);
                                in.putExtra("quizId",Integer.toString(uncomplquiz_details.get(position).getQuizId()));
                                startActivity(in);
                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = alert.create();
                        alertDialog.show();

                    }
                });
                listViewComp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //   Toast.makeText(getApplicationContext(),""+complquiz_details.get(position).getQuizId()+""+position,Toast.LENGTH_LONG).show();
                       // Intent in=new Intent(getApplicationContext(),DisplaycompletequizActivity.class);
                        Intent in=new Intent(getApplicationContext(), CompletedquizActivity.class);
                        in.putExtra("userId",userId);
                        in.putExtra("quizId",Integer.toString(complquiz_details.get(position).getQuizId()));
                        startActivity(in);
                    }
                });



            } catch (IOException e) {
                e.printStackTrace();
            }

            dialog.cancel();

        }
    }
    public double calculateDistance(double lat1, double lat2, double lon1,double lon2)
    {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        System.out.println("distance---"+distance);
        return distance;
    }


}
