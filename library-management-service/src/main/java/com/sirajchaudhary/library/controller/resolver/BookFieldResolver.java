package com.sirajchaudhary.library.controller.resolver;

import com.sirajchaudhary.library.entity.Author;
import com.sirajchaudhary.library.entity.Book;
import com.sirajchaudhary.library.entity.Category;
import com.sirajchaudhary.library.entity.Publisher;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.AuthorRepository;
import com.sirajchaudhary.library.repository.CategoryRepository;
import com.sirajchaudhary.library.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/*
==============================================================================
Book GraphQL Field Resolver
==============================================================================

Similar to a GraphQL Controller, but resolves nested fields of the Book type
instead of handling Query or Mutation operations.

This is also a Spring GraphQL Controller because it is annotated with
@Controller. It resolves GraphQL fields using @SchemaMapping, so it is kept
under the controller package.

Responsibilities

• Uses @SchemaMapping to resolve GraphQL nested fields.
• Resolves Author, Category and Publisher for a Book.
• Invoked only when the client requests these nested fields.
• Loads related entities only when required.
• Delegates data retrieval to the repository layer.
• Throws ResourceNotFoundException if the associated entity does not exist.

GraphQL Field Resolver

• @QueryMapping handles GraphQL queries.
• @MutationMapping handles GraphQL mutations.
• @SchemaMapping resolves nested fields of a GraphQL type.

Example

query {
  book(id: 1) {
    title
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

GraphQL automatically invokes the corresponding @SchemaMapping methods to
resolve the requested nested fields.
*/
@Controller
@RequiredArgsConstructor
public class BookFieldResolver {

    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    /*
    Resolve the author associated with a book.

    GraphQL Field Resolver

    • Resolves the author field of the Book type.
    • Invoked only when the author field is requested.
    • Returns the associated Author.

    Example

    query {
      book(id: 1) {
        title
        author {
          firstName
          lastName
          email
        }
      }
    }
    */
    @SchemaMapping(typeName = "Book", field = "author")
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author not found with id: " + book.getAuthor().getId()));
    }

    /*
    Resolve the category associated with a book.

    GraphQL Field Resolver

    • Resolves the category field of the Book type.
    • Invoked only when the category field is requested.
    • Returns the associated Category.

    Example

    query {
      book(id: 1) {
        title
        category {
          name
          description
        }
      }
    }
    */
    @SchemaMapping(typeName = "Book", field = "category")
    public Category category(Book book) {
        return categoryRepository.findById(book.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + book.getCategory().getId()));
    }

    /*
    Resolve the publisher associated with a book.

    GraphQL Field Resolver

    • Resolves the publisher field of the Book type.
    • Invoked only when the publisher field is requested.
    • Returns the associated Publisher.

    Example

    query {
      book(id: 1) {
        title
        publisher {
          name
          website
        }
      }
    }
    */
    @SchemaMapping(typeName = "Book", field = "publisher")
    public Publisher publisher(Book book) {
        return publisherRepository.findById(book.getPublisher().getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Publisher not found with id: " + book.getPublisher().getId()));
    }
}