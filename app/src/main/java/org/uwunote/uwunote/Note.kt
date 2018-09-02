package org.uwunote.uwunote

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.*
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class Note {

    val uid: UUID
    var content: String? = null

    private val context: Context

    constructor(context: Context, uid: UUID) {
        this.uid = uid
        this.context = context
    }

    constructor(context: Context) : this(context, UUID.randomUUID())
    constructor(context: Context, uid: String) : this(context, UUID.fromString(uid))

    fun getNoteFolder(): String {
        return context.filesDir.absolutePath + File.separator + noteFolder + File.separator
    }

    fun load() {
        Log.d("Note", "Loading...")
        try {
            val inputStream = FileInputStream(File(getNoteFolder() + uid.toString()))
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var currentLine: String? = bufferedReader.readLine()
            val stringBuilder = StringBuilder()
            while (currentLine != null) {
                stringBuilder.append(currentLine + "\n")
                currentLine = bufferedReader.readLine()
            }

            content = stringBuilder.toString()
        } catch (exception: IOException) {
            Log.e("Note", "Load", exception)
            Toast.makeText(context, context.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
        }
    }

    fun save() {
        Log.d("Note", "Saving...")
        try {
            val out = OutputStreamWriter(FileOutputStream(getNoteFolder() + uid.toString()))
            out.write(content)
            out.close()
        } catch (exception: Exception) {
            Log.e("Note", "Save", exception)
            Toast.makeText(context, context.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
        }
    }

    fun delete() {
        Log.d("Note", "Deleting...")
        try {
            Files.deleteIfExists(Paths.get(getNoteFolder() + uid.toString()))
        } catch (exception: IOException) {
            Log.e("Note", "Delete", exception)
            Toast.makeText(context, context?.getText(R.string.error).toString() + exception.message, Toast.LENGTH_LONG).show()
        }
    }
}