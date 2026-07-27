package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.request.PublisherInput;
import com.sirajchaudhary.library.entity.Publisher;
import com.sirajchaudhary.library.service.PublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/*
==============================================================================
Publisher GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for Publisher.
• Uses @QueryMapping for fetching publishers.
• Uses @MutationMapping for creating, updating and deleting publishers.
• Uses @Argument to receive GraphQL request parameters.
• Returns only the fields requested by the GraphQL client.
• Since the Publisher type does not expose related entities in the GraphQL
  schema, clients retrieve only the Publisher fields they request.
• Delegates all business logic to the service layer.
*/
@Controller
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    /*
    Fetch a single publisher by its ID.

    GraphQL Query

    • Retrieves one publisher.
    • Returns only the fields requested by the client.

    Example 1 - Return only publisher name.

    query {
      publisher(id: 1) {
        name
      }
    }

    Example 2 - Return complete publisher details.

    query {
      publisher(id: 1) {
        id
        name
        website
      }
    }
    */
    @QueryMapping
    public Publisher publisher(@Argument Long id) {
        return publisherService.getPublisher(id);
    }

    /*
    Fetch all publishers.

    GraphQL Query

    • Retrieves all publishers.
    • Returns a list of publishers.
    • Each publisher contains only the fields requested by the client.

    Example 1 - Return publisher names.

    query {
      publishers {
        name
      }
    }

    Example 2 - Return complete publisher details.

    query {
      publishers {
        id
        name
        website
      }
    }
    */
    @QueryMapping
    public List<Publisher> publishers() {
        return publisherService.getPublishers();
    }

    /*
    Create a new publisher.

    GraphQL Mutation

    • Creates a new publisher.
    • Accepts PublisherInput as the mutation input.
    • Returns only the fields requested by the client.

    Example 1 - Return only generated ID.

    mutation {
      createPublisher(
        input: {
          name: "Zorba Books"
          website: "https://www.zorbabooks.com"
        }
      ) {
        id
      }
    }

    Example 2 - Return complete publisher details.

    mutation {
      createPublisher(
        input: {
          name: "Zorba Books"
          website: "https://www.zorbabooks.com"
        }
      ) {
        id
        name
        website
      }
    }
    */
    @MutationMapping
    public Publisher createPublisher(@Argument @Valid PublisherInput input) {
        return publisherService.createPublisher(input);
    }

    /*
    Update an existing publisher.

    GraphQL Mutation

    • Updates an existing publisher.
    • Accepts the publisher ID and PublisherInput.
    • Returns only the fields requested by the client.

    Example 1 - Return updated publisher name.

    mutation {
      updatePublisher(
        id: 1
        input: {
          name: "Addison-Wesley Professional"
          website: "https://www.informit.com"
        }
      ) {
        name
      }
    }

    Example 2 - Return complete updated publisher details.

    mutation {
      updatePublisher(
        id: 2
        input: {
          name: "Packt Publishing"
          website: "https://www.packtpub.com"
        }
      ) {
        id
        name
        website
      }
    }
    */
    @MutationMapping
    public Publisher updatePublisher(@Argument Long id, @Argument @Valid PublisherInput input) {
        return publisherService.updatePublisher(id, input);
    }

    /*
    Delete a publisher by its ID.

    GraphQL Mutation

    • Deletes an existing publisher.
    • Uses the publisher ID as input.
    • Returns true when the publisher is deleted successfully.

    Note

    • A publisher cannot be deleted if one or more books are associated with
      that publisher.

    Example

    mutation {
      deletePublisher(id: 10)
    }
    */
    @MutationMapping
    public Boolean deletePublisher(@Argument Long id) {
        return publisherService.deletePublisher(id);
    }
}