package com.sirajchaudhary.library.controller;

import com.sirajchaudhary.library.request.MemberInput;
import com.sirajchaudhary.library.entity.Member;
import com.sirajchaudhary.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/*
==============================================================================
Member GraphQL Controller
==============================================================================

Similar to a REST Controller, but exposes GraphQL operations instead of REST
endpoints.

Responsibilities

• Handles GraphQL Query and Mutation operations for Member.
• Uses @QueryMapping for fetching members.
• Uses @MutationMapping for creating, updating and deleting members.
• Uses @Argument to receive GraphQL request parameters.
• Returns only the fields requested by the GraphQL client.
• Since the Member type does not expose related entities in the GraphQL
  schema, clients retrieve only the Member fields they request.
• Delegates all business logic to the service layer.
*/
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /*
    Fetch a single member by its ID.

    GraphQL Query

    • Retrieves one member.
    • Returns only the fields requested by the client.

    Example 1 - Return only member name.

    query {
      member(id: 1) {
        firstName
        lastName
      }
    }

    Example 2 - Return complete member details.

    query {
      member(id: 1) {
        id
        firstName
        lastName
        email
        membershipDate
        active
      }
    }
    */
    @QueryMapping
    public Member member(@Argument Long id) {
        return memberService.getMember(id);
    }

    /*
    Fetch all members.

    GraphQL Query

    • Retrieves all members.
    • Returns a list of members.
    • Each member contains only the fields requested by the client.

    Example 1 - Return member names.

    query {
      members {
        firstName
        lastName
      }
    }

    Example 2 - Return complete member details.

    query {
      members {
        id
        firstName
        lastName
        email
        membershipDate
      }
    }
    */
    @QueryMapping
    public List<Member> members() {
        return memberService.getMembers();
    }

    /*
    Create a new member.

    GraphQL Mutation

    • Creates a new member.
    • Accepts MemberInput as the mutation input.
    • Returns only the fields requested by the client.

    Example 1 - Return only generated ID.

    mutation {
      createMember(
        input: {
          firstName: "Rahul"
          lastName: "Sharma"
          email: "rahul.sharma@example.com"
        }
      ) {
        id
      }
    }

    Example 2 - Return complete member details.

    mutation {
      createMember(
        input: {
          firstName: "Rohit"
          lastName: "Sharma"
          email: "rohit.sharma@example.com"
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
    public Member createMember(@Argument @Valid MemberInput input) {
        return memberService.createMember(input);
    }

    /*
    Update an existing member.

    GraphQL Mutation

    • Updates an existing member.
    • Accepts the member ID and MemberInput.
    • Returns only the fields requested by the client.

    Example 1 - Return updated member name.

    mutation {
      updateMember(
        id: 1
        input: {
          firstName: "Amita"
          lastName: "Patel"
          email: "amit.patel@example.com"
        }
      ) {
        firstName
        lastName
      }
    }

    Example 2 - Return complete updated member details.

    mutation {
      updateMember(
        id: 2
        input: {
          firstName: "Sneha"
          lastName: "Reddy"
          email: "sneha.reddy@example.com"
        }
      ) {
        id
        firstName
        lastName
        email
        membershipDate
      }
    }
    */
    @MutationMapping
    public Member updateMember(@Argument Long id, @Argument @Valid MemberInput input) {
        return memberService.updateMember(id, input);
    }

    /*
    Delete a member by its ID.

    GraphQL Mutation

    • Deletes an existing member.
    • Uses the member ID as input.
    • Returns true when the member is deleted successfully.

    Note

    • A member cannot be deleted if one or more borrow records are associated
      with that member.

    Example

    mutation {
      deleteMember(id: 10)
    }
    */
    @MutationMapping
    public Boolean deleteMember(@Argument Long id) {
        return memberService.deleteMember(id);
    }
}