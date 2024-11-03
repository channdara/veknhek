package com.example.veknhek.screen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.veknhek.R
import com.example.veknhek.adapter.NewsfeedAdapter
import com.example.veknhek.model.Newsfeed
import com.example.veknhek.utility.PreferencesUtil
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private val userIdTextView: TextView by lazy { findViewById(R.id.userIdTextView) }
    private val contentEditText: EditText by lazy { findViewById(R.id.txtTypingPost) }
    private val postButton: Button by lazy { findViewById(R.id.btnPost) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.newsfeedRecyclerView) }

    private lateinit var adapter: NewsfeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userIdTextView.text = "Anonymous ${PreferencesUtil.getUserId()}"

        recyclerView.layoutManager = LinearLayoutManager(this)
        val newsfeed = Firebase.firestore.collection("newsfeed")
            .orderBy("createdAt", Query.Direction.DESCENDING)
        val option =
            FirestoreRecyclerOptions.Builder<Newsfeed>().setQuery(newsfeed, Newsfeed::class.java)
                .build()
        adapter = NewsfeedAdapter(option)
        recyclerView.adapter = adapter

        postButton.setOnClickListener {
            val content = contentEditText.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                showToast("Please write content before post")
                return@setOnClickListener
            }
            postToFirestore(content)
        }

        findViewById<ImageView>(R.id.imageView2).setOnClickListener {
            PreferencesUtil.clearNecessary()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun postToFirestore(content: String) {
        try {
            val id = System.currentTimeMillis()
            val userId = PreferencesUtil.getUserId()
            val data = Newsfeed(id, content, userId, Timestamp.now(), Timestamp.now()).toHashMap()
            Firebase.firestore.collection("newsfeed").document(id.toString()).set(data)
                .addOnCompleteListener {
                    showToast("Post complete")
                    contentEditText.text.clear()
                }
        } catch (exc: Exception) {
            exc.printStackTrace()
            showToast("")
        }
    }

    private fun showToast(content: String) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }
}