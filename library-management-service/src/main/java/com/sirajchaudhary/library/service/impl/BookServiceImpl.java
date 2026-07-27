package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.request.BookInput;
import com.sirajchaudhary.library.response.BookPage;
import com.sirajchaudhary.library.entity.Author;
import com.sirajchaudhary.library.entity.Book;
import com.sirajchaudhary.library.entity.Category;
import com.sirajchaudhary.library.entity.Publisher;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.AuthorRepository;
import com.sirajchaudhary.library.repository.BookRepository;
import com.sirajchaudhary.library.repository.CategoryRepository;
import com.sirajchaudhary.library.repository.PublisherRepository;
import com.sirajchaudhary.library.service.BookService;
import com.sirajchaudhary.library.specification.BookSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    @Override
    @Cacheable(value = "book", key = "#id")
    public Book getBook(Long id) {

        log.info("Fetching book with id: {}", id);

        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    @Cacheable(
            value = "books",
            key = "{#authorId, #categoryId, #publisherId, #title, #page, #size, #sortBy, #direction}"
    )
    public BookPage getBooks(Long authorId,
                             Long categoryId,
                             Long publisherId,
                             String title,
                             int page,
                             int size,
                             String sortBy,
                             String direction) {

        log.info(
                "Fetching books with filters - title: {}, authorId: {}, categoryId: {}, publisherId: {}, page: {}, size: {}, sortBy: {}, direction: {}",
                title, authorId, categoryId, publisherId, page, size, sortBy, direction);

        Specification<Book> specification = Specification
                .where(BookSpecification.hasTitle(title))
                .and(BookSpecification.hasAuthorId(authorId))
                .and(BookSpecification.hasCategoryId(categoryId))
                .and(BookSpecification.hasPublisherId(publisherId));

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Book> bookPage = bookRepository.findAll(specification, pageable);

        return BookPage.builder()
                .content(bookPage.getContent())
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .pageNumber(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .first(bookPage.isFirst())
                .last(bookPage.isLast())
                .build();
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "book", key = "#result.id")
            },
            evict = {
                    @CacheEvict(value = "books", allEntries = true)
            }
    )
    public Book createBook(BookInput input) {

        log.info("Creating book: {}", input.getTitle());

        if (bookRepository.existsByIsbn(input.getIsbn())) {
            throw new IllegalArgumentException(
                    "Book with ISBN '" + input.getIsbn() + "' already exists.");
        }

        Author author = authorRepository.findById(input.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found with id: " + input.getAuthorId()));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + input.getCategoryId()));

        Publisher publisher = publisherRepository.findById(input.getPublisherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publisher not found with id: " + input.getPublisherId()));

        Book book = Book.builder()
                .title(input.getTitle())
                .isbn(input.getIsbn())
                .publicationYear(input.getPublicationYear())
                .price(input.getPrice())
                .available(Boolean.TRUE.equals(input.getAvailable()))
                .author(author)
                .category(category)
                .publisher(publisher)
                .build();

        return bookRepository.save(book);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "book", key = "#id")
            },
            evict = {
                    @CacheEvict(value = "books", allEntries = true)
            }
    )
    public Book updateBook(Long id, BookInput input) {

        log.info("Updating book with id: {}", id);

        Book book = getBook(id);

        Author author = authorRepository.findById(input.getAuthorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Author not found with id: " + input.getAuthorId()));

        Category category = categoryRepository.findById(input.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + input.getCategoryId()));

        Publisher publisher = publisherRepository.findById(input.getPublisherId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publisher not found with id: " + input.getPublisherId()));

        book.setTitle(input.getTitle());
        book.setIsbn(input.getIsbn());
        book.setPublicationYear(input.getPublicationYear());
        book.setPrice(input.getPrice());
        book.setAvailable(Boolean.TRUE.equals(input.getAvailable()));
        book.setAuthor(author);
        book.setCategory(category);
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "book", key = "#id"),
                    @CacheEvict(value = "books", allEntries = true)
            }
    )
    public boolean deleteBook(Long id) {

        Book book = getBook(id);

        bookRepository.delete(book);

        log.info("Deleted book with id: {}", id);

        return true;
    }
}