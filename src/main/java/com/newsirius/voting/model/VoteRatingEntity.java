package com.newsirius.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vote_rating_history")
public class VoteRatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "integer default nextval('vote_rating_history_seq')")
    private Integer id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    @JsonBackReference(value = "voteRating")
    private Restaurant restaurant;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "date")
    private LocalDate localDate = LocalDate.now();

    public VoteRatingEntity() {
    }

    public VoteRatingEntity(Integer id, Restaurant restaurant, Integer rating, LocalDate localDate) {
        this.id = id;
        this.restaurant = restaurant;
        this.rating = rating;
        this.localDate = localDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public String toString() {
        return "VoteRatingEntity{" +
                ", rating=" + rating +
                ", localDate=" + localDate +
                '}';
    }
}
