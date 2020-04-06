package br.com.farras.appzinho.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import br.com.farras.appzinho.R
import br.com.farras.appzinho.features.generic.GenericActivity
import br.com.farras.appzinho.features.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.action_search -> toast("Buscar")
            R.id.action_logout -> finish()
            R.id.action_refresh -> showProgressBar()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar() {

    }

    private fun setupEvents() {
        bt_search_event.setOnClickListener {
            showGenericActivity(bt_search_event.text.toString())
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
}
