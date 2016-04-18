package com.urjapawar.project;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import java.util.ArrayList;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    TextView tv;
    public static final String[] titles = new String[] { "H C Verma",
            "P Bahadur", "I E Irodov", "M L Khanna",
            "O P Tondon", "Morrison & Boyd", "Halliday Resnick",
            "R D Sharma", "S L Loney", "Hall and Knight", "Krotov", "I A Maron" };
    public EditText search;
    CustomAdapter adapter;
    public static final String[] descriptions = new String[] {

            "Physics",
            "Chemistry",
            "Physics",
            "Mathematics",
            "Chemistry",
            "Chemistry",
            "Physics",
            "Mathematics",
            "Mathematics",
            "Chemistry",
            "Physics",
            "Mathematics"

    };
    public static final Integer[] images = { R.drawable.hcv,
            R.drawable.bahadur, R.drawable.irodov, R.drawable.mlkhanna,
            R.drawable.tondon, R.drawable.boyd, R.drawable.resnick, R.drawable.sharma,
            R.drawable.loney, R.drawable.knight, R.drawable.krotov, R.drawable.maron};

    public static final String[] pub = new String[]{"Bharti Bhavan", "G R Bathla Publications", "Arihant",
    "Jai Prakash Nath", "G R Bathla Publications", "Pearson Publications",
    "Wiley Publications", "Dhanpat Rai Publications","Classic Texts series","Classic Texts series","G K Publications","G K Publications"};

    ListView listView;
    ArrayList<RowItem> rowItems;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pecule Books");
        tv = (TextView)findViewById(R.id.textview);
        search = (EditText)findViewById(R.id.inputSearch);

        rowItems = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i],titles[i], descriptions[i], pub[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setTextFilterEnabled(true);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState != 0) {
                    InputMethodManager inputMethodManager = (InputMethodManager)
                            getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv.setText(adapter.t + " Result(s) found");
                adapter.getFilter().filter(s.toString());


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        toast.show();
    }


}
