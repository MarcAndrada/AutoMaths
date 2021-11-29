package com.example.aplicaciontest

import android.widget.TextView
import java.math.BigDecimal

class Operations {
    enum class Operators{PLUS,MINUS,MULTIPLY,DIVIDE,EQUAL,POWER,PERCENT,NONE}

    var accumulator:BigDecimal = BigDecimal(0)
    var result:BigDecimal = BigDecimal(0)
    var currentOperations = Operators.NONE
    var isDecimal = false
    var decimalCounter = 0


    fun restartAccumulator(writeText: TextView, resultText:TextView,operatorText:TextView){
        //Lo reiniciamos el acumulador y el resultado a 0
        accumulator = BigDecimal(0)
        result = BigDecimal(0)
        //Lo dibujamos en los TextView
        writeText.setText("$accumulator")
        resultText.setText("$result")
        operatorText.setText("")
        decimalCounter = 0
        isDecimal = false

    }

    fun NumberPressed(value:BigDecimal, writeText: TextView, resultText:TextView, operatorText:TextView){
        //Definimos de que forma se insertaran los numeros segun si son decimales o no
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

        //escribimos el resultado en el text view
        writeText.setText("$accumulator")
        resultText.setText("$result")


        //si no estamos en medio de una operacion borraremos el simbolo de la operacion
        if (checkCanDeleteOp()) operatorText.setText("")

    }

    fun checkCanDeleteOp():Boolean{
        if  (currentOperations == Operators.NONE) {
            return true
        }

        return false

    }

    fun DeleteNum(writeText: TextView){
        var accumulatorS = "$accumulator"
        //Si es decimal restamos el contador de decimales, si no hay ningun decimal dejara de ser un numero decimal
        if(isDecimal){
            decimalCounter--
            if (decimalCounter == 0){
                isDecimal = false
            }
        }
        //Quitamos el ultimo valor que hay en nuestro acumulador
        accumulatorS = accumulatorS.substring(0, accumulatorS.length - 1)

        //Dependiendo del valor decidimos si quitar o no quitar el ultimo valor para evitar que pete

        if(accumulatorS != "") {//En el caso de que quitemos el ultimo valor y el string no este vacio lo igualaremos el acumulador al string que hemos creado
            accumulator = accumulatorS.toBigDecimal()
        }else{ //Y si le pasamos un string vacio accumulador sera 0
            accumulator = BigDecimal(0)
        }

        //Lo escribimos en el text View
        writeText.setText("$accumulator")
    }

    fun doOperation(writeText: TextView,resultText:TextView){

        //Aqui revisamos que si la ultima operacion ha sido un igual y queremos volver a empezar sin haberle dado a reiniciar que se borre el resultado
        //o por el contrario que si queremos seguir operando con el resultado anterior
        if (currentOperations == Operators.EQUAL && accumulator != BigDecimal(0)){
            result = BigDecimal(0)
            currentOperations = Operators.NONE
        }
        if (!isDecimal) {
            var accumulatorS = "$accumulator"
            accumulatorS = accumulatorS + ".0"
            accumulator = accumulatorS.toBigDecimal()
        }
        //Aqui definimos lo que hara cada operacion
        result = when(currentOperations){
            Operators.PLUS ->  result + accumulator
            Operators.MINUS -> result - accumulator
            Operators.MULTIPLY -> result * accumulator
            Operators.DIVIDE -> result / accumulator
            Operators.EQUAL -> result
            Operators.POWER -> BigDecimal(Math.pow(result.toDouble(),accumulator.toDouble()))
            Operators.PERCENT -> result * accumulator / BigDecimal(100)
            Operators.NONE -> accumulator

        }

        accumulator = BigDecimal(0)

        writeText.setText("$accumulator")
        resultText.setText("$result")
        decimalCounter = 0
        isDecimal = false
    }

    //Estas funciones llaman a la funcion para que haga la operacion y muestran en el view port el signo de cada operacion
    fun equalFun(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.EQUAL
        operatorText.setText("=")

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
    fun powerNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.POWER
        operatorText.setText("^")

    }
    fun percentNum(writeText: TextView,resultText:TextView,operatorText:TextView){
        doOperation(writeText,resultText)
        currentOperations = Operators.PERCENT
        operatorText.setText("%")

    }

    fun placeDot(writeText: TextView){
        //aqui revisamos si es decimal y en el caso de que no lo sea agregamos un .0 al valor que tenemos y ponemos a true el bool que hace que se calcule con decimales
        if (!isDecimal){
            var accumulatorS = "$accumulator"
            accumulatorS = accumulatorS + ".0"
            accumulator = accumulatorS.toBigDecimal()
            writeText.setText("$accumulator")
            isDecimal = true

        }

    }


}