package br.com.farras.appzinho.features.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.farras.appzinho.features.main.MainActivity
import br.com.farras.appzinho.R
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        setupEvents()
    }

    private fun setupEvents() {
        bt_login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (et_username.text != null && et_password.text != null) {
            if (et_username.text.toString() == "aluno" &&
                et_password.text.toString() == "impacta") {

                startActivity<MainActivity>()
            } else {
                toast("Usuário ou senha incorretos")
            }
        }
    }

}
