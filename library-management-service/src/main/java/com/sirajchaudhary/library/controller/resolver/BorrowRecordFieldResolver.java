package com.sirajchaudhary.library.controller.resolver;

import com.sirajchaudhary.library.entity.Book;
import com.sirajchaudhary.library.entity.BorrowRecord;
import com.sirajchaudhary.library.entity.Member;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.BookRepository;
import com.sirajchaudhary.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

/*
==============================================================================
Borrow Record GraphQL Field Resolver
==============================================================================

Similar to a GraphQL Controller, but resolves nested fields of the
BorrowRecord type instead of handling Query or Mutation operations.

This is also a type of Spring GraphQL Controller because it is annotated with
@Controller. It resolves GraphQL fields using @SchemaMapping, so it is kept
under the controller package.

Responsibilities

• Uses @SchemaMapping to resolve GraphQL nested fields.
• Resolves the associated Book and Member for a BorrowRecord.
• Invoked only when the client requests these nested fields.
• Loads related entities only when required.
• Prevents LazyInitializationException when using LAZY relationships.
• Throws ResourceNotFoundException if the associated entity does not exist.

GraphQL Field Resolver

• @QueryMapping handles GraphQL queries.
• @MutationMapping handles GraphQL mutations.
• @SchemaMapping resolves nested fields of a GraphQL type.

Example

query {
  borrowRecord(id: 1) {
    borrowDate
    dueDate
    status
    book {
      title
      isbn
    }
    member {
      firstName
      lastName
    }
  }
}

GraphQL automatically invokes the corresponding @SchemaMapping methods to
resolve the requested nested fields.
*/
@Controller
@RequiredArgsConstructor
public class BorrowRecordFieldResolver {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /*
    Resolve the book associated with a borrow record.

    GraphQL Field Resolver

    • Resolves the book field of the BorrowRecord type.
    • Invoked only when the book field is requested.
    • Returns the associated Book.

    Example

    query {
      borrowRecord(id: 1) {
        book {
          title
          isbn
        }
      }
    }
    */
    @SchemaMapping(typeName = "BorrowRecord", field = "book")
    public Book book(BorrowRecord borrowRecord) {

        Long bookId = borrowRecord.getBook().getId();

        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found with id: " + bookId));
    }

    /*
    Resolve the member associated with a borrow record.

    GraphQL Field Resolver

    • Resolves the member field of the BorrowRecord type.
    • Invoked only when the member field is requested.
    • Returns the associated Member.

    Example

    query {
      borrowRecord(id: 1) {
        member {
          firstName
          lastName
          email
        }
      }
    }
    */
    @SchemaMapping(typeName = "BorrowRecord", field = "member")
    public Member member(BorrowRecord borrowRecord) {

        Long memberId = borrowRecord.getMember().getId();

        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found with id: " + memberId));
    }
}