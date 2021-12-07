package com.maasglobal.data.entities

import org.junit.Assert
import org.junit.Test

class ImageWikiTest{


    @Test
    fun`test that ImageWiki Model with title field EdgeCase is Formatted to Image Url`() {
        val imageWiki = ImageWiki(ns = 0, title = "File:Commons-logo.svg")
        Assert.assertEquals(
            "https://upload.wikimedia.org/wikipedia/commons/8/8e/Commons-logo.svg",
            imageWiki.formatToGetImage())
    }


    @Test
    fun `test that Hits Model User field  EdgeCase is Formatted to web link Url`() {
        val imageWiki = ImageWiki(ns = 0, title = "File:Commons-logo.svg")
        Assert.assertEquals("https://commons.wikimedia.org/wiki/File:Commons-logo.svg", imageWiki.formatToGetLink())
    }


}