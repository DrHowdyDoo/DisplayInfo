package com.drhowdydoo.displayinfo;

public class Data {

    private int ic1,ic2,ic3,ic4,ic_screen;
    private String title_1,title_2,title_3,title_4;
    private String value_1,value_2;
    private String screenSize;

    public Data(int ic1, int ic2, int ic3, int ic4, int ic_screen, String title_1, String title_2, String title_3, String title_4, String value_1, String value_2, String screenSize) {
        this.ic1 = ic1;
        this.ic2 = ic2;
        this.ic3 = ic3;
        this.ic4 = ic4;
        this.ic_screen = ic_screen;
        this.title_1 = title_1;
        this.title_2 = title_2;
        this.title_3 = title_3;
        this.title_4 = title_4;
        this.value_1 = value_1;
        this.value_2 = value_2;
        this.screenSize = screenSize;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getValue_1() {
        return value_1;
    }

    public void setValue_1(String value_1) {
        this.value_1 = value_1;
    }

    public String getValue_2() {
        return value_2;
    }

    public void setValue_2(String value_2) {
        this.value_2 = value_2;
    }

    public int getIc1() {
        return ic1;
    }

    public void setIc1(int ic1) {
        this.ic1 = ic1;
    }

    public int getIc2() {
        return ic2;
    }

    public void setIc2(int ic2) {
        this.ic2 = ic2;
    }

    public int getIc3() {
        return ic3;
    }

    public void setIc3(int ic3) {
        this.ic3 = ic3;
    }

    public int getIc4() {
        return ic4;
    }

    public void setIc4(int ic4) {
        this.ic4 = ic4;
    }

    public int getIc_screen() {
        return ic_screen;
    }

    public void setIc_screen(int ic_screen) {
        this.ic_screen = ic_screen;
    }

    public String getTitle_1() {
        return title_1;
    }

    public void setTitle_1(String title_1) {
        this.title_1 = title_1;
    }

    public String getTitle_2() {
        return title_2;
    }

    public void setTitle_2(String title_2) {
        this.title_2 = title_2;
    }

    public String getTitle_3() {
        return title_3;
    }

    public void setTitle_3(String title_3) {
        this.title_3 = title_3;
    }

    public String getTitle_4() {
        return title_4;
    }

    public void setTitle_4(String title_4) {
        this.title_4 = title_4;
    }
}
