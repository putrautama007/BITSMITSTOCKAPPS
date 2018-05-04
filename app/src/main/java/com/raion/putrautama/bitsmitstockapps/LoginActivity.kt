package com.raion.putrautama.bitsmitstockapps

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin.setOnClickListener(View.OnClickListener {
            view -> login()
        })
    }
    private fun login(){
        val email = email.text.toString()
        val password = password.text.toString()

        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, OnCompleteListener { task ->
                        if (task.isSuccessful){
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this,"Login berhasil", Toast.LENGTH_SHORT).show()
                        }else {
                            Toast.makeText(this,"Login gagal", Toast.LENGTH_SHORT).show()
                        }
                    })
        }else if (email.isEmpty() && password.isEmpty()){
            Toast.makeText(this,"Mohon masukan email dan password", Toast.LENGTH_SHORT).show()
        }else if (email.isEmpty()){
            Toast.makeText(this,"Mohon masukan email", Toast.LENGTH_SHORT).show()
        }else if (password.isEmpty()){
            Toast.makeText(this,"Mohon masukan password", Toast.LENGTH_SHORT).show()
        }
    }
}
