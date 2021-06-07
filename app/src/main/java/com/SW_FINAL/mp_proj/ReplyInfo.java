package com.SW_FINAL.mp_proj;

public class ReplyInfo {

    private String replyContent;
    private String userName;


    public ReplyInfo(String userName, String replyContent) {
        this.replyContent = replyContent;
        this.userName = userName;
    }

    public ReplyInfo() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




    public String getReplyContent() {
        return replyContent;
    }


    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

}
