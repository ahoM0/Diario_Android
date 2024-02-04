package es.instituto.diario

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListadoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListadoFragment : Fragment(), EntradaAdaptador.OnClickListener {
    private val diarioModel: DiarioModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var v=inflater.inflate(R.layout.fragment_listado, container, false)
        var adaptador= context?.let { EntradaAdaptador(it) }
        if (adaptador != null) {
            adaptador.elementos=diarioModel.elements
            adaptador.setListener(this)
        }
        var grid= GridLayoutManager(context,2)
        val layoutManager = GridLayoutManager(context, 2)
        v.findViewById<RecyclerView>(R.id.grid).layoutManager=layoutManager
        v.findViewById<RecyclerView>(R.id.grid).adapter=adaptador

        return v
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ListadoFragment().apply {  }
            }

    override fun onClick(position: Int, model: Entrada) {
        val fm: FragmentManager = this.parentFragmentManager
        this.diarioModel.item_selected=model
        fm.commit {
            replace(R.id.fragmentContainerView, EntradaFragment())

            addToBackStack("replacement")
        }

        /* val ft: FragmentTransaction = fm.beginTransaction()
         ft.addToBackStack().add(R.id.fragmentContainerView,EntradaFragment()).setReorderingAllowed(true)
         ft.commit()*/
    }

}