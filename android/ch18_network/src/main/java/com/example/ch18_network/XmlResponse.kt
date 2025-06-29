package com.example.ch18_network

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class XmlResponse(
    @Element
    val body : myXmlBody
)

@Xml(name="body")
data class myXmlBody(
    @Element
    val items : myXmlItems
)

@Xml(name="items")
data class myXmlItems(
    @Element
    val item : MutableList<myXmlItem>
)

@Xml(name="item")
data class myXmlItem(
    @PropertyElement
    val stnId:String?,
    @PropertyElement
    val title:String?,
    @PropertyElement
    val tmFC:String?,
    @PropertyElement
    val tmSeq:String?
) {
    constructor() : this(null, null, null, null)
}

