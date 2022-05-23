package com.example.aplicaciontest

import android.content.Context
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class HistoryManager (val context: Context){
    val fileName = "History.dat"
    var indexOperation = 0
    public var operationList = ArrayList<String>()

    public fun AddOperation(newOperation: String)
    {

        readData()

        if(operationList.size < 5)
        {
            operationList.add(newOperation)

        }else
        {
            operationList[indexOperation] = newOperation
            indexOperation++
            if(indexOperation >= 5)
            {
                indexOperation = 0
            }
        }
        saveData()
    }



    fun saveData() {
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use { io ->
            ObjectOutputStream(io).use {
                it.writeObject(operationList)
            }
        }
    }

    public fun readData() {
        val operations = try {
            context.openFileInput(fileName).use { io ->
                ObjectInputStream(io).use {
                    //Asignarle los valores a la lista de arrays
                    operationList.add( it.readObject() as String)
                }
            }
        } catch (e: IOException) {
            null
        }

    }


}