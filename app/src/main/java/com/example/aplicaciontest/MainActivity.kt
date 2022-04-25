package com.example.aplicaciontest

import android.Manifest
import android.content.Intent
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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.aplicaciontest.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private var fileUri: Uri? = null
    private var cameraData = ""
    private lateinit var myText:TextView
    private lateinit var myOperatorText:TextView
    private lateinit var myResultText:TextView
    private  val numerator = Operations()
    private lateinit var binding: ActivityMainBinding

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

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isTaken: Boolean ->
            if (isTaken) {
                val cameraReader = CameraReader()
                val errorFound = ""
                var result: Operations.Result


                fileUri?.let { it ->
                    cameraReader.getImageData(applicationContext,it) { photoData ->
                        cameraData = photoData
                        //Aqui hay que llamar a la funcion proces data cuando este acabada
                        result = numerator.processData(cameraData)

                        myText.text = result.allOperation
                        myResultText.text = "${result.result}"
                        myOperatorText.text = "="

                        if (errorFound != "") myResultText.text = errorFound

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
        binding.historyIcon.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }

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



        myText.text = "0"
        myResultText.text = "0"

        //Cambiar todas estas funciones y pasarselo a la funcion de processData


        //Aqui llamaremos a la funcion correspondiente de cada boton definido en la clase de Operations.kt
        findViewById<Button>(R.id.key_0).setOnClickListener(getClickListerFor("0"))
        findViewById<Button>(R.id.key_1).setOnClickListener(getClickListerFor("1"))
        findViewById<Button>(R.id.key_2).setOnClickListener(getClickListerFor("2"))
        findViewById<Button>(R.id.key_3).setOnClickListener(getClickListerFor("3"))
        findViewById<Button>(R.id.key_4).setOnClickListener(getClickListerFor("4"))
        findViewById<Button>(R.id.key_5).setOnClickListener(getClickListerFor("5"))
        findViewById<Button>(R.id.key_6).setOnClickListener(getClickListerFor("6"))
        findViewById<Button>(R.id.key_7).setOnClickListener(getClickListerFor("7"))
        findViewById<Button>(R.id.key_8).setOnClickListener(getClickListerFor("8"))
        findViewById<Button>(R.id.key_9).setOnClickListener(getClickListerFor("9"))

        findViewById<Button>(R.id.key_plus).setOnClickListener(getClickListerFor("+"))
        findViewById<Button>(R.id.key_minus).setOnClickListener(getClickListerFor("-"))
        findViewById<Button>(R.id.key_multiply).setOnClickListener(getClickListerFor("x"))
        findViewById<Button>(R.id.key_divide).setOnClickListener(getClickListerFor("/"))
        findViewById<Button>(R.id.key_power).setOnClickListener(getClickListerFor("^"))
        findViewById<Button>(R.id.key_percent).setOnClickListener(getClickListerFor("%"))
        findViewById<Button>(R.id.key_dot).setOnClickListener(getClickListerFor("."))

        findViewById<Button>(R.id.key_equal).setOnClickListener {
            val result = numerator.processData(numerator.fullOperation)
            myText.text = result.allOperation
            myResultText.text = "${result.result}"
            myOperatorText.text = ""
        }

        findViewById<Button>(R.id.key_delete).setOnClickListener {
            val dataToProcess = numerator.deleteNum()
            val result = numerator.processData(dataToProcess)
            myText.text = result.allOperation
            myResultText.text = "${result.result}"
        }

        findViewById<Button>(R.id.key_reset).setOnClickListener {
            val result =  numerator.restartAccumulator(true)
            myText.text = "${result.calculatedOperation}"
            myResultText.text = "${result.result}"
            myOperatorText.text = ""

        }

    }

    private fun getClickListerFor(_parameter: String): View.OnClickListener {
        return View.OnClickListener {
            val dataExtract = numerator.inputPressed(_parameter)
            val result = numerator.processData(dataExtract)
            myText.text = result.allOperation
            myResultText.text = "${result.result}"
            if (numerator.checkCanDeleteOp()) myOperatorText.text = ""


        }
    }

    private fun openCamera() {

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



}