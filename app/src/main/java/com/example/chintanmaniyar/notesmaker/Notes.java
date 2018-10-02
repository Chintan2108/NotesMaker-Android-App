package com.example.chintanmaniyar.notesmaker;

public class Notes {
    private String title;
    private String desc;

    public Notes(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }
}
