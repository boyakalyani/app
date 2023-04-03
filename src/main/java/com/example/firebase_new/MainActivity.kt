package com.example.firebase_new

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var name:EditText
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var address:EditText
    private var db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth= Firebase.auth

        name=findViewById(R.id.Name)
        address=findViewById(R.id.Address)
        email=findViewById(R.id.EmailAddress)
        password=findViewById(R.id.Password)
        var btn:Button=findViewById(R.id.SignUp)

        btn.setOnClickListener {
            val sEmail=email.text.toString().trim()
            val sPassword=password.text.toString().trim()
            auth.createUserWithEmailAndPassword(sEmail, sPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"Signed up sucessfully", Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        saveData()
                        updateUI(user)
                    } else {
                        // If sign in
                        Log.e("failure:", task.exception.toString())
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
        }
    }

    private fun saveData() {
        val sEmail=email.text.toString().trim()
        val sName=name.text.toString().trim()
        val sAddress=address.text.toString().trim()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val hashMap = hashMapOf(
            "name" to sName,
            "address" to sAddress,
            "email" to sEmail
        )
        if (userId!=null){
            db.collection("user").document(userId).set(hashMap)
                .addOnCompleteListener{
                    Toast.makeText(this,"Successfully added",Toast.LENGTH_SHORT).show()
                    email.text.clear()
                    name.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }
        }

    }

    private fun updateUI(user: FirebaseUser?) {

        val intent = Intent(this,Home::class.java)
        startActivity(intent)

    }
}