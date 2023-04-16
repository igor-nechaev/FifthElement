package com.nech9ev.sample_fifthelement

import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var uid: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid = Process.myUid()
        Log.e("MainActivity", uid.toString())

        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.url_connection).setOnClickListener {
            startActivity(Intent(this, UrlConnectionActivity::class.java))
        }
        findViewById<View>(R.id.make_crash).setOnClickListener {

        }
        findViewById<View>(R.id.web_view_button).setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
        findViewById<View>(R.id.jni_button).setOnClickListener {
        }
        findViewById<View>(R.id.curl).setOnClickListener {
            startActivity(Intent(this, CurlActivity::class.java))
        }
        findViewById<View>(R.id.ok_http).setOnClickListener {
            startActivity(Intent(this, OkHttpActivity::class.java))
        }
        findViewById<View>(R.id.exo_player).setOnClickListener {
            startActivity(Intent(this, ExoPlayerActivity::class.java))
        }
        findViewById<View>(R.id.fresco).setOnClickListener {
            startActivity(Intent(this, FrescoActivity::class.java))
        }
        findViewById<View>(R.id.retrofit).setOnClickListener {
            startActivity(Intent(this, RetrofitActivity::class.java))
        }
        findViewById<View>(R.id.glide).setOnClickListener {
            startActivity(Intent(this, GlideActivity::class.java))
        }
        findViewById<View>(R.id.picasso).setOnClickListener {
            startActivity(Intent(this, PicassoActivity::class.java))
        }
        findViewById<View>(R.id.downloadmanager).setOnClickListener {
            startActivity(Intent(this, DownloadManagerActivity::class.java))
        }
    }
}