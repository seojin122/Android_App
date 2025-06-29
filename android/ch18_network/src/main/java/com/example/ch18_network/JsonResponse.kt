package com.example.ch18_network

data class myJsonItem(val stnId:String, val title:String, val tmFc:String)
data class myJsonItems(val item : MutableList<myJsonItem>?)
data class myJsonBody(val items : myJsonItems)
data class myJsonResponse(val body:myJsonBody)
data class JsonResponse(val response : myJsonResponse)
