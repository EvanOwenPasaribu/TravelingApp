package com.project.registerloginforgot

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.project.registerloginforgot.database.DatabaseHelper

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var btnBack : ImageView
    lateinit var btnReset : Button
    lateinit var etEmail : EditText
    lateinit var db: DatabaseHelper
    lateinit var context : Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        context = this@ForgetPasswordActivity
        db = DatabaseHelper(context)

        btnBack= findViewById(R.id.btn_back)
        btnReset = findViewById(R.id.btn_reset)
        etEmail = findViewById(R.id.et_email)

        btnBack.setOnClickListener {
            finish()
        }
        btnReset.setOnClickListener {
            if(etEmail.text.toString().isEmpty()){
                Toast.makeText(this@ForgetPasswordActivity, "Masukkan email", Toast.LENGTH_SHORT).show()
            }else if(!isValidEmail(etEmail.text.toString())){
                Toast.makeText(this@ForgetPasswordActivity, "Email tidak valid", Toast.LENGTH_SHORT).show()
            }else {
                if(db.getUserByEmail(etEmail.text.toString()) == null){
                    Toast.makeText(this@ForgetPasswordActivity, "Email tidak ditemukan", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this@ForgetPasswordActivity, "Silahkan ganti password anda", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, ChangePasswordActivity::class.java)
                    intent.putExtra("email", etEmail.text.toString())
                    startActivity(intent)
                }
            }
        }

    }
    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}