package com.SW_FINAL.mp_proj;

public class MessageInfo {
    private String postID;
    private String contents;

    public MessageInfo(String postID, String contents) {
        this.postID = postID;
        this.contents = contents;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
