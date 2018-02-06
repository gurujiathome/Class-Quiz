package odu.handson.classquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import odu.handson.classquiz.R;
import odu.handson.classquiz.model.QuizDetails;

import java.util.List;

/**
 * Created by rgudipati on 10/4/2016.
 */
public class ListAdapter extends BaseAdapter
{
    private final Context context;

    private List<QuizDetails> quizList;

    public ListAdapter(Context context, List<QuizDetails> quizList)
    {
        this.context=context;
        this.quizList=quizList;
    }


    @Override
    public int getCount() {
        return quizList.size();
    }

    @Override
    public QuizDetails getItem(int position) {
        return quizList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        QuizDetails data=quizList.get(position);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.quiz_list,null);
        TextView quizName= (TextView) rowView.findViewById(R.id.quiz_name);
        TextView quizId= (TextView) rowView.findViewById(R.id.idquiz);
       // Log.i("quiz data",""+data.getQuiz_name()+""+data.getQuiz_id());

        quizName.setText(data.getName());
        quizId.setText(Integer.toString(data.getQuizId()));
        quizId.setVisibility(View.GONE);
        return rowView;
    }

}
