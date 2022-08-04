package com.example.socialapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.socialapp.daos.PostDao
import kotlinx.android.synthetic.main.activity_create_post.*

class CreatePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        val postDao = PostDao()

        postButton.setOnClickListener {
            val text = postInput.text.toString().trim()
            if (text.isNotEmpty()) {
                postDao.addPost(text)
                finish()
            }
        }
    }
}