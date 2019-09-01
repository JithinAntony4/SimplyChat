package com.simplychat

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class MessageHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvMsgUser: TextView = view.findViewById(R.id.message_user)
    val tvMsgText: TextView = view.findViewById(R.id.message_text)
    val tvMsgTime: TextView = view.findViewById(R.id.message_time)
}
