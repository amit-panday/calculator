package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var currentNumber = ""
    private var operator = ""
    private var firstOperand: Double? = null
    private var secondOperand: Double? = null
    private var inputSequence = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.apply {
            One.setOnClickListener { appendNumber("1") }
            Two.setOnClickListener { appendNumber("2") }
            Three.setOnClickListener { appendNumber("3") }
            four.setOnClickListener { appendNumber("4") }
            five.setOnClickListener { appendNumber("5") }
            six.setOnClickListener { appendNumber("6") }
            seven.setOnClickListener { appendNumber("7") }
            eight.setOnClickListener { appendNumber("8") }
            nine.setOnClickListener { appendNumber("9") }
            zero.setOnClickListener { appendNumber("0") }
            point.setOnClickListener {
                appendNumber(".")
            point.isEnabled = false
            }

            // Operator buttons
            addition.setOnClickListener { setOperator("+") }
            substraction.setOnClickListener { setOperator("-") }
            multiplication.setOnClickListener { setOperator("*") }
            divide.setOnClickListener { setOperator("/") }
            percent.setOnClickListener { calculatePercentage()}
            squareRoot.setOnClickListener { calculateSquareRoot() }

            // Equals button
            equal.setOnClickListener { calculateResult() }

            // Clear button
            Clear.setOnClickListener { clearCalculator() }
            cross.setOnClickListener { backspace() }
            cross.setOnLongClickListener {
                currentNumber = ""
                inputSequence=""
                enteredNoTv.text = ""
                true
            }
        }

    }
    private fun calculatePercentage() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDoubleOrNull()
            if (number != null) {
                val result = number / 100.0
                binding.enteredNoTv.text = result.toString() // Display percentage result
                clearCalculationState()
            }
        }
    }

    private fun calculateSquareRoot() {
        if (currentNumber.isNotEmpty()) {
            val number = currentNumber.toDoubleOrNull()
            if (number != null) {
                if (number < 0) {
                    binding.enteredNoTv.text = "Error" // Handle negative square root
                } else {
                    val result = kotlin.math.sqrt(number)
                    binding.enteredNoTv.text = result.toString() // Display square root result
                }
                clearCalculationState()
            }
        }
    }

    private fun appendNumber(number: String) {
        currentNumber += number
        inputSequence += number
        binding.enteredNoTv.text = inputSequence // Update the display
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toDoubleOrNull()
            operator = op
            inputSequence += " $op "
            currentNumber = ""
            binding.enteredNoTv.text = inputSequence
        }
    }
    private fun backspace() {
        if (inputSequence.isNotEmpty()) {
            // Remove the last character from the inputSequence
            inputSequence = inputSequence.dropLast(1)

            // Update currentNumber if we're currently entering a number
            if (currentNumber.isNotEmpty()) {
                currentNumber = currentNumber.dropLast(1)
            } else if (inputSequence.endsWith(" ")) {
                // If operator and spaces are removed, also reset the operator
                operator = ""
            }

            // Update the display
            binding.enteredNoTv.text = inputSequence
        }
    }
    private fun calculateResult() {
        if (currentNumber.isNotEmpty() && firstOperand != null) {
            secondOperand = currentNumber.toDoubleOrNull()
            val result = when (operator) {
                "+" -> firstOperand!! + secondOperand!!
                "-" -> firstOperand!! - secondOperand!!
                "*" -> firstOperand!! * secondOperand!!
                "/" -> {
                    if (secondOperand == 0.0) {
                        binding.enteredNoTv.text = "Error"
                        return
                    } else {
                        firstOperand!! / secondOperand!!
                    }
                }
                else -> return
            }
            binding.enteredNoTv.text = result.toString()
            clearCalculationState()
        }
    }

    private fun clearCalculator() {
        currentNumber = ""
        firstOperand = null
        secondOperand = null
        operator = ""
        inputSequence = ""
        binding.enteredNoTv.text = ""
    }

    private fun clearCalculationState() {
        currentNumber = ""
        inputSequence = ""
        firstOperand = null
        secondOperand = null
        operator = ""

    }
}
