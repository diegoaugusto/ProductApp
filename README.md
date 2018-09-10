# ProductApp
This repository is the home of the development of a basic set of services to manage Products with the goal of practicing the use of different technologies like Spring Boot, Angular and others.

## Quick Start
- Make sure you have MySQL up and running on port `3306` and:
    - Database is created: `CREATE DATABASE \`productdb\` DEFAULT CHARACTER SET utf8 ;`
    - User `springuser` with password `springuser` exist.
    - You can following the steps below to create the database and configure the user.
    ```
        mysql> create database db_example; -- Create the new database
        mysql> create user 'springuser'@'localhost' identified by 'ThePassword'; -- Creates the user
        mysql> grant all on db_example.* to 'springuser'@'localhost'; -- Gives all the privileges to the new user on the newly created database
    ```
- `git clone https://github.com/diegoaugusto/ProductApp.git`
- `java -jar server/target/ProductApp-server-0.0.1-SNAPSHOT.jar`
- Access http://localhost:8080

## Longer Start
In order to run this project locally, it is necessary to download some tools (if you don't have them already) and proceed with the steps below.

### Tools
These set of tools were used during the development of this project and are the recommended tools. 
Other versions or different tools that do the same job were not tested and therefore may or may not not work properly. Feel free to try it and provide your feedback.

**Tool** | **Selected Option** | **Why it is used in this project?**
------------ | ------------- | -------------
OS | MacOs | Dev's machine.
Java :coffee: | Oracle 1.8.0_171 | Default version used in the system because of other softwares that depends of version 8.
MySQL | MySQL 8.0.12 | Latest version installed from HomeBrew.
Build Automation | Apache Maven 3.5.2 | Easy to use and widely used.  
IDE | Eclipse Oxygen.3a Release (4.7.3a) | Used to it.

### Steps
- 