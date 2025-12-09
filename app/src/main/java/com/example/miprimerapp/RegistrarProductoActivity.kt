package com.example.miprimerapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RegistrarProductoActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore

    private lateinit var etCodigoBarra: EditText
    private lateinit var etNombreProducto: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etNombreCategoria: EditText
    private lateinit var etUrlProducto: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_producto)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        firestore = FirebaseFirestore.getInstance()

        etCodigoBarra = findViewById(R.id.et_codigo_barra)
        etNombreProducto = findViewById(R.id.et_nombre_producto)
        etPrecio = findViewById(R.id.et_precio)
        etNombreCategoria = findViewById(R.id.et_nombre_categoria)
        etUrlProducto = findViewById(R.id.et_url_producto)
    }

    fun registrarProducto(view: View) {
        val codigoBarra = etCodigoBarra.text.toString()
        val nombreProducto = etNombreProducto.text.toString()
        val precio = etPrecio.text.toString()
        val nombreCategoria = etNombreCategoria.text.toString()
        val urlProducto = etUrlProducto.text.toString()

        if (codigoBarra.isEmpty() || nombreProducto.isEmpty() || precio.isEmpty() || nombreCategoria.isEmpty() || urlProducto.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios")
            return
        }

        val producto = hashMapOf(
            "codigo_barra" to codigoBarra,
            "nombre" to nombreProducto,
            "precio" to precio.toDouble(),
            "categoria" to nombreCategoria,
            "url" to urlProducto
        )

        firestore.collection("productos").document(codigoBarra).get().addOnSuccessListener { document ->
            if (document.exists()) {
                mostrarAlerta("Error", "El código de barra ya existe")
            } else {
                firestore.collection("productos").document(codigoBarra).set(producto)
                    .addOnSuccessListener { mostrarAlerta("Éxito", "Producto registrado correctamente") }
                    .addOnFailureListener { e -> mostrarAlerta("Error", "No se pudo registrar el producto: ${e.message}") }
            }
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