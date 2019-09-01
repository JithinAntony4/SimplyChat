package com.simplychat;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {
    private String messageText;
    private String messageUser;
    private Date date;

    public Message(String messageText, String messageUser, Date date) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.date = date;
    }

    public Message() {
    }

    @ServerTimestamp
    public Date getDate() {
        return date;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

}
