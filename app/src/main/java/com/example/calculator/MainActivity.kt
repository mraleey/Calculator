package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var input = findViewById<TextView>(R.id.input)
        var result = findViewById<TextView>(R.id.result)
        val buttons = listOf(
            findViewById<Button>(R.id.btn0),
            findViewById<Button>(R.id.btn1),
            findViewById<Button>(R.id.btn2),
            findViewById<Button>(R.id.btn3),
            findViewById<Button>(R.id.btn4),
            findViewById<Button>(R.id.btn5),
            findViewById<Button>(R.id.btn6),
            findViewById<Button>(R.id.btn7),
            findViewById<Button>(R.id.btn8),
            findViewById<Button>(R.id.btn9),
            findViewById<Button>(R.id.btnPlus),
            findViewById<Button>(R.id.btnMinus),
            findViewById<Button>(R.id.btnMultiply),
            findViewById<Button>(R.id.btnDivide),
            findViewById<Button>(R.id.btnAC),
            findViewById<Button>(R.id.btnC),
            findViewById<Button>(R.id.btnEqual)
        )

        var expression = ""

        buttons.forEach { button ->
            button.setOnClickListener {
                when (button.text) {
                    "AC" -> {
                        expression = ""
                        input.text = ""
                        result.text = ""
                    }
                    "C" -> {
                        expression = expression.dropLast(1)
                        input.text = expression
                    }
                    "=" -> {
                        try {
                            result.text = eval(expression).toString()
                        } catch (e: Exception) {
                            result.text = "Error"
                        }
                    }
                    (expression.isNotEmpty() && listOf("+", "-", "*", "/").contains(expression.last().toString())).toString() -> {
                        expression = expression.dropLast(1) + button.text
                        input.text = expression
                    }
                    else -> {
                        expression += button.text
                        input.text = expression
                    }
                }
            }
        }
    }

    private fun eval(expression: String): Int {
        return expression.split("+", "-", "*", "/").map { it.toInt() }.reduce { acc, i ->
            when {
                expression.contains("+") -> acc + i
                expression.contains("-") -> acc - i
                expression.contains("*") -> acc * i
                expression.contains("/") -> acc / i
                else -> throw Exception()
            }
        }
    }

}