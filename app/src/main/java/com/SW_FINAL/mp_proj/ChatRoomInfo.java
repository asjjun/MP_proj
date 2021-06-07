package com.SW_FINAL.mp_proj;

public class ChatRoomInfo {
    private String chatRoomName;
    private String chatContent;


    public ChatRoomInfo(String chatRoomName, String chatContent) {
        this.chatRoomName = chatRoomName;
        this.chatContent = chatContent;
    }


    public String getChatRoomName() {
        return chatRoomName;
    }

    public void setChatRoomName(String chatRoomName) {
        this.chatRoomName = chatRoomName;
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }
}