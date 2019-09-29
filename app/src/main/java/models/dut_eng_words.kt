package models

import classes.Word

fun loadDutEngWords(): MutableList<Word> {
    return mutableListOf(
        Word("rain", "regen"),
        Word("headset","hoofdtelefoon"),
        Word("doctor","arts"),
        Word("grapefruit","pompelmoes"),
        Word("cooperate" ,"samenwerken"),
        Word("crop rotation","teeltwisseling"),
        Word("supply chain","toeleveringsketen"),
        Word("unilateral","eenzijdig"),
        Word("performance","performance"),
        Word("judgement","oordeel")
    )
}