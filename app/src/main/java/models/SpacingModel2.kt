package models

import classes.*
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.pow
import kotlin.random.Random

class SpacingModel2 {


    // Model constants
    val LOOKAHEAD_TIME = 15000L
    val FORGET_THRESHOLD = -0.8F // ??? changed to see an effect
    val DEFAULT_ALPHA = 0.3F
    val C = 0.25F
    val F = 1.0F

    // initialize
    var facts = ArrayList<Fact>()
    var responses = ArrayList<Response>()


    // sent message, if fact already exists
    fun add_fact(newFact: Fact) {
        /*"""
        Add a fact to the list of study items.
        """ */
        // Ensure that a fact with this ID does not exist already
        if (facts.size > 0) {
            for (fact in facts) {
                if (fact.question == newFact.question) {
                }
            }
        }
        facts.add(newFact)
    }

    // added to fast add facts
    fun addAllFacts(facts:ArrayList<Fact>){
        this.facts = facts
    }
    // add prior responses
    fun addAllResponses(responses:ArrayList<Response>){
        // check if there was no csv file
        println("check if responses are null")
        if(responses!=null){
            this.responses = responses
        }
    }

    fun register_response(newResponse: Response) {
        // prevent dublicate responses
        if (responses.size > 0) {
            for (response in responses) {
                if (response.startTime == newResponse.startTime) {
                    println("There is already a response with the same question String")
                }
            }
        }
        responses.add(newResponse)
        println("registered new response:"+responses)
    }

    // addad that the prior fact is taken into consideration, so that it is not called again.
    fun get_next_fact(current_time:Long,previous_fact:Fact):Pair<Fact,Boolean> {
        /*
        Returns a tuple containing the fact that needs to be repeated most urgently and a boolean indicating whether this fact is new (True) or has been presented before (False).
        If none of the previously studied facts needs to be repeated right now, return a new fact instead.
         */

        // Calculate all fact activations in the near future

        var fact_activations = ArrayList<Pair<Fact,Float>>()
        for(f in facts){
            //only consider facts that are not the same as the previous one
            if (f != previous_fact) {
                fact_activations.add(
                    Pair(
                        f,
                        calculate_activation(current_time + LOOKAHEAD_TIME, f)
                    )
                )
            }
        }
        /*
        // check activations
        for(fact_activation in fact_activations){
            println("fact:" + fact_activation.first.question + "activation:" + fact_activation.second)
        }

         */


        var seen_facts = ArrayList<Pair<Fact,Float>>()
        var not_seen_facts = ArrayList<Pair<Fact,Float>>()
        for (a in fact_activations){
            if(a.second > Float.NEGATIVE_INFINITY){
                // item has been seen
                seen_facts.add(a)
            } else {
                // fact has been seen
                not_seen_facts.add(a)
            }
        }

        // Prevent an immediate repetition of the same fact
        if (seen_facts.size > 2) {
            var last_response = responses.get(responses.size-1)
            // remove the last response from seen_facts

            for(fact in seen_facts){
                if(fact.first.question==last_response.fact.question){
                    seen_facts.remove(fact)
                }
            }
        }
         // Reinforce the weakest fact with an activation below the threshold
        var seen_facts_below_threshold = ArrayList<Pair<Fact,Float>>()
        for (a in seen_facts){
            if (a.second < FORGET_THRESHOLD){
                seen_facts_below_threshold.add(a)
            }

        }
        if (not_seen_facts.size == 0 || seen_facts_below_threshold.size > 0){

            // get smallest seen_fact
            var weakest_fact = seen_facts[0]
            for (fact in seen_facts){
                if(fact.second<=fact.second){
                    weakest_fact = fact
                }
            }

            //??? problem getting the min amount of a factor
            return Pair(weakest_fact.first, false)

        }
        // If none of the previously seen facts has an activation below the threshold, return a new random fact
        return Pair(not_seen_facts[(Random.nextInt(0, not_seen_facts.size-1))].first, true)
    }



