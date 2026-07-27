package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.request.CategoryInput;
import com.sirajchaudhary.library.entity.Category;
import com.sirajchaudhary.library.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/*
==============================================================================
Category GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for Category.
• Uses @QueryMapping for fetching categories.
• Uses @MutationMapping for creating, updating and deleting categories.
• Uses @Argument to receive GraphQL request parameters.
• Returns only the fields requested by the GraphQL client.
• Since the Category type does not expose related entities in the GraphQL
  schema, clients retrieve only the Category fields they request.
• Delegates all business logic to the service layer.
*/
@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /*
    Fetch a single category by its ID.

    GraphQL Query

    • Retrieves one category.
    • Returns only the fields requested by the client.

    Example 1 - Return only category name.

    query {
      category(id: 1) {
        name
      }
    }

    Example 2 - Return complete category details.

    query {
      category(id: 1) {
        id
        name
        description
      }
    }
    */
    @QueryMapping
    public Category category(@Argument Long id) {
        return categoryService.getCategory(id);
    }

    /*
    Fetch all categories.

    GraphQL Query

    • Retrieves all categories.
    • Returns a list of categories.
    • Each category contains only the fields requested by the client.

    Example 1 - Return only category names.

    query {
      categories {
        name
      }
    }

    Example 2 - Return complete category details.

    query {
      categories {
        id
        name
        description
      }
    }
    */
    @QueryMapping
    public List<Category> categories() {
        return categoryService.getCategories();
    }

    /*
    Create a new category.

    GraphQL Mutation

    • Creates a new category.
    • Accepts CategoryInput as the mutation input.
    • Returns only the fields requested by the client.

    Example 1 - Return only generated ID.

    mutation {
      createCategory(
        input: {
          name: "Cloud Computing"
          description: "Books related to cloud platforms and services."
        }
      ) {
        id
      }
    }

    Example 2 - Return complete category details.

    mutation {
      createCategory(
        input: {
          name: "Cloud Computing"
          description: "Books related to cloud platforms and services."
        }
      ) {
        id
        name
        description
      }
    }
    */
    @MutationMapping
    public Category createCategory(@Argument @Valid CategoryInput input) {
        return categoryService.createCategory(input);
    }

    /*
    Update an existing category.

    GraphQL Mutation

    • Updates an existing category.
    • Accepts the category ID and CategoryInput.
    • Returns only the fields requested by the client.

    Example 1 - Return updated category name.

    mutation {
      updateCategory(
        id: 1
        input: {
          name: "Advanced Java"
          description: "Advanced Java programming concepts."
        }
      ) {
        name
      }
    }

    Example 2 - Return complete updated category details.

    mutation {
      updateCategory(
        id: 2
        input: {
          name: "Spring Framework"
          description: "Spring Framework and Spring Boot books."
        }
      ) {
        id
        name
        description
      }
    }
    */
    @MutationMapping
    public Category updateCategory(@Argument Long id, @Argument @Valid CategoryInput input) {
        return categoryService.updateCategory(id, input);
    }

    /*
    Delete a category by its ID.

    GraphQL Mutation

    • Deletes an existing category.
    • Uses the category ID as input.
    • Returns true when the category is deleted successfully.

    Note

    • A category cannot be deleted if one or more books are associated with
      that category.

    Example

    mutation {
      deleteCategory(id: 10)
    }
    */
    @MutationMapping
    public Boolean deleteCategory(@Argument Long id) {
        return categoryService.deleteCategory(id);
    }
}