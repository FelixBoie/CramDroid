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
import classes.Word;

public class WorkWithCSV {
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

    String FILENAME = "saveWords.csv";
    // words are saved in a long string, one word per line, the pos_ variables define the position they are on
    int pos_dutch = 0;
    int pos_english = 1;


    ArrayList words = new ArrayList<Word>();



    public ArrayList<Word> general_readCsv(Context context){
        File file = new File(context.getFilesDir(),FILENAME);
        if(file.exists()){
//            System.out.println("File already exists");
            return readCSV_priorWritten(context);

        } else {
//            System.out.println("File does not exit");
            return initializeWords_readInCsv(context);
        }
    }

    public String general_readCsv2_asString(Context context){
        String wordsList_String = "";
        ArrayList<Word> words = general_readCsv(context);

        // loop over all words and add them together
        for(int i=0; i<words.size(); i++) {
            wordsList_String += words.get(i).getAsString() + ";"; // havent found a good way to show end, \n does not work
        }
        return wordsList_String;
    }

    // reads in the words from the folder res.raw
    private ArrayList<Word> initializeWords_readInCsv(Context context){
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
                Word sample = new Word(tokens[pos_english], tokens[pos_dutch]);
                words.add(sample);
            }

        } catch (IOException e){
            Log.wtf("My acitivity", "Error reading datafile" + line, e);
            e.printStackTrace();
        }
        return words;
    }

    // reads in from the internalized memory
    private ArrayList<Word> readCSV_priorWritten(Context context){
        FileInputStream fis = null;
        ArrayList<Word> words = new ArrayList<Word>();

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String oneLine;

            while((oneLine =br.readLine()) != null){
                words.add(setStringLineToWord(oneLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }


    public void writeCSV(Context context, ArrayList<Word> words){
        // but out where the data is saved: /data/user/0/com.example.cramdroid/files
        Log.d("Files", "Path: " + context.getFilesDir());

        //data saved
        String data = setWordsToString(words);
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e){
            Log.wtf("My acitivity", "Error writing datafile" , e);
            e.printStackTrace();
        }
    }


    private String setWordsToString(ArrayList<Word> wordList){
        String output = new String();


        //needs to be adjusted to fit
        for (int i = 0; i < wordList.size(); i++) {
            output = output +   wordList.get(i).getAsString();
        }
        return output;
    }

    private Word setStringLineToWord(String oneLine){
        String[] parts = oneLine.split(",");
        //0    val english = _english
        //1    val dutch = _dutch
        //2    var activation: Float = Float.NEGATIVE_INFINITY
        //3    var prev_seen = false
        //4    var previous_activation = Float.NEGATIVE_INFINITY
        //5    var alpha = 0.3F
        //6    var previous_alpha : Float = 0.3F
        //7    var decay = 0.3F
        //8    var encounters = ArrayList<Encounter>()
        Word word = new Word(parts[0],parts[1]);
        //create enounters
        ArrayList<Encounter> encounterList = new ArrayList<Encounter>();

        // first encounter from 8 to 11; as long as encounter consists of 4 things
        for (int i = 11; i < parts.length; i = i+4) {
            Encounter newEncounter = new Encounter(Float.parseFloat(parts[i-3]),Float.parseFloat(parts[i-2]),Float.parseFloat(parts[i-0]));
            encounterList.add(newEncounter);
        }

        word.setStringAsWord(Float.parseFloat(parts[2]),Boolean.parseBoolean(parts[3]),Float.parseFloat(parts[4]),Float.parseFloat(parts[5]),Float.parseFloat(parts[6]),Float.parseFloat(parts[7]),encounterList);
        //activation: Float, prev_seen: Boolean, previous_activation :Float, alpha : Float, previous_alpha : Float, decay: Float, encounter: ArrayList<Encounter>

        return word;
    }
}
