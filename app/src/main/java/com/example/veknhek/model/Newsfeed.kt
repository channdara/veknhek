package com.example.veknhek.model

import com.google.firebase.Timestamp

data class Newsfeed(
    val id: Long = 0,
    val content: String = "",
    val userId: Long = 0,
    val createdAt: Timestamp = Timestamp.now(),
    val updatedAt: Timestamp = Timestamp.now(),
) {
    fun toHashMap(): HashMap<String, Any> = hashMapOf(
        "id" to id,
        "content" to content,
        "userId" to userId,
        "createdAt" to createdAt,
        "updatedAt" to updatedAt,
    )
}