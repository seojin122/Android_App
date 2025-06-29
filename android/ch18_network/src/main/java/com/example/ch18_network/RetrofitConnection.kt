package com.example.ch18_network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// https://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnList?serviceKey=서비스키(일반 인증키(Encoding))
// &pageNo=1&numOfRows=10&dataType=XML&stnId=184&fromTmFc=20250509&toTmFc=20250509

class RetrofitConnection{

    //객체를 하나만 생성하는 싱글턴 패턴을 적용합니다.
    companion object {
        //API 서버의 주소가 BASE_URL이 됩니다.
        private const val BASE_URL = "https://apis.data.go.kr/1360000/WthrWrnInfoService/"

        var jsonNetworkService : NetworkService
        val jsonRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        var xmlNetworkService : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        val BASE_URL2 = "https://newsapi.org"
        var imgNetworkService : NetworkService
        val imgRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()




        init{
            jsonNetworkService = jsonRetrofit.create(NetworkService::class.java)
            xmlNetworkService = xmlRetrofit.create(NetworkService::class.java)
            imgNetworkService = imgRetrofit.create(NetworkService::class.java)
        }
    }
}