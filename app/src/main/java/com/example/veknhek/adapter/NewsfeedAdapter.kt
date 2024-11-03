package com.example.veknhek.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.veknhek.R
import com.example.veknhek.model.Newsfeed
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class NewsfeedAdapter(options: FirestoreRecyclerOptions<Newsfeed>) :
    FirestoreRecyclerAdapter<Newsfeed, NewsfeedViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsfeedViewHolder {
        return NewsfeedViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_view_news_feed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsfeedViewHolder, position: Int, model: Newsfeed) {
        holder.bind(model)
    }
}

class NewsfeedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val contentTextView: TextView by lazy { view.findViewById(R.id.contentTextView) }
    private val dateTextView: TextView by lazy { view.findViewById(R.id.dateTextView) }
    private val userId: TextView by lazy { view.findViewById(R.id.userIdTextView) }

    fun bind(newsfeed: Newsfeed) {
        contentTextView.text = newsfeed.content
        userId.text = "Anonymous ${newsfeed.userId}"
        val date = newsfeed.createdAt.toDate().toLocaleString()
        dateTextView.text = date
    }
}