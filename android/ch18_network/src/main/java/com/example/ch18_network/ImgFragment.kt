package com.example.ch18_network

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_network.databinding.FragmentImgBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImgFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImgFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val TAG = "25android"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentImgBinding.inflate(inflater, container, false)

        val query = arguments?.getString("searchLoc") ?: "android"
        Log.d(TAG, query)

        val call: Call<ImgResponse> = RetrofitConnection.imgNetworkService.getImgList(
            query,
            "079dac74a5f94ebdb990ecf61c8854b7",
            1,
            10
        )

        call?.enqueue(object : Callback<ImgResponse> {
            override fun onResponse(call: Call<ImgResponse>, response: Response<ImgResponse>) {
                if(response.isSuccessful){
                    Log.d(TAG, "$response")
                    Log.d(TAG, "${response.body()}")

                    binding.imgRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.imgRecyclerView.adapter = ImgAdapter(response.body()?.articles)
                    binding.imgRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }

            override fun onFailure(call: Call<ImgResponse>, t: Throwable) {
                Log.d(TAG, "${call.request()}")
                Log.d(TAG, "onFailure : ${t.message}")
            }
        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImgFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImgFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}