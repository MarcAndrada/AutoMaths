package com.example.aplicaciontest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.aplicaciontest.HistoryManager
import com.example.aplicaciontest.R
import com.example.aplicaciontest.databinding.ActivityHistorialBinding

class HistorialActivity : AppCompatActivity() {
    lateinit var binding: ActivityHistorialBinding
    var operatorList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val manager = HistoryManager(this)
        manager.readData()
        operatorList = manager.operationList

        when(operatorList.size)
        {
            1-> findViewById<Button>(R.id.historialText1).text = operatorList[0]
            2-> let{
                findViewById<Button>(R.id.historialText1).text = operatorList[0]
                findViewById<Button>(R.id.historialText2).text = operatorList[1]

            }
            3-> let{
                findViewById<Button>(R.id.historialText1).text = operatorList[0]
                findViewById<Button>(R.id.historialText2).text = operatorList[1]
                findViewById<Button>(R.id.historialText3).text = operatorList[2]

            }
            4-> let{
                findViewById<Button>(R.id.historialText1).text = operatorList[0]
                findViewById<Button>(R.id.historialText2).text = operatorList[1]
                findViewById<Button>(R.id.historialText3).text = operatorList[2]
                findViewById<Button>(R.id.historialText3).text = operatorList[3]

            }
            5-> let{
                findViewById<Button>(R.id.historialText1).text = operatorList[0]
                findViewById<Button>(R.id.historialText2).text = operatorList[1]
                findViewById<Button>(R.id.historialText3).text = operatorList[2]
                findViewById<Button>(R.id.historialText3).text = operatorList[3]
                findViewById<Button>(R.id.historialText3).text = operatorList[4]

            }
        }
    }


}