    fun get_rate_of_forgetting(time:Float, fact:Fact):Float {
        /*
                Return the estimated rate of forgetting of the fact at the specified time
                same as calculate_activation only different output
         */
        var local_encounters = ArrayList<Encounter2>()
        var responses_for_fact = ArrayList<Response>()

        for (r in responses){
            if(r.fact.question == fact.question){
                if(r.startTime < time){
                    responses_for_fact.add(r)
                }
            }
        }
        var alpha = DEFAULT_ALPHA
        // Calculate the activation by running through the sequence of previous responses
        for (response in responses_for_fact){
            var activation = calculate_activation_from_encounters(local_encounters, response.startTime)
            local_encounters.add(Encounter2(activation, response.startTime, normalise_reaction_time(response), DEFAULT_ALPHA))
            alpha = estimate_alpha(local_encounters, activation, response, alpha)

            //Update decay estimates of previous encounters
            var output_encounters = ArrayList<Encounter2>() // final output with will be used for the output
            for (encounter  in local_encounters){
                var tmp_encounter = encounter
                tmp_encounter.decay = calculate_decay(encounter.activation,alpha)
                output_encounters.add(tmp_encounter)
            }
        }
        return (alpha)
    }



    fun calculate_activation(time:Long, fact:Fact):Float{
        /*
                Calculate the activation of a fact at the given time.
         */
        var local_encounters = ArrayList<Encounter2>()

        var responses_for_fact = ArrayList<Response>()

        //only use responses before the current time
        for (r in responses){
            if(r.fact.question == fact.question){
                if(r.startTime < time){
                    responses_for_fact.add(r)
                }
            }
        }
        var alpha = DEFAULT_ALPHA
        // Calculate the activation by running through the sequence of previous responses
        for (response in responses_for_fact){
            var activation = calculate_activation_from_encounters(local_encounters, response.startTime)
            local_encounters.add(Encounter2(activation, response.startTime, normalise_reaction_time(response), DEFAULT_ALPHA))
            alpha = estimate_alpha(local_encounters, activation, response, alpha)

            //Update decay estimates of previous encounters
            var output_encounters = ArrayList<Encounter2>() // final output with will be used for the output
            for (encounter  in local_encounters){
                var tmp_encounter = encounter
                tmp_encounter.decay = calculate_decay(encounter.activation,alpha)
                output_encounters.add(tmp_encounter)

            }
        }
        return(calculate_activation_from_encounters(local_encounters, time))

    }

    fun calculate_decay(activation:Float, alpha:Float):Float{
        /*
                Calculate activation-dependent decay
         */
        val decay = C * exp(activation)+ alpha
        return decay
    }



    fun estimate_alpha( encounters: ArrayList<Encounter2>, activation: Float, response: Response, previous_alpha: Float ): Float {
        /*
                Estimate the rate of forgetting parameter (alpha) for an item.
         */

        if (encounters.size < 3) {
            return DEFAULT_ALPHA
        }

        var a_fit = previous_alpha
        var reading_time = get_reading_time(response.fact.question)
        var estimated_rt = estimate_reaction_time_from_activation(activation, reading_time)
        var est_diff = estimated_rt - normalise_reaction_time(response)

        var a0 = 0F
        var a1 = 0F
        var a0_diff = 0F
        var a1_diff = 0F
        var d_a0 = ArrayList<Encounter2>()
        var d_a1 = ArrayList<Encounter2>()

        if (est_diff < 0) {
            // Estimated RT was too short (estimated activation too high), so actual decay was larger
            a0 = a_fit
            a1 = a_fit + 0.05F
        } else {
            // Estimated RT was too long (estimated activation too low), so actual decay was smaller
            a0 = a_fit - 0.05F
            a1 = a_fit
        }
        // Binary search between previous fit and proposed alpha
        for (i in 1..6) {
            // Adjust all decays to use the new alpha
            a0_diff = a0 - a_fit
            a1_diff = a1 - a_fit

            // used to avoid exeptions in the loop
            val tmp= encounters.size - 5
            val copEncounters = encounters
            for (e in encounters) {
                d_a0.add(e) // add encounter
                d_a0[d_a0.size - 1].decay = e.decay + a0_diff // change the decay value





                d_a1.add(e)// add encounter
                d_a1[d_a1.size - 1].decay = e.decay + a1_diff // change the decay value




                // Calculate the reaction times from activation and compare against observed RTs
                val start = maxOf(1, tmp)
                var encounter_window = ArrayList<Encounter2>()

                // remove the first encounters start-1
                for (a in 0..encounter_window.size-1) {
                    encounter_window.add(encounters[a])
                }



                // now back on track with python file
                val total_a0_error =
                    calculate_predicted_reaction_time_error(encounter_window, d_a0, reading_time)
                val total_a1_error =
                    calculate_predicted_reaction_time_error(encounter_window, d_a1, reading_time)



                // Adjust the search area based on the lowest total error
                var ac = (a0 + a1) / 2
                if (total_a0_error < total_a1_error) {
                    a1 = ac
                } else {
                    a0 = ac
                }

            }
        }
        return ((a0 + a1) / 2)
    }


