package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.request.BookInput;
import com.sirajchaudhary.library.response.BookPage;
import com.sirajchaudhary.library.entity.Book;

public interface BookService {

    Book getBook(Long id);

    BookPage getBooks(Long authorId,
                      Long categoryId,
                      Long publisherId,
                      String title,
                      int page,
                      int size,
                      String sortBy,
                      String direction);

    Book createBook(BookInput input);

    Book updateBook(Long id, BookInput input);

    boolean deleteBook(Long id);
}