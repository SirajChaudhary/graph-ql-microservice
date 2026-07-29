# Library Management Service

A **Library Management Service** built using **Java 21**, **Spring GraphQL**, **Spring Boot**, **Spring Data JPA**, and **MySQL**.

This project demonstrates how to build GraphQL APIs using Spring Boot while implementing a layered architecture, GraphQL queries, mutations, field resolvers, dynamic filtering, pagination, sorting, caching, validation, transaction management, JPA relationships, and other software development best practices.

---
# Project Overview

This project implements the following modules:

- Author Management
- Book Management
- Category Management
- Publisher Management
- Member Management
- Borrow Record Management

### Implemented Features

- GraphQL Query APIs
- GraphQL Mutation APIs
- GraphQL Field Resolvers
- Spring Data JPA
- Dynamic Filtering
- Pagination
- Sorting
- Caching using Caffeine
- Transaction Management
- Validation
- Logging
- Specifications
- MySQL Integration
- Lombok
- Layered Architecture

### Technology Stack

| Category | Technology |
|----------|------------|
| Programming Language | Java 21 |
| Framework | Spring Boot 4 |
| API Framework | Spring GraphQL |
| ORM | Spring Data JPA |
| Database | MySQL |
| Build Tool | Maven |
| Validation | Jakarta Bean Validation |
| Caching | Spring Cache + Caffeine |
| Logging | SLF4J + Logback |
| Boilerplate Reduction | Lombok |
| API Testing | GraphiQL |

### Project Structure

```text
library-management-service
│
├── src
│   ├── main
│   │
│   ├── java
│   │   └── com.sirajchaudhary.library
│   │
│   │       ├── config
│   │       ├── controller
│   │       │   └── resolver
│   │       ├── entity
│   │       ├── exception
│   │       ├── repository
│   │       ├── request
│   │       ├── response
│   │       ├── service
│   │       │   └── impl
│   │       └── specification
│   │
│   ├── resources
│   │
│   │   ├── graphql
│   │   │   ├── author.graphqls
│   │   │   ├── book.graphqls
│   │   │   ├── borrow-record.graphqls
│   │   │   ├── category.graphqls
│   │   │   ├── member.graphqls
│   │   │   ├── publisher.graphqls
│   │   │   └── schema.graphqls
│   │   │
│   │   ├── application.yml
│   │   └── library-management-sample-db.sql
│   │
│   └── test
│
└── pom.xml
```

The initial project skeleton was generated using **Spring Initializr** and then extended by adding Spring GraphQL and other required dependencies.

### Entity Relationship Diagram

```
        +----------------+              +----------------+              +----------------+
        |     Author     |              |    Category    |              |   Publisher    |
        +----------------+              +----------------+              +----------------+
              \                               |                            /
               \                              |                           /
                \                             |                          /
                 \                            |                         /
                  \                           |                        /
                   \                          |                       /
                    \                         |                      /
          One Author \                        | One Category        / One Publisher
          Many Books  \                       | Many Books         / Many Books
                       \                      |                   /
                        \                     |                  /
                         +--------------------------------------+
                         |                Book                  |
                         +--------------------------------------+
                                              |
                                              |
                                           One Book
                                      Many Borrow Records
                                              |
                                              v
                                    +----------------------+
                                    |    BorrowRecord      |
                                    +----------------------+
                                              ^
                                              |
                                       Many Borrow Records
                                            One Member
                                              |
                                    +----------------------+
                                    |       Member         |
                                    +----------------------+
```

### Relationship Mapping

The relationships shown above are mapped in the JPA entity classes using relationship annotations.

- One **Author** can write many **Books**.
- One **Category** can contain many **Books**.
- One **Publisher** can publish many **Books**.
- One **Book** can have many **Borrow Records**.
- One **Member** can have many **Borrow Records**.
- Each **Borrow Record** belongs to exactly one **Book** and one **Member**.

The entity relationships are implemented using JPA annotations such as:

- `@OneToMany`
- `@ManyToOne`
- `@JoinColumn`

