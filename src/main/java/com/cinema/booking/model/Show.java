package com.cinema.booking.model;

import java.util.Date;
import java.math.BigDecimal;

public class Show {
    private int showId;
    private int movieId;
    private int theaterId;
    private Date showTime;
    private BigDecimal price;
    private int availableSeats;
    private String movieTitle;
    private String theaterName;
    
    // Getters and Setters
    public int getShowId() { return showId; }
    public void setShowId(int showId) { this.showId = showId; }
    
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    
    public int getTheaterId() { return theaterId; }
    public void setTheaterId(int theaterId) { this.theaterId = theaterId; }
    
    public Date getShowTime() { return showTime; }
    public void setShowTime(Date showTime) { this.showTime = showTime; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
    
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }
    
    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }
}
