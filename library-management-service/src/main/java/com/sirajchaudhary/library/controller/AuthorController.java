package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.request.AuthorInput;
import com.sirajchaudhary.library.entity.Author;
import com.sirajchaudhary.library.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/*
==============================================================================
Author GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for Author.
• Uses @QueryMapping for fetching authors.
• Uses @MutationMapping for creating, updating and deleting authors.
• Uses @Argument to receive GraphQL request parameters.
• Returns only the fields requested by the GraphQL client.
• Since the Author type does not expose related entities in the GraphQL schema,
  clients retrieve only the Author fields they request.
• Delegates all business logic to the service layer.

GraphQL vs REST

REST

• Multiple endpoints may be required to retrieve different resources.
• Response structure is fixed by the server.

GraphQL

• Single endpoint serves all operations.
• Client decides which fields to retrieve.
• Eliminates over-fetching and under-fetching of data.
*/
@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    /*
    Fetch a single author by its ID.

    GraphQL Query

    • Retrieves one author.
    • Returns only the fields requested by the client.

    Example 1 - Return only first name.

    query {
      author(id: 1) {
        firstName
      }
    }

    Example 2 - Return full name.

    query {
      author(id: 2) {
        firstName
        lastName
      }
    }

    Example 3 - Return complete author details.

    query {
      author(id: 1) {
        id
        firstName
        lastName
        email
      }
    }
    */
    @QueryMapping
    public Author author(@Argument Long id) {
        return authorService.getAuthor(id);
    }

    /*
    Fetch all authors.

    GraphQL Query

    • Retrieves all authors.
    • Returns a list of authors.
    • Each author contains only the fields requested by the client.

    Example 1 - Return author names.

    query {
      authors {
        firstName
        lastName
      }
    }

    Example 2 - Return complete author details.

    query {
      authors {
        id
        firstName
        lastName
        email
      }
    }
    */
    @QueryMapping
    public List<Author> authors() {
        return authorService.getAuthors();
    }

    /*
    Create a new author.

    GraphQL Mutation

    • Creates a new author.
    • Accepts AuthorInput as the mutation input.
    • Returns only the fields requested by the client.

    Example 1 - Return only generated ID.

    mutation {
      createAuthor(
        input: {
          firstName: "John"
          lastName: "Doe"
          email: "john.doe@example.com"
        }
      ) {
        id
      }
    }

    Example 2 - Return complete author details.

    mutation {
      createAuthor(
        input: {
          firstName: "John"
          lastName: "Doe"
          email: "john.doe@example.com"
        }
      ) {
        id
        firstName
        lastName
        email
      }
    }
    */
    @MutationMapping
    public Author createAuthor(@Argument @Valid AuthorInput input) {
        return authorService.createAuthor(input);
    }

    /*
    Update an existing author.

    GraphQL Mutation

    • Updates an existing author.
    • Accepts the author ID and AuthorInput.
    • Returns only the fields requested by the client.

    Example 1 - Return updated author's name.

    mutation {
      updateAuthor(
        id: 1
        input: {
          firstName: "Siraj S"
          lastName: "Chaudhary"
          email: "siraj.chaudhary@example.com"
        }
      ) {
        firstName
        lastName
      }
    }

    Example 2 - Return complete updated author details.

    mutation {
      updateAuthor(
        id: 3
        input: {
          firstName: "Martin"
          lastName: "Fowler"
          email: "martin.fowler@example.com"
        }
      ) {
        id
        firstName
        lastName
        email
      }
    }
    */
    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument @Valid AuthorInput input) {
        return authorService.updateAuthor(id, input);
    }

    /*
    Delete an author by its ID.

    GraphQL Mutation

    • Deletes an existing author.
    • Uses the author ID as input.
    • Returns true when the author is deleted successfully.

    Note

    • An author cannot be deleted if one or more books are associated with
      that author.

    Example

    mutation {
      deleteAuthor(id: 10)
    }
    */
    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id) {
        return authorService.deleteAuthor(id);
    }
}