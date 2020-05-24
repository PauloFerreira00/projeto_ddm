package br.com.farras.appzinho.helpers

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun AppCompatActivity.getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
    val canvas = Canvas()
    val bitmap = Bitmap.createBitmap(
        75,
        100,
        Bitmap.Config.ARGB_8888
    );
    canvas.setBitmap(bitmap)
    drawable.setBounds(0, 0, 75, 100);
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
