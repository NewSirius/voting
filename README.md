#### Task

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


####Remarks
You need register path variable "VOTING_ROOT" to the root directory of project.
This is necessary for generating log files in root project folder.

This project with the embedded HSQLDB base.

#### Curl Commands

* #####Get all restaurants
`curl -s http://localhost:8080/voting/rest/admin/restaurants`

* #####Get restaurants with rating for current date (sort by rating and name fields)
`curl -s http://localhost:8080/voting/rest/restaurants`

* #####Get restaurant with rating and menu for current day
`curl -s http://localhost:8080/voting/rest/restaurants/1000`

* #####Create a new restaurant
`curl -s -X POST -d '{"name": "NewRestaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/restaurants`

* #####Delete the restaurant (with id 1000)
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/restaurants/1000`
 
* #####Create dishes (for restaurant with id 1001)
`curl -s -X POST -d '{"name": "NewDish", "price": 299}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/restaurants/1001/dishes`
  
* #####Get users
`curl -s http://localhost:8080/voting/rest/admin/users`
  
* #####Get user (with id 1000)
`curl -s http://localhost:8080/voting/rest/admin/users/1000`
   
* #####Create a new user
`curl -s -X POST -d '{"name": "NewUser", "password": "user123", "email": "newuser@email.com"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/admin/users`

* #####Delete user (with id 1000)
`curl -s -X DELETE http://localhost:8080/voting/rest/admin/users/1000`

* #####Vote for the restaurant's menu (with restaurantId 1000)
`curl -s -X POST -d '{}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/voting/rest/restaurants/1000/vote`