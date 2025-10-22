package es.ua.eps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import es.ua.eps.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindings : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindings = MainActivityBinding.inflate(layoutInflater)
        with(bindings) {
            setContentView(root)

            camara.setOnClickListener {  }
            gps.setOnClickListener {  }
        }

    }
}