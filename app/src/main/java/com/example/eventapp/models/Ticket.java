package com.example.eventapp.models;

public class Ticket {
    private String eventName;
    private String eventDate;
    private String seatType;
    private int ticketCount;

    // Empty constructor for Firebase
    public Ticket() {
    }

    public Ticket(String eventName, String eventDate, String seatType, int ticketCount) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.seatType = seatType;
        this.ticketCount = ticketCount;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getTicketCount() {
        return ticketCount;
    }
}
