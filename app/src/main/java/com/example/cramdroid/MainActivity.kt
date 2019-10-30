package com.example.cramdroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import android.app.*
import android.os.SystemClock
import android.R.drawable.ic_dialog_alert
import android.graphics.Color
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log
import classes.Fact
import classes.Response
import classes.StudyNotificationPublisher
import classes.Word
import models.SchedullingModel
import models.SpacingModel2
import models.WorkWithCSV
import models.WorkWithCSV2
import java.io.IOException
import java.io.OutputStreamWriter


class MainActivity : AppCompatActivity() {
    public val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "Penis"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val words: List<Word>
        //createNotificationChannel()
    }

    fun openTaskChoice(view: View) {
        val intent = Intent(this, TaskChoiceActivity::class.java)
        startActivity(intent)
    }

    fun openTutorial(view: View) {
        val intent = Intent(this, TutorialActivity::class.java)
        startActivity(intent)
    }

    fun settingsPress(view: View) {

        //used to checking alpha ToDo: Add here the message from the email, the times at the end are needed in addtion
        var EmailOutput = "elimu,science,409417244,4659.0,true,adhama,honor,409425170,1730.0,true,elimu,science,409429375,2954.0,false,adhama,honor,409433081,2678.0,true,wakati,alarm,409437186,1657.0,true,dhoruba,storm,409442361,2264.0,true,wakati,alarm,409445602,2585.0,true,dhoruba,storm,409449244,2919.0,true,karamu,party,409453327,2059.0,true,lugha,language,409457792,2080.0,true,karamu,party,409461404,2761.0,false,lugha,language,409465673,3003.0,false,degaga,glasses,409470071,2201.0,false,saduku,box,409474987,2009.0,true,degaga,glasses,409478985,3423.0,false,saduku,box,409482003,2278.0,true,adhama,honor,409487790,5180.0,false,elimu,science,409505111,16622.0,false,wakati,alarm,409507149,1462.0,false,dhoruba,storm,409508772,1077.0,false,karamu,party,409510503,1019.0,false,wimbo,song,409514700,1396.0,true,lugha,language,409517919,2541.0,false,wimbo,song,409523086,4590.0,false,barua,letter,409531113,6307.0,true,degaga,glasses,409532735,1084.0,false,adhama,honor,409540972,7662.0,true,barua,letter,409544194,2535.0,false,saduku,box,409546875,2151.0,true,elimu,science,409551846,4437.0,false,wakati,alarm,409554467,2013.0,false,dhoruba,storm,409558486,3445.0,false,karamu,party,409563071,4028.0,false,lugha,language,409567424,3408.0,true,adhama,honor,409569496,1567.0,false,wimbo,song,409571650,1661.0,false,rafiki,friend,409575866,2171.0,true,degaga,glasses,409596602,20158.0,true,rafiki,friend,409599482,2105.0,true,elimu,science,409621967,2553.0,false,adhama,honor,409626389,3794.0,true,wakati,alarm,409631555,4654.0,false,dhoruba,storm,409633841,1694.0,false,karamu,party,409636689,2317.0,true,lugha,language,409640775,3531.0,true,barua,letter,409644680,3212.0,false,adhama,honor,409651676,6323.0,false,degaga,glasses,409653434,1187.0,false,elimu,science,409659354,5355.0,false,wimbo,song,409663887,3966.0,false,wakati,alarm,409666818,2216.0,false,adhama,honor,409668308,1014.0,false,dhoruba,storm,409669839,990.0,false,lugha,language,409673625,3118.0,true,adhama,honor,409677516,3406.0,false,elimu,science,409682722,4687.0,true,adhama,honor,409686681,3511.0,false,degaga,glasses,409689822,2494.0,false,adhama,honor,409695048,4694.0,true,wakati,alarm,409702716,7234.0,false,adhama,honor,409706084,2878.0,true,dhoruba,storm,409709791,3123.0,false,adhama,honor,409715543,5110.0,true,elimu,science,409719658,3577.0,true,adhama,honor,409729653,8803.0,true,lugha,language,409733235,3109.0,true,adhama,honor,409737631,3787.0,true,wakati,alarm,409743503,5336.0,false,adhama,honor,409749276,5199.0,true,elimu,science,409756623,6396.0,true,adhama,honor,409759754,2495.0,true,dhoruba,storm,409762835,2546.0,true,adhama,honor,409765856,2472.0,true,degaga,glasses,409768823,2333.0,false,adhama,honor,409772153,2559.0,true,elimu,science,409777208,4516.0,true,adhama,honor,409780078,2351.0,true,wakati,alarm,409784364,3784.0,false,adhama,honor,409788168,3199.0,true,elimu,science,409791782,3136.0,true,adhama,honor,409795399,3057.0,true,degaga,glasses,409800194,4274.0,true,adhama,honor,409805446,4690.0,true,wimbo,song,409809143,3124.0,true,adhama,honor,409811934,2271.0,true,elimu,science,409815723,3260.0,true,adhama,honor,409818892,2672.0,true,wakati,alarm,409823525,4042.0,false,adhama,honor,409829229,5146.0,true,degaga,glasses,409833594,3859.0,true,adhama,honor,409836701,2592.0,true,dhoruba,storm,409840641,3374.0,true,adhama,honor,409843891,2607.0,true,elimu,science,409850102,5683.0,true,adhama,honor,409853157,2548.0,true,wakati,alarm,409894355,40730.0,false,adhama,honor,409899663,4707.0,true,degaga,glasses,409903832,3523.0,true,adhama,honor,409908241,3863.0,true,elimu,science,409913813,5026.0,true,adhama,honor,409945276,28171.0,true,degaga,glasses,409948779,2917.0,true,adhama,honor,409953005,3632.0,true,wakati,alarm,409970736,16237.0,false,adhama,honor,409984460,13120.0,true,elimu,science,409989203,3948.0,true,adhama,honor,409992957,2852.0,true,lugha,language,409999623,6079.0,true,adhama,honor,410003298,2812.0,true,,410006730,241200000,,412352222,241200000,\n"
        //split string into the required parts
        val parts = EmailOutput.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()

        var count = 0

        val spacingModel2 = SpacingModel2()
        var WorkWithCSV = WorkWithCSV2()

        // add all possible facts
        spacingModel2.addAllFacts(WorkWithCSV.readInFacts(this.applicationContext))



        //read in encounters
        //-5 because there need to be at least 5 things left
        while(count<parts.size-5) {

            // only add if the last one is false or true
            if(parts[count+4]=="true"||parts[4]=="false"){
                spacingModel2.register_response(Response(Fact(parts[count],parts[count+1]),parts[count+2].toLong(),parts[count+3].toFloat(),parts[count+4].toBoolean()))
            }
            count +=5
        }
        // at what time do you want to alpha?
        val atTime = 410006730F


        // save to csv
        var data = ""

        for(fact in spacingModel2.facts){
            println(fact.question+","+fact.answer+","+spacingModel2.get_rate_of_forgetting(atTime,fact))
            data += fact.question+","+fact.answer+","+spacingModel2.get_rate_of_forgetting(atTime,fact)+"\n"
        }

        try {
            val outputStreamWriter =
                OutputStreamWriter(this.applicationContext.openFileOutput("csv_with_alpha.csv", Context.MODE_PRIVATE))
            outputStreamWriter.write(data)
            outputStreamWriter.close()

        } catch (e: IOException) {
            Log.wtf("My acitivity", "Error writing datafile", e)
            e.printStackTrace()
        }


    }

    // Save the important information for later analysis
    fun sendOutputViaEmail(view: View) {
        // taken from https://www.youtube.com/watch?v=tZ2YEw6SoBU
        val recipientList = "fbfelix@web.de,e.n.meijer@student.rug.nl,S.Steffen.2@student.rug.nl" //add here your email address, with "," between them
        val recipients = recipientList.split(",".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray() // email addresses to sent to

        val subject = "UserModel_data"

        var test = WorkWithCSV2()

        val message = test.getCSVResponsesAsString(this.applicationContext)+test.getCurrentTimeAndSuggestedTime(this.applicationContext) // reads in the output from the trial

        println("could read in the message")
        println(message)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        intent.type = "message/rfc822" // only use Email apps

        startActivity(Intent.createChooser(intent, "Choose an email client"))
    }

}