### Application Architecture

```text
                    +----------------------+
                    |      GraphiQL        |
                    |      Postman         |
                    |  GraphQL Clients     |
                    +----------+-----------+
                               |
                               |
                               | HTTP POST /graphql
                               |
                               v
                 +-------------------------------+
                 | Spring GraphQL Controllers    |
                 |-------------------------------|
                 | @QueryMapping                 |
                 | @MutationMapping              |
                 | @SchemaMapping                |
                 +---------------+---------------+
                                 |
                                 |
                                 v
                    +-------------------------+
                    |      Service Layer      |
                    +-------------------------+
                                 |
                                 |
                                 v
                  +----------------------------+
                  |     Repository Layer       |
                  |     Spring Data JPA        |
                  +----------------------------+
                                 |
                                 |
                                 v
                       +------------------+
                       |      MySQL       |
                       +------------------+
```

### GraphQL Request Flow

```text
GraphQL Client
        │
        ▼
POST /graphql
        │
        ▼
GraphQL Controller
(@QueryMapping / @MutationMapping / @SchemaMapping)
        │
        ▼
Service Layer
        │
        ▼
Repository Layer
        │
        ▼
MySQL Database
        │
        ▼
GraphQL Response
```

---
# What is GraphQL?

GraphQL is an API query language and runtime developed by Facebook (now Meta) that allows clients to request exactly the data they need from a server.

Unlike REST APIs, where each endpoint returns a predefined response, GraphQL allows clients to specify the fields they want in a single request.

### Example

```graphql
query {
    book(id: 1) {
        title
        price
        author {
            firstName
        }
    }
}
```

The server returns only the requested fields.

```json
{
  "data": {
    "book": {
      "title": "Effective Java",
      "price": 45.50,
      "author": {
        "firstName": "Joshua"
      }
    }
  }
}
```

### History

- Developed by Facebook (now Meta).
- Initially created in 2012.
- Open sourced in 2015.
- Designed to solve over-fetching and under-fetching problems in REST APIs.
- Now maintained by the GraphQL Foundation.
- Supported by almost every major programming language.

### Key Features

- Request exactly the required data.
- Single endpoint (`/graphql`).
- Strongly typed schema.
- Self-documenting APIs.
- Nested object fetching.
- Multiple resources in a single request.
- Supports Queries and Mutations.
- Supports real-time Subscriptions.
- Language independent.
- Client-driven API design.

### Supported Programming Languages

GraphQL is not limited to Java.

It can be implemented using many programming languages.

| Language | Popular Frameworks |
|----------|--------------------|
| Java | Spring GraphQL, Netflix DGS, Quarkus GraphQL |
| Kotlin | Spring GraphQL |
| Python | Graphene, Ariadne |
| JavaScript | Apollo Server, Express GraphQL |
| TypeScript | Apollo Server, NestJS GraphQL |
| Node.js | Apollo Server |
| C# | Hot Chocolate |
| Go | gqlgen |
| PHP | Lighthouse |
| Ruby | graphql-ruby |

Similarly, GraphQL clients can also be written using:

- Java
- JavaScript
- React
- Angular
- Vue
- Flutter
- Swift
- Kotlin
- Python
- C#
- Go

### Why was GraphQL introduced?

REST APIs work well for many applications.

However, as applications became larger, clients often needed data from multiple REST endpoints.

For example, to display a book with its author, category, and publisher, a client may need to call:

```text
GET /books/1

GET /authors/10

GET /categories/5

GET /publishers/3
```

Multiple network requests are required.

With GraphQL, the client can fetch everything in a single request.

```graphql
query {
    book(id: 1) {
        title
        author {
            firstName
        }
        category {
            name
        }
        publisher {
            name
        }
    }
}
```

### Advantages

- Fetch only the required fields.
- Avoid over-fetching.
- Avoid under-fetching.
- Single endpoint.
- Strong type system.
- Better for mobile applications.
- Easier frontend development.
- Self-documenting schema.
- Reduces network requests.
- Excellent support for nested objects.

