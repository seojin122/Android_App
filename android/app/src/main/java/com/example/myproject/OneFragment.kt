package com.example.myproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myproject.databinding.FragmentOneBinding
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class OneFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // 프레그먼트 실행시 최초 실행
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // 프레그먼트가 구성하는 화면
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        val binding = FragmentOneBinding.inflate(inflater,container, false)
        // activity는 layoutInflater매개변수를 이용하지만, 프레그먼트는 inflate를 괄호 안에 쓴다.

        // 데이터 베이스 불러오고 저장하기
        var datas = mutableListOf<String>()
        val db= DBHelper(inflater.context).readableDatabase
        val cursor = db.rawQuery("select * from TODO_TB", null)
        cursor.run{
            while(moveToNext()) {
                datas.add(cursor.getString(1))
            }
        }
        db.close()


        val radapter = Myadapter(datas)
        //binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        // 그리드로 표현
        binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.recyclerView.adapter = radapter

        // 데코레이션 반영
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))




        // add_activity로 데이터를 받아서 반영
        val requestLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult (ActivityResultContracts.StartActivityForResult()) {
                val todo = it.data!!.getStringExtra("result")
                if(todo != ""){
                    datas.add(todo!!)
                    // 아답터에서 바뀐 데이터를 다시 반영해줘
                    radapter.notifyDataSetChanged()
                }
            }

        // 이벤트처리
        binding.fab.setOnClickListener {
            //datas.add("Item 100")
            // 아답터에서 바뀐 데이터를 다시 반영해줘
            //radapter.notifyDataSetChanged()



            // add_activity로 데이터를 받아서 반영
            val intent = Intent(it.context, AddActivity::class.java)
            //날짜 추가하기
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))
            //startActivity(intent)
            requestLauncher.launch(intent)


        }

        //return inflater.inflate(R.layout.fragment_one, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}