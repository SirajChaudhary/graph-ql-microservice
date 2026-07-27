package com.sirajchaudhary.library.specification;

import com.sirajchaudhary.library.entity.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    private BookSpecification() {
    }

    public static Specification<Book> hasTitle(String title) {
        return (root, query, cb) ->
                title == null || title.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorId(Long authorId) {
        return (root, query, cb) ->
                authorId == null
                        ? null
                        : cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Book> hasCategoryId(Long categoryId) {
        return (root, query, cb) ->
                categoryId == null
                        ? null
                        : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Book> hasPublisherId(Long publisherId) {
        return (root, query, cb) ->
                publisherId == null
                        ? null
                        : cb.equal(root.get("publisher").get("id"), publisherId);
    }
}