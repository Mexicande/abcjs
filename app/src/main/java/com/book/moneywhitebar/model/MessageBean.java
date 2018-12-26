package com.book.moneywhitebar.model;

import java.io.Serializable;

public class MessageBean implements Serializable {

    /**
     * body : 130****0975 通过手机借款申请下款5000元
     */

    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
