package models;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.cramdroid.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;

import classes.Word;

public class CSVImport2 {

    String FILENAME = "saveWords.csv";
    int wordSwahili = 0;
    int wordEnglish = 1;

    ArrayList words = new ArrayList<Word>();



    public ArrayList<Word> general_readCsv(Context context){
        File file = new File(context.getFilesDir(),FILENAME);
        if(file.exists()){
            System.out.println("File already exists");
            return readCSV_priorWritten(context);

        } else {
            System.out.println("File does not exit");
            return initializeWords_readInCsv(context);
        }
    }


    public ArrayList<Word> initializeWords_readInCsv(Context context){
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
                Word sample = new Word(tokens[wordSwahili], tokens[wordEnglish]);
                words.add(sample);
            }

        } catch (IOException e){
            Log.wtf("My acitivity", "Error reading datafile" + line, e);
            e.printStackTrace();
        }
        return words;
    }

    public ArrayList<Word> readCSV_priorWritten(Context context){
        FileInputStream fis = null;
        ArrayList<Word> words = new ArrayList<Word>();

        try {
            fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String oneLine;

            while((oneLine =br.readLine()) != null){
                words.add(setStringLineToWord(oneLine));
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            output = output +   wordList.get(i).getSwahili() + "," +
                                wordList.get(i).getEnglish() + "," +
                                wordList.get(i).getActivation() + "," +
                                wordList.get(i).getPrev_seen() + "\n";
        }
        return output;
    }

    private Word setStringLineToWord(String oneLine){
        String[] parts = oneLine.split(",");
        //part 0 swahili, 1 english, 2 activation, 3 previous seen
        Word word = new Word(parts[0],parts[1]);
        if (parts[3]=="false"){
            word.setPrev_seen(false);
        } else {
            word.setPrev_seen(true);
        }
        return word;
    }

}