### Limitations

- More complex than REST.
- Caching is more challenging.
- Higher learning curve.
- Queries can become deeply nested.
- Authorization can be more complex.
- File uploads require additional configuration.

### When should we use GraphQL?

GraphQL is a good choice when:

- Building mobile applications.
- Developing React, Angular, or Vue frontends.
- Multiple related entities need to be fetched together.
- Different clients require different fields.
- Building dashboards.
- Creating reporting applications.
- Working with complex business domains.
- Building microservices with a GraphQL Gateway.

For example:

```text
Book

Author

Publisher

Category

Borrow Records
```

All can be retrieved in a single request.

### When should we use REST APIs?

REST APIs are usually better for:

- Simple CRUD operations.
- Public APIs.
- Third-party integrations.
- Payment gateways.
- Webhooks.
- File uploads.
- Simple microservices.
- Systems with heavy HTTP caching.

For example:

```text
GET /books

GET /books/1

POST /books

PUT /books/1

DELETE /books/1
```

REST is simpler and easier to understand for straightforward resource-based APIs.

---
# REST API vs GraphQL

| Feature | REST API | GraphQL |
|----------|----------|----------|
| Endpoint | Multiple endpoints | Single endpoint |
| Data Returned | Fixed response | Client decides the fields |
| Over-fetching | Possible | No |
| Under-fetching | Possible | No |
| Multiple Resources | Multiple requests | Single request |
| Strong Schema | No | Yes |
| Nested Objects | Multiple calls | Single query |
| Versioning | Common | Usually not required |
| Learning Curve | Easy | Moderate |
| Mobile Friendly | Good | Excellent |
| Frontend Flexibility | Limited | High |
| Self Documentation | Swagger/OpenAPI | GraphQL Schema |
| Best For | CRUD APIs | Flexible data retrieval |

### GraphQL vs REST Example

**REST**

```text
GET /books/1
```

Response

```json
{
  "id": 1,
  "title": "Effective Java",
  "isbn": "...",
  "price": 45.50,
  "publicationYear": 2018,
  "available": true,
  "author": {},
  "category": {},
  "publisher": {}
}
```

Even if the client only needs the title, the server returns all fields.

**GraphQL**

```graphql
query {
    book(id: 1) {
        title
    }
}
```

Response

```json
{
  "data": {
    "book": {
      "title": "Effective Java"
    }
  }
}
```

Only the requested field is returned.

### GraphQL Endpoint

Unlike REST APIs, GraphQL uses a single endpoint.

```text
POST /graphql
```

GraphiQL UI:

```text
http://localhost:8080/graphiql
```

All Queries and Mutations are executed through this endpoint.

GraphQL determines which operation to execute based on the request body rather than the URL.

### Can We Add GraphQL to an Existing REST Microservice?

Yes. It is perfectly normal to use both REST APIs and GraphQL APIs in the same Spring Boot microservice.

Both API styles can share the same business logic, service layer, repository layer, database, validation, transactions, and security. Only the API layer is different.

Typical approach:

- Use REST APIs for CRUD operations, public APIs, third-party integrations, and webhooks.
- Use GraphQL APIs for flexible data fetching, dashboards, mobile applications, and complex nested data.

### Steps to add GraphQL to an existing Spring Boot application

**Step 1:** Add the Spring GraphQL dependency.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-graphql</artifactId>
</dependency>
```

**Step 2:** Create GraphQL schema files under:

```text
src/main/resources/graphql
```

Example:

```graphql
type Query {
    book(id: ID!): Book
}
```

**Step 3:** Create a GraphQL Controller using `@QueryMapping` and `@MutationMapping`.

```java
@Controller
public class BookController {

    @QueryMapping
    public Book book(@Argument Long id) {
        return bookService.getBook(id);
    }
}
```

**Step 4:** Create a Field Resolver using `@SchemaMapping` (only if nested fields need to be resolved).

```java
@Controller
public class BookFieldResolver {

