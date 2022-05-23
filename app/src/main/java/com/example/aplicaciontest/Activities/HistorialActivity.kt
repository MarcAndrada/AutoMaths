package com.example.aplicaciontest.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.aplicaciontest.HistoryManager
import com.example.aplicaciontest.R
import com.example.aplicaciontest.databinding.ActivityHistorialBinding
import com.example.aplicaciontest.databinding.ActivityMainBinding

class HistorialActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistorialBinding
    private var text = ArrayList<TextView>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHistorialBinding.inflate(layoutInflater)


        text.add(binding.historialText1)
        text.add(binding.historialText2)
        text.add(binding.historialText3)
        text.add(binding.historialText4)
        text.add(binding.historialText5)

        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.returnButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val manager = HistoryManager(this)
        manager.readData()
        val operatorList = manager.operationList

        when(operatorList.size)
        {
            1-> binding.historialText1.text = operatorList[0]
            2-> let{
                binding.historialText1.text = operatorList[0]
                binding.historialText2.text = operatorList[1]

            }
            3-> let{
                binding.historialText1.text = operatorList[0]
                binding.historialText2.text = operatorList[1]
                binding.historialText3.text = operatorList[2]

            }
            4-> let{
                binding.historialText1.text = operatorList[0]
                binding.historialText2.text = operatorList[1]
                binding.historialText3.text = operatorList[2]
                binding.historialText4.text = operatorList[3]
            }
            5-> let{
                binding.historialText1.text = operatorList[0]
                binding.historialText2.text = operatorList[1]
                binding.historialText3.text = operatorList[2]
                binding.historialText4.text = operatorList[3]
                binding.historialText5.text = operatorList[4]

            }
        }

        binding.deleteButton.setOnClickListener {
            manager.DestroyHistory()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


}