package com.example.fianl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

object FirestoreCommunityManager {
    private val db = FirebaseFirestore.getInstance()
    private val postsCol = db.collection("community_posts")

    fun savePost(post: Post, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val ref = if (post.id.isBlank()) postsCol.document() else postsCol.document(post.id)
        post.id = ref.id
        post.timestamp = System.currentTimeMillis()
        ref.set(post)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun addPostsListener(onUpdate: (List<Post>) -> Unit, onError: (Exception) -> Unit) {
        postsCol
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) { onError(err); return@addSnapshotListener }
                val list = snap!!.documents.mapNotNull { doc ->
                    doc.toObject(Post::class.java)?.apply { id = doc.id }
                }
                onUpdate(list)
            }
    }

    fun incrementLike(postId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val ref = postsCol.document(postId)
        db.runTransaction { tx ->
            val cur = tx.get(ref).getLong("likes") ?: 0
            tx.update(ref, "likes", (cur + 1).toInt())
        }.addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    private fun commentsCol(postId: String) = postsCol.document(postId).collection("comments")

    fun addComment(postId: String, comment: Comment, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val ref = commentsCol(postId).document()
        comment.id = ref.id
        comment.timestamp = System.currentTimeMillis()
        ref.set(comment)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun addCommentsListener(postId: String, onUpdate: (List<Comment>) -> Unit, onError: (Exception) -> Unit) {
        commentsCol(postId)
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snap, err ->
                if (err != null) { onError(err); return@addSnapshotListener }
                val list = snap!!.documents.mapNotNull { doc ->
                    doc.toObject(Comment::class.java)?.apply { id = doc.id }
                }
                onUpdate(list)
            }
    }
}