    @SchemaMapping
    public Author author(Book book) {
        return book.getAuthor();
    }
}
```

**Step 5:** Run the application and execute GraphQL requests using GraphiQL, Postman, or any GraphQL client.

Example:

```graphql
query {
    book(id: 1) {
        title
        author {
            firstName
        }
    }
}
```

Your existing REST APIs continue to work without any changes, allowing REST and GraphQL to coexist in the same Spring Boot application.

---
# GraphQL Core Concepts

This section explains the fundamental GraphQL concepts used throughout this project.

### GraphQL Schema

A GraphQL schema defines the structure of the API.

It specifies:

- Available Queries
- Available Mutations
- Object Types
- Input Types
- Relationships between objects

In Spring Boot, GraphQL schema files are stored under:

```text
src/main/resources/graphql
```

Example

```graphql
type Query {
    book(id: ID!): Book
    books: [Book]
}
```

### GraphQL Query

A GraphQL Query is used to retrieve data.

It is similar to an HTTP GET request in REST APIs.

Example

```graphql
query {
    book(id: 1) {
        title
        price
    }
}
```

Spring Boot

```java
@QueryMapping
public Book book(@Argument Long id) {

    return bookService.getBook(id);
}
```

Annotation

| Annotation | Description |
|------------|-------------|
| `@QueryMapping` | Maps a GraphQL Query to a Java method. |

### GraphQL Mutation

A GraphQL Mutation is used to create, update, or delete data.

It is similar to the following HTTP methods in REST APIs:

- POST
- PUT
- PATCH
- DELETE

Example

```graphql
mutation {
    createBook(
        input: {
            title: "Spring Boot"
            isbn: "12345"
            price: 50
            publicationYear: 2025
            available: true
            authorId: 1
            categoryId: 1
            publisherId: 1
        }
    ) {
        id
        title
    }
}
```

```java
@MutationMapping
public Book createBook(@Argument BookInput input) {

    return bookService.createBook(input);
}
```

Annotation

| Annotation | Description |
|------------|-------------|
| `@MutationMapping` | Maps a GraphQL Mutation to a Java method. |

### GraphQL Resolver

A Resolver is a Java method responsible for fetching data for a GraphQL operation.

Resolvers execute the business logic and return the requested data.

Spring GraphQL provides three types of resolvers.

- Query Resolver
- Mutation Resolver
- Field Resolver

### Query Resolver

A Query Resolver handles GraphQL Queries.

Example

```graphql
query {
    author(id: 1) {
        firstName
    }
}
```

Spring Boot

```java
@QueryMapping
public Author author(@Argument Long id) {

    return authorService.getAuthor(id);
}
```

### Mutation Resolver

A Mutation Resolver handles GraphQL Mutations.

Example

```graphql
mutation {
    deleteAuthor(id: 1)
}
```

Spring Boot

```java
@MutationMapping
public boolean deleteAuthor(@Argument Long id) {

    return authorService.deleteAuthor(id);
}
```

### Field Resolver

A Field Resolver resolves nested fields of a GraphQL object.

Suppose the client executes:

```graphql
query {
    book(id: 1) {
        title
        author {
            firstName
        }
    }
}
```

The **Book** is fetched first.

When GraphQL encounters the nested **author** field, it invokes **BookFieldResolver** to resolve that field.

Spring Boot

```java
@Controller
public class BookFieldResolver {

