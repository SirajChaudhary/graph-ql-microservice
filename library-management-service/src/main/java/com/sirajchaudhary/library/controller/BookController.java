package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.request.BookInput;
import com.sirajchaudhary.library.response.BookPage;
import com.sirajchaudhary.library.entity.Book;
import com.sirajchaudhary.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

/*
==============================================================================
Book GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for Book.
• Uses @QueryMapping for fetching books.
• Uses @MutationMapping for creating, updating and deleting books.
• Uses @Argument to receive GraphQL request parameters.
• Uses @Valid and @Validated for request validation.
• Supports filtering, pagination and sorting.
• Returns only the fields requested by the GraphQL client.
• The Book entity maintains JPA relationships with Author, Category and
  Publisher. When these nested fields are requested, GraphQL resolves
  them through the configured @SchemaMapping field resolvers, enabling
  the client to retrieve the complete object graph in a single request.
• Delegates all business logic to the service layer.
*/
@Controller
@RequiredArgsConstructor
@Validated
public class BookController {

    private final BookService bookService;

    /*
    Fetch a single book by its ID.

    GraphQL Query

    • Retrieves one book.
    • Returns only the requested fields.
    • Related Author, Category and Publisher can also be fetched in the
      same query through the Book entity relationships.

    Example 1 - Return only the title.

    query {
      book(id: 1) {
        title
      }
    }

    Example 2 - Return title and ISBN.

    query {
      book(id: 1) {
        title
        isbn
      }
    }

    Example 3 - Return complete book details.

    query {
      book(id: 1) {
        id
        title
        isbn
        publicationYear
        price
        available
        author {
          firstName
          lastName
        }
        category {
          name
        }
        publisher {
          name
        }
      }
    }
    */
    @QueryMapping
    public Book book(@Argument Long id) {
        return bookService.getBook(id);
    }

    /*
    Fetch books using filtering, pagination and sorting.

    GraphQL Query

    Supports

    • Filter by title.
    • Filter by author.
    • Filter by category.
    • Filter by publisher.
    • Pagination.
    • Sorting.
    • Returns BookPage containing books and pagination information.
    • Related Author, Category and Publisher can also be requested.

    Example 1 - Fetch first page using default sorting.

    query {
      books {
        content {
          title
          author {
            firstName
            lastName
          }
        }
        totalElements
        totalPages
      }
    }

    Example 2 - Filter by title.

    query {
      books(title: "Java") {
        content {
          title
          isbn
        }
      }
    }

    Example 3 - Filter by author and category.

    query {
      books(
        authorId: 2
        categoryId: 1
      ) {
        content {
          title
          author {
            firstName
            lastName
          }
          category {
            name
          }
        }
      }
    }

    Example 4 - Pagination and sorting.

    query {
      books(
        page: 0
        size: 5
        sortBy: "publicationYear"
        direction: DESC
      ) {
        content {
          title
          publicationYear
          price
        }
        pageNumber
        pageSize
        totalPages
        totalElements
        first
        last
      }
    }
    */
    @QueryMapping
    public BookPage books(
            @Argument Long authorId,
            @Argument Long categoryId,
            @Argument Long publisherId,
            @Argument String title,
            @Argument Integer page,
            @Argument Integer size,
            @Argument String sortBy,
            @Argument String direction) {

        return bookService.getBooks(
                authorId,
                categoryId,
                publisherId,
                title,
                page,
                size,
                sortBy,
                direction);
    }

    /*
    Create a new book.

    GraphQL Mutation

    • Creates a new book.
    • Accepts BookInput as the mutation input.
    • Validates the request using @Valid.
    • Verifies that the specified Author, Category and Publisher exist.
    • Establishes relationships between Book and the associated entities.
    • Returns only the fields requested by the client.

    Example 1 - Return only generated ID.

    mutation {
      createBook(
        input: {
          title: "Java Performance"
          isbn: "9780137142521"
          publicationYear: 2024
          price: 59.99
          available: true
          authorId: 2
          categoryId: 1
          publisherId: 1
        }
      ) {
        id
      }
    }

    Example 2 - Return complete book details.

    mutation {
      createBook(
        input: {
          title: "Java Performance"
          isbn: "9780137142521"
          publicationYear: 2024
          price: 59.99
          available: true
          authorId: 2
          categoryId: 1
          publisherId: 1
        }
      ) {
        id
        title
        isbn
        publicationYear
        price
        available
        author {
          firstName
          lastName
        }
        category {
          name
        }
        publisher {
          name
        }
      }
    }
    */
    @MutationMapping
    public Book createBook(@Argument @Valid BookInput input) {
        return bookService.createBook(input);
    }

    /*
    Update an existing book.

    GraphQL Mutation

    • Updates an existing book.
    • Accepts the book ID and BookInput.
    • Validates the request using @Valid.
    • Verifies that the specified Author, Category and Publisher exist.
    • Updates the relationships if they are changed.
    • Returns only the fields requested by the client.

    Example 1 - Return only updated title.

    mutation {
      updateBook(
        id: 1
        input: {
          title: "Effective Java - Third Edition"
          isbn: "9780134685991"
          publicationYear: 2018
          price: 54.99
          available: true
          authorId: 2
          categoryId: 1
          publisherId: 1
        }
      ) {
        title
      }
    }

    Example 2 - Return complete updated book details.

    mutation {
      updateBook(
        id: 1
        input: {
          title: "Effective Java - Third Edition"
          isbn: "9780134685991"
          publicationYear: 2018
          price: 54.99
          available: true
          authorId: 2
          categoryId: 1
          publisherId: 1
        }
      ) {
        id
        title
        publicationYear
        price
        available
        author {
          firstName
          lastName
        }
        category {
          name
        }
        publisher {
          name
        }
      }
    }
    */
    @MutationMapping
    public Book updateBook(@Argument Long id,
                           @Argument @Valid BookInput input) {
        return bookService.updateBook(id, input);
    }

    /*
    Delete a book by its ID.

    GraphQL Mutation

    • Deletes an existing book.
    • Uses the book ID as input.
    • Returns true when the book is deleted successfully.

    Example

    mutation {
      deleteBook(id: 10)
    }
    */
    @MutationMapping
    public Boolean deleteBook(@Argument Long id) {
        return bookService.deleteBook(id);
    }
}