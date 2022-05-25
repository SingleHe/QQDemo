package cn.edu.gzy.qqdemo.beans;

public class QQContactBean {
    private String name;//QQ 名
    private String onLineMode;//登录方式
    private String newAction;//新状态
    private String imgUrl;
    private String num;//QQ 号码
    private String belong_country;//所属国家
    public QQContactBean(){

    }
    public QQContactBean(String name, String imgUrl, String onLineMode, String newAction) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.onLineMode = onLineMode;
        this.newAction = newAction;
    }

    public QQContactBean(String name, String imgUrl, String onLineMode, String newAction, String num, String belong_country) {
        this.name = name;
        this.imgUrl = imgUrl;
        this.onLineMode = onLineMode;
        this.newAction = newAction;
        this.num = num;
        this.belong_country = belong_country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getBelong_country() {
        return belong_country;
    }

    public void setBelong_country(String belong_country) {
        this.belong_country = belong_country;
    }
}
