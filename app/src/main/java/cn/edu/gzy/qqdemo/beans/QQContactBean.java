package cn.edu.gzy.qqdemo.beans;

public class QQContactBean {
    private String name;
    private int img;
    private String onLineMode;
    private String newAction;

    public QQContactBean(String name, int img, String onLineMode, String newAction) {
        this.name = name;
        this.img = img;
        this.onLineMode = onLineMode;
        this.newAction = newAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getOnLineMode() {
        return onLineMode;
    }

    public void setOnLineMode(String onLineMode) {
        this.onLineMode = onLineMode;
    }

    public String getNewAction() {
        return newAction;
    }

    public void setNewAction(String newAction) {
        this.newAction = newAction;
    }
}