    @SchemaMapping
    public Author author(Book book) {
        return book.getAuthor();
    }
}
```

Similarly,

```graphql
query {
    borrowRecord(id: 1) {
        borrowDate
        member {
            firstName
        }
    }
}
```

is resolved using:

```text
BorrowRecordFieldResolver
```

Field Resolvers are invoked only when nested fields are requested by the client.

### GraphQL Variables

Variables allow values to be passed separately from the query.

Instead of hardcoding values:

```graphql
query {
    book(id: 1) {
        title
    }
}
```

Use variables.

Query

```graphql
query GetBook($id: ID!) {
    book(id: $id) {
        title
    }
}
```

Variables

```json
{
  "id": 1
}
```

Variables improve query reusability and make queries easier to maintain.

### GraphQL Arguments

Arguments pass values to GraphQL fields.

Example

```
book(id: 1)
```

Spring Boot

```
public Book book(@Argument Long id)
```

Annotation

| Annotation | Description |
|------------|-------------|
| `@Argument` | Maps GraphQL arguments to Java method parameters. |

### GraphQL Object Types

Object Types define the data returned by GraphQL.

Example

```graphql
type Book {
    id: ID!
    title: String
    price: Float
}
```

In Spring Boot applications, Object Types usually correspond to Java entities or DTOs.

### GraphQL Input Types

Input Types define the data accepted by GraphQL Mutations.

Example

```graphql
input BookInput {
    title: String!
    isbn: String!
    price: Float!
}
```

Java

```text
BookInput
```

Unlike Object Types, Input Types are only used for input data.

### GraphQL Scalar Types

GraphQL provides the following built-in scalar types.

| GraphQL Type | Java Type |
|--------------|-----------|
| ID | Long / String |
| String | String |
| Int | Integer |
| Float | Double / BigDecimal |
| Boolean | Boolean |

### GraphQL Enum Types

Enum Types restrict values to a predefined list.

Example

```graphql
enum BorrowStatus {
    BORROWED
    RETURNED
}
```

Java

```java
public enum BorrowStatus {

    BORROWED,
    RETURNED
}
```

---
# GraphQL Annotations

| Annotation | Description |
|------------|-------------|
| `@Controller` | Marks a GraphQL Controller or Field Resolver. |
| `@QueryMapping` | Handles GraphQL Query operations. |
| `@MutationMapping` | Handles GraphQL Mutation operations. |
| `@SchemaMapping` | Resolves nested fields of a GraphQL type. |
| `@Argument` | Maps GraphQL arguments to Java method parameters. |

---
# Query, Mutation and Field Resolver Flow

```text
     Client
        │
        ▼
  GraphQL Query
        │
        ▼
   @QueryMapping
 (BookController)
        │
        ▼
   BookService
        │
        ▼
 BookRepository
        │
        ▼
  Book Entity
        │
        ▼
Nested field requested?
        │
        ├──────── No ───────► Response
        │
        ▼
       Yes
        │
        ▼
BookFieldResolver
(@SchemaMapping)
        │
        ▼
      Author
      Category
      Publisher
        │
        ▼
 GraphQL Response
```

---
# Important GraphQL Files in this Project

| File | Purpose |
|------|---------|
| `schema.graphqls` | Root GraphQL schema. |
| `author.graphqls` | Author Queries, Mutations, and Types. |
| `book.graphqls` | Book Queries, Mutations, and Types. |
| `member.graphqls` | Member GraphQL schema. |
| `publisher.graphqls` | Publisher GraphQL schema. |
| `category.graphqls` | Category GraphQL schema. |
| `borrow-record.graphqls` | Borrow Record GraphQL schema. |
| `BookController` | Handles Book Queries and Mutations. |
| `BookFieldResolver` | Resolves nested Author, Category, and Publisher fields of Book. |
| `BorrowRecordFieldResolver` | Resolves nested Book and Member fields of BorrowRecord. |

---
# Running the Project

### Step 1: Clone the Repository

```bash
git clone https://github.com/SirajChaudhary/graph-ql-microservice.git
```

```bash
cd graph-ql-microservice
cd library-management-service
```

### Step 2: Configure the Database

Create a MySQL database.

```sql
CREATE DATABASE librarydb;
```

Update the database configuration in `application.yml`.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/librarydb
    username: root
    password: your-password
```

### Step 3: Create Tables and Load Sample Data

Run the following SQL script to create the database schema and load sample data.

```text
library-management-sample-db.sql
```

### Step 4: Build the Application

```bash
mvn clean install
```

### Step 5: Run the Application

```bash
mvn spring-boot:run
```

---
# GraphQL Endpoints

