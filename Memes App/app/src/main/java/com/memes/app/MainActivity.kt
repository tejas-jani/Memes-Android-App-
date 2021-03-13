package com.memes.app

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var currentMeme: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val shareButton = findViewById<Button>(R.id.shareButton);
//        val nextButton: Button = findViewById(R.id.nextButton);
        loadMeme();

        nextButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Share ", Toast.LENGTH_SHORT).show()
            loadMeme();
        });
//                shareButton.setOnClickListener {
//                    }
        shareButton.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "next meme ", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "$currentMeme")
            val chooser = Intent.createChooser(intent, "share this meme...")
            startActivity(chooser)
        });
    }

    private fun loadMeme() {
        // for showing ProgresssBar for image
        imageProgressBar.visibility = View.VISIBLE;

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
//                val url = response.getString("url");
//                val urll = response.getJSONArray("preview");
//                var cUrl=urll[2] .toString();

                val cUrl = response.getJSONArray("preview")[2].toString(); //we can allso use this
                Log.d("imgData", cUrl)
                currentMeme = cUrl
                Glide.with(this).load(cUrl).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
//                        TODO("Not yet implemented")
                        imageProgressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        imageProgressBar.visibility = View.GONE
//                        TODO("Not yet implemented")
                        return false
                    }
                }).into(MemeimageView)
                Log.d("img", url);
            },
            {
                Log.d("response", it.localizedMessage);

            })
        queue.add(jsonObjectRequest);
    }

}