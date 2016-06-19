package com.llk.weather.ui.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.llk.weather.R;
import com.llk.weather.model.Today;

import java.util.List;

/**
 * Created by King on 2016/6/12.
 */
public class CityManagerAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private List<Today> data;
    private LayoutInflater layoutInflater;

    private Callback callback;

    public CityManagerAdapter(Context context ,List<Today> data,Callback callback){
        this.context = context;
        this.data = data;
        this.callback = callback;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_city_manager,null);
            holder = new ViewHolder();

            holder.city = (TextView) convertView.findViewById(R.id.textView_city);
            holder.delete = (ImageButton) convertView.findViewById(R.id.imageButton_delete);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.city.setText(data.get(position).getCity());
        holder.delete.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        callback.click(v);
    }

    public class ViewHolder{

        private TextView city;
        private ImageButton delete;

    }
}
