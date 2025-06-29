package com.example.fianl

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import com.example.fianl.CommunityActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*
import kotlin.jvm.java
import com.github.mikephil.charting.data.Entry

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var prefs: android.content.SharedPreferences
    private var isNotificationOn = true

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                showWelcomeNotification()
            } else {
                Toast.makeText(this, "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnAddRecord).setOnClickListener {
            startActivity(Intent(this, RecordActivity::class.java))
        }

        prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        isNotificationOn = prefs.getBoolean("isNotificationOn", true)

        findViewById<ImageView>(R.id.icon_bell).setOnClickListener {
            isNotificationOn = !isNotificationOn
            prefs.edit().putBoolean("isNotificationOn", isNotificationOn).apply()
            Toast.makeText(this,
                if (isNotificationOn) "알림 켜짐" else "알림 꺼짐",
                Toast.LENGTH_SHORT
            ).show()
        }

        findViewById<TextView>(R.id.textDate).text =
            SimpleDateFormat("M월 d일", Locale.KOREA).format(Date())


        drawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        findViewById<ImageView>(R.id.logoImage).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        findViewById<ImageView>(R.id.icon_profile).setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val cls = if (user != null) AccountActivity::class.java else LoginActivity::class.java
            startActivity(Intent(this, cls))
        }
        navView.setNavigationItemSelectedListener { item ->
            drawerLayout.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.nav_hospital  -> startActivity(Intent(this, HospitalActivity::class.java))
                R.id.nav_record    -> startActivity(Intent(this, RecordListActivity::class.java))
                R.id.nav_community -> startActivity(Intent(this, CommunityActivity::class.java))
                R.id.nav_settings  -> startActivity(Intent(this, SettingsActivity::class.java))
                R.id.nav_youtube  -> startActivity(Intent(this, YoutubeSearchActivity::class.java))
            }
            true
        }


        showRandomCommunityPost()

        findViewById<TextView>(R.id.textDate).setOnClickListener {
            showGraphPopup()
        }


        if (isNotificationOn) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                when {
                    ContextCompat.checkSelfPermission(
                        this, Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        showWelcomeNotification()
                    }
                    shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                        Toast.makeText(this,
                            "알림을 받으려면 권한을 허용해주세요.", Toast.LENGTH_SHORT
                        ).show()
                        requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                    else -> {
                        requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }
            } else {
                showWelcomeNotification()
            }
        }
    }

    private fun showRandomCommunityPost() {
        val db = com.google.firebase.firestore.FirebaseFirestore.getInstance()
        db.collection("community_posts")
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                val doc = documents.firstOrNull()
                if (doc != null) {
                    val post = doc.toObject(Post::class.java).apply { id = doc.id }

                    findViewById<TextView>(R.id.communityTitlePreview).text = post.title
                    findViewById<TextView>(R.id.communityContentPreview).text = post.content.take(50)
                    findViewById<androidx.cardview.widget.CardView>(R.id.communityCard)
                        .setOnClickListener {
                            startActivity(Intent(this, CommunityDetailActivity::class.java).apply {
                                putExtra("postId", post.id)
                            })
                        }
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
                Toast.makeText(this, "커뮤니티 게시글 불러오기 실패", Toast.LENGTH_SHORT).show()
            }
    }


    private fun showWelcomeNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        try {
            NotificationManagerCompat.from(this)
                .notify(
                    102,
                    NotificationCompat.Builder(this, "record_channel")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("오늘의 응원")
                        .setContentText("오늘도 힘내세요! 💪 건강 기록 작성 잊지 마세요.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                        .build()
                )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    private fun showGraphPopup() {
        val dialogView = layoutInflater.inflate(R.layout.graph_popup, null)
        val chart = dialogView.findViewById<LineChart>(R.id.chartPopup)
        val btnClose = dialogView.findViewById<Button>(R.id.btnClosePopup)

        val icon = dialogView.findViewById<ImageView>(R.id.iconGraph)

        val anim = android.view.animation.ScaleAnimation(
            0f, 1f,
            0f, 1f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.duration = 500
        icon.startAnimation(anim)

        val values = ArrayList<Entry>()
        for (i in 0 until 7) {
            val y = (Math.random() * 10).toFloat()
            values.add(Entry(i.toFloat(), y))
        }

        val dataSet = LineDataSet(values, "일별 기록")
        dataSet.color = Color.BLUE
        dataSet.setCircleColor(Color.RED)
        dataSet.lineWidth = 2f
        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.invalidate()

        val dialog = android.app.AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        btnClose.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

}
