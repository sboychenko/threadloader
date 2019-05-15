package ru.sboychenko.model;

public class File {

    private String url;
    private String md5;

    public File(String url, String md5) {
        this.url = url;
        this.md5 = md5;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
