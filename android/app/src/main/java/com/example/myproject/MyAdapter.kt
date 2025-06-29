package com.example.myproject

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemRecyclerviewBinding

// 화면을 구성하는 화면의 바이딩이 필요
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

// 리사이클러 뷰관리, 리사이클러 뷰에 사용자가 만든 데이터를 전달하고 그 데이터로 MyViewHolder내용을 리사이클러 뷰에 넣는다.
// 이때 Myadapter은 밑에 세개의 함수가 필수적이다.
class Myadapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 리사이클러뷰 안에 있는 아이템의 개수가 몇개냐 = 데이터의 갯수에 따라 다르다.
    override fun getItemCount(): Int {
        return datas.size
    }

    // MyVeiwHolder와 Myadapter연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder((ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    // 전달받은 데이터를 뷰홀더에 연결, 하나하나 대응
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]

        // 이벤트 처리
        binding.itemRoot.setOnClickListener {
            Toast.makeText(it.context, "${datas[position]}이 선택되었습니다.", Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(it.context).run{
                setTitle("알림")
                setIcon(android.R.drawable.ic_dialog_alert)
                setMessage("${datas[position]}이 선택되었습니다.")
                setPositiveButton("예", null)
                show()
            }
        }
    }
}

//꾸미기
class MyDecoration(val context: Context): RecyclerView.ItemDecoration(){
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // Darw : kbo -> item
        //override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        // DrawOver : item => kbo
        super.onDraw(c, parent, state)
        val width = parent.width
        val height = parent.height

        val dr = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        // 중앙정렬
        //val left = width/2 - drWidth?.div(2) as Int
        //val top = height/2 - drHeight?.div(2) as Int
        val left = width/2 - (drWidth?.div(2) ?: 0)
        val top = height/2 - (drHeight?.div(2) ?: 0)

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }


    // 아이템들 사이의 간격
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view)+1

        if(index % 3 == 0)
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 0)

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}
