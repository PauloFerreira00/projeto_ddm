package br.com.farras.appzinho.features.main

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.farras.appzinho.R
import br.com.farras.appzinho.features.generic.GenericActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setupView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.count() != 0) {
                    toast("Texto digitado: ${newText}")
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                toast("Evento buscado: ${query}")
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_logout -> finish()
            R.id.action_refresh -> showProgressBar()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar() {
        rv_events.visibility = View.INVISIBLE

        pb_loading.show()

        GlobalScope.launch(context = Dispatchers.Main) {
            delay(10000)

            rv_events.visibility = View.VISIBLE

            pb_loading.hide()
        }
    }

    private fun setupView() {
        val events = RecyclerViewMock().events
        val adapter = MainAdapter(events)

        rv_events.layoutManager = GridLayoutManager(this, 2)
        rv_events.adapter = adapter
    }

    private fun setupEvents() {
        bt_my_events.setOnClickListener {
            showGenericActivity(bt_my_events.text.toString())
        }

        bt_register_event.setOnClickListener {
            showGenericActivity(bt_register_event.text.toString())
        }

        bt_update_event.setOnClickListener {
            showGenericActivity(bt_update_event.text.toString())
        }
    }

    private fun showGenericActivity(title: String) {
        startActivity<GenericActivity>("title" to title)
    }

    class RecyclerViewMock {
        val events: List<String> = listOf(
            "Festa do professor",
            "Lançamento do farras",
            "Formatura da impacta",
            "Rolezinho aleatório",
            "Festa do professor",
            "Lançamento do farras",
            "Formatura da impacta",
            "Rolezinho aleatório",
            "Festa do professor",
            "Lançamento do farras",
            "Formatura da impacta",
            "Rolezinho aleatório",
            "Festa do professor",
            "Lançamento do farras",
            "Formatura da impacta",
            "Rolezinho aleatório"
        )
    }
}