package com.openclassrooms.watchlist.domain;

import com.openclassrooms.watchlist.annotations.GoodMovie;
import com.openclassrooms.watchlist.annotations.BadMovie;
import com.openclassrooms.watchlist.annotations.Priority;
import com.openclassrooms.watchlist.annotations.Rating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@GoodMovie
@BadMovie
public class WatchlistItem {

    private Integer id;

    @NotBlank(message="Please enter the title")
    private String title;

    @Rating
    private String rating;

    @Priority
    private String priority;

    @Size(max=50, message="Comment should be maximum of 50 characters")
    private String comment;

    public static int index = 0;

    public WatchlistItem() {
        this.id = index++;
    }

    public WatchlistItem(String title, String rating, String priority, String comment) {
        super();
        this.id = index++;
        this.title = title;
        this.rating = rating;
        this.priority = priority;
        this.comment = comment;
    }

    public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return this.rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPriority() {
        return this.priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getComment() {
        return this.comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

}
