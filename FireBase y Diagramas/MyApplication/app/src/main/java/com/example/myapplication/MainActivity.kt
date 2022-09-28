package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.StringBuilder

class Paciente {

    var peso1 = ""
    var fecha1 = ""
    var estatura1 = ""
    var blood1 = ""

    constructor(peso1: String, fecha1: String, estatura1: String, blood1: String){
        this.peso1 = peso1
        this.fecha1 = fecha1
        this.estatura1 = estatura1
        this.blood1 = blood1

    }

}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance().reference

        val button = findViewById<Button>(R.id.button)
        val text1 = findViewById<EditText>(R.id.Peso)
        val text2 = findViewById<EditText>(R.id.Fecha)
        val text3 = findViewById<EditText>(R.id.Estatura)
        val text4 = findViewById<EditText>(R.id.tipoSanguineo)

        button.setOnClickListener{

            val peso1 = text1.text.toString()
            val fecha1 = text2.text.toString()
            val estatura1 = text3.text.toString()
            val blood1 = text4.text.toString()

            database.child(peso1.toString()).setValue(Paciente(peso1,fecha1,estatura1,blood1))

        }

        val getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val sb = StringBuilder()
                for(i in snapshot.children) {
                    val peso1 = i.child("peso").value
                    val fecha1 = i.child("fecha").value
                    val estatura1 = i.child("estatura").value
                    val blood1 = i.child("sangre").value
                    sb.append("${i.key} $peso1 $fecha1 $estatura1 $blood1 \n")
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        database.addValueEventListener(getData)
        database.addListenerForSingleValueEvent(getData)
    }
}