package odu.handson.classquiz.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;


import odu.handson.classquiz.R;
import odu.handson.classquiz.adapter.CompleteQuestionListAdapter;
import odu.handson.classquiz.model.Options;
import odu.handson.classquiz.model.QuestionDetails;
import odu.handson.classquiz.model.QuestionResponse;
import odu.handson.classquiz.utility.ServiceCaller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplaycompletequizActivity extends AppCompatActivity {
    public static List<QuestionDetails> question_details=new ArrayList<QuestionDetails>();
    public static List<Options> option_list=new ArrayList<Options>();
    public static List<Options> option_list1=new ArrayList<Options>();
    String quizId,userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaycompletequiz);
//        CompleteQuestionListAdapter completeQuestionListAdapter=new CompleteQuestionListAdapter(this,question_details);
//        ListView listView= (ListView) findViewById(R.id.questionCompleteList);
//        listView.setAdapter(completeQuestionListAdapter);
//        MyApplication myApplication= (MyApplication) getApplicationContext();
//        String userId=myApplication.getUserId();
//
//        TabLayout tabLayout=new TabLayout(getApplicationContext());
//        tabLayout.addTab(tabLayout.newTab().setText("Quiz"));
//        tabLayout.addTab(tabLayout.newTab().setText("Reports"));

        quizId=getIntent().getStringExtra("quizId");
        userId=getIntent().getStringExtra("userId");
        new RequestQuestionsService().execute(getIntent().getStringExtra("userId"), getIntent().getStringExtra("quizId"));
      //  new RequestQuestionsService().execute(userId, getIntent().getStringExtra("quizId"));
    }
    @Override
    public void onBackPressed()
    {
        Intent in=new Intent(getApplicationContext(),DisplayquizActivity.class);
        in.putExtra("userId",getIntent().getStringExtra("userId"));
        startActivity(in);
    }
    class RequestQuestionsService extends AsyncTask<String,Integer,String>
    {
        public ProgressDialog dialog=new ProgressDialog(DisplaycompletequizActivity.this);
        @Override
        protected void onPreExecute() {
            dialog.setMessage("loading...");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            return ServiceCaller.startGetQuestionService("https://qav2.cs.odu.edu/mobile/get_quiz_question_answers.php","userId",params[0],"quizId",params[1]);
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println("Result is "+result);

            try
            {
                JSONObject jsonobj=new JSONObject(result);
                if(jsonobj.getString("statusCode").equals("200")) {
                    ObjectMapper mapper = new ObjectMapper();
                    QuestionResponse resObj = mapper.readValue(result, QuestionResponse.class);
                    System.out.println(resObj.toString() + resObj.getStatusCode() + resObj.getData().getAnswers());
                   // if (resObj.getStatusCode() == 200) {
                        question_details = resObj.getData().getAnswers();
                        CompleteQuestionListAdapter completeQuestionListAdapter = new CompleteQuestionListAdapter(getApplicationContext(), question_details);
                        ListView listView = (ListView) findViewById(R.id.questionCompleteList);
                        listView.setAdapter(completeQuestionListAdapter);


                   // } else {
                     //   System.out.println("error");
                    //}
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(DisplaycompletequizActivity.this);
                    alert.setTitle("No Results yet");
                    alert.setMessage(jsonobj.getString("msg"));
                    alert.setCancelable(false);
                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in=new Intent(getApplicationContext(),DisplayquizActivity.class);
                            in.putExtra("userId",getIntent().getStringExtra("userId"));
                            startActivity(in);
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            dialog.cancel();
        }
    }
}
