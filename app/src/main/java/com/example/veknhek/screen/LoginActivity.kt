package com.example.veknhek.screen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.veknhek.R
import com.example.veknhek.utility.PreferencesUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private val emailEditText: EditText by lazy { findViewById(R.id.txtUsername) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.txtPassword) }
    private val createAccountTextView: TextView by lazy { findViewById(R.id.CreateNewAccount) }
    private val loginButton: Button by lazy { findViewById(R.id.btnLogin) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createAccountTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        loginButton.setOnClickListener {
            val email = emailEditText.getText().toString().trim()
            val password = passwordEditText.getText().toString().trim()
            if (TextUtils.isEmpty(email)) {
                showToast("Please enter your email")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showToast("Please enter your password")
                return@setOnClickListener
            }
            login(email, password)
        }
    }

    private fun login(email: String, password: String) {
        try {
            val auth = Firebase.auth
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val id = auth.currentUser?.metadata?.creationTimestamp
                    showToast("Login success")
                    PreferencesUtil.putToken(auth.currentUser?.uid ?: "")
                    PreferencesUtil.putUserId(id ?: 0)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showToast("Login failed")
                }
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            showToast("Login failed")
        }
    }

    private fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}