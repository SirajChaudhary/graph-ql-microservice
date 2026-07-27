package com.sirajchaudhary.library.response;

import com.sirajchaudhary.library.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookPage {

    private List<Book> content;

    private long totalElements;

    private int totalPages;

    private int pageNumber;

    private int pageSize;

    private boolean first;

    private boolean last;
}