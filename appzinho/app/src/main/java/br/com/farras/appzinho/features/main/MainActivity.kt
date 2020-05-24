package br.com.farras.appzinho.features.main

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.farras.appzinho.R
import br.com.farras.appzinho.features.map.MapActivity
import br.com.farras.appzinho.features.generic.GenericActivity
import br.com.farras.appzinho.features.register.RegisterActivity
import br.com.farras.appzinho.helpers.NotificationHelper
import br.com.farras.appzinho.models.Event
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: MainViewModel by viewModel()

    private val notificationHelper: NotificationHelper by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
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
            R.id.action_refresh -> showProgressBar()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_action_my_events -> showGenericActivity("Meus rolês")
            R.id.item_action_register_event -> startActivity<RegisterActivity>()
            R.id.item_action_update_event -> showGenericActivity("Atualizar rolê")
            R.id.item_action_logout -> finish()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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
        setupToolbar()
        setupNavigationDrawer()

        notificationHelper.show("Aqui é o lugar do seu rolê",
            "No Farras você encontra um rolê a qualquer hora!")
    }

    private fun setupRecyclerView() {
        if (isNetworkAvailable()) {
            viewModel.getEvents().observe(this, Observer { result ->
                if(result.success != null) {
                    val events = result.success
                    setupAdapter(events)
                } else {
                    result.failure?.message?.let { toast(it) }
                }
            })
        } else {
            viewModel.getEventsFromLocal().observe(this, Observer { result ->
                if (result.success != null) {
                    val events = result.success
                    setupAdapter(events)
                } else {
                    result.failure?.message?.let { toast(it) }
                }
            })
        }
    }

    private fun setupAdapter(events: List<Event>) {
        val adapter = MainAdapter(events)

        rv_events.layoutManager = GridLayoutManager(this, 2)
        rv_events.adapter = adapter
    }

    private fun setupNavigationDrawer() {
        var toogle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        drawer_menu.setNavigationItemSelectedListener(this)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupEvents() {
        bt_my_events.setOnClickListener {
            showGenericActivity(bt_my_events.text.toString())
        }

        bt_register_event.setOnClickListener {
            startActivity<RegisterActivity>()
        }

        bt_update_event.setOnClickListener {
            showGenericActivity(bt_update_event.text.toString())
        }

        bt_map.setOnClickListener {
            startActivity<MapActivity>()
        }
    }

    private fun showGenericActivity(title: String) {
        startActivity<GenericActivity>("title" to title)
    }
}

fun Activity.isNetworkAvailable(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}