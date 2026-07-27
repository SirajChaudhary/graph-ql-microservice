package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.entity.BorrowRecord;
import com.sirajchaudhary.library.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/*
==============================================================================
Borrow Record GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for BorrowRecord.
• Uses @QueryMapping for fetching borrow records.
• Uses @MutationMapping for borrowing and returning books.
• Uses @Argument to receive GraphQL request parameters.
• Returns only the fields requested by the GraphQL client.
• Since the BorrowRecord type exposes Book and Member relationships,
  clients can retrieve borrow record details together with associated
  book and member information in a single GraphQL query.
• Delegates all business logic to the service layer.
*/
@Controller
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    /*
    Fetch a single borrow record by its ID.

    GraphQL Query

    • Retrieves one borrow record.
    • Returns only the fields requested by the client.

    Example 1 - Return borrow status only.

    query {
      borrowRecord(id: 1) {
        status
      }
    }

    Example 2 - Return borrow record with book details.

    query {
      borrowRecord(id: 1) {
        borrowDate
        dueDate
        status
        book {
          title
          isbn
        }
      }
    }

    Example 3 - Return complete borrow record details.

    query {
      borrowRecord(id: 1) {
        id
        borrowDate
        dueDate
        returnDate
        status
        book {
          id
          title
        }
        member {
          id
          firstName
          lastName
        }
      }
    }
    */
    @QueryMapping
    public BorrowRecord borrowRecord(@Argument Long id) {
        return borrowRecordService.getBorrowRecord(id);
    }

    /*
    Fetch all borrow records.

    GraphQL Query

    • Retrieves all borrow records.
    • Returns a list of borrow records.
    • Each borrow record contains only the fields requested by the client.

    Example

    query {
      borrowRecords {
        id
        borrowDate
        dueDate
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
    */
    @QueryMapping
    public List<BorrowRecord> borrowRecords() {
        return borrowRecordService.getBorrowRecords();
    }

    /*
    Borrow a book.

    GraphQL Mutation

    • Creates a new borrow record.
    • Accepts a book ID and member ID.
    • Automatically sets the borrow date and due date.
    • Marks the book as unavailable.
    • Returns only the fields requested by the client.

    Example

    mutation {
      borrowBook(bookId: 1, memberId: 2) {
        id
        borrowDate
        dueDate
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
    */
    @MutationMapping
    public BorrowRecord borrowBook(@Argument Long bookId,
                                   @Argument Long memberId) {
        return borrowRecordService.borrowBook(bookId, memberId);
    }

    /*
    Return a borrowed book.

    GraphQL Mutation

    • Returns a previously borrowed book.
    • Accepts the borrow record ID.
    • Automatically sets the return date.
    • Marks the book as available.
    • Returns only the fields requested by the client.

    Example

    mutation {
      returnBook(id: 1) {
        id
        returnDate
        status
        book {
          title
          available
        }
      }
    }
    */
    @MutationMapping
    public BorrowRecord returnBook(@Argument Long id) {
        return borrowRecordService.returnBook(id);
    }
}