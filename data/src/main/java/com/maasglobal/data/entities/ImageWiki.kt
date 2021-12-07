package com.maasglobal.data.entities

data class ImageWiki (

    var ns: Int? = null,
    var title: String? = null
){

    fun formatToGetLink(): String{
        return "https://commons.wikimedia.org/wiki/$title"
    }



    fun formatToGetImage(): String{
        val formattedString = title?.substring(5)
        return "https://upload.wikimedia.org/wikipedia/commons/8/8e/$formattedString"
    }

}
