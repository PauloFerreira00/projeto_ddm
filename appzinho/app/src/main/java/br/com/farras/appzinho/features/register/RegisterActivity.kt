package br.com.farras.appzinho.features.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import br.com.farras.appzinho.R
import br.com.farras.appzinho.features.login.LoginViewModel
import br.com.farras.appzinho.features.main.isNetworkAvailable
import br.com.farras.appzinho.models.Event
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Cadastrar rolê"

        setupActions()
    }

    private fun setupActions() {
       bt_register_event_data.setOnClickListener {
           if (et_event_name.text.toString().isNotEmpty() &&
               et_evente_date.text.toString().isNotEmpty()
           ) {
               val event = Event(name = et_event_name.text.toString(), date = et_evente_date.text.toString())
               register(event)
           } else {
               toast("Preencha todos os campos!")
           }

       }
    }

    private fun register(event: Event) {
        if (isNetworkAvailable()) {
            viewModel.register(event).observe(this, Observer { result ->
                if (result.success != null) {
                    toast(result.success)
                    finish()
                } else {
                    result.failure?.message?.let { toast(it) }
                }
            })
        }
    }
}
