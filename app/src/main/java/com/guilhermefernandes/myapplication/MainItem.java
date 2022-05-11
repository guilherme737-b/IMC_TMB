package com.guilhermefernandes.myapplication;

public class MainItem {

    private int id;
    private int drawableID;
    private int textStringId;
    private int color;


    public MainItem(int id, int drawableID, int textStringId, int color) {
        this.id = id;
        this.drawableID = drawableID;
        this.textStringId = textStringId;
        this.color = color;
    }


    public void setColor(int color){
        this.color = color;
    }

    public void setDrawableID(int drawableID){
        this.drawableID = drawableID;
    }

    public void setTextStringId(int textStringId) {
        this.textStringId = textStringId;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public int getDrawableID() {
        return drawableID;
    }

    public int getTextStringId() {
        return textStringId;
    }

    public int getId() {
        return id;
    }
}
