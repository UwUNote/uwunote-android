package de.marvinklar.uwunote

import android.content.Context
import android.widget.Toast
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class Note {
    private val context: Context
    val uid: UUID
    var content: String? = null

    constructor(context: Context, uid: UUID) {
        this.uid = uid
        this.context = context
    }

    constructor(context: Context) : this(context, UUID.randomUUID())
    constructor(context: Context, uid: String) : this(context, UUID.fromString(uid))

    fun load() {
        context.openFileInput(uid.toString()).use {
            try {
                val bufferedReader = BufferedReader(InputStreamReader(it))
                var currentLine: String? = bufferedReader.readLine()
                val stringBuilder = StringBuilder()
                while (currentLine != null) {
                    stringBuilder.append(currentLine + "\n")
                    currentLine = bufferedReader.readLine()
                }

                content = stringBuilder.toString()
            } catch (exception: IOException) {
                Toast.makeText(context, context.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun save() {
        try {
            val out = OutputStreamWriter(context.openFileOutput(uid.toString(), 0))
            out.write(content)
            out.close()
        } catch (exception: Exception) {
            Toast.makeText(context, context.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
        }
    }

    fun delete() {
        try {
            val noteDir = context.filesDir
            if (noteDir != null) {
                val notePath = noteDir.absolutePath + File.separator + uid.toString()
                Files.deleteIfExists(Paths.get(notePath))
            }
        } catch (exception: IOException) {
            Toast.makeText(context, context?.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
        }
    }
}