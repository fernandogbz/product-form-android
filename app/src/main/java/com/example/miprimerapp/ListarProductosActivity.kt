package com.example.miprimerapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ListarProductosActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var lvProductos: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_productos)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firestore = FirebaseFirestore.getInstance()
        lvProductos = findViewById(R.id.lv_productos)

        firestore.collection("productos").get()
            .addOnSuccessListener { result ->
                val productos = ArrayList<String>()
                for (document in result) {
                    val codigoBarra = document.getString("codigo_barra")
                    val nombre = document.getString("nombre")
                    val categoria = document.getString("categoria")
                    productos.add("$codigoBarra - $nombre - $categoria")
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, productos)
                lvProductos.adapter = adapter
            }
            .addOnFailureListener { exception ->
                mostrarAlerta("Error", "No se pudieron obtener los productos: ${exception.message}")
            }
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }
}