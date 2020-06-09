package com.aestheticcoder.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //  Declaring variables

    // Saves the current score
    private var score = 0
    // Instances of Button and TextViews
    private lateinit var tapMeButton: Button
    private lateinit var timeLeftTextView: TextView
    private lateinit var yourScoreTextView: TextView

    // A flag for checking whether the game has started or not.
    private var gameStarted = false

    // An instance of type CountDownTimer
    private lateinit var countDownTimer: CountDownTimer
    // It will save the initial value of 60 secs
    internal var initialCountDown: Long = 60000
    // The interval of 1 sec
    internal var countDownInterval: Long = 1000
    // It will save the time left in case the user rotates
    // the device to the landscape mode
    internal var timeLeftOnTimer: Long = 60000

    /*
    A companion object is equivalent to 'static' objects in Java
    It is common to all instances of the classes. It can access
    all the members of the class, including the private constructors.
    A companion object is initialized when the class is instantiated.
    A companion object cannot be declared outside the class.
    */

    companion object {
        // The TAG saves the name of the class
        private val TAG = MainActivity::class.java.simpleName

        // Score and time left keys for sending to bundle
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }

    // This is the first method that runs when the app starts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is the main layout that will be displayed when the app launches
        setContentView(R.layout.activity_main)

        // Using Log for debugging
        Log.d(TAG, "onCreate called. Score is: $score")

        // Assigning views to our variables (instances)
        tapMeButton = findViewById(R.id.tap_me_button)
        timeLeftTextView = findViewById(R.id.time_left_textview)
        yourScoreTextView = findViewById(R.id.your_score_textview)

        /*
        Puts 0 in Your Score text view instead of %1$d
        yourScoreTextView.text = getString(R.string.your_score, 0)
        Puts 0 in Your Score text view instead of %1$d
        timeLeftTextView.text = getString(R.string.time_left, 0)
        */

        // Sets everything to initial settings when the application launches
        resetGame()

        // When the user clicks the tap me button the score increases by one
        tapMeButton.setOnClickListener {view ->
            // Function call for increasing the score
            increaseScore()
        }
    }

    /*
     This function saves the state of our variables that we can pass into
     newly created activity. So that we don't lose our data. This function
     takes an instance of type Bundle as a parameter.
     */

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        /*
         putInt requires a key and a variable that needs to be stored in that key
         we can restore our data by using these keys
         */
        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        // Stops countdown timer
        countDownTimer.cancel()
        // A log message that will display the score and time left when the activity stops
        Log.d(TAG, "onSaveInstanceState: Saving score: $score and time left: $timeLeftOnTimer")
    }

    // This function is called when the activity is destroyed
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called.")
    }

    // Resets the game to initial condition
    private fun resetGame(){
        score = 0

        yourScoreTextView.text = getString(R.string.your_score, score)

        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft)

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                timeLeftTextView.text = getString(R.string.time_left, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
        gameStarted = false
    }


    private fun increaseScore() {
        // Checks if the gameStarted flag is set to false or not
        // If it is false then the startGame function will be called
        if (!gameStarted){
            startGame()
        }
        // Increase score by one
        score += 1
        // getString takes two parameters the first one is the your_score string
        // variable that is stored in the strings.xml file and the second one is
        // the score variable that will give the score to the %1$d in the strings.xml
        // The getString will return "Your Score: 0" (0 will increment with each tap)
        val newScore = getString(R.string.your_score, score)
        // The newScore string will be assigned to the yourScoreTextView and it will
        // show the message
        yourScoreTextView.text = newScore
    }

    // This function starts the countdown timer and sets the gameStarted flag to true
    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }

    // This function ends the game when the countdown timer finishes. It also displays the
    // score inside a toast message and resets the game to initial condition.
    private fun endGame(){
        Toast.makeText(this, getString(R.string.final_score, score), Toast.LENGTH_LONG).show()
        resetGame()
    }
}
