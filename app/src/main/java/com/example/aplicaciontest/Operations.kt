package com.example.aplicaciontest

import java.math.BigDecimal
import kotlin.math.pow

open class Operations {
    data class Result(val calculatedOperation:BigDecimal, val result: BigDecimal, val error:String = "", var allOperation:String = "")
    enum class Operators{PLUS,MINUS,MULTIPLY,DIVIDE,EQUAL,POWER,PERCENT,NONE}

    var fullOperation = ""
    private var accumulator:BigDecimal = BigDecimal(0)
    private var result:BigDecimal = BigDecimal(0)
    private var currentOperations = Operators.NONE
    private var isDecimal = false
    private var decimalCounter = 0


    fun inputPressed(_input: String) : String {
        fullOperation += _input
        return fullOperation
    }



    fun restartAccumulator(_restartEverything: Boolean = false):Result{
        currentOperations = Operators.NONE
        //Lo reiniciamos el acumulador y el resultado a 0
        accumulator = BigDecimal(0)
        result = BigDecimal(0)
        //Lo dibujamos en los TextView

        decimalCounter = 0
        isDecimal = false
        if (_restartEverything){
            fullOperation = ""
        }
        //Lo enviamos el resultado para que se aplique en el text View
        return Result(accumulator, result)

    }

    private fun numberPressed(value:BigDecimal):Result{
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
        return Result(accumulator,result)

    }

    fun checkCanDeleteOp():Boolean{
        if  (currentOperations == Operators.NONE) {
            return true
        }

        return false

    }

    fun deleteNum():String{

        //Dependiendo del valor decidimos si quitar o no quitar el ultimo valor para evitar que pete

        if(fullOperation != "") {
            //En el caso de que quitemos el ultimo valor y el string no este vacio lo igualaremos el acumulador al string que hemos creado
            fullOperation = fullOperation.substring(0, fullOperation.length - 1)

        }else{
            //Y si le pasamos un string vacio accumulador sera 0
            fullOperation = "0"
        }


        return fullOperation

    }

