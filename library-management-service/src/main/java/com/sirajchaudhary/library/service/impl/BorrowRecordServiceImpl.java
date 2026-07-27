package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.entity.Book;
import com.sirajchaudhary.library.entity.BorrowRecord;
import com.sirajchaudhary.library.entity.BorrowStatus;
import com.sirajchaudhary.library.entity.Member;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.BookRepository;
import com.sirajchaudhary.library.repository.BorrowRecordRepository;
import com.sirajchaudhary.library.repository.MemberRepository;
import com.sirajchaudhary.library.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private static final int BORROW_DURATION_DAYS = 14;

    private final BorrowRecordRepository repository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Override
    public BorrowRecord getBorrowRecord(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Borrow record not found with id: " + id));
    }

    @Override
    public List<BorrowRecord> getBorrowRecords() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public BorrowRecord borrowBook(Long bookId, Long memberId) {

        log.info("Borrowing book with id: {} for member: {}", bookId, memberId);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Book not found with id: " + bookId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Member not found with id: " + memberId));

        if (repository.existsByBookIdAndStatus(bookId, BorrowStatus.BORROWED)) {
            throw new IllegalArgumentException("Book is already borrowed.");
        }

        if (!Boolean.TRUE.equals(book.getAvailable())) {
            throw new IllegalArgumentException("Book is currently unavailable.");
        }

        book.setAvailable(false);
        bookRepository.save(book);

        BorrowRecord borrowRecord = BorrowRecord.builder()
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(BORROW_DURATION_DAYS))
                .status(BorrowStatus.BORROWED)
                .book(book)
                .member(member)
                .build();

        return repository.save(borrowRecord);
    }

    @Override
    @Transactional
    public BorrowRecord returnBook(Long id) {

        log.info("Returning borrowed book with borrow record id: {}", id);

        BorrowRecord borrowRecord = getBorrowRecord(id);

        if (borrowRecord.getStatus() == BorrowStatus.RETURNED) {
            throw new IllegalArgumentException("Book has already been returned.");
        }

        borrowRecord.setReturnDate(LocalDate.now());
        borrowRecord.setStatus(BorrowStatus.RETURNED);

        Book book = borrowRecord.getBook();
        book.setAvailable(true);

        bookRepository.save(book);

        return repository.save(borrowRecord);
    }
}