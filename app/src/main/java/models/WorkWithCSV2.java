package models;

import android.content.Context;
import android.util.Log;

import com.example.cramdroid.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

import classes.Encounter;
import classes.Fact;
import classes.Response;
import classes.Word;

public class WorkWithCSV2 {
    // things to save
    /*
    val english = _english
    val dutch = _dutch
    var activation: Float = Float.NEGATIVE_INFINITY
    var prev_seen = false
    var encounters = mutableListOf<Encounter>()
    var previous_activation = Float.NEGATIVE_INFINITY
    var alpha = 0.3F
    var previous_alpha : Float = 0.3F
    var decay = 0.3F
    */

    String FILENAME = "saveFacts.csv";
    // words are saved in a long string, one word per line, the pos_ variables define the position they are on
    int question = 0;
    int answer = 1;


    ArrayList facts = new ArrayList<Fact>();
    ArrayList responses = new ArrayList<Response>();
    ArrayList AllFacts = new ArrayList<Response>();



    public ArrayList<Fact> initializeFacts_readInCsv(Context context){
        InputStream is = context.getResources().openRawResource(R.raw.swahili);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";

        try {
            while( (line = reader.readLine())!= null ){
                // split by ','
                String[] tokens = line.split(",");

                // read the data
                Fact sample = new Fact(tokens[question], tokens[answer]);
                facts.add(sample);
            }

        } catch (IOException e){
            Log.wtf("My acitivity", "Error reading datafile" + line, e);
            e.printStackTrace();
        }
        return facts;
    }
}
