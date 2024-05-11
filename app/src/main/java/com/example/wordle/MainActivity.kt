package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.view.Gravity

class MainActivity : AppCompatActivity() {

    private var wordToGuess: String = ""
    private var guessesLeft = 3
    private lateinit var guessEditText: EditText
    private lateinit var submitButton: Button

    private fun checkGuess(guess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }

    private fun startGame() {
        var fourLetterWorldList = FourLetterWordList
        wordToGuess = fourLetterWorldList.getRandomFourLetterWord()
        guessesLeft = 3
        submitButton.isEnabled = true
    }

    private fun guessHandler (guess: String) {
        var guessNumber = 4 - guessesLeft

        var checkResult = checkGuess(guess)

        val scrollView: ScrollView = findViewById(R.id.scrollView)
        val linearLayout: LinearLayout = findViewById(R.id.linearLayout)

        val pairLayout = LinearLayout(this)
        pairLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        pairLayout.orientation = LinearLayout.VERTICAL

        val line1Layout = LinearLayout(this)
        line1Layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        line1Layout.orientation = LinearLayout.HORIZONTAL

        val leftTextView1 = TextView(this)
        leftTextView1.text = "Guess #$guessNumber"
        leftTextView1.gravity = Gravity.START
        leftTextView1.textSize = 24.0f
        leftTextView1.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        val rightTextView1 = TextView(this)
        rightTextView1.text = guess.lowercase()
        rightTextView1.gravity = Gravity.END
        rightTextView1.textSize = 24.0f
        rightTextView1.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        line1Layout.addView(leftTextView1)
        line1Layout.addView(rightTextView1)

        val line2Layout = LinearLayout(this)
        line2Layout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        line2Layout.orientation = LinearLayout.HORIZONTAL

        val leftTextView2 = TextView(this)
        leftTextView2.text = "Guess #$guessNumber Check"
        leftTextView2.gravity = Gravity.START
        leftTextView2.textSize = 24.0f
        leftTextView2.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )

        val rightTextView2 = TextView(this)
        rightTextView2.text = checkResult
        rightTextView2.gravity = Gravity.END
        rightTextView2.textSize = 24.0f
        rightTextView2.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        line2Layout.addView(leftTextView2)
        line2Layout.addView(rightTextView2)

        pairLayout.addView(line1Layout)
        pairLayout.addView(line2Layout)

        linearLayout.addView(pairLayout)

        guessesLeft--

        if (guessesLeft == 0 || wordToGuess == guess) {
            submitButton.isEnabled = false
            val result: TextView = findViewById<EditText>(R.id.result)
            result.setText(wordToGuess.lowercase())
        }
        guessEditText.text.clear()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        submitButton = findViewById<Button>(R.id.submitButton)
        guessEditText = findViewById<EditText>(R.id.guessEditText)
        startGame()
        submitButton.setOnClickListener {
            guessHandler(guessEditText.text.toString().uppercase())

        }
    }
}