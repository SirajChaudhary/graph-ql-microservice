package com.sirajchaudhary.library.repository;

import com.sirajchaudhary.library.entity.BorrowRecord;
import com.sirajchaudhary.library.entity.BorrowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    boolean existsByBookIdAndStatus(Long bookId, BorrowStatus status);

    List<BorrowRecord> findByMemberId(Long memberId);

    List<BorrowRecord> findByBookId(Long bookId);
}