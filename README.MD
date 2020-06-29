[![CircleCI](https://circleci.com/gh/KunnChan/os_web.svg?style=svg)](https://circleci.com/gh/KunnChan/os_web)
### Online Shopping System

 by Min Kunn Chan, May 8 2020, 48 hours challenge

#### Setup
##### Maven  from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
##### Spring cli from [https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-installation](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-cli.html#cli-installation)

to make it easy you can import project to your IDE 
and run main class [OnlineShoppingApplication.java]



### Steps to run
You can run in multiple methods.
### 1 - Import to your IDE method
 1. Download and Extract
 2. Import to your IDE [I am using Intellij]
 3. open application.properties file and edit your MySql user name and password
 4. Run data.sql under resources folder
 5. Run command => mvn clean install
 6. Run  [OnlineShoppingApplication.java]

### 2 - Run with terminal
 1. Download and Extract
 2. Open Terminal and navigate to project folder
 3. open application.properties file and edit your MySql user name and password
 4. Run data.sql under resources folder
 5. run command => mvn clean install
 6. run command => mvn spring-boot:run

### Guide
once application is started on port 8085
you can access it @ [http://localhost:8085/home](http://localhost:8085/order)

This project is using MySql database
You need to run database script file under folder
    resources > data.sql

In that script I have added table creation and data inserting scripts
500 records of product to PRODUCT table.
4 records of user to User table.

As you can see in data.sql file,
1. for User 1
    1. email => user1@gmail.com
    2. password => user1
2. for User 2
    1. email => user2@gmail.com
	2. password => user2

## Current features
1. View products with load more function 12 product per request [using ajax loading]
2. Search product by category and title by using query DSL 
		[backend done, need to bind with UI]
3. Login/Logout with email and password
4. View product detail
5. Increase and decrease qty in product detail page before you add it to cart
6. Product prices and quantity count are updated in real time
7. Delete product from cart 
8. Add to cart function
9. Cart with product list, you can update qty
10. Checkout function
11. Billing Address creation
12. Selecting payment method
13. Making order
14. Order list with price and order state
15. Order detail page
16. History table, which is stored every event done by user
        [Using it to create product suggestion list to user in future]

## Future features to add
1. Bind search function to UI
2. Form validation in creating billing address and payment
3. Selecting existing address and edit
4. Payment method edit
5. Update audit user [createdBy, createdDate, updatedBy, updatedDate]
6. Add admin panel for updating order state [ORDER_CREATED, ORDER_PROCESSING, ORDER_SHIPPED]
7. Add new product page
