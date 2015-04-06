package com.bgu.project.finalprojectir;

/**
 * Created by yoav on 09-Jan-15.
 */
public class ListItem {

    private int icon;
    private String title;

    public ListItem(){
    }

    public ListItem(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}