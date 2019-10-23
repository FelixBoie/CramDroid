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
    // only save responses, I do not think that we need facts

    String FILENAME = "saveFacts.csv";

    // words are saved in a long string, one word per line, the pos_ variables define the position they are on
    int question = 0;
    int answer = 1;


    ArrayList facts = new ArrayList<Fact>();




    public ArrayList<Fact> readInFacts(Context context){
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

    // responses need to be leaded earlier in, than with the beginning of the ViewControllerModel
    public ArrayList<Response> readInPriorResponses (Context context){
        File file = new File(context.getFilesDir(),FILENAME);

        // if the csv file already exist then open it
        if(file.exists()) {
            FileInputStream fis = null;
            ArrayList<Response> responses = new ArrayList<Response>();

            try {
                fis = context.openFileInput(FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String oneLine;
                System.out.println("test1");

                while ((oneLine = br.readLine()) != null) {
                    String[] parts = oneLine.split(",");

                    responses.add(new Response(new Fact(parts[0], parts[1]), Long.parseLong(parts[2]), Float.parseFloat(parts[3]), Boolean.parseBoolean(parts[4])));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (responses.size() == 0) {
                //there was not csv file yet
                return new ArrayList<Response>();
            } else {
                return responses;

            }
        } else {
            System.out.println("Currently no csv file for responses");
            return new ArrayList<Response>();
        }
    }

    public String getCSVResponsesAsString(Context context){
        ArrayList<Response> responses = this.readInPriorResponses(context);
        String output = "";
        for (Response response : responses){
            output += response.responseToString();
        }
        return output;
    }


    // write to files
    public void writeCSV(Context context,ArrayList<Response> responses){
        // but out where the data is saved: /data/user/0/com.example.cramdroid/files
        Log.d("Files", "Path: " + context.getFilesDir());

        //data saved Facts
        String data = "";
        for(Response response : responses){
            data +=response.responseToString()+"\n";
        }
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

        } catch (IOException e){
            Log.wtf("My acitivity", "Error writing datafile" , e);
            e.printStackTrace();
        }
    }
}
