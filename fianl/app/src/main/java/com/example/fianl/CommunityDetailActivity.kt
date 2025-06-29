package com.example.fianl

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import com.example.fianl.CommentAdapter

class CommunityDetailActivity : AppCompatActivity() {

    private lateinit var commentAdapter: CommentAdapter
    private val comments = mutableListOf<Comment>()
    private var postId: String = ""
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)

        postId = intent.getStringExtra("postId") ?: return

        val tvTitle    = findViewById<TextView>(R.id.textDetailTitle)
        val tvContent  = findViewById<TextView>(R.id.textDetailContent)
        val btnLike    = findViewById<Button>(R.id.btnLike)
        val etComment  = findViewById<EditText>(R.id.editComment)
        val btnSubmit  = findViewById<Button>(R.id.btnSubmitComment)
        val rvComment  = findViewById<RecyclerView>(R.id.commentRecyclerView)

        commentAdapter = CommentAdapter(comments)
        rvComment.layoutManager = LinearLayoutManager(this)
        rvComment.adapter = commentAdapter

        loadPostAndComments(tvTitle, tvContent, btnLike)

        btnLike.setOnClickListener {
            lifecycleScope.launch {
                FirestoreCommunityManager.incrementLike(
                    postId,
                    onSuccess = {
                        val current = btnLike.text.toString()
                            .substringAfter("👍 ").toIntOrNull() ?: 0
                        btnLike.text = "👍 ${current + 1}"
                    },
                    onFailure = { e ->
                        Toast.makeText(this@CommunityDetailActivity,
                            "좋아요 실패: ${e.message}", Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }

        btnSubmit.setOnClickListener {
            val text = etComment.text.toString().trim()
            if (text.isEmpty()) return@setOnClickListener

            val comment = Comment(
                username = "익명",
                content = text,
                timestamp = System.currentTimeMillis()
            )

            FirestoreCommunityManager.addComment(
                postId, comment,
                onSuccess = {
                    comments.add(comment)
                    commentAdapter.notifyItemInserted(comments.size - 1)
                    etComment.text.clear()
                },
                onFailure = { e ->
                    Toast.makeText(this@CommunityDetailActivity,
                        "댓글 등록 실패: ${e.message}", Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun loadPostAndComments(
        tvTitle: TextView,
        tvContent: TextView,
        btnLike: Button
    ) {
        db.collection("community_posts")
            .document(postId)
            .get()
            .addOnSuccessListener { doc ->
                doc.toObject(Post::class.java)?.let { post ->
                    tvTitle.text   = post.title
                    tvContent.text = post.content
                    btnLike.text   = "👍 ${post.likes}"
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "게시글 조회 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        FirestoreCommunityManager.addCommentsListener(
            postId,
            onUpdate = { list ->
                comments.clear()
                comments.addAll(list)
                commentAdapter.notifyDataSetChanged()
            },
            onError = { e ->
                Toast.makeText(this, "댓글 로드 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}
