package ms.zem.firebasecompleto.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import ms.zem.firebasecompleto.R

object ImageDownloadUtil {

    fun downloadPicasso(url: String, imageView: ImageView, progressBar: ProgressBar? = null) {
        Picasso.get()
            .load(url)
            .into(object : Target {
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    progressBar?.visibility = View.VISIBLE
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    imageView.setImageResource(R.drawable.image_failed)
                    progressBar?.visibility = View.GONE
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    imageView.setImageBitmap(bitmap)
                    progressBar?.visibility = View.GONE
                }

            })
    }

    fun downloadGlide(uri: Uri, imageView: ImageView) {

        Glide.with(imageView.context)
            .asBitmap()
            .load(uri)
            .error(R.drawable.image_failed)
            .listener(object : RequestListener<Bitmap> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(imageView)
    }

    fun downloadGlide(url: String, imageView: ImageView, progressBar: ProgressBar? = null) {

        progressBar?.visibility = View.VISIBLE
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .error(R.drawable.image_failed)
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Bitmap>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imageView)
    }

}