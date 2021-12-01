package com.example.aplicaciontest

import android.widget.TextView
import java.math.BigDecimal

open class Operations {
    data class Result(val accumulator:BigDecimal, val result: BigDecimal, var error:String = "")
    enum class Operators{PLUS,MINUS,MULTIPLY,DIVIDE,EQUAL,POWER,PERCENT,NONE}

    var accumulator:BigDecimal = BigDecimal(0)
    var result:BigDecimal = BigDecimal(0)
    var currentOperations = Operators.NONE
    var isDecimal = false
    var decimalCounter = 0


    fun restartAccumulator():Result{
        //Lo reiniciamos el acumulador y el resultado a 0
        accumulator = BigDecimal(0)
        result = BigDecimal(0)
        //Lo dibujamos en los TextView

        decimalCounter = 0
        isDecimal = false
        //Lo enviamos el resultado para que se aplique en el text View
        return Result(accumulator, result)

    }

    fun numberPressed(value:BigDecimal):Result{
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

        //Lo enviamos el resultado para que se aplique en el text View
        return Result(accumulator, result)

    }

    fun checkCanDeleteOp():Boolean{
        if  (currentOperations == Operators.NONE) {
            return true
        }

        return false

    }

    fun deleteNum():Result{
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

        //Lo enviamos el resultado para que se aplique en el text View
        return Result(accumulator, result)
    }

    fun doOperation():Result{
        var finalResult:BigDecimal
        var finalError:String = ""
        //Aqui revisamos que si la ultima operacion ha sido un igual y queremos volver a empezar sin haberle dado a reiniciar que se borre el resultado
        //o por el contrario que si queremos seguir operando con el resultado anterior
        if (currentOperations == Operators.EQUAL && accumulator != BigDecimal(0)){
            result = BigDecimal(0)
            finalResult = result
            currentOperations = Operators.NONE
        }
        if (!isDecimal) {
            var accumulatorS = "$accumulator"
            accumulatorS = accumulatorS + ".0"
            accumulator = accumulatorS.toBigDecimal()
        }
        //Aqui definimos lo que hara cada operacion
        try {
            result = when (currentOperations) {
                Operators.PLUS -> result + accumulator
                Operators.MINUS -> result - accumulator
                Operators.MULTIPLY -> result * accumulator
                Operators.DIVIDE -> result / accumulator
                Operators.EQUAL -> result
                Operators.POWER -> BigDecimal(Math.pow(result.toDouble(), accumulator.toDouble()))
                Operators.PERCENT -> result * accumulator / BigDecimal(100)
                Operators.NONE -> accumulator
            }

            finalResult = result

        } catch (e: ArithmeticException) {
            finalError = ("${e.message}")
            result = BigDecimal(0)
            finalResult = result
        }

        accumulator = BigDecimal(0)
        decimalCounter = 0
        isDecimal = false


        return Result(accumulator,finalResult,finalError)

    }

    //Estas funciones llaman a la funcion para que haga la operacion y muestran en el view port el signo de cada operacion
    fun equalFun():Result{
        val result = doOperation()
        currentOperations = Operators.EQUAL
        return result

    }
    fun plusNum():Result{
        val result = doOperation()
        currentOperations = Operators.PLUS
        return result

    }
    fun minusNum():Result{
        val result = doOperation()
        currentOperations = Operators.MINUS
        return result
    }
    fun multiplyNum():Result{
        val result = doOperation()
        currentOperations = Operators.MULTIPLY
        return result
    }
    fun divideNum():Result{
        val result = doOperation()
        currentOperations = Operators.DIVIDE
        return result
    }
    fun powerNum():Result{
        val result = doOperation()
        currentOperations = Operators.POWER
        return result

    }
    fun percentNum():Result{
        val result = doOperation()
        currentOperations = Operators.PERCENT
        return result
    }

    fun placeDot():Result{
        //aqui revisamos si es decimal y en el caso de que no lo sea agregamos un .0 al valor que tenemos y ponemos a true el bool que hace que se calcule con decimales
        if (!isDecimal){
            var accumulatorS = "$accumulator"
            accumulatorS = accumulatorS + ".0"
            accumulator = accumulatorS.toBigDecimal()
            isDecimal = true

        }

        return Result(accumulator, result)
    }


}