package es.ua.eps.sensorapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import android.content.Intent
import android.view.View
import es.ua.eps.sensorapp.CamaraActivity
import es.ua.eps.sensorapp.GpsActivity
import es.ua.eps.sensorapp.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bindings : MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindings = MainActivityBinding.inflate(layoutInflater)
        with(bindings) {
            setContentView(root)

            buttonCamara.setOnClickListener { showCamara() }
            buttonGPS.setOnClickListener { showGPS() }
        }
    }
    private fun showCamara(){
        val camaraIntent = Intent(this@MainActivity, CamaraActivity::class.java)
        startActivity(camaraIntent)
    }
    private fun showGPS(){
        val gpsIntent = Intent(this@MainActivity, GpsActivity::class.java)
        startActivity(gpsIntent)
    }
}