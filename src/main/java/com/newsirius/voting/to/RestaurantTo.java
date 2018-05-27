package com.newsirius.voting.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RestaurantTo {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    public RestaurantTo(String name) {
        this.name = name;
    }

    public RestaurantTo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "name='" + name + '\'' +
                '}';
    }
}
