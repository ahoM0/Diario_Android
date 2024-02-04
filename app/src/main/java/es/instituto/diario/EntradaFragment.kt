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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EntradaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EntradaFragment : Fragment() {
    private val diarioModel: DiarioModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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
        if(diarioModel.item_selected!=null){
            val formatter = SimpleDateFormat("dd-MM-yyyy")
            var fecha= diarioModel.item_selected!!.fecha
            var texto= diarioModel.item_selected!!.texto
            elements.findViewById<TextView>(R.id.imagenFecha).text=formatter.format(fecha)


           elements.findViewById<EditText>(R.id.texto).setText(texto)
        }
        elements.findViewById<Button>(R.id.aceptar).setOnClickListener{
            if(elements.findViewById<TextView>(R.id.fecha).text.length>0 && elements.findViewById<EditText>(R.id.texto).length()>0){

                var fecha=elements.findViewById<TextView>(R.id.fecha).text.toString()
                val formatter = SimpleDateFormat("dd-MM-yyyy")
                var texto=elements.findViewById<EditText>(R.id.texto).text.toString()
                if(diarioModel.item_selected!=null){
                    diarioModel.item_selected!!.fecha=formatter.parse(fecha)
                    diarioModel.item_selected!!.texto=texto


                }else {
                    var entrada = Entrada(formatter.parse(fecha), texto)
                    this.diarioModel.add(entrada)
                }
                this.closeForm()

            }
        }
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EntradaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance()= //param1: String, param2: String) =
            EntradaFragment().apply {
               /* arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}