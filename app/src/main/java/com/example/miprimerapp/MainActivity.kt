package com.example.miprimerapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToRegistrarProducto(view: View) {
        val intent = Intent(this, RegistrarProductoActivity::class.java)
        startActivity(intent)
    }

    fun goToListarProductos(view: View) {
        val intent = Intent(this, ListarProductosActivity::class.java)
        startActivity(intent)
    }
}