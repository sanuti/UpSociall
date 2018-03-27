package com.example.sarthak.sanuti.Activities.db;

/**
 * Created by SARTHAK on 3/8/2018.
 */

public class ChatSender {
    String messageId,time,message,userid;

    public ChatSender(String messageId, String time, String message, String userid) {
        this.messageId = messageId;
        this.time = time;
        this.userid=userid;
        this.message = message;
    }
   public ChatSender()
    {

    }

    public String getuserid() {
        return userid;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }
}
