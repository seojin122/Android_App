package com.example.fianl

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CommunityActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CommunityAdapter
    private val posts = mutableListOf<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        recyclerView = findViewById(R.id.communityRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CommunityAdapter(posts) { post ->
            startActivity(Intent(this, CommunityDetailActivity::class.java).apply {
                putExtra("postId", post.id)
            })
        }
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.writeButton)
            .setOnClickListener {
                startActivity(Intent(this, CommunityWriteActivity::class.java))
            }
    }

    override fun onStart() {
        super.onStart()
        FirestoreCommunityManager.addPostsListener(
            onUpdate = { list ->
                posts.apply {
                    clear()
                    addAll(list)
                }
                adapter.notifyDataSetChanged()
            },
            onError = { e ->
                e.printStackTrace()
                Toast.makeText(
                    this,
                    "게시글 로드 실패: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }
}
