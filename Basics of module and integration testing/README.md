# Day 06 – Java bootcamp
### JUnit/Mockito

*Takeaways: Today you will learn the basics of module and integration testing*

### Exercise 00 – First Tests

Exercise 00: First Tests ||
---|---
Turn-in directory |	ex00
Files to turn-in |	Tests-folder

Now you need to implement NumberWorker class that contains the following functionality:

```java
public boolean isPrime(int number) {
  ...
}

```
This method determines if a number is prime and returns true/false for all natural (positive integer) numbers. For negative numbers, as well as 0 and 1, the program shall throw an unchecked exception. IllegalNumberException.
```java
public int digitsSum(int number) {
  ...
}
```

This method returns the sum of digits of a source number.

We also need to create edu.school21.numbers.edu.school21.numbers.NumberWorkerTest class that implements the module testing logic. Methods of edu.school21.numbers.edu.school21.numbers.NumberWorkerTest class shall check the correct operation of NumberWorker methods for various input data:
1. isPrimeForPrimes method to check isPrime using prime numbers (at least three)
2. isPrimeForNotPrimes method to check isPrime using composite numbers (at least three)
3. isPrimeForIncorrectNumbers method to check isPrime using incorrect numbers (at least three)
4. a method to check digitsSum using a set of at least 10 numbers

**Requirements**:
- edu.school21.numbers.edu.school21.numbers.NumberWorkerTest class must contain at least 4 methods to test NumberWorker functionality
- Use of @ParameterizedTest and @ValueSource is mandatory for methods 1–3.
- Use of @ParameterizedTest and @CsvFileSource is mandatory for method 4.
- You need to prepare data.csv file for method 4 where you shall specify at least 10 numbers and their correct sum of digits. A file content example:
1234, 10

**Project structure**:

- Tests
    - src
        - main
            - java
                 - edu.school21.numbers
                    - NumberWorker
            - resources
        - test
            - java
                - edu.school21.numbers
                    - edu.school21.numbers.edu.school21.numbers.NumberWorkerTest
            - resources
                -	data.csv
    - pom.xml

### Exercise 01 – Embedded DataBase

Exercise 01: Embedded DataBase ||
---|---
Turn-in directory |	ex01
Files to turn-in |	Tests

Do not use a heavy DBMS (like PostgreSQL) to implement integration testing of components that interact with the database. It is best to create a lightweight in-memory database with prearranged data.  

Implement DataSource creation mechanism for HSQL DBMS. To do so, connect spring-jdbc and hsqldb dependencies to the project. Prepare schema.sql and data.sql files where you will describe product table structure and test data (at least five).

Product table structure:
- identifier
- name
- price

Also create EmbeddedDataSourceTest class. In this class, implement init() method marked with @BeforeEach annotation. In this class, implement functionality to create DataSource using EmbeddedDataBaseBuilder (a class in spring-jdbc library). Implement a simple test method to check the return value of getConnection() method created by DataSource (this value must not be null).

**Project structure**:

- Tests
    - src
        - main
            - java
                - edu.school21.numbers
                    - NumberWorker
            - resources
        - test
            - java
                - edu.school21
                    - numbers
                        - edu.school21.numbers.edu.school21.numbers.NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
            - resources
                -	data.csv
                -	schema.sql
                -	data.sql
    - pom.xml

### Exercise 02 – Test for JDBC Repository

Exercise 02: Test for JDBC Repository ||
---|---
Turn-in directory |	ex02
Files to turn-in |	Tests

Implement ProductsRepository/ProductsRepositoryJdbcImpl interface/class pair with the following methods:

```java
List<Product> findAll()

Optional<Product> findById(Long id)

void update(Product product)

void save(Product product)

void delete(Long id)
```
You shall implement ProductsRepositoryJdbcImplTest class containing methods checking repository functionality using the in-memory database mentioned in the previous exercise. In this class, you should prepare in advance model objects that will be used for comparison in all tests.

Example of declaring test data is given below:
```java
class ProductsRepositoryJdbcImplTest {
  final List<Product> EXPECTED_FIND_ALL_PRODUCTS = ...;
  final Product EXPECTED_FIND_BY_ID_PRODUCT = ...;
  final Product EXPECTED_UPDATED_PRODUCT = ...;
}
```
**Notes**:
1.	Each test shall be isolated from behavior of other tests. Thus, the database must be in its initial state before each test is run.
2.	Test methods may call other methods that are not under the current test. For instance, when testing update() method, findById() method may be called to check the entity update validity in the database.

**Project structure**:

- Tests
    - src
        - main
            - java
                - edu.school21
                    - numbers
                        - NumberWorker
                    - models
                        - Product
                    - repositories
                        - ProductsRepository
                        - ProductsRepositoryJdbcImpl
            - resources
        - test
            - java
                - edu.school21
                    - numbers
                        - edu.school21.numbers.edu.school21.numbers.NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
                        - ProductsRepositoryJdbcImplTest
            - resources
                -	data.csv
                -	schema.sql
                -	data.sql
    - pom.xml

### Exercise 03 – Test for Service

Exercise 03: Test for Service ||
---|---
Turn-in directory |	ex03
Files to turn-in |	Tests

An important rule for module tests:  an individual system component shall be tested without calling its dependencies' functionality. This approach allows developers to create and test components independently, as well as postpone the implementation of specific application parts.

Now you need to implement the business logic layer represented by UsersServiceImpl class. This class contains a user authentication logic. It also has a dependency on UsersRepository interface (in this task, you do not need to implement this interface).

UsersRepository interface (that you have described) shall contain the following methods:
```java
User findByLogin(String login);
void update(User user);
```
It is assumed that findByLogin method returns a Userobject found via login, or throws EntityNotFoundException if no user is found with the login specified. Update method throws a similar exception when updating a user that does not exist in the database.

User entity shall contain the following fields:
- Identifier
- Login
- Password
- Authentication success status (true - authenticated, false - not authenticated)

In turn, UsersServiceImpl class calls these methods inside the authentication function:
```java
boolean authenticate(String login, String password)
```

This method:
1.	Checks if a user has been authenticated in the system using this login. If authentication was performed, AlreadyAuthenticatedException must be thrown.
2.	The user with this login is retrieved from UsersRepository.
3.	If the retrieved user password matches the specified password, the method sets the status of the authentication success for the user, updates its information in the database and returns true. If passwords mismatch, the method returns false.

Your goal is to:
1.	Create UsersRepository interface
2.	Create UsersServiceImpl class and authenticate method
3.	Create a module test for UsersServiceImpl class

Since your objective is to check correct operation of authenticate method independently of UsersRepository component, you should use mock object and stubs of findByLogin and update methods (see Mockito library).

Authenticate method shall be checked for three cases:
1.	Correct login/password (check calling update method using verify instruction of Mockito library)
2.	Incorrect login
3.	Incorrect password

**Project structure**:

- Tests
    - src
        - main
            - java
                - edu.school21
                    - exceptions
                        - AlreadyAuthenticatedException
                    - numbers
                        - NumberWorker
                    - models
                        - Product
                        - User
                    - services
                        - UsersServiceImpl
                    - repositories
                        - ProductsRepository
                        - ProductsRepositoryJdbcImpl
                        - UsersRepository
            - resources
        - test
            - java
                - edu.school21
                    - services
                        - UsersServiceImplTest
                    - numbers
                        - edu.school21.numbers.edu.school21.numbers.NumberWorkerTest
                    - repositories
                        - EmbeddedDataSourceTest
                        - ProductsRepositoryJdbcImplTest
            - resources
                -	data.csv
                -	schema.sql
                -	data.sql
    - pom.xml
