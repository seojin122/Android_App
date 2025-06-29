

package com.example.ch20_firebase

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch20_firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Firebase.messaging.token.addOnSuccessListener {
//            Log.d("25android", it)
//        }

        // 허용되지 않았을 때
        val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it.all {permission -> permission.value != true}){
                Toast.makeText(this, "permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
        // api 버전 확인
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this, "android.permission.POST_NOTIFICATION") != PackageManager.PERMISSION_GRANTED){
                //승인 단계
                permissionLauncher.launch(arrayOf("android.permission.POST_NOTIFICATION"))
            }

        }

        if(MyApplication.checkAuth() || MyApplication.email != null){
            binding.toolbar.title = "${MyApplication.email}님"
        }
        else{
            binding.toolbar.title = "안녕하세요"
        }

        setSupportActionBar(binding.toolbar)

        //MyApplication.auth
        binding.addFab.setOnClickListener {
            if(MyApplication.checkAuth()){
                // AddActivity 호출
                val intent = Intent(this, AddActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "인증을 먼저 해주세요", Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.menu_login -> {
                val intent = Intent(this, AuthActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.menu_login)
        if(MyApplication.checkAuth() || MyApplication.email != null){
            item?.title = "로그아웃"
        }
        else{
            item?.title = "로그인"
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()

        invalidateMenu()
        if(MyApplication.checkAuth() || MyApplication.email != null){
            binding.toolbar.title = "${MyApplication.email}님"
            binding.mainRecyclerView.visibility = View.VISIBLE


            MyApplication.db.collection("news")
                .get()
                .addOnSuccessListener { result ->
                    var itemList = mutableListOf<ItemData>()
                    for( document in result){
                        val item = document.toObject(ItemData::class.java) // email, data, centent
                        item.docId = document.id
                        itemList.add(item)
                    }
                    binding.mainRecyclerView.layoutManager = LinearLayoutManager(this)
                    binding.mainRecyclerView.adapter= MyAdapter(itemList)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Firestore로부터 데이터획득에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }

        }
        else{
            binding.toolbar.title = "안녕하세요"
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

}