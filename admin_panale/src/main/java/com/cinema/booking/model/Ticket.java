package com.cinema.booking.model;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {
    private int ticketId;
    private int showId;
    private BigDecimal price;
    private int quantity;
    private String status;
    private Date showTime;
    private String movieTitle;
    
    // Getters
    public int getTicketId() { return ticketId; }
    public int getShowId() { return showId; }
    public BigDecimal getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public Date getShowTime() { return showTime; }
    public String getMovieTitle() { return movieTitle; }
    
    // Setters
    public void setTicketId(int ticketId) { this.ticketId = ticketId; }
    public void setShowId(int showId) { this.showId = showId; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
    public void setShowTime(Date showTime) { this.showTime = showTime; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
}