    private fun doOperation():Result{
        var finalResult:BigDecimal
        var finalError = ""
        //Aqui revisamos que si la ultima operacion ha sido un igual y queremos volver a empezar sin haberle dado a reiniciar que se borre el resultado
        //o por el contrario que si queremos seguir operando con el resultado anterior
        if (currentOperations == Operators.EQUAL && accumulator != BigDecimal(0)){
            result = BigDecimal(0)
            accumulator = result
            currentOperations = Operators.NONE
        }
        if (!isDecimal) {
            var accumulatorS = "$accumulator"
            accumulatorS += ".0"
            accumulator = accumulatorS.toBigDecimal()
        }
        //Aqui definimos lo que hara cada operacion
        try {
            when (currentOperations) {
                Operators.PLUS -> let{
                    result += accumulator
                }
                Operators.MINUS -> let{
                    result -= accumulator
                }
                Operators.MULTIPLY -> let{
                    result *= accumulator
                }
                Operators.DIVIDE ->let{
                    result /= accumulator
                }
                Operators.EQUAL -> result
                Operators.POWER -> BigDecimal(result.toDouble().pow(accumulator.toDouble()))
                Operators.PERCENT -> result * accumulator / BigDecimal(100)
                Operators.NONE -> result = accumulator
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
            accumulatorS += ".0"
            accumulator = accumulatorS.toBigDecimal()
            isDecimal = true

        }

        return Result(accumulator, result)
    }




    fun processData(dataToProcess:String) : Result{

        restartAccumulator()
        var result: Result
        val filteringResult: String
        val orderedOperation: String

        result = Result(BigDecimal(0),BigDecimal(0), "" , "")
        if (dataToProcess != "") {

            filteringResult = filterCameraData(dataToProcess)

            val blockList : ArrayList<String> = divideInBlocks(filteringResult)

            orderedOperation = reorderOperation(blockList)

            //Bucle que interpreta los datos que hemos sacado y los calcula
            orderedOperation.forEach {

                // Aqui para acabar dividiremos por char el string ordenado y llamaremos a las funciones de la clase Operators.kt para calcular la operacion
                when(it){

                    '0' -> result = numberPressed(BigDecimal(0))

                    '1' -> result = numberPressed(BigDecimal(1))

                    '2' -> result = numberPressed(BigDecimal(2))

                    '3' -> result = numberPressed(BigDecimal(3))

                    '4' -> result = numberPressed(BigDecimal(4))

                    '5' -> result = numberPressed(BigDecimal(5))

                    '6' -> result = numberPressed(BigDecimal(6))

                    '7' -> result = numberPressed(BigDecimal(7))

                    '8' -> result = numberPressed(BigDecimal(8))

                    '9' -> result = numberPressed(BigDecimal(9))

                    '+' -> result = plusNum()

                    '-' -> result = minusNum()

                    'X' -> result = multiplyNum()

                    '/' -> result = divideNum()

                    '^' -> result = powerNum()

                    '%' -> result = percentNum()

                    '.' -> result = placeDot()
                }


            }


            result = doOperation()
            result.allOperation = filteringResult

        }
        return result
    }


    private fun filterCameraData(dataFromCamera: String) : String{

        var operationToFilter = ""
        //Bucle  para encontrar los inputs
        dataFromCamera.forEach {
            //filtramos todos los caracteres encontrados despues de hacer la foto para juntarlos en un string (filteringResult)
            when (it) {
                '0' -> operationToFilter += '0'

                //'o' -> filteringResult += '0'

                //'O' -> filteringResult += '0'

                '1' -> operationToFilter += '1'

                '2' -> operationToFilter += '2'

                '3' -> operationToFilter += '3'

                '4' -> operationToFilter += '4'

                '5' -> operationToFilter += '5'

                '6' -> operationToFilter += '6'

                '7' -> operationToFilter += '7'

                '8' -> operationToFilter += '8'

                '9' -> operationToFilter += '9'

                '+' -> operationToFilter += '+'

                '-' -> operationToFilter += '-'

                'x' -> operationToFilter += 'X'

                'X' -> operationToFilter += 'X'

                '*' -> operationToFilter += 'X'

                '/' -> operationToFilter += '/'

                '^' -> operationToFilter += '^'

                '%' -> operationToFilter += '%'

                '.' -> operationToFilter += '.'


            }

        }

        return operationToFilter
    }

    private fun divideInBlocks(filteringResult:String) :ArrayList<String>{
        //Bucle para dividir el contenido en bloques
        var blockIndex = 0
        val blockList = arrayListOf("")
        filteringResult.forEach {
            // Aqui en caso de encontrar alguna operacion dividimos por bloques la operacion que recibimos ej:b1(14)b2(+)b3(37)
            if (it == 'X' || it == '/' || it == '-' || it == '+') {
                blockIndex++
                // Al encontrar un operador incrementaremos el numero de bloques que tenemos y los separaremos
                // En caso de que sea miltiplicacion o division marcaremos como que se tiene que reordenar la operacion
                when (it) {
                    '/' -> {
                        blockList.add("/")
                    }
                    'X' -> {
                        blockList.add("X")
                    }
                    '+' -> {
                        blockList.add("+")
                    }
                    else -> { // if (it == '-') {
                        blockList.add("-")
                    }
                }


                blockIndex++
                blockList.add("")
            } else {
                // Si no encuentra ningun operador lo sigue sumando todo al mismo bloque
                blockList[blockIndex] += it.toString()
            }

        }

        return blockList

    }

    private fun reorderOperation(blockList: ArrayList<String>) :String{

        //Bucle que reordena la operacion para que las operaciones con prioridad se hagan las primeras


        var orderedBlocks = ""
        // Hacemos una lista con la misma cantidad de bloques que hay para saber si un elemento se ha movido en algun momento
        val checkedBlock = arrayListOf<Boolean>()
        blockList.forEachIndexed{ index, _ ->
            // Inicializamos la lista con el valor por defecto
            checkedBlock.add(index, false)
        }
        blockList.forEachIndexed{index, elem->
            if(elem == "X" || elem == "/"){
                //Cuando encuentre una multiplicacion o division empieza a reordenarlo
                if(!checkedBlock[index - 1]) {
                    // En caso de que ningun valor de los que tiene en los lados de la multiplicacion haya sido modificado continuara por aqui
                    // Ej: (7*3)+3(-5/8) <- Aqui desplaza estas dos operaciones separadas hacia la primera posicion (o la mas cercana)
                    if (index - 2 >= 0){
                        //Aqui revisamos si el valor del frente tiene algun operador para llevarnoslo tambien (por si es negativo)
                        // Ej: (-)7*2
                        // Si no hay nada (es positivo) no hara nada (en caso de que sea la primera posicion) o cogera el signo de suma para borrarlo
                        orderedBlocks += blockList[index - 2]
                        checkedBlock[index - 2] = true
                    }
                    // Luego cogemos el valor delantero
                    // (7)*2
                    orderedBlocks += blockList[index - 1]
                    checkedBlock[index - 1] = true
                    // El operador
                    // 7(*)2
                    orderedBlocks += blockList[index]
                    checkedBlock[index] = true
                    // Y el valor trasero
                    // 7*(2)
                    orderedBlocks += blockList[index + 1]
                    checkedBlock[index + 1] = true
                }else{
                    // En el caso de que haya encontrado algun valor modificado continuara por aqui Ej: 7*3(/8)
                    // Aqui solo cogeremos el operador 7*3(/)8
                    orderedBlocks += blockList[index]
                    checkedBlock[index] = true
                    // Y el valor trasero 7*3/(8)
                    orderedBlocks += blockList[index + 1]
                    checkedBlock[index + 1] = true
                }

                // Se marcaran todos los valores y operaciones utilizados como que han sido movidos
            }

            // Al acabar de analizar los valores afectados por la operacion agregamos un signo de sumar para que encadene con las proximas operaciones
            // (7*3)+(-5/8)+3
            orderedBlocks += "+"
        }



        // Al acabar de analizar todas las operaciones con prioridad agregaremos a la cola  todo lo demas
        blockList.forEachIndexed { index, _ ->
            if (!checkedBlock[index]){
                orderedBlocks += blockList[index]
            }

        }

        return orderedBlocks

    }



}