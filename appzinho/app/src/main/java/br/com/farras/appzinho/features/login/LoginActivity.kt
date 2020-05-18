package br.com.farras.appzinho.features.login

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import br.com.farras.appzinho.features.main.MainActivity
import br.com.farras.appzinho.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE)

        setupEvents()
        verifyCache()
    }

    private fun setupEvents() {
        bt_login.setOnClickListener {
            login()
        }
    }

    private fun login() {
        if (et_username.text.toString().isNotEmpty() && et_password.text.toString().isNotEmpty()) {
            viewModel.login(et_username.text.toString(),
                et_password.text.toString()).observe(this, Observer {  result ->
                if (result.success != null) {
                    checkRememberPassword()
                    startActivity<MainActivity>()
                } else {
                    toast("Usuário ou senha incorretos")
                }
            })
        } else {
            toast("Preencha todos os campos!")
        }
    }

    private fun checkRememberPassword() {
        if(cb_remember_password.isChecked) {
            val login = hashMapOf(
                "username" to et_username.text.toString(),
                "password" to et_password.text.toString()
            )
            saveUserAndPasswordInCache(login)
        } else {
            removeUserAndPasswordFromCache()
        }
    }

    private fun verifyCache() {
        val json = sharedPreferences?.getString("loginData", "")
        val type = object: TypeToken<HashMap<String, Any>>() {}.type

        if(json != null && json.isNotEmpty()) {
            val loginData = Gson().fromJson<HashMap<String, Any>>(json, type)
            et_username.setText(loginData["username"] as CharSequence)
            et_password.setText(loginData["password"] as CharSequence)
            cb_remember_password.isChecked = true
        }
    }

    private fun saveUserAndPasswordInCache(data: HashMap<String, String>) {
        val editor = sharedPreferences?.edit()
        val gson = Gson()
        val json = gson.toJson(data)
        editor?.putString("loginData", json)
        editor?.apply()
    }

    private fun removeUserAndPasswordFromCache() {
        val editor = sharedPreferences?.edit()
        editor?.remove("loginData")
        editor?.apply()
    }

}
