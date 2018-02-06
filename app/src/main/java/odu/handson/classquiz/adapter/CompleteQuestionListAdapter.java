package odu.handson.classquiz.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import odu.handson.classquiz.R;
import odu.handson.classquiz.model.Options;
import odu.handson.classquiz.model.QuestionDetails;

import java.util.List;

/**
 * Created by rgudipati on 10/5/2016.
 */
public class CompleteQuestionListAdapter extends BaseAdapter {
    private final Context context;
    private List<QuestionDetails> questions;
    static int qno=1;
    public CompleteQuestionListAdapter(Context context, List<QuestionDetails> question_details) {
        this.context=context;
        this.questions=question_details;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public QuestionDetails getItem(int position) {
        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        QuestionDetails data=questions.get(position);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.completedquestions_list,null);
        TextView qname= (TextView) rowView.findViewById(R.id.cqname);
        TextView qnumber= (TextView) rowView.findViewById(R.id.cqnumber);
        TextView qnotxt= (TextView) rowView.findViewById(R.id.cqno);
        qnotxt.setText("Question "+(getItemId(position)+1));
        qnotxt.setTextColor(Color.BLACK);
        qname.setText(data.getQuestion());
        qnumber.setText(Integer.toString(data.getQuestionId()));
        qnumber.setVisibility(View.INVISIBLE);
        String correct=data.getCorrectOptionId();
        String stu_ans=data.getStudentAnswerOptionId();
//        OptionAdapter mOptionAdapter=new OptionAdapter(context,data.getOptions());
//        ListView listView= (ListView) rowView.findViewById(R.id.optionList);
//        listView.setAdapter(mOptionAdapter);

        for(Options option:data.getOptions())
        {
            LinearLayout mLinearlayout= (LinearLayout) rowView.findViewById(R.id.completedLinearLayout);
            TextView textView=new TextView(context);
            textView.setTextColor(Color.GREEN);
            textView.setText(option.getOption());
            textView.setId(option.getOptionId());
            textView.setPadding(10,0,0,0);

            if((Integer.parseInt(stu_ans)==Integer.parseInt(correct) && Integer.parseInt(correct)==option.getOptionId()) || Integer.parseInt(correct)==option.getOptionId() )
                textView.setTextColor(Color.GREEN);
            else if(Integer.parseInt(stu_ans)==option.getOptionId())
                textView.setTextColor(Color.RED);
            else
                textView.setTextColor(Color.BLACK);

            mLinearlayout.addView(textView);
        }
        qno++;
        return rowView;
    }
}
