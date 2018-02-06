package odu.handson.classquiz.UI;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidplot.pie.PieChart;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import odu.handson.classquiz.R;
import odu.handson.classquiz.model.QuestionResponse;
import odu.handson.classquiz.utility.ServiceCaller;


import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReportsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReportsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
public PieChart pie;
    private XYPlot barChart;
    private Segment s1;
    private Segment s2;

private List<QuestionResponse.ReportData.Ranges> range;
    public ReportsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportsFragment newInstance(String param1, String param2) {
        ReportsFragment fragment = new ReportsFragment();
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
        System.out.println("reports fragment");
       // Toast.makeText(getActivity().getApplicationContext(),"hello"+getArguments().getString("userId")+"--"+getArguments().getString("quizId"),Toast.LENGTH_SHORT).show();

        new RequestQuestionsService().execute(getArguments().getString("userId"), getArguments().getString("quizId"));
        return inflater.inflate(R.layout.fragment_reports, container, false);
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
            System.out.println("Result in reports fragment is "+result);
            ObjectMapper mapper=new ObjectMapper();

            try {
                JSONObject jsonobj=new JSONObject(result);
                if(jsonobj.getString("statusCode").equals("200")) {
                    QuestionResponse qres = mapper.readValue(result, QuestionResponse.class);
                    TextView tv = (TextView) getView().findViewById(R.id.reportData);
                    int totalCorrect = qres.getData().getReportData().getCorrectAnswers();
                    int total = qres.getData().getReportData().getTotalQuestions();
                    int totalWrong = total - totalCorrect;
                    float percentCorrect = (totalCorrect * 100) / total;
                    float percentWrong = 100 - percentCorrect;
                    tv.setText("Number of questions correct : " + totalCorrect + "\nNumber of questions wrong : " + totalWrong);
                    System.out.println("" + percentCorrect + "--" + percentWrong);
                    pie = (PieChart) getView().findViewById(R.id.pieChart);

                    s1 = new Segment("Correct Answers :" + percentCorrect + "%", percentCorrect);
                    s2 = new Segment("Wrong Answers :" + percentWrong + "%", percentWrong);


                    SegmentFormatter sf1 = new SegmentFormatter(Color.parseColor("#FF63B260"));
                    //sf1.configure(getActivity().getApplicationContext(),R.xml.pie_segment_formatter1);
                    SegmentFormatter sf2 = new SegmentFormatter(Color.parseColor("#FFE34646"));

                    pie.addSegment(s1, sf1);
                    pie.addSegment(s2, sf2);
                    pie.setTitle("Percentage of marks in this quiz");

                    pie.getBackgroundPaint().setColor(Color.TRANSPARENT);

                    range = qres.getData().getReportData().getRanges();
                    ArrayList<Number> count = new ArrayList<Number>();
                    for (QuestionResponse.ReportData.Ranges r : range) {
                        count.add(r.getStudentCount());
                    }

                    XYSeries wins = new SimpleXYSeries(count, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Student Count");

                    barChart = (XYPlot) getView().findViewById(R.id.barChart);
                    BarFormatter bf = new BarFormatter(Color.parseColor("#FF5573D6"), Color.WHITE);
                    barChart.setTitle("Class Percentage");
                    barChart.setDomainLabel(qres.getData().getReportData().getxLabel());
                    barChart.setRangeLabel(qres.getData().getReportData().getyLabel());
                    barChart.getGraphWidget().getDomainOriginTickLabelPaint().setTextSize(20);
                    barChart.getGraphWidget().getRangeOriginTickLabelPaint().setTextSize(20);
                    barChart.getGraphWidget().getDomainTickLabelPaint().setTextSize(20);
                    barChart.getGraphWidget().getRangeTickLabelPaint().setTextSize(20);
                    barChart.setPlotPaddingTop(50);
                    barChart.setPlotPaddingBottom(200);
                    barChart.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1);
                    barChart.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 1);
                    barChart.getGraphWidget().setDomainValueFormat(new GraphXLabelFormat());
                    barChart.getGraphWidget().setRangeValueFormat(new DecimalFormat("#"));
                    barChart.addSeries(wins, bf);
                    BarRenderer renderer = (BarRenderer) barChart.getRenderer(BarRenderer.class);
                    renderer.setBarWidth(50);
                    barChart.getBackgroundPaint().setColor(Color.TRANSPARENT);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialog.cancel();

        }
    }

    private class GraphXLabelFormat extends Format {

        @Override
        public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            int parsedInt = Math.round(Float.parseFloat(object.toString()));
            String label=range.get(parsedInt).getRangeStart()+"-"+range.get(parsedInt).getRangeEnd();
            //range.get(parsedInt).getRangeStart()+"-"+
            return buffer.append(label);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
        //    return java.util.Arrays.asList(xLabels).indexOf(string);
            return null;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
