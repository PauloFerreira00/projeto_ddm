package br.com.farras.appzinho.features.generic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.farras.appzinho.R

class GenericActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        setupView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
    private fun setupView() {
        val title = intent?.getStringExtra("title")

        title.let { title ->
            supportActionBar?.title = title
        }
    }
}
