package com.app.videocall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dbFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Constants.isIntiatedNow = true
        Constants.isCallEnded = true



        start_meeting.setOnClickListener {
            if (meeting_id.text.toString().trim().isEmpty())
                meeting_id.error = "Please enter meeting id"
            else {
                dbFirestore.collection("calls")
                    .document(meeting_id.text.toString())
                    .get()
                    .addOnSuccessListener {
                        if (it["type"]=="OFFER" || it["type"]=="ANSWER" || it["type"]=="END_CALL") {
                            meeting_id.error = "Please enter new meeting ID"
                        } else {
                            val intent = Intent(this@MainActivity, RTCActivity::class.java)
                            intent.putExtra("meetingID",meeting_id.text.toString())
                            intent.putExtra("isJoin",false)
                            startActivity(intent)
                            finish()
                        }
                    }
                    .addOnFailureListener {
                        meeting_id.error = "Please enter new meeting ID-"
                    }
            }
        }


        join_meeting.setOnClickListener {
            if (meeting_id.text.toString().trim().isNullOrEmpty())
                meeting_id.error = "Please enter meeting id"
            else {
                val intent = Intent(this@MainActivity, RTCActivity::class.java)
                intent.putExtra("meetingID",meeting_id.text.toString())
                intent.putExtra("isJoin",true)
                startActivity(intent)
                finish()
            }
        }



    }



}