package com.example.feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.feelsbook.FeelsBookActivity.FILENAME;

public class edit_deleteActivity extends AppCompatActivity {

    //Define variables
    private String[] feels;
    private String feelsString;
    private int selectedPos;
    private String toDisplay;

    // Define buttons
    private Button deleteBtn;
    private Button editBtn;
    //Define EditText
    private EditText editTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        // set buttons and editText
        deleteBtn = findViewById(R.id.deleteButton);
        editBtn = findViewById(R.id.editButton);
        editTxt = findViewById(R.id.editTextView);

        // obtain position of clicked item
        Intent intent = getIntent();
        selectedPos = intent.getIntExtra("selectedTextPos",-1);
        // read recorded feelings from file
        loadFromFile();
        // display editText as selected item
        if (selectedPos > -1){
            toDisplay = feels[selectedPos];
            editTxt.setText((CharSequence) toDisplay);
        }else{
            editTxt.setText("Not working");
        }

        // set what happens when delete button is clicked
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFromFile(selectedPos);
            }
        });
        // set what happens when edit button is clicked
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFile(editTxt.getText().toString(),selectedPos);
            }
        });

    }
    // Delete selected item from file
    private void DeleteFromFile(int pos) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> feelsList = new ArrayList<String>(Arrays.asList(feels));
        feelsList.remove(pos);
        feels = feelsList.toArray(new String[feelsList.size()]);
//        System.arraycopy(feels, pos + 1, feelsCopy, pos, feels.length - 1 - pos);
        saveToFile(feels);
    }
    // Replace edited item with editText
    private void EditFile(String edited,int pos) {
        feels[pos] = edited;
        saveToFile(feels);
    }
    // save changes to file
    protected void saveToFile(String[] changedFeels) {
        try {
            String result = TextUtils.join("},{", changedFeels);
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter saveWriter = new OutputStreamWriter(fos);
            saveWriter.write("[{" + result + "}]");
            saveWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openFeelingsHistoryActivity();
    }
    // read recorded feelings from file
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            feelsString = in.readLine();
            feelsString = feelsString.substring(2,feelsString.length()-2 );
            feels = feelsString.split("\\},\\{");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // return to FeelingsHistoryActivity
    private void openFeelingsHistoryActivity(){
        Intent intent = new Intent(edit_deleteActivity.this,feelingsHistoryActivity.class);
        edit_deleteActivity.this.finish();
        startActivity(intent);
    }
}
