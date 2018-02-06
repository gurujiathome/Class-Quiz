package odu.handson.classquiz.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CompletedquizFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CompletedquizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedquizFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static List<QuestionDetails> question_details=new ArrayList<QuestionDetails>();
    public static List<Options> option_list=new ArrayList<Options>();
    public static List<Options> option_list1=new ArrayList<Options>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CompletedquizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CompletedquizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CompletedquizFragment newInstance(String param1, String param2) {
        CompletedquizFragment fragment = new CompletedquizFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        new RequestQuestionsService().execute(getArguments().getString("userId"), getArguments().getString("quizId"));
        return inflater.inflate(R.layout.fragment_completedquiz, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    class RequestQuestionsService extends AsyncTask<String,Integer,String>
    {
        public ProgressDialog dialog=new ProgressDialog(getActivity());
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
                    CompleteQuestionListAdapter completeQuestionListAdapter = new CompleteQuestionListAdapter(getActivity().getApplicationContext(), question_details);
                    ListView listView = (ListView) getView().findViewById(R.id.questionCompleteList);
                    listView.setAdapter(completeQuestionListAdapter);


                    // } else {
                    //   System.out.println("error");
                    //}
                }
                else
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("No Results yet");
                    alert.setMessage(jsonobj.getString("msg"));
                    alert.setCancelable(false);
                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent in=new Intent(getActivity().getApplicationContext(),DisplayquizActivity.class);
                            in.putExtra("userId",getArguments().getString("userId"));
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


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
