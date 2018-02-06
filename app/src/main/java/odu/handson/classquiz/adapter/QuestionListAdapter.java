package odu.handson.classquiz.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import odu.handson.classquiz.R;
import odu.handson.classquiz.UI.DisplayquestionActivity;

import odu.handson.classquiz.model.Options;



import java.util.List;

import odu.handson.classquiz.model.ActiveQuestionDetails.DataActive.Question;

/**
 * Created by rgudipati on 10/5/2016.
 */
public class QuestionListAdapter extends BaseAdapter {

    private final Context context;
    private List<Question> quesList;
    public static int qno=1;
    public QuestionListAdapter(Context context, List<Question> question_details) {
        this.context=context;
        this.quesList=question_details;
    }

    @Override
    public int getCount() {
        return quesList.size();
    }

    @Override
    public Question getItem(int position) {
        return quesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Question data=quesList.get(position);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.questions_list,null);
        TextView qname= (TextView) rowView.findViewById(R.id.qname);
        TextView qnumber= (TextView) rowView.findViewById(R.id.qnumber);
        TextView qnotxt= (TextView) rowView.findViewById(R.id.qno);
        qnotxt.setText("Question "+(getItemId(position)+1));
        qnotxt.setTextColor(Color.BLACK);
        qname.setText(data.getQuestion());
        qname.setTextColor(Color.BLACK);
        qnumber.setText(Integer.toString(data.getQuestionId()));
        qnumber.setVisibility(View.INVISIBLE);
        RadioGroup radioGroup=(RadioGroup)rowView.findViewById(R.id.answerOptions);
        final int quesid=data.getQuestionId();


        for (Options option:data.getOptions())
        {
            final int optid=option.getOptionId();
            RadioButton rb=new RadioButton(context);
            rb.setText(option.getOption());
            rb.setTextColor(Color.BLACK);
            System.out.println("in adapter--"+DisplayquestionActivity.quesOptionMap.toString()+"--"+DisplayquestionActivity.quesOptionMap.get(quesid+"")+"---"+quesid);
           // System.out.println("if consition---"+DisplayquestionActivity.quesOptionMap.get(quesid+"")+"--"+DisplayquestionActivity.quesOptionMap.get(quesid+"").equals(optid+"")+"--"+optid);
            if( !(DisplayquestionActivity.quesOptionMap.get(quesid+"")==null) && DisplayquestionActivity.quesOptionMap.get(quesid+"").equals(optid+"") )
            {

                rb.setChecked(true);

            }
           // rb.setHighlightColor(Color.BLUE);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{

                            new int[]{-android.R.attr.state_enabled}, //disabled
                            new int[]{android.R.attr.state_enabled} //enabled
                    },
                    new int[] {

                            Color.BLACK //disabled
                            ,Color.BLUE //enabled

                    }
            );


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                rb.setButtonTintList(colorStateList);
            }

            rb.setId(option.getOptionId());
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                   // Toast.makeText(context,"rb id:"+checkedId+"ques id"+quesid,Toast.LENGTH_SHORT).show();
                    DisplayquestionActivity.mapQuesAns(quesid,checkedId);
                }
            });
            radioGroup.addView(rb);
        }


        qno++;
        return rowView;
    }
}
