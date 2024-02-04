package es.instituto.diario

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat


class DiarioModel: ViewModel() {
    //métodos estáticos de la clase
    companion object{
        const val filenameCSV="AGENDA.cvs"
        const val separator=";"
        const val filenameJSON = "AGENDA.json";
    }

    //atributos
    private var elementos= mutableListOf<Entrada>()
    var item_selected: Entrada? =null
    val elements get()=elementos

    //Método para almacenar los datos en formato CSV.
    public fun saveCSV(context:Context): Void?{
        val file: File = File(context.getFilesDir(), filenameCSV)
        //if(file.exists() && !file.isDirectory){
            var fw= FileWriter(/* file = */ file)
            var bw= BufferedWriter(fw)
            elementos.forEach{
                bw.write(" ${it.fecha}${DiarioModel.separator} ${it.texto}${DiarioModel.separator}")
                bw.newLine()
            }
            bw.flush()
            bw.close()
            fw.close()
       return null
    }
    //Método para cargar los datos a partir fichero CVS.
    public fun loadCSV(context:Context): Void?{
        val file: File = File(context.getFilesDir(), filenameCSV)
        //if(file.exists() && !file.isDirectory){
        var fr= InputStreamReader(file.inputStream())//FileReader(/* file = */ file)
        var br= BufferedReader(fr)
        this.elementos=br.lineSequence().map {
            val (fecha,texto)=it.split(DiarioModel.separator, ignoreCase = false,limit=2)
            Entrada(fecha,texto)
        }.toMutableList()
        br.close()
        fr.close()

        // }
        return null
    }

    // Metodo para almacenar los datos en formato JSON
    public fun saveJSON(context: Context): Void? {
        val file: File = File(context.getFilesDir(), filenameJSON)
        if (!file.exists() || (file.exists() && !file.isDirectory)) {
            var fw = FileWriter(/* file = */ file)
            var bw = BufferedWriter(fw)
            this.elementos.forEach { entrada ->
                val jsonString = Json.encodeToString(entrada)
                bw.write(jsonString)
                bw.flush()
            }
            bw.close()
            fw.close()
        }
        return null
    }

    // Metodo para cargar los datos de fichero JSON
    public fun loadJSON(context: Context): Void? {
        val file: File = File(context.getFilesDir(), filenameJSON)
        var stringBuider = StringBuilder()
        if (file.exists() && !file.isDirectory) {
            context.openFileInput(
                filenameJSON
            ).bufferedReader().useLines { lines ->
                lines.forEach { stringBuider.append(it) }
            }
        }
        var cadena = stringBuider.toString()
        val entradas = Json.decodeFromString<Array<Entrada>>(cadena)
        this.elementos.clear()
        this.elementos.addAll(entradas)
        return null
    }

    public fun add(entrada:Entrada): Void?{
        elementos.add(entrada)
        return null
    }
}