package com.example.aplicaciontest

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class CameraReader : Operations() {
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun getImageData(context: Context, uri: Uri) {
        val image: InputImage
        try {
            image = InputImage.fromFilePath(context, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }

        val process = recognizer.process(image)

        process.addOnSuccessListener { visionText ->
            // Task completed successfully
            // ...
            val resultText = process.result.text
            for (block in process.result.textBlocks) {
                val blockText = block.text
                val blockCornerPoints = block.cornerPoints
                val blockFrame = block.boundingBox
                for (line in block.lines) {
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    line.text.forEach {

                    }
//                    for (element in line.elements) {
//                        val elementText = element.text
//                        val elementCornerPoints = element.cornerPoints
//                        val elementFrame = element.boundingBox
//                    }
                }
            }
        }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
                TODO()
            }
    }


}