| Endpoint | Description |
|----------|-------------|
| `/graphql` | Executes all GraphQL Queries and Mutations |
| `/graphiql` | Interactive GraphQL UI |

### GraphiQL

GraphiQL is a browser-based IDE used to write, execute, and test GraphQL APIs.

Once the application is running, open:

```text
http://localhost:8080/graphiql
```

Features:

- Execute GraphQL Queries
- Execute GraphQL Mutations
- Auto-completion
- Syntax Highlighting
- Query History
- Documentation Explorer

---
# Running GraphQL APIs

This project exposes the following GraphQL APIs.

### Author APIs

**Queries**

- `author(id)` – Fetch a single Author by ID. Returns only the requested fields.
  <br /><br />
  Example 1 - Return only first name.
  <img width="3840" height="1162" alt="image" src="https://github.com/user-attachments/assets/c7015b0d-6e02-405b-9c90-0f878ba4c793" />
  <br />
  Example 2 - Return full name.
  <img width="3840" height="944" alt="image" src="https://github.com/user-attachments/assets/1c262409-0193-4a70-bc0c-6aec6fdeefd1" />
  <br />
  Example 3 - Return complete author details.
  <img width="3840" height="980" alt="image" src="https://github.com/user-attachments/assets/26f15101-89a9-4b67-92b4-eb0febca4eb5" />

- `authors()` – Fetch all Authors. Returns only the requested fields.
  <br /><br />
  Example 1 - Return author names.
  <img width="3840" height="2072" alt="image" src="https://github.com/user-attachments/assets/f070bf7a-f8c1-4238-b5d4-0f4b5aaf8bac" />
  <br />
  Example 2 - Return complete author details.
  <img width="3840" height="2068" alt="image" src="https://github.com/user-attachments/assets/836f4b9b-be9e-4ab4-86e4-4d2d9a9e9d40" />

**Mutations**

- `createAuthor` – Create a new Author.
  <br /><br />
  Example 1 - Create and return complete author details.
  <img width="3840" height="1078" alt="image" src="https://github.com/user-attachments/assets/d949806f-8e11-421f-8085-81c78d6dd1ee" />
  <br />
  Example 2 - Create and return only generated ID.
  <img width="3840" height="1034" alt="image" src="https://github.com/user-attachments/assets/716b792d-d760-4e18-ab27-fd837c6998d4" />

- `updateAuthor` – Update an existing Author.
  <br /><br />
  Example 1 - Update and return updated author's name.
  <img width="3840" height="1114" alt="image" src="https://github.com/user-attachments/assets/f0acc382-5cc7-4feb-93fc-09f0106d9ce0" />

- `deleteAuthor` – Delete an Author by ID.
  <br /><br />
  Example 1 - Delete an author by its ID (An author cannot be deleted if any book is associated with that author)
  <img width="3840" height="1394" alt="image" src="https://github.com/user-attachments/assets/096cef69-e580-433b-a660-174a37a5a47e" />
  <br />
  Example 2 - Delete an author by its ID 
  <img width="3840" height="1046" alt="image" src="https://github.com/user-attachments/assets/2e03b5d1-fac3-4d95-863f-d1a88617e1ed" />

### Book APIs

**Queries**

- `book(id)` – Fetch a single Book by ID. Returns only the requested fields.
  <br /><br />
  Example 1 - Return only the title.
  <img width="3840" height="1066" alt="image" src="https://github.com/user-attachments/assets/b863eafa-0489-4f88-ae7e-57aa76e96ff9" />
  <br />
  Example 2 - Return title and ISBN.
  <img width="3840" height="952" alt="image" src="https://github.com/user-attachments/assets/9b8bdf30-80cc-4fd9-804e-1452de718c97" />
  <br />
  Example 3 - Return complete book details.
  <img width="3840" height="1362" alt="image" src="https://github.com/user-attachments/assets/51bef604-16f1-4a0b-a65a-c8a1224f6db4" />

