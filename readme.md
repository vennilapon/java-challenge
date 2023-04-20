### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.




### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push
- use `mvn spotless:check` and `mvn spotless:apply` for the code format 
- Find credentials from `application.properties` file

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.


#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### My experience

- I have around 4 years experience in Java and I started to use Spring Boot Webflux for last 6 months
- I am good in analytics and logics
- I have experience in working with Insurance projects

#### Improvements done from the master branch
- Bug fixes 
- Exception Handling
- Spring documentation
- Adding comments
- Security for controllers
- Caching Logics
- Unit tests and Integration tests for controller
- Pagination for GET ALL method
- Spotless code format plugin to maintain same code format 

#### Improvements I would like to do
- using docker containers
- Jenkins deployment
- Sonar and Jacoco integrations
