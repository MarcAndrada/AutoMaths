package com.example.aplicaciontest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.aplicaciontest.databinding.ActivityMainBinding
import java.io.File
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {
    var fileUri: Uri? = null
    var cameraData = ""
    private lateinit var appBarConfiguration: AppBarConfiguration
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

            }
        }

    val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isTaken: Boolean ->
            if (isTaken) {
                val cameraReader = CameraReader()

                fileUri?.let {
                    cameraData = cameraReader.getImageData(applicationContext,it)
                }

                if (cameraData != ""){
                    cameraData.forEach {
                        when(it){
                           '0' ->
                        }

                    }

                }

                // TODO
            }
        }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val myText = findViewById<TextView>(R.id.editTextNumber)
        val myOperatorText = findViewById<TextView>(R.id.operatorText)
        val myResultText = findViewById<TextView>(R.id.result_number)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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





        val numerator = Operations()
        myText.setText("0")
        myResultText.setText("0")

        //Aqui llamaremos a la funcion correspondiente de cada boton definido en la clase de Operations.kt
        findViewById<Button>(R.id.key_0).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(0))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")

        }
        findViewById<Button>(R.id.key_1).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(1))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_2).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(2))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_3).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(3))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_4).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(4))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_5).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(5))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_6).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(6))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_7).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(7))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        findViewById<Button>(R.id.key_8).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(8))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")

        }
        findViewById<Button>(R.id.key_9).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(9))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }

        findViewById<Button>(R.id.key_equal).setOnClickListener {
            val result = numerator.equalFun()
            myText.setText("${result.accumulator}")
            if (result.error == ""){
                myResultText.setText("${result.result}")
                myOperatorText.setText("=")
            }else{
                myResultText.setText(result.error)
            }
        }

        findViewById<Button>(R.id.key_plus).setOnClickListener {
            val result = numerator.plusNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("+")

        }

        findViewById<Button>(R.id.key_minus).setOnClickListener {
            val result = numerator.minusNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("-")

        }

        findViewById<Button>(R.id.key_multiply).setOnClickListener {
            val result = numerator.multiplyNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("x")

        }

        findViewById<Button>(R.id.key_divide).setOnClickListener {
            val result =numerator.divideNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("/")

        }

        findViewById<Button>(R.id.key_delete).setOnClickListener {
            val result = numerator.deleteNum()
            myText.setText("${result.accumulator}")

        }

        findViewById<Button>(R.id.key_reset).setOnClickListener {
            val result =  numerator.restartAccumulator()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("")

        }
        findViewById<Button>(R.id.key_power).setOnClickListener {
            val result = numerator.powerNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("^")
        }
        findViewById<Button>(R.id.key_percent).setOnClickListener {
            val result = numerator.percentNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("%")

        }

        findViewById<Button>(R.id.key_dot).setOnClickListener {
            val result = numerator.placeDot()
            myText.setText("${result.accumulator}")
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

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }*/


}