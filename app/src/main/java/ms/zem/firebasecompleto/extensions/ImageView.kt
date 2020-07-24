package ms.zem.firebasecompleto.extensions

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.ImageView
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import java.io.ByteArrayOutputStream


fun ImageView.compartilhar() {
    if (this.drawable != null){
        val bitmap = (this.drawable as BitmapDrawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
        val uri = Uri.parse(MediaStore.Images.Media.insertImage(this.context.contentResolver,
            bitmap, "Imagem", null))
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        this.context.startActivity(Intent.createChooser(intent, "Compartilhar imagem"))
    } else {
        AlertDialogUtil.init(this.context)
            .erro("sem imagem para compartilhar")
    }
}

fun ImageView.compartilhar2020() { /// não esta ok
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/jpeg"
    val bitmap = (this.drawable as BitmapDrawable).bitmap
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.TITLE, "Compartilhar")
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        contentValues.put("image", bytes.toByteArray())
        val resolver: ContentResolver = this.context.contentResolver
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    } else {
        Uri.parse(MediaStore.Images.Media.insertImage(
            this.context.contentResolver, bitmap, "Compartilhar", null))
    }
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    this.context.startActivity(Intent.createChooser(intent, "Compartilhar imagem"))
}