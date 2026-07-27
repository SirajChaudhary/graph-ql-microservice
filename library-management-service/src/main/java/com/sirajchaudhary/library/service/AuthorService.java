package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.request.AuthorInput;
import com.sirajchaudhary.library.entity.Author;

import java.util.List;

public interface AuthorService {

    Author getAuthor(Long id);

    List<Author> getAuthors();

    Author createAuthor(AuthorInput input);

    Author updateAuthor(Long id, AuthorInput input);

    boolean deleteAuthor(Long id);

}