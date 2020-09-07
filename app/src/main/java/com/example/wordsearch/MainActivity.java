package com.example.wordsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    final Vector<String> ans = new Vector<String>();
    final ArrayList<Integer> butId = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start The Intro
        Intent intent = new Intent(MainActivity.this, PlayMenu.class);
        MainActivity.this.startActivity(intent);

    }


}