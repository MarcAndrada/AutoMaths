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
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.aplicaciontest.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    val request = 3
    var file:File? = null
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

                // TODO
            }
        }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        file = File.createTempFile(
            "example_image", ".jpg",
           externalCacheDir
        )

        val fileUri = FileProvider.getUriForFile(
            this,
            "com.example.aplicaciontest.fileprovider",
            file?:createTempFile(
                    "example_image", ".jpg",
            externalCacheDir
        )
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}