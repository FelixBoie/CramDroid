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
import androidx.core.content.ContextCompat.startActivity
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
        var EmailOutput = "skati,skirt,61589221,4869.0,true,kitanda,bed,61597754,1904.0,true,skati,skirt,61611380,4432.0,true,kitanda,bed,61614790,2199.0,true,barua,letter,61623055,2166.0,true,kaputula,pants,61630314,2333.0,true,barua,letter,61634671,3353.0,true,kaputula,pants,61637815,2168.0,true,dhoruba,storm,61643133,2128.0,true,askari,police,61650113,4707.0,true,dhoruba,storm,61654712,3866.0,true,askari,police,61657888,2568.0,false,degaga,glasses,61666156,2661.0,true,skati,skirt,61669611,2622.0,true,degaga,glasses,61674398,4173.0,true,kitanda,bed,61678612,3496.0,true,wingu,cloud,61684325,3422.0,true,kaputula,pants,61691981,4500.0,false,wingu,cloud,61696846,4035.0,false,barua,letter,61710361,12068.0,false,askari,police,61720809,9679.0,false,dhoruba,storm,61723898,2187.0,false,skati,skirt,61727386,2735.0,true,kitanda,bed,61731709,3689.0,true,degaga,glasses,61736057,3774.0,false,kaputula,pants,61741437,4630.0,true,kiti,chair,61749334,4239.0,true,wingu,cloud,61755698,5558.0,true,kiti,chair,61762808,6184.0,false,barua,letter,61766847,3175.0,false,skati,skirt,61773817,2115.0,true,kitanda,bed,61777924,3386.0,true,askari,police,61785637,6834.0,true,dhoruba,storm,61792176,5280.0,false,kaputula,pants,61806223,13284.0,false,degaga,glasses,61816620,9672.0,false,kitanda,bed,61824161,6856.0,false,maji,ice,61840446,2153.0,true,barua,letter,61855676,14058.0,true,kaputula,pants,61865514,7601.0,false,kitanda,bed,61868700,1871.0,true,maji,ice,61871982,2270.0,true,askari,police,61876590,3974.0,false,dhoruba,storm,61881477,4184.0,true,wingu,cloud,61887051,2483.0,true,degaga,glasses,61888125,200.0,false,kiti,chair,61896298,7056.0,true,kaputula,pants,61912846,15159.0,false,barua,letter,61923467,9966.0,true,askari,police,61926718,2279.0,false,kitanda,bed,61945367,18026.0,true,kaputula,pants,61963330,7994.0,false,dhoruba,storm,61974105,6250.0,true,barua,letter,61979379,3435.0,true,kaputula,pants,61984893,3983.0,true,degaga,glasses,61995659,9851.0,false,askari,police,62021536,17461.0,true,kaputula,pants,62035542,5255.0,true,kitanda,bed,62039138,2676.0,true,dhoruba,storm,62042982,3015.0,true,degaga,glasses,62056415,12784.0,true,kaputula,pants,62060882,3788.0,true,skati,skirt,62064769,3245.0,true,barua,letter,62071302,5814.0,true,kaputula,pants,62079339,7164.0,true,kiti,chair,62084738,4659.0,true,kaputula,pants,62091806,6226.0,true,kitanda,bed,62096681,4165.0,true,kaputula,pants,62111541,4121.0,true,askari,police,62121135,8705.0,false,kaputula,pants,62126784,4096.0,true,barua,letter,62134523,7146.0,true,kaputula,pants,62142538,7355.0,true,kitanda,bed,62146245,3050.0,true,kaputula,pants,62150596,3730.0,true,dhoruba,storm,62154052,2732.0,true,kaputula,pants,62157323,2631.0,true,degaga,glasses,62160766,2846.0,true,kaputula,pants,62165967,4522.0,true,maji,ice,62168679,2087.0,true,kaputula,pants,62171799,2456.0,true,kitanda,bed,62177998,5596.0,true,kaputula,pants,62182471,3596.0,true,barua,letter,62186314,3171.0,true,kaputula,pants,62192262,5337.0,true,kitanda,bed,62195553,2707.0,true,kaputula,pants,62201340,4855.0,true,kiti,chair,62214502,12550.0,true,kaputula,pants,62217652,2040.0,true,askari,police,62224014,5545.0,true,kaputula,pants,62226672,2108.0,true,kitanda,bed,62230821,3547.0,true,kaputula,pants,62234448,2996.0,true,wingu,cloud,62240553,5518.0,true,kaputula,pants,62243822,2658.0,true,kitanda,bed,62247009,2601.0,true,kaputula,pants,62249532,1975.0,true,dhoruba,storm,62252412,2236.0,true,kaputula,pants,62255223,2168.0,true,askari,police,62260608,4712.0,true,kaputula,pants,62263274,2034.0,true,degaga,glasses,62266685,2727.0,true,kaputula,pants,62269607,2230.0,true,kitanda,bed,62273970,3666.0,true,kaputula,pants,62277288,2690.0,true,barua,letter,62280736,2847.0,true,kaputula,pants,62283458,2151.0,true,kitanda,bed,62286750,2589.0,true,kaputula,pants,62291084,3758.0,true,askari,police,62297461,5584.0,false,kaputula,pants,62300918,1974.0,true,kiti,chair,62305844,4024.0,true,kaputula,pants,62310777,4271.0,true,kitanda,bed,62314882,2865.0,true,kaputula,pants,62318538,2488.0,true,skati,skirt,62325840,6588.0,true,kaputula,pants,62328785,2158.0,true,kitanda,bed,62333188,3787.0,true,kaputula,pants,62335887,2019.0,true,kitanda,bed,62338287,1610.0,true,kaputula,pants,62344329,5320.0,true,askari,police,62348858,3646.0,true,kaputula,pants,62356489,6979.0,true,kitanda,bed,62359358,2149.0,true,kaputula,pants,62369878,9712.0,true,kitanda,bed,62377111,6404.0,true,kaputula,pants,62379776,1938.0,true,kitanda,bed,62382256,1758.0,true,kaputula,pants,62384676,1740.0,true,barua,letter,62388664,3313.0,true,kaputula,pants,62391101,1836.0,true,kitanda,bed,62393282,1548.0,true,kaputula,pants,62400711,5946.0,true,wingu,cloud,62404357,3015.0,true,kaputula,pants,62411367,6194.0,true,kitanda,bed,62414207,2009.0,true,kaputula,pants,62418858,4003.0,true,askari,police,62422497,2863.0,true,kaputula,pants,62425296,2137.0,true,kitanda,bed,62428335,2361.0,true,kaputula,pants,62431521,2311.0,true,basi,bus,62451891,1923.0,true,kaputula,pants,62455095,2399.0,true,kitanda,bed,62459066,2021.0,true,kaputula,pants,62463670,3172.0,true,dhoruba,storm,62467804,3091.0,true,kaputula,pants,62471697,3086.0,true,kitanda,bed,62474408,1898.0,true,kaputula,pants,62477960,2733.0,true,degaga,glasses,62483114,4197.0,true,kaputula,pants,62491834,7804.0,true,kitanda,bed,62494830,2021.0,true,kaputula,pants,62499798,4114.0,true,skati,skirt,62504050,3098.0,true,kaputula,pants,62508285,2213.0,true,kitanda,bed,62510381,1380.0,true,kaputula,pants,62512822,1692.0,true,askari,police,62515940,2415.0,true,kaputula,pants,62519153,2020.0,true,kiti,chair,62527725,7511.0,true,kaputula,pants,62531324,2277.0,true,kitanda,bed,62535502,2958.0,true,kaputula,pants,62540940,4457.0,true,basi,bus,62546752,5000.0,false,kaputula,pants,62549899,2051.0,true,kitanda,bed,62552558,1715.0,true,kaputula,pants,62555380,1740.0,true,barua,letter,62568262,11932.0,true,kaputula,pants,62575700,6268.0,true,kitanda,bed,62578811,2028.0,true,kaputula,pants,62583547,3732.0,true,askari,police,62586950,2535.0,true,kaputula,pants,62590832,3071.0,true,kitanda,bed,62593371,1758.0,true,kaputula,pants,62597704,3369.0,true,dhoruba,storm,62600867,2249.0,true,kaputula,pants,62604480,2550.0,true,kitanda,bed,62607649,2387.0,true,kaputula,pants,62610261,1809.0,true,degaga,glasses,62614972,3435.0,true,kaputula,pants,62618705,2786.0,true,kitanda,bed,62621535,1878.0,true,kaputula,pants,62624893,2595.0,true,barua,letter,62629579,3593.0,true,kaputula,pants,62632227,1670.0,true,kitanda,bed,62636108,2903.0,true,kaputula,pants,62638658,1694.0,true,askari,police,62645941,6362.0,true,kaputula,pants,62649935,3043.0,true,kitanda,bed,62652851,1886.0,true,kaputula,pants,62659028,5213.0,true,barua,letter,62664437,4377.0,true,kaputula,pants,62667204,1976.0,true,askari,police,62674557,6494.0,true,kaputula,pants,62678080,2425.0,true,kitanda,bed,62680929,1952.0,true,kaputula,pants,62687985,5706.0,true,barua,letter,62691482,2521.0,true,kaputula,pants,62701407,8564.0,true,askari,police,62705102,2744.0,true,kaputula,pants,62711620,5374.0,true,kitanda,bed,62714354,1771.0,true,kaputula,pants,62716701,1594.0,true,skati,skirt,62721307,2469.0,true,kaputula,pants,62727004,4049.0,true,kitanda,bed,62729825,1889.0,true,kaputula,pants,62735804,4970.0,true,askari,police,62742597,5021.0,true,kaputula,pants,62747416,3494.0,true,barua,letter,62750931,2272.0,true,kaputula,pants,62753655,1809.0,true,kitanda,bed,62759610,4997.0,true,kaputula,pants,62762300,1803.0,true,kiti,chair,62768033,4893.0,true,kaputula,pants,62770940,1936.0,true,askari,police,62775715,3831.0,true,kaputula,pants,62778877,2172.0,true,kitanda,bed,62782368,2484.0,true,kaputula,pants,62785894,2489.0,true,wingu,cloud,62789391,2598.0,true,kaputula,pants,62792175,1855.0,true,barua,letter,62795840,2579.0,true,kaputula,pants,62798990,2215.0,true,kitanda,bed,62805600,4055.0,true,kaputula,pants,62809181,2503.0,true,askari,police,62812540,2385.0,true,kaputula,pants,62817417,3256.0,true,dhoruba,storm,62826631,8011.0,true,kaputula,pants,62832001,3932.0,true,kitanda,bed,62835906,2926.0,true,kaputula,pants,62839021,1842.0,true,degaga,glasses,62842701,2793.0,true,kaputula,pants,62845338,1536.0,true,barua,letter,62849179,2876.0,true,kaputula,pants,62853074,3055.0,true,kitanda,bed,62857346,2885.0,true,kaputula,pants,62861865,3521.0,true,askari,police,62866273,2680.0,true,kaputula,pants,62869264,1950.0,true,kitanda,bed,62872831,2377.0,true,kaputula,pants,62881157,7447.0,true,maji,ice,62885855,3549.0,true,kaputula,pants,62889865,2199.0,true,kitanda,bed,62892449,1489.0,true,kaputula,pants,62896916,3456.0,true,askari,police,62900611,2640.0,true,kaputula,pants,62907332,5759.0,true,barua,letter,62911825,3300.0,true,kaputula,pants,62914705,1840.0,true,kitanda,bed,62922182,6279.0,true,kaputula,pants,62927931,4573.0,true,basi,bus,62931224,2119.0,true,kaputula,pants,62935141,2877.0,true,kitanda,bed,62940449,3389.0,true,kaputula,pants,62944838,3163.0,true,askari,police,62951202,5215.0,true,kaputula,pants,62954849,2702.0,true,kitanda,bed,62958578,2371.0,true,kaputula,pants,62966243,6854.0,true,barua,letter,62969845,2606.0,true,kaputula,pants,62973096,2061.0,true,skati,skirt,62978289,4104.0,true,kaputula,pants,62981239,1893.0,true,kitanda,bed,62983810,1538.0,true,kaputula,pants,62986524,1683.0,true,askari,police,62990084,2429.0,true,kaputula,pants,62993028,1900.0,true,kiti,chair,63000110,5509.0,true,kaputula,pants,63007655,6425.0,true,kitanda,bed,63012195,2654.0,true,kaputula,pants,63017391,3529.0,true,dhoruba,storm,63022214,3646.0,true,kaputula,pants,63025195,1632.0,true,barua,letter,63029385,2860.0,true,kaputula,pants,63037177,6342.0,true,lango,door,155079745,2829.0,true,kaputula,pants,155086734,5205.0,true,kitanda,bed,155091730,3341.0,true,kaputula,pants,155094904,2290.0,true,basi,bus,155099694,3481.0,true,askari,police,155103883,2742.0,true,kaputula,pants,155108725,2351.0,true,maji,ice,155113034,2984.0,true,degaga,glasses,155117623,3113.0,true,kitanda,bed,155121424,2565.0,true,kaputula,pants,155126498,3963.0,true,barua,letter,155140274,12386.0,true,kiti,chair,155146001,4424.0,true,kaputula,pants,155155766,8412.0,true,dhoruba,storm,155161078,4093.0,true,wingu,cloud,155165955,3242.0,true,kaputula,pants,155172056,4704.0,true,kitanda,bed,155177707,3375.0,true,askari,police,155181931,2952.0,true,kaputula,pants,155187014,3441.0,true,skati,skirt,155199689,11426.0,true,kaputula,pants,155204421,3124.0,true,barua,letter,155208891,3286.0,true,kitanda,bed,155214725,4615.0,true,kaputula,pants,155218786,2885.0,true,degaga,glasses,155229869,9633.0,true,lango,door,155234114,3034.0,true,kaputula,pants,155238626,3281.0,true,dhoruba,storm,155244343,4176.0,true,askari,police,155251470,5847.0,false,kitanda,bed,155254608,235.0,false,kaputula,pants,155266959,10198.0,true,kiti,chair,155272700,4555.0,true,kaputula,pants,155278802,4943.0,true,basi,bus,155282915,2935.0,true,maji,ice,155292316,8195.0,true,kaputula,pants,155295994,2380.0,true,barua,letter,155305337,7711.0,true,kitanda,bed,155310152,3483.0,true,wingu,cloud,155314367,2786.0,true,kaputula,pants,155319308,2195.0,true,askari,police,155326795,6295.0,true,kaputula,pants,155329759,2337.0,true,degaga,glasses,155333839,2640.0,true,kitanda,bed,155338139,2309.0,true,kaputula,pants,155341653,2171.0,true,skati,skirt,155346767,3816.0,true,dhoruba,storm,155350138,2214.0,true,kaputula,pants,155353685,2044.0,true,barua,letter,155358175,3084.0,true,kaputula,pants,155361490,2109.0,true,kitanda,bed,155364597,1797.0,true,kiti,chair,155373769,6279.0,true,askari,police,155378199,3067.0,true,kaputula,pants,155383656,2665.0,true,lango,door,155387537,2440.0,true,kaputula,pants,155392734,3739.0,true,kitanda,bed,155396100,1856.0,true,kaputula,pants,155399246,1830.0,true,wingu,cloud,155403094,2390.0,true,basi,bus,155407585,2959.0,true,degaga,glasses,155413955,5003.0,true,kaputula,pants,155418070,2556.0,true,barua,letter,155423343,3696.0,true,maji,ice,155427276,2552.0,true,kaputula,pants,155431096,2269.0,true,kitanda,bed,155434636,1920.0,true,dhoruba,storm,155439600,3352.0,true,askari,police,155445276,2721.0,true,kaputula,pants,155448596,1919.0,true,skati,skirt,155452265,2331.0,true,kaputula,pants,155455910,2145.0,true,kitanda,bed,155459254,1888.0,true,kiti,chair,155464724,4080.0,true,kaputula,pants,155468446,2157.0,true,barua,letter,155473174,3064.0,true,kaputula,pants,155477063,2089.0,true,askari,police,155484069,5351.0,true,kaputula,pants,155488198,2552.0,true,kitanda,bed,155491919,2321.0,true,degaga,glasses,155496094,2760.0,true,kaputula,pants,155511629,14060.0,true,lango,door,155518505,5395.0,true,kaputula,pants,155522808,2442.0,true,dhoruba,storm,155526756,2401.0,true,kitanda,bed,155530066,1797.0,true,kaputula,pants,155533415,1905.0,true,wingu,cloud,155539081,2845.0,true,kaputula,pants,155544320,2368.0,true,askari,police,155548629,2749.0,true,barua,letter,155552648,2280.0,true,kaputula,pants,155563276,8958.0,true,kitanda,bed,155566949,1997.0,true,basi,bus,155570916,2558.0,true,kaputula,pants,155575746,3289.0,true,skati,skirt,155579378,2032.0,true,kiti,chair,155583529,2491.0,true,kaputula,pants,155587506,2395.0,true,maji,ice,155592275,2109.0,true,kaputula,pants,155597239,3317.0,true,kitanda,bed,155600632,1764.0,true,degaga,glasses,155605334,2775.0,true,kaputula,pants,155608831,1944.0,true,askari,police,155613783,3321.0,true,dhoruba,storm,155618837,3285.0,true,kaputula,pants,155622546,1981.0,true,barua,letter,155627224,2753.0,true,kitanda,bed,155631218,1909.0,true,kaputula,pants,155634713,1820.0,true,wingu,cloud,155638985,2732.0,true,kaputula,pants,155642745,2121.0,true,adui,enemy,155657804,3803.0,true,kaputula,pants,155663476,3997.0,true,adui,enemy,155667301,2225.0,true,kitanda,bed,155670725,1741.0,true,wimbo,song,87397797,1919.0,true,kaputula,pants,87401516,2252.0,true,basi,bus,87405023,2225.0,true,kaputula,pants,87408426,2232.0,true,kitanda,bed,87411575,1993.0,true,kaputula,pants,87414830,2009.0,true,maji,ice,87418520,2334.0,true,kaputula,pants,87421479,1816.0,true,kitanda,bed,87424581,1829.0,true,kaputula,pants,87431696,6151.0,true,askari,police,87435773,2703.0,true,degaga,glasses,87440397,3456.0,true,kaputula,pants,87446090,4443.0,true,kiti,chair,87453510,6200.0,true,wingu,cloud,87458888,4081.0,true,kaputula,pants,87463132,3056.0,true,kitanda,bed,87465782,1528.0,true,dhoruba,storm,87469167,2334.0,true,barua,letter,87473693,3286.0,true,kaputula,pants,87477599,2745.0,true,wimbo,song,87482232,3451.0,true,kaputula,pants,87487221,3820.0,true,askari,police,87491471,3056.0,true,kitanda,bed,87496119,3398.0,true,kaputula,pants,87500063,2686.0,true,degaga,glasses,87503913,2867.0,true,kaputula,pants,87506972,1834.0,true,basi,bus,87509772,1730.0,true,barua,letter,87514287,3481.0,true,skati,skirt,87518270,2650.0,true,kaputula,pants,87522888,3396.0,true,kitanda,bed,87526013,1989.0,true,kiti,chair,87535066,8042.0,true,kaputula,pants,87538836,2511.0,true,dhoruba,storm,87542795,2574.0,true,askari,police,87549005,5051.0,true,kaputula,pants,87551976,1754.0,true,maji,ice,87555066,1829.0,true,kitanda,bed,87558175,1845.0,true,wingu,cloud,87562019,2570.0,true,kaputula,pants,87565023,1709.0,true,barua,letter,87568374,2272.0,true,kaputula,pants,87572879,3324.0,true,kitanda,bed,87575650,1512.0,true,kaputula,pants,87578730,1673.0,true,degaga,glasses,87587284,7310.0,true,askari,police,87591552,2914.0,true,wimbo,song,87595252,2607.0,false,kaputula,pants,87603630,7177.0,true,dhoruba,storm,87606930,2119.0,true,kaputula,pants,87609777,1719.0,true,kitanda,bed,87616704,5810.0,true,skati,skirt,87621506,3531.0,true,kaputula,pants,87625141,2435.0,true,kiti,chair,87629770,3349.0,true,kaputula,pants,87635997,4642.0,true,barua,letter,87640797,3515.0,true,askari,police,87644552,2655.0,true,kaputula,pants,87649695,3848.0,true,kitanda,bed,87653591,2606.0,true,basi,bus,87657143,2067.0,true,kaputula,pants,87661146,2743.0,true,wingu,cloud,87665019,2429.0,true,kaputula,pants,87668785,2557.0,true,maji,ice,87672068,1894.0,true,degaga,glasses,87675765,2421.0,true,kitanda,bed,87678656,1557.0,true,kaputula,pants,87681591,1656.0,true,dhoruba,storm,87686055,3347.0,true,kaputula,pants,87689142,1815.0,true,barua,letter,87692567,2142.0,true,askari,police,87696773,2911.0,true,kaputula,pants,87700047,1903.0,true,kitanda,bed,87703547,1853.0,true,wimbo,song,87708297,3332.0,true,kaputula,pants,87714647,4930.0,true,kiti,chair,87721448,3005.0,true,kaputula,pants,87724523,1742.0,true,skati,skirt,87727770,1920.0,true,kitanda,bed,87730745,1699.0,true,kaputula,pants,87734922,2319.0,true,askari,police,87739082,2844.0,true,kaputula,pants,87744302,3566.0,true,barua,letter,87748120,2395.0,true,degaga,glasses,87751955,2571.0,true,kaputula,pants,87756453,2448.0,true,kitanda,bed,87759735,1878.0,true,dhoruba,storm,87763530,2336.0,true,kaputula,pants,87768349,3226.0,true,wingu,cloud,87775446,5649.0,true,basi,bus,87778694,1856.0,true,kaputula,pants,87782311,2286.0,true,kitanda,bed,87785623,1965.0,true,kaputula,pants,87789825,2576.0,true,askari,police,87797143,5867.0,true,kaputula,pants,87800491,1832.0,true,maji,ice,87803793,1864.0,true,barua,letter,87807474,2376.0,true,kaputula,pants,87811924,2902.0,true,kiti,chair,87815595,2285.0,true,kitanda,bed,87818583,1600.0,true,kaputula,pants,87822045,1908.0,true,shukuru,thanks,87828829,2167.0,true,kisu,knife,87835421,2498.0,true,shukuru,thanks,87841394,4313.0,true,kisu,knife,87845690,2668.0,true,kaputula,pants,87849310,1956.0,true,shukuru,thanks,87854048,3241.0,true,kisu,knife,87857360,1858.0,true,kaputula,pants,87862322,3309.0,true,degaga,glasses,87866291,2259.0,true,kaputula,pants,87869217,1533.0,true,kitanda,bed,87872210,1277.0,true,kichwa,head,87879992,1961.0,true,askari,police,87885972,4408.0,true,kichwa,head,87891022,3522.0,true,kaputula,pants,87894714,2010.0,true,shukuru,thanks,87900129,3923.0,true,kichwa,head,87904033,2495.0,false,kisu,knife,87910251,3408.0,true,kaputula,pants,87914187,2430.0,true,wimbo,song,87920196,4161.0,true,kaputula,pants,87924167,2302.0,true,skati,skirt,87928172,2435.0,true,kitanda,bed,87931545,1808.0,true,dhoruba,storm,87935513,2548.0,true,kaputula,pants,87943747,6662.0,true,kichwa,head,87952343,6880.0,true,kaputula,pants,87955704,1826.0,true,barua,letter,87961617,4324.0,true,sauti,voice,87972900,1729.0,true,kaputula,pants,87976164,1746.0,true,sauti,voice,87979930,2245.0,true,kitanda,bed,87983130,1720.0,true,sauti,voice,87987464,2782.0,true,kaputula,pants,87991483,2508.0,true,afisi,office,4966967,2000.0,true,kichwa,head,4971756,1642.0,true,afisi,office,4974758,2304.0,true,kichwa,head,4977476,2021.0,true,afisi,office,4984927,6836.0,true,kichwa,head,4989405,2084.0,true,maliki,king,4993992,1903.0,true,samaki,fish,5000308,2738.0,true,maliki,king,5010009,8850.0,false,samaki,fish,5013414,2587.0,true,maliki,king,5016507,2490.0,true,samaki,fish,5019652,2481.0,true,karamu,party,5027407,2001.0,true,afisi,office,5030706,2742.0,true,karamu,party,5039316,8028.0,true,kichwa,head,5043044,2334.0,true,karamu,party,5048160,4512.0,true,wimbo,song,5053630,2199.0,true,adhama,honor,5065716,4933.0,true,wimbo,song,5070066,3652.0,true,adhama,honor,5074911,4235.0,true,maliki,king,5078874,3433.0,false,samaki,fish,5118864,39127.0,true,wimbo,song,5121909,2042.0,true,adhama,honor,5131158,8582.0,false,karamu,party,5139499,7656.0,false,maliki,king,5176783,1585.0,true,samaki,fish,5184896,7409.0,true,adhama,honor,5193447,3944.0,false,afisi,office,5205503,3471.0,true,kichwa,head,5208226,2008.0,true,samaki,fish,5212453,3641.0,true,wimbo,song,5214790,1745.0,true,karamu,party,5230986,15645.0,false,adhama,honor,5241290,5342.0,false,maliki,king,5251796,2620.0,true,askari,police,5261032,4490.0,true,mbwa,dog,5272200,1589.0,true,askari,police,5275173,2281.0,true,mbwa,dog,5278535,2312.0,true,adhama,honor,5283295,3353.0,true,askari,police,5286263,2353.0,true,mbwa,dog,5289689,2885.0,true,samaki,fish,5293075,2846.0,true,dunia,world,5326586,1709.0,true,askari,police,5329759,2653.0,true,dunia,world,5332452,2117.0,true,mbwa,dog,5334685,1761.0,true,kichwa,head,5337010,1830.0,true,dunia,world,5340232,2718.0,true,afisi,office,5345075,4316.0,true,maji,ice,5351770,1859.0,true,barua,letter,5356083,1889.0,true,maji,ice,5358971,1817.0,true,barua,letter,5361497,1921.0,false,maji,ice,5364059,1675.0,true,barua,letter,5366765,2121.0,false,sauti,voice,5384888,1885.0,true,dunia,world,5389836,4280.0,true,sauti,voice,5393578,3132.0,true,adhama,honor,5397212,2904.0,true,sauti,voice,5400510,2755.0,true,karamu,party,5404867,3886.0,true,maji,ice,5407162,1757.0,true,barua,letter,5409724,2054.0,true,wimbo,song,5412582,2377.0,true,maliki,king,5416938,3688.0,true,chanjo,scissors,5435300,2465.0,true,mbwa,dog,5438047,1998.0,true,chanjo,scissors,5444867,6199.0,true,sauti,voice,5448674,3148.0,true,askari,police,5452046,2794.0,true,chanjo,scissors,5456370,3664.0,true,samaki,fish,5461121,4166.0,true,lango,door,5473515,1496.0,true,dunia,world,5478648,4570.0,true,lango,door,5480917,1720.0,true,kanisa,church,5497698,4446.0,true,lango,door,5504061,5568.0,true,kanisa,church,5508954,4259.0,true,chanjo,scissors,5517399,7686.0,true,kanisa,church,5528468,5225.0,true,barua,letter,5531354,2245.0,true,maji,ice,5533602,1665.0,true,adhama,honor,5536994,2856.0,true,lango,door,5540473,2961.0,true,sauti,voice,5544726,3665.0,true,,62179783,165600000,,62434193,248400000,,63039732,500400000,,155673402,-3600000,,87992484,-3600000,,5561391,-3600000,,6093845,-3600000,,6107060,-3600000,"
        // at what time do you want to alpha?
        val atTime = 937190951F


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
            if(parts[count+4]=="true") {
                spacingModel2.register_response(Response(Fact(parts[count],parts[count+1]),parts[count+2].toLong(),parts[count+3].toFloat(),true))

            }
            if(parts[count+4]=="false"){
                spacingModel2.register_response(Response(Fact(parts[count],parts[count+1]),parts[count+2].toLong(),parts[count+3].toFloat(),false))
                println("boolean is false")
            }
            count +=5
        }
        WorkWithCSV.writeCSV(this.applicationContext,spacingModel2.responses)







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

