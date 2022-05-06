package cn.edu.gzy.qqdemo.dialogs;

/**
 * 解决添加新联系人后，无法自动刷新联系人列表问题。
 */
public interface OnDialogCompleted {
    void dialogCompleted(String dialogResult, int dialogId);
}
