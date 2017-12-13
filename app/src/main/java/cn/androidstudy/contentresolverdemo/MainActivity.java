package cn.androidstudy.contentresolverdemo;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String AUTHORITY = "cn.androidstudy.course12";
    private static final Uri PERSON_ALL_URI = Uri.parse("content://" + AUTHORITY + "/person");
    private EditText et_name;
    private EditText et_age;
    private ListView listView;
    ArrayList<Person> persons;
    private PersonAdapter personAdapter;
    private String cur_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        initViews();
    }

    private void initViews() {
        et_name = (EditText)findViewById(R.id.editText);
        et_age = (EditText)findViewById(R.id.editText2);
        listView = (ListView)findViewById(R.id.listview);
        personAdapter = new PersonAdapter(this,
                persons);
        listView.setAdapter(personAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Person person = persons.get(position);
                    cur_id = person.getId();
                    et_name.setText(person.getName());
                    et_age.setText(person.getAge());
                }
            }
        });
    }

    private void initDatas() {
        persons = new ArrayList<>();
        Person person = new Person();
        person.setId("序号");
        person.setName("姓名");
        person.setAge("年龄");
        persons.add(person);
    }

    public void btnClick(View view){
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        ContentResolver cr = getContentResolver();
        switch (view.getId()){
            case R.id.button:
                ContentValues values = new ContentValues();
                values.put("name",name);
                values.put("age",age);
                cr.insert(PERSON_ALL_URI,values);
                break;
            case R.id.button2:
                Uri updateUri = ContentUris.withAppendedId(PERSON_ALL_URI, Long.parseLong(cur_id));
                ContentValues values1 = new ContentValues();
                values1.put("name",name);
                values1.put("age",age);
                cr.update(updateUri,values1,null,null);
                break;
            case R.id.button3:
                Uri deleteUri = ContentUris.withAppendedId(PERSON_ALL_URI, Long.parseLong(cur_id));
                cr.delete(deleteUri,null,null);
                break;
        }
        //刷新ListView中数据
        persons.clear();
        persons = null;
        initDatas();
        Cursor cursor = cr.query(PERSON_ALL_URI,null,null,null,null);
        while (cursor.moveToNext()){
            String id = cursor.getString(0);
            String xm = cursor.getString(1);
            String nl = cursor.getString(2);
            //System.out.println("id:"+id+",name:"+xm+",age:"+nl);
            Person person = new Person();
            person.setId(id);
            person.setName(xm);
            person.setAge(nl);
            persons.add(person);
        }
        cursor.close();
        personAdapter.setPersons(persons);
        personAdapter.notifyDataSetChanged();
    }
}
