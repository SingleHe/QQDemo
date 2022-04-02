package cn.edu.gzy.qqdemo.beans;

public class QQMessageBean {
    private String qqName;
    private int qqIcon;
    private String lastMsgTime;
    private String lastTitle;
    private int notReadMsgCount;
    public QQMessageBean(){

    }
    public QQMessageBean(String qqName, int qqIcon,String lastTitle, String lastMsgTime, int notReadMsgCount){
        this.qqName = qqName;
        this.qqIcon = qqIcon;
        this.lastTitle = lastTitle;
        this.lastMsgTime = lastMsgTime;
        this.notReadMsgCount = notReadMsgCount;
    }
    public String getQqName() {
        return qqName;
    }

    public void setQqName(String qqName) {
        this.qqName = qqName;
    }

    public int getQqIcon() {
        return qqIcon;
    }

    public void setQqIcon(int qqIcon) {
        this.qqIcon = qqIcon;
    }

    public String getLastMsgTime() {
        return lastMsgTime;
    }

    public void setLastMsgTime(String lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getLastTitle() {
        return lastTitle;
    }

    public void setLastTitle(String lastTitle) {
        this.lastTitle = lastTitle;
    }

    public int getNotReadMsgCount() {
        return notReadMsgCount;
    }

    public void setNotReadMsgCount(int notReadMsgCount) {
        this.notReadMsgCount = notReadMsgCount;
    }

    @Override
    public String toString() {
        return "QQMessageBean{" +
                "qqName='" + qqName + '\'' +
                ", qqIcon=" + qqIcon +
                ", lastMsgTime='" + lastMsgTime + '\'' +
                ", lastTitle='" + lastTitle + '\'' +
                ", notReadMsgCount=" + notReadMsgCount +
                '}';
    }
}
