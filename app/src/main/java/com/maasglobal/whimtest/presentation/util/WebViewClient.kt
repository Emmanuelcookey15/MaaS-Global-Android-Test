package com.maasglobal.whimtest.presentation.util

import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.view.View
import android.webkit.*
import android.widget.ProgressBar


class MyWebClient(var progressBar: ProgressBar, var context: Context): WebViewClient(){
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        var message = "SSL Certificate error."
        when (error!!.primaryError) {
            SslError.SSL_UNTRUSTED -> message = "The certificate authority is not trusted."
            SslError.SSL_EXPIRED -> message = "The certificate has expired."
            SslError.SSL_IDMISMATCH -> message = "The certificate Hostname mismatch."
            SslError.SSL_NOTYETVALID -> message = "The certificate is not yet valid."
            SslError.SSL_DATE_INVALID -> message = "The date of the certificate is invalid."
            SslError.SSL_INVALID -> message = "A generic error occured."
        }
        message += " Do you want to continue anyway?"

        builder.setTitle("SSL Certificate Error")
        builder.setMessage(message)
        builder.setPositiveButton("continue"
        ) { _, _ -> handler?.proceed() }
        builder.setNegativeButton("cancel"
        ) { _, _ -> handler?.cancel() }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        progressBar.visibility = View.VISIBLE
        return super.shouldOverrideUrlLoading(view, request)

    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        progressBar.visibility = View.GONE
        super.onPageStarted(view, url, favicon)
    }

    override fun onPageFinished(view: WebView?, url: String?) {

    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)

    }
}