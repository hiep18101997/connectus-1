package com.connect.chat.connectus.model;

public class ItemOffline {

    private String name, mac ;
    private int status ;

    public ItemOffline(String name, String mac, int status) {
        this.name = name;
        this.mac = mac;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
