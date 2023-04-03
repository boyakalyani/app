package com.example.firebase_new

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpdateActivity : AppCompatActivity() {
    lateinit var names:EditText
    lateinit var address:EditText
    lateinit var email:EditText
    lateinit var Btn:Button
    private var db= Firebase.firestore
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)
        names=findViewById(R.id.NameUp)
        address=findViewById(R.id.AddressUp)
        email=findViewById(R.id.EmailAddressUp)
        Btn=findViewById(R.id.btn_updateup)
    }
    fun updation(view: View){
        val sName =names.text.toString()
        val saddress =address.text.toString()
        val semail =email.text.toString()
//        val spassword =password.text.toString()

        val updateMap = mapOf(
            "names" to sName,
            "address" to saddress,
            "email" to semail
        )
        val userId=FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null){
            db.collection("user").document(userId).update(updateMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }
}