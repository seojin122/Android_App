package com.example.ch18_network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


// https://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnList?serviceKey=서비스키(일반 인증키(Encoding))
// &pageNo=1&numOfRows=10&dataType=XML&stnId=184&fromTmFc=20250509&toTmFc=20250509

interface NetworkService {

    @GET("getWthrWrnList")
    fun getJsonList(
        @Query("stnId") loc:Int,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("dataType") returnType:String,
        @Query("serviceKey") apiKey:String,
        @Query("fromTmFc") from_date:String,
        @Query("toTmFc") to_date:String
    ) : Call<JsonResponse>



    @GET("getWthrWrnList")
    fun getXmlList(
        @Query("stnId") loc:Int,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("dataType") returnType:String,
        @Query("serviceKey") apiKey:String,
        @Query("fromTmFc") from_date:String,
        @Query("toTmFc") to_date:String
    ) : Call<XmlResponse>


    @GET("/v2/everything")
    fun getImgList(
        @Query("q") q:String,
        @Query("apiKey") apiKey: String,
        @Query("page") pageNo:Long,
        @Query("pageSize") numOfRows:Int
    ) : Call<ImgResponse>

}