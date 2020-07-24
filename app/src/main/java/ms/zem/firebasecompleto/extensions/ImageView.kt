package ms.zem.firebasecompleto.extensions

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import ms.zem.firebasecompleto.utils.AlertDialogUtil
import java.io.ByteArrayOutputStream


fun ImageView.compartilhar() {
    if (this.drawable != null) {
        val bitmap = (this.drawable as BitmapDrawable).bitmap
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
        val uri = Uri.parse(
            MediaStore.Images.Media.insertImage(
                this.context.contentResolver,
                bitmap, "Imagem", null
            )
        )
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        this.context.startActivity(Intent.createChooser(intent, "Compartilhar imagem"))
    } else {
        AlertDialogUtil.init(this.context)
            .erro("sem imagem para compartilhar")
    }
}