    fun calculate_activation_from_encounters(
        encounters: ArrayList<Encounter2>,
        current_time: Long
    ): Float {
        var include_encounters = ArrayList<Encounter2>()

        // only add encounters before the current time
        for (e in encounters) {
            if (e.time < current_time) {
                include_encounters.add(e)
            }
        }
        if (include_encounters.size == 0) {
            return Float.NEGATIVE_INFINITY
        }

        var activation = 0F
        for (e in include_encounters) {

            activation += (((current_time - e.time) / 1000).toFloat().pow(-e.decay))
        }
        return ln(activation)
    }


    fun calculate_predicted_reaction_time_error(test_set:ArrayList<Encounter2>, decay_adjusted_encounters:ArrayList<Encounter2>, reading_time:Float):Float{
            /*
        Calculate the summed absolute difference between observed response times and those predicted based on a decay adjustment.
             */
        var activations = ArrayList<Float>()
        for (e in test_set) {
            activations.add(calculate_activation_from_encounters(decay_adjusted_encounters,e.time - 100))
        }

        var estimated_rt = ArrayList<Float>()
        for (a in activations) {
            estimated_rt.add(estimate_reaction_time_from_activation(a, reading_time))
        }

        var rt_errors = 0F
        for(cnt in 0..activations.lastIndex) {
            rt_errors = test_set[cnt].reaction_time - estimated_rt[cnt]
        }
        return rt_errors
    }


    fun estimate_reaction_time_from_activation(activation:Float, reading_time:Float):Float{
        /*
        Calculate an estimated reaction time given a fact's activation and the expected reading time
         */
        return((this.F * exp(-activation) + (reading_time / 1000)) * 1000).toFloat()
    }

    fun get_max_reaction_time_for_fact(fact:Fact):Float {
        /*
        Return the highest response time we can reasonably expect for a given fact
        */
        val reading_time = get_reading_time(fact.question)
        val max_rt = 1.5 * estimate_reaction_time_from_activation(FORGET_THRESHOLD, reading_time)
        return(max_rt).toFloat()
    }

    fun get_reading_time(text: String): Float{
        var word_count = text.split(" ")

        if (word_count.size > 1) {
            var character_count = text.length
            var test:Float = (-157.9 + character_count * 19.5).toFloat()
            var test2 = maxOf(test, (300.0).toFloat())
            return test2
        }
        return((300).toFloat())
    }

    fun  normalise_reaction_time(response:Response):Float {
        /*
            Cut off extremely long responses to keep the reaction time within reasonable bounds
        */
        var rt = 0F
        if (response.correct) {
            rt = response.reactionTime
        } else {
            rt = 6000F
        }
        val max_rt = get_max_reaction_time_for_fact(response.fact)
        return (minOf(rt, max_rt))
    }
}