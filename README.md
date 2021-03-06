# Voting system application

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

## Running Voting Application locally (IntelliJ IDEA + Tomcat)
To start, use Java 8 or higher with Tomcat 8.5.27 or higher.  

In IntelliJ IDEA import project from GitHub:  
`File -> New -> Project from Version Control -> GitHub`

Add Tomcat server (local) in Run/Debug Configurations with Application Context "/voting"  
You can then access application here: <http://localhost:8080/voting/>

## Database configuration
In its default configuration, Voting Application uses an in-memory database (HSQLDB) which gets populated at startup with data.

## Description

Logged admins can
- create, get restaurants;
- create dishes;  
`If this is the first dish of the restaurant for the current day, then a restaurant rating is created for the current day with a value of zero`
- create, get, delete users.

Anybody users (anonymous and logged) can:
- get list of restaurants with rating >= 0 current day;
- get restaurant with rating and menu current day;
 
Logged users can:
- vote for the restaurant and revote until 11:00 am same day.  
    - if this is the first user voting current day:  
      -- check vote time;  
      -- increment restaurant rating;  
      -- save user vote.  
    - else:  
      -- check vote time;  
      -- check the vote for the same restaurant;  
      -- decrement rating for the "old" restaurant;  
      -- increment rating for the "new" restaurant;  
      -- merge user vote.


Embedded accounts in application:  
Username: user1@yandex.ru Password: "password" (User role)  
Username: admin@gmail.com Password: "admin" (Administrator role)

P.S. You need register environment variable "VOTING_ROOT" to the root directory of project.
This is necessary for generating log files in root project folder.


## Curl Commands

* #### Get all restaurants
`curl -s http://localhost:8080/voting/rest/admin/restaurants --user admin@gmail.com:admin`

* #### Get restaurants with rating for current date (sort by rating and name fields)
`curl -s http://localhost:8080/voting/rest/restaurants`

* #### Get restaurant with rating and menu for current day
`curl -s http://localhost:8080/voting/rest/restaurants/1000`

* #### Create a new restaurant
`curl -s -X POST -d '{"name": "NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/restaurants --user admin@gmail.com:admin`

* #### Delete the restaurant (with id 1000)
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/restaurants/1000 --user admin@gmail.com:admin`
 
* #### Create dishes (for restaurant with id 1001)
`curl -s -X POST -d '{"name": "NewDish", "price": 299}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/restaurants/1001/dishes --user admin@gmail.com:admin`
  
* #### Get users
`curl -s http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`
  
* #### Get user (with id 1000)
`curl -s http://localhost:8080/voting/rest/admin/users/1000 --user admin@gmail.com:admin`
   
* #### Create a new user
`curl -s -X POST -d '{"name": "NewUser", "password": "user123", "email": "newuser@email.com"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/users --user admin@gmail.com:admin`

* #### Delete user (with id 1000)
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/users/1000 --user admin@gmail.com:admin`

* #### Vote for the restaurant's menu (with restaurantId 1000)
`curl -s -X POST -d '{}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/restaurants/1000/vote --user user1@yandex.ru:password`