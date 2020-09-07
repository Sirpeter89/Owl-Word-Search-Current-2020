package com.example.wordsearch;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Button;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class wordSearch extends AppCompatActivity {

    //vector to store the current letters/buttons the user has pressed
    final Vector<String> ans = new Vector<String>();
    //ArrList to store button Ids
    final ArrayList<Integer> butId = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        makeSearch();
    }

    //Helper function allows user to select letter/button
    public void allowSelection(Button[][] but, int index, int jin, int idN, TextView out) {
        but[index][jin].setBackgroundColor(Color.YELLOW);
        //Check if button has already been pressed, if not add to selection
        if (!butId.contains(idN)) {
            ans.addElement(but[index][jin].getText().toString());
            butId.add(idN);
        }
        out.setText(ans.toString());
    }

    //creates wordSearch layout, populates it with letters/buttons and incorporates functionality
    public void makeSearch() {
        //Layout for the word search
        LinearLayout letterGrid = (LinearLayout) findViewById(R.id.wordGrid);
        //TextView to show what letters the user has currently selected
        final TextView output = (TextView) findViewById(R.id.textView);
        final int[] Score = {0};
        //ArrayList to keep track of words that have already been found
        final ArrayList<String> ansFound = new ArrayList<String>();

        final String[] obC = {"O", "B", "J", "E", "C", "T", "I", "V", "E", "C"};
        final String[] var = {"V", "A", "R", "I", "A", "B", "L", "E"};
        final String[] jav = {"J", "A", "V", "A"};
        final String[] mob = {"E", "L", "I", "B", "O", "M"};
        final String[] kot = {"K", "O", "T", "L", "I", "N"};
        final String[] sft = {"T", "F", "I", "W", "S"};

        //"Send the Word"/Submit button, allows users to submit highlighted word for check
        //Temporarily acting as a clear function as well
        final Button sub = (Button) findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create copies of word arrays to sort and compare with submitted answer
                List<String[]> chk = new ArrayList<String[]>();
                chk.add(obC);
                chk.add(var);
                chk.add(jav);
                chk.add(mob);
                chk.add(kot);
                chk.add(sft);
                Collections.sort(ans);
                for (int word = 0; word < chk.size(); word++) {
                    Arrays.sort(chk.get(word));
                }
                //check for correct word
                for (int correctCheck = 0; correctCheck < chk.size(); correctCheck++) {
                    if (Arrays.toString(chk.get(correctCheck)).equals(ans.toString())) {
                        for (int i = 0; i < butId.size(); i++) {
                            Button ref = (Button) findViewById(butId.get(i));
                            ref.setBackgroundColor(Color.GREEN);
                            //Using hint as a boolean flag to see if button has been highlighted
                            ref.setHint("C");
                        }
                        //Keeping score/words found
                        RatingBar rat = (RatingBar) findViewById(R.id.ratingBar);
                        //Making sure they don't find the same word more than once
                        if (rat.getRating() < 6 && !ansFound.contains(ans.toString())) {
                            rat.setRating(Score[0] += 1);
                            ansFound.add(ans.toString());
                        }
                        //Win condition
                        if(rat.getRating() == 6){
                            Intent win = new Intent(wordSearch.this, winner.class);
                            wordSearch.this.startActivity(win);
                        }
                        ans.clear();
                        butId.clear();
                    }
                }
                //check for wrong word, it it's wrong clears user selection allowing them to choose again
                for (int wrongCheck = 0; wrongCheck < chk.size(); wrongCheck++) {
                    if (!Arrays.toString(chk.get(wrongCheck)).equals(ans.toString())) {
                        for (int i = 0; i < butId.size(); i++) {
                            Button ref = (Button) findViewById(butId.get(i));
                            ref.setBackgroundColor(Color.TRANSPARENT);
                            //Checking if the button was highlighted
                            if (ref.getHint() == "C") {
                                ref.setBackgroundColor(Color.GREEN);
                            }
                        }
                        ans.clear();
                        butId.clear();
                    }
                }


            }
        });

        //Creating 10x10 grid of buttons, each row containing 10 buttons
        LinearLayout rows = null;
        final Button[][] buttons = new Button[10][10];
        //Using count as a check to see which row we're on
        int count = 101;
        //Settings to be used for buttons and rows later
        ViewGroup.LayoutParams settings = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);

        //Variable used to put words into word grid following odd index pattern
        int odd = 1;

        for (int i = 0; i < 10; i++) {
            //make a new row for every 10 buttons
            if (count % 10 == 1) {
                rows = new LinearLayout(this);
                rows.setWeightSum(10);
                letterGrid.addView(rows, settings);
                count -= 10;
            }
            for (int j = 0; j < 10; j++) {
                //button for every letter populated
                buttons[i][j] = new Button(this);
                buttons[i][j].setBackgroundColor(Color.TRANSPARENT);
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //creating button and ids to be used as indexes for operations
                        String id = Integer.toString(finalI) + Integer.toString(finalJ);
                        int idNum = Integer.parseInt(id);
                        System.out.println(idNum);
                        buttons[finalI][finalJ].setId(idNum);
                        //Check if we pressed a new button or if the next button is adjacent
                        if (butId.isEmpty() || (idNum > butId.get(butId.size() - 1) - 12 && idNum < butId.get(butId.size() - 1) - 8)
                                || (idNum > butId.get(butId.size() - 1) - 2 && idNum < butId.get(butId.size() - 1) + 2)
                                || (idNum > butId.get(butId.size() - 1) + 8 && idNum < butId.get(butId.size() - 1) + 12)) {

                            //check which direction the user is going
                            if (butId.size() > 1) {
                                //going up right diagonal
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) - 9 && (idNum == butId.get(butId.size() - 1) - 9)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going right
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) + 1 && (idNum == butId.get(butId.size() - 1) + 1)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going down right diagonal
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) + 11 && (idNum == butId.get(butId.size() - 1) + 11)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going up left diagonal
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) - 11 && (idNum == butId.get(butId.size() - 1) - 11)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going left
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) - 1 && (idNum == butId.get(butId.size() - 1) - 1)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going down left diagonal
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) + 9 && (idNum == butId.get(butId.size() - 1) + 9)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going up
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) - 10 && (idNum == butId.get(butId.size() - 1) - 10)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                                //going down
                                if (butId.get(butId.size() - 1) == butId.get(butId.size() - 2) + 10 && (idNum == butId.get(butId.size() - 1) + 10)) {
                                    allowSelection(buttons, finalI, finalJ, idNum, output);
                                }
                            } else {
                                //User is picking a direction
                                allowSelection(buttons, finalI, finalJ, idNum, output);
                            }
                        }
                    }
                });

                //Operations to place words in the grid as buttons
                if (i == 0) {
                    buttons[i][j].setText(obC[j]);
                } else if (j == 7 && i < 8) {
                    buttons[i][j].setText(var[i]);
                } else if (i == 4 && j > 5) {
                    buttons[i][j].setText(jav[j - 6]);
                } else if (j == 3 && i < 6) {
                    buttons[i][j].setText(mob[i]);
                } else if (i > 2 && i < 9 && (i - j == 1)) {
                    //3,2   4,3   5,4   6,5   7,6  8,7 used as example for thinking proccess
                    buttons[i][j].setText(kot[i - 3]);
                } else {
                    Random r = new Random();
                    buttons[i][j].setText(String.valueOf((char) (r.nextInt(26) + 'A')));
                }

                if (i > 4 && (i - j == odd)) {
                    //5,4 6,3 7,2 8,1 9,0
                    buttons[i][j].setText(sft[i - 5]);
                    odd += 2;
                }
                rows.addView(buttons[i][j], settings);
            }
        }
    }
}
