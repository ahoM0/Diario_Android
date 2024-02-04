package es.instituto.diario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import es.instituto.diario.databinding.ActivityMainBinding



import androidx.appcompat.app.AlertDialog


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val viewModel: DiarioModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(es.instituto.diario.R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Boton para añadir una nota al diario
            es.instituto.diario.R.id.nuevo-> {
                this.viewModel.item_selected=null
                val fm: FragmentManager = supportFragmentManager
                fm.commit {
                    replace(R.id.fragmentContainerView, EntradaFragment.newInstance())
                    addToBackStack("replacement")
                }
                true
            }
            // Boton para ver todas las notas del diario
            es.instituto.diario.R.id.todos -> {
                val fm: FragmentManager = supportFragmentManager
                fm.commit {
                    replace(R.id.fragmentContainerView, ListadoFragment.newInstance())
                    addToBackStack("replacement")
                }

                true
            }
            // Boton para guardar el diario en ficheros
            es.instituto.diario.R.id.guardar->{
                val options = arrayOf(" CSV", " JSON", " XML")

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Selecciona un formato para guardar: ")
                    .setItems(options) { dialog, which ->
                        // Tipos de formato
                        when (which) {
                            0 ->
                                //Metodo del viewmodel que guarda el diario en el fichero
                                this.viewModel.saveCSV(applicationContext )
                            1 ->
                                //Metodo del viewmodel que guarda el diario en el fichero
                                this.viewModel.saveJSON(applicationContext )
                          //  2 ->
                                //Metodo del viewmodel que guarda el diario en el fichero
                               // this.viewModel.saveCSV(applicationContext )

                        }
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        // Cierra si cancelamos
                        dialog.dismiss()
                    }

                val dialog = builder.create()
                dialog.show()

                true
            }
            // Carga el diario de los ficheros.
            es.instituto.diario.R.id.cargar->{
                val options = arrayOf(" CSV", " JSON ", " XML")

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Selecciona un formato para cargar: ")
                    .setItems(options) { dialog, which ->
                        // Tipos de formato
                        when (which) {
                            0 ->
                                // Metodo del viewmodel que carga el diario en el fichero
                                this.viewModel.loadCSV(applicationContext )
                            1 ->
                            // Metodo del viewmodel que carga el diario en el fichero
                            this.viewModel.loadJSON(applicationContext )
                            //2 -> // Acciones para la opción 3

                        }
                    }
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        // Cierra si cancelamos
                        dialog.dismiss()
                    }

                val dialog = builder.create()
                dialog.show()

                true
            }
            android.R.id.home->{
                supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}