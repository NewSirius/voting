package com.newsirius.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "integer default nextval('restaurants_seq')")
    private Integer id;

    @Column(name = "name")
    private String name;

    //https://stackoverflow.com/questions/33031541/lazy-loading-issue-with-one-to-one-association-in-hibernate
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "voteRating")
    private Set<VoteRatingEntity> voteRating;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonManagedReference(value = "voteEntity")
    private Set<VoteUserEntity> voteUserEntity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference(value = "dishes")
    private List<Dish> dishes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<VoteRatingEntity> getVoteRating() {
        return voteRating;
    }

    public void setVoteRating(Set<VoteRatingEntity> voteRating) {
        this.voteRating = voteRating;
    }

    public Set<VoteUserEntity> getVoteUserEntity() {
        return voteUserEntity;
    }

    public void setVoteUserEntity(Set<VoteUserEntity> voteUserEntity) {
        this.voteUserEntity = voteUserEntity;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
