package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.request.AuthorInput;
import com.sirajchaudhary.library.entity.Author;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.AuthorRepository;
import com.sirajchaudhary.library.repository.BookRepository;
import com.sirajchaudhary.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public Author getAuthor(Long id) {

        return authorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Override
    public List<Author> getAuthors() {

        return authorRepository.findAll();
    }

    @Override
    public Author createAuthor(AuthorInput input) {

        log.info("Creating author: {} {}", input.getFirstName(), input.getLastName());

        if (authorRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException(
                    "Author with email '" + input.getEmail() + "' already exists.");
        }

        Author author = Author.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .email(input.getEmail())
                .build();

        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Long id, AuthorInput input) {

        Author author = getAuthor(id);

        author.setFirstName(input.getFirstName());
        author.setLastName(input.getLastName());
        author.setEmail(input.getEmail());

        return authorRepository.save(author);
    }

    @Override
    public boolean deleteAuthor(Long id) {

        Author author = getAuthor(id);

        if (bookRepository.existsByAuthorId(id)) {
            throw new IllegalArgumentException(
                    "Cannot delete author because one or more books are associated with this author.");
        }

        authorRepository.delete(author);

        return true;
    }
}