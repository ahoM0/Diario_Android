package es.instituto.diario

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import java.text.SimpleDateFormat
import java.util.Calendar

class EntradaFragment : Fragment() {
    private val diarioModel: DiarioModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    // Cerrar formulario, vuelve al anterior fragment o ventana
    private fun closeForm(){
        val fm: FragmentManager = this.parentFragmentManager
        fm.popBackStack()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         var elements=inflater.inflate(R.layout.fragment_entrada, container, false)
        elements.findViewById<Button>(R.id.cancelar).setOnClickListener{
           this.closeForm()

        }
        // Si hemos seleccionado una nota pues muestra sus datos para modificar
        if(diarioModel.item_selected!=null){
            var fecha= diarioModel.item_selected!!.fecha
            var texto= diarioModel.item_selected!!.texto
            elements.findViewById<TextView>(R.id.imagenFecha).text=fecha
            elements.findViewById<EditText>(R.id.texto).setText(texto)
        }

        // Listener del boton aceptar
            elements.findViewById<Button>(R.id.aceptar).setOnClickListener{
            if(elements.findViewById<TextView>(R.id.fecha).text.length>0 && elements.findViewById<EditText>(R.id.texto).length()>0){
                var fecha=elements.findViewById<TextView>(R.id.fecha).text.toString()
                var texto=elements.findViewById<EditText>(R.id.texto).text.toString()

                // Si hay una nota seleccionada, actualiza sus datos.
                if(diarioModel.item_selected!=null){
                    diarioModel.item_selected!!.fecha=fecha
                    diarioModel.item_selected!!.texto=texto
                }else {
                    // Si no hay la añade.
                    var entrada = Entrada(fecha, texto)
                    this.diarioModel.add(entrada)
                }
                // Y cerramos el fragmento, salimos al listado
                this.closeForm()

            }
        }
        // Listener del calendario, que muestra el grafico para seleccionar una fecha.
        elements.findViewById<ImageView>(R.id.imagenFecha).setOnClickListener{
            // on below line we are getting
            // the instance of our calendar.
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = context?.let { it1 ->
                DatePickerDialog(
                    // on below line we are passing context.
                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        // on below line we are setting
                        // date to our edit text.
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        elements.findViewById<TextView>(R.id.fecha).setText(dat) //dateEdt.setText(dat)
                    },
                    // on below line we are passing year, month
                    // and day for the selected date in our date picker.
                    year,
                    month,
                    day
                )
            }
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog?.show()
        }
            /*val fm: FragmentManager = this.parentFragmentManager
            (elements.findViewById<EditText>(R.id.fecha).
            val ft: FragmentTransaction = fm.beginTransaction()
            ft.replace(R.id.fragmentContainerView,PrincipalFragment()).setReorderingAllowed(true)
            ft.commit()*/

      //  }
        return elements
        // Inflate the layout for this fragment

    }

    companion object {
        @JvmStatic
        fun newInstance()=
            EntradaFragment().apply {

            }
    }
}