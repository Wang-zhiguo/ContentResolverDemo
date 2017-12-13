package cn.androidstudy.contentresolverdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/9.
 */

public class PersonAdapter extends BaseAdapter {
    private ArrayList<Person> persons = null;//数据
    private Context context = null;//加载布局

    public PersonAdapter(Context context,ArrayList<Person> persons){
        this.context = context;
        this.persons = persons;
    }
    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int position) {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    //优化后的写法
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            holder = new ViewHolder();
            holder.tv_id = (TextView) convertView.findViewById(R.id.textView);
            holder.tv_name = (TextView) convertView.findViewById(R.id.textView2);
            holder.tv_age = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Person person = persons.get(position);
        holder.tv_id.setText(person.getId());
        holder.tv_name.setText(person.getName());
        holder.tv_age.setText(person.getAge());
        return convertView;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    class ViewHolder{
        public TextView tv_id;
        public TextView tv_name;
        public TextView tv_age;
    }
}