- `books(...)` – Fetch Books with filtering, pagination, and sorting.
  <br /><br />
  Example 1 - Fetch first page using default sorting.
  <img width="3840" height="2078" alt="image" src="https://github.com/user-attachments/assets/1a718c57-1575-4c85-9b9c-4378f9e8dbf0" />
  <br />
  Example 2 - Filter by title.
  <img width="3840" height="1240" alt="image" src="https://github.com/user-attachments/assets/c591cab4-07cf-4b6a-aafe-a253de7f8589" />
  <br />
  👉 Partial search also allowed.
  <img width="3840" height="1276" alt="image" src="https://github.com/user-attachments/assets/c5c51d10-04cc-47be-bdaf-9af99e6e3130" />
  <br />
  Example 3 - Filter by author and category.
  <img width="3840" height="1288" alt="image" src="https://github.com/user-attachments/assets/5f042103-97c2-4537-84ce-a187c2e3cc57" />
  <br />
  Example 4 - Pagination and sorting.
  <img width="3840" height="2060" alt="image" src="https://github.com/user-attachments/assets/be3ae67f-1b38-43fc-a787-cb83811f1015" />

**Mutations**

- `createBook` – Create a new Book.
  <br /><br />
  Example 1 - Create and return complete book details.
  <img width="3840" height="2076" alt="image" src="https://github.com/user-attachments/assets/d68638d7-4d8f-46e1-b3d1-aeb5c169b51f" />

- `updateBook` – Update an existing Book.
  <br /><br />
  Example 1 - Update book title and author and then return only updated title.
  <img width="3840" height="1230" alt="image" src="https://github.com/user-attachments/assets/7e14eee3-0034-4a6f-b63e-49f9da57033f" />

- `deleteBook` – Delete a Book by ID.
  <br /><br />
  Example 1 - Delete a book by its ID.
  <img width="3840" height="804" alt="image" src="https://github.com/user-attachments/assets/42d58abd-285b-4469-9cc7-a7ccd326ee8b" />

### Category APIs

**Queries**

- `category(id)` – Fetch a single Category by ID.
  <br /><br />
  <img width="3840" height="902" alt="image" src="https://github.com/user-attachments/assets/dea66670-41b6-4274-9b82-23c0b8ec5fac" />

- `categories()` – Fetch all Categories.
  <br /><br />
  <img width="3840" height="1710" alt="image" src="https://github.com/user-attachments/assets/082df2ce-6a1f-4b4a-be06-addcec28d727" />

**Mutations**

- `createCategory` – Create a new Category.
  <br /><br />
  <img width="3840" height="1012" alt="image" src="https://github.com/user-attachments/assets/38fa6a49-aa88-4649-9a9f-5401da818053" />

- `updateCategory` – Update an existing Category.
  <br /><br />
  <img width="3840" height="1070" alt="image" src="https://github.com/user-attachments/assets/1f367c91-bebe-4e0d-8465-5f8d8934db61" />

- `deleteCategory` – Delete a Category by ID.
  <br /><br />
  <img width="3840" height="942" alt="image" src="https://github.com/user-attachments/assets/a4a449b0-96c4-421d-b9b1-fcaa8f86847b" />

### Publisher APIs

**Queries**

- `publisher(id)` – Fetch a single Publisher by ID.
  <br /><br />
  <img width="3840" height="852" alt="image" src="https://github.com/user-attachments/assets/2eb5026b-4062-4370-813e-bd35e8a6b6fb" />

- `publishers()` – Fetch all Publishers.
  <img width="3840" height="2076" alt="image" src="https://github.com/user-attachments/assets/a4d0252b-98c9-4f51-bd98-c4ff36b24e0a" />

**Mutations**

- `createPublisher` – Create a new Publisher.
  <br /><br />
  <img width="3840" height="886" alt="image" src="https://github.com/user-attachments/assets/bea0e932-e51d-4d2a-9565-77f85a4ae05e" />

- `updatePublisher` – Update an existing Publisher.
  <br /><br />
  <img width="3840" height="1008" alt="image" src="https://github.com/user-attachments/assets/2b63bfdf-7c0a-4523-894c-badc86d43586" />

