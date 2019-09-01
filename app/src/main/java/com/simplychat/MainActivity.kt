package com.simplychat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private var adapter: FirestoreRecyclerAdapter<Message, MessageHolder>? = null
    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRV()
        fab.setOnClickListener {
            //Save Message to Firestore
            fab.hide()
            val displayName = FirebaseAuth.getInstance().currentUser?.displayName
            val msg = input.text.toString()
            val currentTime = Calendar.getInstance().time

            input.setText("")

            FirebaseFirestore.getInstance().collection("Messages")
                .add(Message(msg, displayName, currentTime))
                .addOnSuccessListener {
                    fab.show()
                }
                .addOnFailureListener {
                    fab.show()
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }

        }

        val query = FirebaseFirestore.getInstance().collection("Messages").orderBy("date")
        val options = FirestoreRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()

        adapter = object : FirestoreRecyclerAdapter<Message, MessageHolder>(options) {
            override fun onBindViewHolder(
                holder: MessageHolder,
                position: Int,
                model: Message
            ) {
                // Bind the Chat object to the ChatHolder
                holder.tvMsgText.text = model.messageText
                holder.tvMsgUser.text = model.messageUser
                holder.tvMsgTime.text = model.date.time.toString()
            }

            override fun onCreateViewHolder(group: ViewGroup, i: Int): MessageHolder {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                val view = LayoutInflater.from(group.context)
                    .inflate(R.layout.message, group, false)

                return MessageHolder(view)
            }
        }
        adapter?.notifyDataSetChanged()
        rv_Messages.adapter = adapter
/*
        query.addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(snapshot: QuerySnapshot?, e: FirebaseFirestoreException?) {
                if (e != null) {
                    // Handle error
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    return
                }

                // Convert query snapshot to a list of chats
                val chats = snapshot?.toObjects<Message>(Message::class.java)


                //Update UI
                if (chats != null) {
                    displayMessages(chats)
                } else {
                    Toast.makeText(applicationContext, "No Messages Yet", Toast.LENGTH_LONG).show()
                }
            }

        })
*/
    }

    private fun displayMessages(chats: List<Message>) {

        Toast.makeText(applicationContext, chats.size.toString(), Toast.LENGTH_LONG).show()
    }

    private fun initRV() {
        rv_Messages.layoutManager = LinearLayoutManager(this@MainActivity)
//                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
//        rv_Messages.adapter = adapter
        rv_Messages.addItemDecoration(
            Utils().GridSpacingItemDecoration(
                1,
                Utils().dpToPx(10, resources),
                true
            )
        )
        rv_Messages.itemAnimator = DefaultItemAnimator()

    }
}
