package com.example.firebase_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    lateinit var fname:TextView
    lateinit var femail:TextView
    lateinit var faddress:TextView
    lateinit var btnUpdate:Button
    lateinit var btnDelete:Button
    private var db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fname=findViewById(R.id.name)
        femail=findViewById(R.id.email)
        btnUpdate=findViewById(R.id.btn_update)
        btnDelete=findViewById(R.id.btn_delete)
        faddress=findViewById(R.id.address)

//        btnUpdate.setOnClickListener(){
//            startActivity(Intent(this,UpdateActivity::class.java))
//        }
//        btnDelete.setOnClickListener(){
//            startActivity(Intent(this,DeleteActivity::class.java))
//        }
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        var ref = db.collection("user").document(userId!!)
        ref.get().addOnSuccessListener {
            if(it!=null){
                val name=it.data?.get("name").toString()
                val email=it.data?.get("email").toString()
                val address=it.data?.get("address").toString()
                fname.text=name
                femail.text=email
                faddress.text=address
            }
        }
            .addOnFailureListener {
                Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
            }
    }
    fun update(view: View){
        startActivity(Intent(this,UpdateActivity::class.java))
    }
    fun delete(view:View){
        val mapDelete=mapOf(
            "address" to FieldValue.delete()
        )
        val userId=FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null){
            db.collection("user").document(userId).update(mapDelete)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}