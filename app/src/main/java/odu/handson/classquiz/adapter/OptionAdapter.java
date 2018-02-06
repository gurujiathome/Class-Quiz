package odu.handson.classquiz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import odu.handson.classquiz.R;
import odu.handson.classquiz.model.Options;

import java.util.List;

/**
 * Created by rgudipati on 10/5/2016.
 */
public class OptionAdapter extends BaseAdapter {
    private final Context context;
    private List<Options> option_list;
    public OptionAdapter(Context context, List<Options> options) {
        this.context=context;
        this.option_list=options;
    }

    @Override
    public int getCount() {
        return option_list.size();
    }

    @Override
    public Options getItem(int position) {
        return option_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Options data=option_list.get(position);
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.option,null);
        TextView name= (TextView) rowView.findViewById(R.id.optionName);
        TextView id= (TextView) rowView.findViewById(R.id.optionId);
        name.setText(data.getOption());
        id.setText(Integer.toString(data.getOptionId()));
        id.setVisibility(View.INVISIBLE);
        return rowView;
    }
}
