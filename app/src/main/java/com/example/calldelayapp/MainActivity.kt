package com.example.calldelayapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var phoneNumberEditText: EditText
    private lateinit var delaySeekBar: SeekBar
    private lateinit var delayTextView: TextView
    private lateinit var callButton: Button

    private var delaySeconds = 5 // default

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText)
        delaySeekBar = findViewById(R.id.delaySeekBar)
        delayTextView = findViewById(R.id.delayTextView)
        callButton = findViewById(R.id.callButton)

        delaySeekBar.max = 30
        delaySeekBar.progress = delaySeconds
        delayTextView.text = "$delaySeconds sec"

        delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                delaySeconds = progress
                delayTextView.text = "$delaySeconds sec"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        callButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            if (phoneNumber.isNotEmpty()) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(Intent.ACTION_CALL)
                    intent.data = Uri.parse("tel:$phoneNumber")
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Permesso CALL_PHONE non concesso", Toast.LENGTH_SHORT).show()
                    }
                }, delaySeconds * 1000L)
            }
        }
    }
}
