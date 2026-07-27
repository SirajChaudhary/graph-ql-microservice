package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.entity.BorrowRecord;

import java.util.List;

public interface BorrowRecordService {

    BorrowRecord getBorrowRecord(Long id);

    List<BorrowRecord> getBorrowRecords();

    BorrowRecord borrowBook(Long bookId, Long memberId);

    BorrowRecord returnBook(Long id);
}