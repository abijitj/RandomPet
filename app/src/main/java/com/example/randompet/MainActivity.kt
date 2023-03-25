package com.example.randompet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var petImageURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.petButton);
        var imageView = findViewById<ImageView>(R.id.petImage)
        getCatImageURL()//getDogImageURL()
        getNextImage(button, imageView)
    }

    private fun getDogImageURL(){
        val client = AsyncHttpClient()

        client["https://dog.ceo/api/breeds/image/random", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Dog", "response successful")
                Log.d("Dog", "response successful$json")

                petImageURL = json.jsonObject.getString("message")
                Log.d("petImageURL", "pet image URL set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]

    }

    private fun getNextImage(button: Button, imageView: ImageView) {


        button.setOnClickListener {
            val randVal = Random.nextInt()
            Log.d("getNextImage", "Randval: ${randVal}")
            if(randVal % 2 == 0){
                getDogImageURL()
            } else {
                getCatImageURL()
            }

            Glide.with(this)
                . load(petImageURL)
                .fitCenter()
                .into(imageView)
        }
    }


    private fun getCatImageURL() {
        val client = AsyncHttpClient()

        client["https://api.thecatapi.com/v1/images/search", object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JsonHttpResponseHandler.JSON) {
                Log.d("Cat", "response successful")
                Log.d("Cat", "response successful$json")


                var resultsJSON = json.jsonArray.getJSONObject(0)
                petImageURL = resultsJSON.getString("url")


                Log.d("catImageURL", "cat image URL set")
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable?
            ) {
                Log.d("Dog Error", errorResponse)
            }
        }]

    }



}