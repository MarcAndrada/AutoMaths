package com.example.aplicaciontest

import android.widget.TextView
import java.math.BigDecimal

class Operations {
    enum class Operators{PLUS,MINUS,MULTIPLY,DIVIDE, NONE}

    var accumulator:BigDecimal = BigDecimal(0)
    var result:BigDecimal = BigDecimal(0)
    var currentOperations = Operators.NONE
    var isDecimal = false
    var decimalCounter = 0

    fun doOperation(writeText: TextView,resultText:TextView){
        result = when(currentOperations){
            Operators.PLUS->  result + accumulator
            Operators.MINUS -> result - accumulator
            Operators.MULTIPLY -> result * accumulator
            Operators.DIVIDE -> result / accumulator
            Operators.NONE -> accumulator
        }
        accumulator = BigDecimal(0)

        writeText.setText("$accumulator")
        resultText.setText("$result")

    }

    fun equalFun(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.NONE
        operatorText.setText("=")

    }

    fun NumberPressed(value:BigDecimal, writeText: TextView, resultText:TextView, operatorText:TextView){

        if(!isDecimal) {
            accumulator = accumulator * BigDecimal(10) + value

        }else{
            decimalCounter++
            var divisibleValue = value
            for (i in 0 until decimalCounter) {
                divisibleValue = divisibleValue.divide(BigDecimal(10))
            }
            accumulator += divisibleValue
        }

        writeText.setText("$accumulator")
        resultText.setText("$result")

        if (checkCanDeleteOp()) operatorText.setText("")

    }

    fun DeleteNum(writeText: TextView){
        var accumulatorS = "$accumulator"
        if (decimalCounter == 0){
            isDecimal = false
        }
        if(isDecimal){
            decimalCounter--
        }
        accumulatorS = accumulatorS.substring(0, accumulatorS.length - 1)

        if(accumulatorS != "") {
            accumulator = accumulatorS.toBigDecimal()
        }else{
            accumulator = BigDecimal(0)
        }
        writeText.setText("$accumulator")
    }

    fun plusNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.PLUS
        operatorText.setText("+")

    }
    fun minusNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.MINUS
        operatorText.setText("-")

    }
    fun multiplyNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.MULTIPLY
        operatorText.setText("x")

    }
    fun divideNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.DIVIDE
        operatorText.setText("/")

    }

    fun restartAccumulator(writeText: TextView, resultText:TextView,operatorText:TextView){
        accumulator = BigDecimal(0)
        result = BigDecimal(0)
        writeText.setText("$accumulator")
        resultText.setText("$result")
        operatorText.setText("")
    }

    fun placeDot(writeText: TextView){
        if (!isDecimal){
            var accumulatorS = "$accumulator"
            accumulatorS = accumulatorS + ".0"
            accumulator = accumulatorS.toBigDecimal()
            writeText.setText("$accumulator")
            isDecimal = true

        }

    }

    fun checkCanDeleteOp():Boolean{
        if  (currentOperations == Operators.NONE) {
            return true
        }

        return false

    }

}