- `deletePublisher` – Delete a Publisher by ID.
  <br /><br />
  <img width="3840" height="848" alt="image" src="https://github.com/user-attachments/assets/dcf7acbb-fc90-4c20-aa2e-6dfe9a65d472" />

### Member APIs

**Queries**

- `member(id)` – Fetch a single Member by ID.
  <br /><br />
  <img width="3840" height="858" alt="image" src="https://github.com/user-attachments/assets/38247822-582c-4712-b730-3cc9985498ee" />

- `members()` – Fetch all Members.
  <br /><br />
  <img width="3840" height="2074" alt="image" src="https://github.com/user-attachments/assets/7df9bdfa-1ecd-473f-9122-517236e3c43f" />

**Mutations**

- `createMember` – Create a new Member.
  <br /><br />
  <img width="3840" height="1088" alt="image" src="https://github.com/user-attachments/assets/013903c8-6421-4ae6-9d60-f193079b0b05" />

- `updateMember` – Update an existing Member.
  <br /><br />
  <img width="3840" height="1016" alt="image" src="https://github.com/user-attachments/assets/8cac3640-7f06-4ef5-aaac-0641ab67ed0f" />

- `deleteMember` – Delete a Member by ID.
  <br /><br />
  <img width="3840" height="730" alt="image" src="https://github.com/user-attachments/assets/52a34884-9a5e-41ee-9bb1-6e5e9720d8c7" />

### Borrow Record APIs

**Queries**

- `borrowRecord(id)` – Fetch a single Borrow Record by ID.
  <br /><br />
  <img width="3840" height="1370" alt="image" src="https://github.com/user-attachments/assets/a1c414e2-17a3-4c3c-bc84-9782390b4fae" />

- `borrowRecords()` – Fetch all Borrow Records.
  <br /><br />
  <img width="3840" height="2074" alt="image" src="https://github.com/user-attachments/assets/255fc066-45d2-4e47-b50d-a4600594359c" />

**Mutations**

- `borrowBook(bookId, memberId)` – Borrow a Book.
  <br /><br />
  <img width="3840" height="1346" alt="image" src="https://github.com/user-attachments/assets/f9b36f1b-a7b4-45f1-ad66-ebe8def82e88" />

- `returnBook(id)` – Return a borrowed Book.
  <br /><br />
  <img width="3840" height="1134" alt="image" src="https://github.com/user-attachments/assets/fb2c42fe-50fb-4962-ab7b-b826c8622274" />

### GraphQL Field Resolvers

Field Resolvers are automatically invoked by GraphQL only when nested fields are requested.

### BookFieldResolver

Resolves the following nested fields of the `Book` type.

- `author`
- `category`
- `publisher`

Example

```graphql
query {
  book(id: 1) {
    title
    author {
      firstName
    }
    category {
      name
    }
    publisher {
      name
    }
  }
}
```
<img width="3840" height="2076" alt="image" src="https://github.com/user-attachments/assets/05370cc5-c668-44fa-814c-f30a8538e11c" />

### BorrowRecordFieldResolver

Resolves the following nested fields of the `BorrowRecord` type.

- `book`
- `member`

Example

```graphql
query {
  borrowRecord(id: 1) {
    borrowDate
    status
    book {
      title
    }
    member {
      firstName
      lastName
    }
  }
}
```
<img width="3840" height="2076" alt="image" src="https://github.com/user-attachments/assets/0df8a060-fd71-4fac-8898-95c7ccbef184" />

---
# Project Highlights

This project demonstrates:

- Spring GraphQL
- Query Mapping
- Mutation Mapping
- Field Resolvers
- Schema Mapping
- Dynamic Filtering
- Pagination
- Sorting
- JPA Relationships
- Spring Data JPA
- Specifications
- Transaction Management
- Caffeine Cache
- Validation
- Layered Architecture
- Clean Package Structure

---
# License

Free software, [Siraj Chaudhary](https://www.linkedin.com/in/sirajchaudhary/)
