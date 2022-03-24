package com.example.aplicaciontest

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.aplicaciontest.databinding.ActivityMainBinding
import java.io.File
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    var fileUri: Uri? = null
    var cameraData = ""
    lateinit var myText:TextView
    lateinit var myOperatorText:TextView
    lateinit var myResultText:TextView
    val numerator = Operations()
    private lateinit var binding: ActivityMainBinding
    //Esta es una variable probisional para que todo funcione
    var fullOperationCameraData = ""
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission())
        { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
            } else {
                // Explain to the user that the feature is unavailable because the
                // features requires a permission that the user has denied. At the
                // same time, respect the user's decision. Don't link to system
                // settings in an effort to convince the user to change their
                // decision.

                //TODO
            }
        }

    val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isTaken: Boolean ->
            if (isTaken) {
                val cameraReader = CameraReader()
                val errorFound = ""
                var result: Operations.Result


                fileUri?.let { it ->
                    cameraReader.getImageData(applicationContext,it) { photoData ->
                        cameraData = photoData
                        //Aqui hay que llamar a la funcion proces data cuando este acabada
                        result = processData(cameraData)

                        myText.setText(fullOperationCameraData)
                        myResultText.setText("${result.result}")
                        myOperatorText.setText("=")

                        if (errorFound != "") myResultText.setText(errorFound)

                    }
                }
            }
        }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myText = findViewById(R.id.editTextNumber)
        myOperatorText = findViewById(R.id.operatorText)
        myResultText = findViewById(R.id.result_number)

        //binding.camIcon.setOnClickListener {
        //    val intent = Intent(this, CameraActivity::class.java)
        //    startActivity(intent)
        //}

        findViewById<ImageButton>(R.id.cam_icon).setOnClickListener {

            val ctx = applicationContext ?: return@setOnClickListener

            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                // Ja tinc el permís
                openCamera()

            } else {
                // No tinc el permís i l'he de sol·licitar
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }



        myText.setText("0")
        myResultText.setText("0")

        //Cambiar todas estas funciones y pasarselo a la funcion de processData


        //Aqui llamaremos a la funcion correspondiente de cada boton definido en la clase de Operations.kt
        findViewById<Button>(R.id.key_0).setOnClickListener(getClickListerFor(0))
        findViewById<Button>(R.id.key_1).setOnClickListener(getClickListerFor(1))
        findViewById<Button>(R.id.key_2).setOnClickListener(getClickListerFor(2))
        findViewById<Button>(R.id.key_3).setOnClickListener(getClickListerFor(3))
        findViewById<Button>(R.id.key_4).setOnClickListener(getClickListerFor(4))
        findViewById<Button>(R.id.key_5).setOnClickListener(getClickListerFor(5))
        findViewById<Button>(R.id.key_6).setOnClickListener(getClickListerFor(6))
        findViewById<Button>(R.id.key_7).setOnClickListener(getClickListerFor(7))
        findViewById<Button>(R.id.key_8).setOnClickListener(getClickListerFor(8))
        findViewById<Button>(R.id.key_9).setOnClickListener(getClickListerFor(9))

        findViewById<Button>(R.id.key_equal).setOnClickListener {
            val result = numerator.equalFun()
            myText.setText("${result.calculatedOperation}")
            if (result.error == ""){
                myResultText.setText("${result.result}")
                myOperatorText.setText("=")
            }else{
                myResultText.setText(result.error)
            }
        }

        findViewById<Button>(R.id.key_plus).setOnClickListener {
            val result = numerator.plusNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("+")

        }

        findViewById<Button>(R.id.key_minus).setOnClickListener {
            val result = numerator.minusNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("-")

        }

        findViewById<Button>(R.id.key_multiply).setOnClickListener {
            val result = numerator.multiplyNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("x")

        }

        findViewById<Button>(R.id.key_divide).setOnClickListener {
            val result = numerator.divideNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("/")

        }

        findViewById<Button>(R.id.key_delete).setOnClickListener {
            val result = numerator.deleteNum()
            myText.setText("${result.calculatedOperation}")

        }

        findViewById<Button>(R.id.key_reset).setOnClickListener {
            val result =  numerator.restartAccumulator()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("")

        }
        findViewById<Button>(R.id.key_power).setOnClickListener {
            val result = numerator.powerNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("^")
        }
        findViewById<Button>(R.id.key_percent).setOnClickListener {
            val result = numerator.percentNum()
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("%")

        }

        findViewById<Button>(R.id.key_dot).setOnClickListener {
            val result = numerator.placeDot()
            myText.setText("${result.calculatedOperation}")
        }
        /*setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
    }

    fun getClickListerFor(number: Int): View.OnClickListener {
        return View.OnClickListener {
            val result = numerator.numberPressed(BigDecimal(number))
            myText.setText("${result.calculatedOperation}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
    }

    fun openCamera() {

        val file = File.createTempFile(
            "example_image", ".jpg",
           externalCacheDir
        )

        fileUri = FileProvider.getUriForFile(
            this,
            "com.example.aplicaciontest.fileprovider",file
        )



        cameraLauncher.launch(fileUri)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }









    fun processData(dataToProces:String) : Operations.Result{

        val errorFound:String = ""
        var result = numerator.equalFun()
        var filteringResult = ""
        var orderedOperation = ""

        result = numerator.restartAccumulator()
        if (dataToProces != "") {

            val blockList : ArrayList<String>

            filteringResult = filterCameraData(dataToProces)
            //Esto es para que funcione todo para la entrega
            fullOperationCameraData = filteringResult

            blockList = divideInBlocks(filteringResult)

            orderedOperation = reorderOperation(blockList)

            //Bucle que interpreta los datos que hemos sacado y los calcula
            orderedOperation.forEach {

                // Aqui para acabar dividiremos por char el string ordenado y llamaremos a las funciones de la clase Operators.kt para calcular la operacion
                when(it){

                    '0' -> result = numerator.numberPressed(BigDecimal(0))

                    '1' -> result = numerator.numberPressed(BigDecimal(1))

                    '2' -> result = numerator.numberPressed(BigDecimal(2))

                    '3' -> result = numerator.numberPressed(BigDecimal(3))

                    '4' -> result = numerator.numberPressed(BigDecimal(4))

                    '5' -> result = numerator.numberPressed(BigDecimal(5))

                    '6' -> result = numerator.numberPressed(BigDecimal(6))

                    '7' -> result = numerator.numberPressed(BigDecimal(7))

                    '8' -> result = numerator.numberPressed(BigDecimal(8))

                    '9' -> result = numerator.numberPressed(BigDecimal(9))

                    '+' -> result = numerator.plusNum()

                    '-' -> result = numerator.minusNum()

                    'X' -> result = numerator.multiplyNum()

                    '/' -> result = numerator.divideNum()

                    '^' -> result = numerator.powerNum()

                    '%' -> result = numerator.percentNum()

                    '.' -> result = numerator.placeDot()
                }


            }


            result = numerator.doOperation()


        }
        return result
    }


    fun filterCameraData(dataFromCamera: String) : String{

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

    fun divideInBlocks(filteringResult:String) :ArrayList<String>{
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

    fun reorderOperation( blockList: ArrayList<String>) :String{

        //Bucle que reordena la operacion para que las operaciones con prioridad se hagan las primeras


        var orderedBlocks = ""
        // Hacemos una lista con la misma cantidad de bloques que hay para saber si un elemento se ha movido en algun momento
        val checkedBlock = arrayListOf<Boolean>()
        blockList.forEachIndexed{index, elem->
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

        }

        // Al acabar de analizar los valores afectados por la operacion agregamos un signo de sumar para que encadene con las proximas operaciones
        // (7*3)+(-5/8)+3
        orderedBlocks += "+"

        // Al acabar de analizar todas las operaciones con prioridad agregaremos a la cola  todo lo demas
        blockList.forEachIndexed { index, elem ->
            if (!checkedBlock[index]){
                orderedBlocks += blockList[index]
            }

        }

        return orderedBlocks

    }


}