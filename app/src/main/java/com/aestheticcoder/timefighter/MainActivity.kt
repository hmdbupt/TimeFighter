package com.aestheticcoder.timefighter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

//    Declaring variables for the button, two text views, and score
    internal var score = 0
    internal lateinit var tapMeButton: Button
    internal lateinit var timeLeftTextView: TextView
    internal lateinit var yourScoreTextView: TextView

    internal var gameStarted = false

    internal lateinit var countDownTimer: CountDownTimer
    internal var initialCountDown: Long = 60000
    internal var countDownInterval: Long = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Assigning views to our variables (instances)
        tapMeButton = findViewById(R.id.tap_me_button)
        timeLeftTextView = findViewById(R.id.time_left_textview)
        yourScoreTextView = findViewById(R.id.your_score_textview)

//        Puts 0 in Your Score text view instead of %1$d
//        yourScoreTextView.text = getString(R.string.your_score, 0)
//        Puts 0 in Your Score text view instead of %1$d
//        timeLeftTextView.text = getString(R.string.time_left, 0)

//      Sets everything to initial settings when the application launches
        resetGame()

//      When the user clicks the tap me button the score increases by one
        tapMeButton.setOnClickListener {view ->
            increaseScore()
        }
    }

    private fun resetGame(){
        score = 0

        yourScoreTextView.text = getString(R.string.your_score, score)

        val initialTimeLeft = initialCountDown / 1000
        timeLeftTextView.text = getString(R.string.time_left, initialTimeLeft)

        countDownTimer = object: CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
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
