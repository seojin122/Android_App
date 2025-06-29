package com.example.fianl

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fianl.databinding.ActivityHospitalBinding
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.URL

class HospitalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHospitalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView = findViewById<RecyclerView>(R.id.hospitalRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Thread {
            val hospitalList = fetchAllHospitals()

            runOnUiThread {
                recyclerView.adapter = HospitalAdapter(hospitalList) { hospital ->
                    val uri = Uri.parse("geo:${hospital.lat},${hospital.lng}?q=${Uri.encode(hospital.name)}")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.setPackage("com.google.android.apps.maps")
                    if (intent.resolveActivity(packageManager) != null) {
                        startActivity(intent)
                    } else {
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                    }
                }
                hospitalList.forEach {
                    Log.d("병원", "[${it.source}] 이름: ${it.name}, 주소: ${it.address}, 전화번호: ${it.tel}")
                }
            }
        }.start()
    }

    private fun fetchAllHospitals(): List<Hospital> {
        val postpartumList = fetchHospitalsFromApi(
            "https://openapi.gg.go.kr/PostnatalCare?KEY=5e94f02f975944578a3cbd386b61a5d6&Type=xml&pIndex=1&pSize=100",
            "산후조리원"
        )
        val obgynList = fetchHospitalsFromApi(
            "https://openapi.gg.go.kr/GenrlhosptlObgyn?KEY=967466c1a5724c8ca679e49bd2c0fd20&Type=xml&pIndex=1&pSize=100",
            "산부인과"
        )
        return postpartumList + obgynList
    }

    private fun fetchHospitalsFromApi(urlStr: String, sourceType: String): List<Hospital> {
        val hospitals = mutableListOf<Hospital>()

        try {
            val url = URL(urlStr)
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(url.openStream(), "UTF-8")

            var eventType = parser.eventType
            var name = ""
            var address = ""
            var tel = ""
            var lat = 0.0
            var lng = 0.0

            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        when (parser.name) {
                            "BIZPLC_NM" -> name = parser.nextText()
                            "REFINE_ROADNM_ADDR" -> address = parser.nextText()
                            "LOCPLC_FACLT_TELNO" -> tel = parser.nextText()
                            "REFINE_WGS84_LAT" -> lat = parser.nextText().toDoubleOrNull() ?: 0.0
                            "REFINE_WGS84_LOGT" -> lng = parser.nextText().toDoubleOrNull() ?: 0.0
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == "row") {
                            if (tel.isBlank()) tel = "정보 없음"
                            hospitals.add(Hospital(name, address, tel, lat, lng, sourceType))
                            name = ""; address = ""; tel = ""; lat = 0.0; lng = 0.0
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return hospitals
    }
}
