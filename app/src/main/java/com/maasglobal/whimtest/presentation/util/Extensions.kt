package com.maasglobal.whimtest.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.material.snackbar.Snackbar




inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG) {
  val snack = Snackbar.make(this, message, length)
  snack.show()
}

inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_INDEFINITE, f: Snackbar.() -> Unit) {
  val snack = Snackbar.make(this, message, length)
  snack.f()
  snack.show()
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
  setAction(action, listener)
  color?.let { setActionTextColor(color) }
}


fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
  return ContextCompat.getDrawable(context, vectorResId)?.run {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
    draw(Canvas(bitmap))
    BitmapDescriptorFactory.fromBitmap(bitmap)
  }
}

