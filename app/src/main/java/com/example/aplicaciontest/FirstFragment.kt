package com.example.aplicaciontest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.aplicaciontest.databinding.FragmentFirstBinding
import java.math.BigDecimal

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {


    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myText = view.findViewById<TextView>(R.id.editTextNumber)
        val myOperatorText = view.findViewById<TextView>(R.id.operatorText)
        val myResultText = view.findViewById<TextView>(R.id.result_number)

        val numerator = Operations()
        myText.setText("0")
        myResultText.setText("0")

        //Aqui llamaremos a la funcion correspondiente de cada boton definido en la clase de Operations.kt
        view.findViewById<Button>(R.id.key_0).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(0))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")

        }
        view.findViewById<Button>(R.id.key_1).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(1))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_2).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(2))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_3).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(3))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_4).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(4))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_5).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(5))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_6).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(6))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_7).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(7))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }
        view.findViewById<Button>(R.id.key_8).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(8))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")

        }
        view.findViewById<Button>(R.id.key_9).setOnClickListener {
            val result = numerator.numberPressed(BigDecimal(9))
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            if (numerator.checkCanDeleteOp()) myOperatorText.setText("")
        }

        view.findViewById<Button>(R.id.key_equal).setOnClickListener {
            val result = numerator.equalFun()
            myText.setText("${result.accumulator}")
            if (result.error == ""){
                myResultText.setText("${result.result}")
                myOperatorText.setText("=")
            }else{
                myResultText.setText(result.error)
            }
        }

        view.findViewById<Button>(R.id.key_plus).setOnClickListener {
            val result = numerator.plusNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("+")

        }

        view.findViewById<Button>(R.id.key_minus).setOnClickListener {
            val result = numerator.minusNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("-")

        }

        view.findViewById<Button>(R.id.key_multiply).setOnClickListener {
            val result = numerator.multiplyNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("x")

        }

        view.findViewById<Button>(R.id.key_divide).setOnClickListener {
            val result =numerator.divideNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("/")

        }

        view.findViewById<Button>(R.id.key_delete).setOnClickListener {
            val result = numerator.deleteNum()
            myText.setText("${result.accumulator}")

        }

        view.findViewById<Button>(R.id.key_reset).setOnClickListener {
            val result =  numerator.restartAccumulator()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("")

        }
        view.findViewById<Button>(R.id.key_power).setOnClickListener {
            val result = numerator.powerNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("^")
        }
        view.findViewById<Button>(R.id.key_percent).setOnClickListener {
            val result = numerator.percentNum()
            myText.setText("${result.accumulator}")
            myResultText.setText("${result.result}")
            myOperatorText.setText("%")

        }

        view.findViewById<Button>(R.id.key_dot).setOnClickListener {
            val result = numerator.placeDot()
            myText.setText("${result.accumulator}")
        }




        //binding.cam_icon.setOnClickListener {
        //    findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        //}

        /*view.findViewById<Switch>(R.id.altoke_1).setOnCheckedChangeListener { buttonView, isChecked ->
            val text = view.findViewById<TextView>(R.id.texto_1)

            if(isChecked){
                text.text = "Button Checked"
            }else{
                text.text = "Button not Checked"

            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}