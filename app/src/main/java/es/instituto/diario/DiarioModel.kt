package es.instituto.diario

import android.content.Context
import androidx.lifecycle.ViewModel
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
        const val filename="AGENDA.cvs"
        const val separator=";"
    }

    //atributos
    private var elementos= mutableListOf<Entrada>()
    var item_selected: Entrada? =null
    val elements get()=elementos

    //método para almacenar los datos en formato CSV
    public fun save(context:Context): Void?{
        val file: File = File(context.getFilesDir(), filename)
        //if(file.exists() && !file.isDirectory){
            var fw= FileWriter(/* file = */ file)
            var bw= BufferedWriter(fw)
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            elementos.forEach{
                bw.write(" ${formatter.format(it.fecha)}${DiarioModel.separator} ${it.texto}${DiarioModel.separator}")
                bw.newLine()
            }
            bw.flush()
            bw.close()
            fw.close()
       return null
    }

    //nétodo para cargar los datos a partir fichero CVS
    public fun load(context:Context): Void?{
        val file: File = File(context.getFilesDir(), filename)
        //if(file.exists() && !file.isDirectory){
        var fr= InputStreamReader(file.inputStream())//FileReader(/* file = */ file)
        var br= BufferedReader(fr)
        this.elementos=br.lineSequence().map {
            val (fecha,texto)=it.split(DiarioModel.separator, ignoreCase = false,limit=2)
            val formatter = SimpleDateFormat("dd-MM-yyyy")

           Entrada(formatter.parse(fecha),texto)
        }.toMutableList()
        br.close()
        fr.close()

        // }
        return null
    }

    public fun add(entrada:Entrada): Void?{
        elementos.add(entrada)
        return null
    }
}