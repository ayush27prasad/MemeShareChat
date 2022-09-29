package com.example.memechat

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {

    var currentImageurl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Toast.makeText(this,"App developed by Prasad", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest =JsonObjectRequest(
            Request.Method.GET,
            url ,
            null ,
            { response ->
                currentImageurl = response.getString("url")
                Glide.with(this).load(currentImageurl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                        //TODO("Not yet implemented")
                        return false

                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //TODO("Not yet implemented")
                        return false
                    }
                }).into(findViewById(R.id.meme_image))
                }
            ,
            {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            }
    )

    queue.add(jsonObjectRequest)
}
fun nextmeme(view: View) {
    loadMeme()
}
fun sharememe(view: View) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type="text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"Accha meme banaya h, dekhlo ek baari is link se $currentImageurl")
    val chooser = Intent.createChooser(intent,"Kispr share krne ka hai?...")
    Toast.makeText(this,"Sharing...", Toast.LENGTH_SHORT).show()
    startActivity(chooser)
}
}