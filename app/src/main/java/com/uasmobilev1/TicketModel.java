package com.uasmobilev1;

public class TicketModel {

    private String ticketId;
    private String date;
    private String time;
    private String price;
    private String busInfo;
    private String route;
    private String seatInfo;



    public TicketModel(String ticketId, String date, String time, String price, String busInfo, String route, String seatInfo) {
        this.ticketId = ticketId;
        this.date = date;
        this.time = time;
        this.price = price;
        this.busInfo = busInfo;
        this.route = route;
        this.seatInfo = seatInfo;
    }

    // Getters and setters for each field

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBusInfo() {
        return busInfo;
    }

    public void setBusInfo(String busInfo) {
        this.busInfo = busInfo;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getSeatInfo() {
        return seatInfo;
    }

    public void setSeatInfo(String seatInfo) {
        this.seatInfo = seatInfo;
    }
}