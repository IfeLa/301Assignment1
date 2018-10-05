package com.example.feelsbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class FeelsBookActivity extends AppCompatActivity {

    // Create file to store recorded feelings in
    protected static final String FILENAME = "file.sav";

    // Create an ArrayList to store Feelings
    protected ArrayList<Feeling> feelings = new ArrayList<Feeling>();

    // Define emotion buttons
    private Button loveBtn ,joyBtn ,surpriseBtn ,angerBtn ,sadnessBtn ,fearBtn;
    // Define history button
    private Button historyBtn;
    // Define editText
    private EditText commentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feels_book);

        // Set comment editText
        commentText = findViewById(R.id.commentEditText);

        // Get buttons
        loveBtn = findViewById(R.id.loveButton);
        joyBtn = findViewById(R.id.joyButton);
        surpriseBtn = findViewById(R.id.surpriseButton);
        angerBtn = findViewById(R.id.angerButton);
        sadnessBtn = findViewById(R.id.sadnessButton);
        fearBtn = findViewById(R.id.fearButton);
        historyBtn = findViewById(R.id.historyButton);

        // Set what happens when the user clicks an emotion button
        loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtain entered comment
                String commented = commentText.getText().toString();
                // Create an instance of the corresponding type
                Feeling Love;
                // Construct instance based on whether or not comment was entered
                if (commented.length() > 0){
                    Love = new Feeling("Love",commented);
                }
                else{
                    Love = new Feeling("Love");
                }
                // add the feeling to the array list, save it in the file and clear comment
                feelings.add(Love);
                saveInFile();
                commentText.setText("");
            }
        });
        joyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commented = commentText.getText().toString();
                Feeling Joy;
                if (commented.length() > 0){
                    Joy = new Feeling("Joy",commented);
                }
                else{
                    Joy = new Feeling("Joy");
                }
                feelings.add(Joy);
                saveInFile();
                commentText.setText("");
            }
        });
        surpriseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commented = commentText.getText().toString();
                Feeling Surprise;
                if (commented.length() > 0){
                    Surprise = new Feeling("Surprise",commented);
                }
                else{
                    Surprise = new Feeling("Surprise");
                }
                feelings.add(Surprise);
                saveInFile();
                commentText.setText("");
            }
        });
        angerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commented = commentText.getText().toString();
                Feeling Anger;
                if (commented.length() > 0){
                    Anger = new Feeling("Anger",commented);
                }
                else{
                    Anger = new Feeling("Anger");
                }
                feelings.add(Anger);
                saveInFile();
                commentText.setText("");
            }
        });
        sadnessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commented = commentText.getText().toString();
                Feeling Sadness;
                if (commented.length() > 0){
                    Sadness = new Feeling("Sadness",commented);
                }
                else{
                    Sadness = new Feeling("Sadness");
                }
                feelings.add(Sadness);
                saveInFile();
                commentText.setText("");
            }
        });
        fearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commented = commentText.getText().toString();
                Feeling Fear;
                if (commented.length() > 0){
                    Fear = new Feeling("Fear",commented);
                }
                else{
                    Fear = new Feeling("Fear");
                }
                feelings.add(Fear);
                saveInFile();
                commentText.setText("");
            }
        });

        // set what happens when the "view history" button is clicked
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFeelingsHistoryActivity();
            }
        });

    }

    // Function to save each recorded feeling to file
    // gotten from https://github.com/joshua2ua/lonelyTwitter/blob/f15tuesday/app/src/main/java/ca/ualberta/cs/lonelytwitter/LonelyTwitterActivity.java
    protected void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(feelings, writer);
            writer.flush();
            fos.close();
            // make toast to assure user that the feeling ws saved
            Toast.makeText(getApplicationContext(),"Recorded",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Function that starts the activity that displays the count and history
    private void openFeelingsHistoryActivity(){
        Intent intent = new Intent(this,feelingsHistoryActivity.class);
        startActivity(intent);
    }
}

