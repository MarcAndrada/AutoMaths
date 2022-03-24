package com.example.aplicaciontest

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class CameraReader : Operations() {
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun getImageData(context: Context, uri: Uri, onSuccess: (String)-> Unit) {
        val image: InputImage
        lateinit var imageResut: String

        try {
            image = InputImage.fromFilePath(context, uri)
            val proceso = recognizer.process(image)
            imageResut = ""
            proceso.addOnSuccessListener {
                // Task completed successfully
                // ...
                proceso.result.text
                for (block in proceso.result.textBlocks) {

                    for (line in block.lines) {
                        line.text.forEach {
                            if (it != ' ') {
                                imageResut += it
                            }
                        }
                    }

                }

                onSuccess(imageResut)

            }

        }catch (e: IOException) {
            e.printStackTrace()
            onSuccess("")
        }
    }
}


