package com.example.aplicaciontest

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class CameraReader() : Operations() {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun getImageData(context: Context, uri: Uri):String {
        val image: InputImage
        var imageResut = "Fallo"
        try {
            image = InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

        val process = recognizer.process(image)

        process.addOnSuccessListener { visionText ->
            // Task completed successfully
            // ...
            val resultText = process.result.text
            for (block in process.result.textBlocks) {

                for (line in block.lines) {
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    imageResut = lineText
                    /*line.text.forEach {
                        if (it != ' '){
                            imageResut += it
                        }
                    }*/


                   for (element in line.elements) {
                       val elementText = element.text
                       val elementCornerPoints = element.cornerPoints
                       val elementFrame = element.boundingBox
                   }
                }



                //onSuccess(result)
            }
        }
        .addOnFailureListener {
            imageResut = it.toString()
        }


        return imageResut
    }


}