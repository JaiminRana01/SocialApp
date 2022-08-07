package com.example.socialapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialapp.daos.PostDao
import com.example.socialapp.models.Post
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IPostAdapter {

    lateinit var adapter: PostAdapter
    lateinit var postDao: PostDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, CreatePostActivity::class.java)
            startActivity(intent)
        }

        val db = FirebaseFirestore.getInstance()
        postDao = PostDao()
        val postCollection = db.collection("posts")
        val query = postCollection.orderBy("createdAt", Query.Direction.DESCENDING)
        val recyclerViewOptions =
            FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        adapter = PostAdapter(recyclerViewOptions, this)


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onLikedClicked(postId: String) {
        postDao.updateLikes(postId)
    }
}