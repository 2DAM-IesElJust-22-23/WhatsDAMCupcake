package com.ieseljust.pmdm.whatsdam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ieseljust.pmdm.whatsdam.databinding.ActivityMessagesWindowBinding

class Activity_messages_window : AppCompatActivity() {

    private lateinit var binding: ActivityMessagesWindowBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_window)

        binding = ActivityMessagesWindowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val messageText = binding.MessageText
        val sendMessage = binding.sendMessage


        sendMessage.setOnClickListener{
            messageText.text.clear()



        }




    }

}