package cn.luischen.model;

import lombok.Data;

@Data
public class Mail {
    private String from;
    private String to;
    private String subject;
    private String content;
    public Mail() {
    }
    public Mail(String from, String to, String subject, String content) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}