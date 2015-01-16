# Homework 1: Domain Logic
###### Purpose:
Understand 2 enterprise architectural patterns of domain logic layer.

1. Transaction scripts
2. Domain model

###### Exercise:
Implement domain logic layer of an enterprise application which books revenue earned by selling 3 types of products 
(Word processor app, Spread sheet app and Database app).

## 1. Implementing using Transaction scripts
> "Most business applications can be thought of as a series of transactions. 
A transaction may view some information as organized in a particular way, another will make changes to it."
(Martin Fowler - http://martinfowler.com/eaaCatalog/transactionScript.html)

Transaction script is the simplest enterprise architectural pattern. Using this pattern, we can do each
action/transaction in the application (write data about a product to the database, create a contract for a product, 
record revenue when a product is purchased, calculate revenue up to a given date, and so on) by set of procedures.
These procedures are called Transaction scripts.

* In this application all the database transactions are handled by Gateway classes (in src/sad/hw1/gateway).
* Transaction scripts to record revenue of a given contract, and calculate revenue of a given contract up to a 
given date are contained in RevenueRecognitionService class (src/sad/hw1/service/RevenueRecognitionService.java)
* JUnit Tester classes for Transaction scripts are in src/sad/hw1/ts/test/ directory.

## 2. Implementing using Domain model
> "A Domain Model creates a web of interconnected objects, where each object represents some meaningful individual.
(Martin Fowler - http://martinfowler.com/eaaCatalog/domainModel.html)"

Transaction script pattern is appropriate for application with simple business logic and simple database design.
Transaction scripts are not necessarily be organized as classes. We can write 1 class and implement all procudures 
as methods of that class (more like procedural programming). But to build more complex applications we need to go for a 
object model. That is where we need Domain model.

In Domain model, entire domain logic layer has organized in to a network of objects, invoke methods on each other 
to execute the business logic. Some objects encapsulate data in the application and some objects encapsulate actions.

* In this application all classes of objects encapsulate data are in src/sad/hw1/domain directory.
* Classes of objects encapsulate actions in the application are in src/sad/hw1/strategy.
