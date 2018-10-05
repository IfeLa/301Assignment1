package com.example.feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.feelsbook.FeelsBookActivity.FILENAME;


public class feelingsHistoryActivity extends AppCompatActivity {
    // Create adapter
    private ArrayAdapter<String> adapter;
    // Define Views
    private ListView historyList;
    private TextView countView;
    // Define string variables
    protected String feelingsString;
    protected String[] feelings;

    // Define emotion counts
    private int loveCount = 0 ,joyCount = 0 ,surpriseCount = 0 ,
            angerCount = 0 ,sadnessCount = 0 ,fearCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feelings_history);

        // Set Views
        historyList = findViewById(R.id.historyListView) ;
        countView = findViewById(R.id.historyLabel);
        // read recorded feelings from file
        loadFromFile();
        // Check how many times each kind of feeling occurs and update count
        for (int x = 0; x < feelings.length; x++) {
            Pattern p = Pattern.compile("Love");
            Matcher m = p.matcher(feelings[x]);
            if (m.find()){
                loveCount++;
            };
            p = Pattern.compile("Joy");
            m = p.matcher(feelings[x]);
            if (m.find()){
                joyCount++;
            };
            p = Pattern.compile("Surprise");
            m = p.matcher(feelings[x]);
            if (m.find()){
                surpriseCount++;
            };
            p = Pattern.compile("Anger");
            m = p.matcher(feelings[x]);
            if (m.find()){
                angerCount++;
            };
            p = Pattern.compile("Sadness");
            m = p.matcher(feelings[x]);
            if (m.find()){
               sadnessCount++;
            };
            p = Pattern.compile("Fear");
            m = p.matcher(feelings[x]);
            if (m.find()){
                fearCount++;
            };
        }
        // Join counts together in string and display with countView
        String countDisplay = "Love: " + String.valueOf(loveCount) + " Joy: " + String.valueOf(joyCount) +
                " Surprise: " + String.valueOf(surpriseCount) + " Anger: " + String.valueOf(angerCount)  +
                " Sadness: " + String.valueOf(sadnessCount) + " Fear: " + String.valueOf(fearCount) ;
        countView.setText(countDisplay);
        adapter = new ArrayAdapter<String>(feelingsHistoryActivity.this, android.R.layout.simple_list_item_1, feelings);
        historyList.setAdapter(adapter);

        // Set what happens when an item on the list is clicked
        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(feelingsHistoryActivity.this, edit_deleteActivity.class);
                intent.putExtra("selectedTextPos", position );
                feelingsHistoryActivity.this.finish();
                startActivity(intent);
            }
        });
    }

    // Function to read recorded feelings from file
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            feelingsString = in.readLine();
            feelingsString = feelingsString.substring(2,feelingsString.length()-2 );
            feelings = feelingsString.split("\\},\\{");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
