package com.example.veknhek.screen

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.veknhek.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {
    private val signUpButton: Button by lazy { findViewById(R.id.btnSignUp) }
    private val backButton: LinearLayout by lazy { findViewById(R.id.btnBackToLogin) }
    private val emailEditText: EditText by lazy { findViewById(R.id.txtCreateUsername) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.txtCreatePassword) }
    private val confirmPasswordEditText: EditText by lazy { findViewById(R.id.txtCreateConfirmPassword) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Set OnClickListener for the SignUp button
        signUpButton.setOnClickListener {
            val email = emailEditText.getText().toString().trim()
            val password = passwordEditText.getText().toString().trim()
            val confirmPassword = confirmPasswordEditText.getText().toString().trim()

            // Input validation
            if (TextUtils.isEmpty(email)) {
                showToast("Please enter your email")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                showToast("Please enter your password")
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                showToast("Passwords do not match")
                return@setOnClickListener
            }

            // register to Firebase
            signUp(email, password)
        }
    }

    private fun signUp(email: String, password: String) {
        try {
            val auth = Firebase.auth
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showToast("Register success")
                    onBackPressedDispatcher.onBackPressed()
                } else {
                    showToast("Register failed")
                }
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            showToast("Register failed")
        }
    }

    private fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}