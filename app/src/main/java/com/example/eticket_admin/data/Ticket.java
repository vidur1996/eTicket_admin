package com.example.eticket_admin.data;

public class Ticket {
    String ticketNo;
    String userName;
    String ticketValue;
    String ticketTo;
    String ticketFrom;

    public Ticket() {

    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTicketValue() {
        return ticketValue;
    }

    public void setTicketValue(String ticketValue) {
        this.ticketValue = ticketValue;
    }

    public String getTicketTo() {
        return ticketTo;
    }

    public void setTicketTo(String ticketTo) {
        this.ticketTo = ticketTo;
    }

    public String getTicketFrom() {
        return ticketFrom;
    }

    public void setTicketFrom(String ticketFrom) {
        this.ticketFrom = ticketFrom;
    }
}
