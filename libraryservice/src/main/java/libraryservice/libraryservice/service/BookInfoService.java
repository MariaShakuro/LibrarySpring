package libraryservice.libraryservice.service;


import libraryservice.libraryservice.client.JwtAuthClient;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.dto.BookInfoMapper;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class BookInfoService {

    BookInfoRepository bookInfoRepository;
    JwtAuthClient jwtAuthClient;
    BookInfoMapper bookInfoMapper;

    public List<BookInfoDto> getAvailableBooks() {
        log.info("Fetching available books");
        List<BookInfo> availableBooks = bookInfoRepository.findByStatus("available");
        List<BookInfoDto> availableBookDTOs = availableBooks.stream()
                .map(BookInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return availableBookDTOs;
    }

    public void updateBookStatus(BookInfoDto bookInfoDto) {
        if (!bookInfoDto.getStatus().equals("available") && !bookInfoDto.getStatus().equals("taken")) {
            throw new IllegalArgumentException("Irrelevant status value");
        }
        BookInfo bookInfo = bookInfoRepository.findByBookId(bookInfoDto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookInfoDto.setStatus(bookInfoDto.getStatus());
       BookInfo updatedBookStatus= bookInfoRepository.save(bookInfo);
        BookInfoMapper.INSTANCE.toDto(updatedBookStatus);
    }

    public List<BookInfoDto> getAllBooksInfo() {
        List<BookInfo> allBooks = bookInfoRepository.findAll();
        List<BookInfoDto> allBookDtos = allBooks.stream()
                .map(BookInfoMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
        return allBookDtos;
    }

    public BookInfoDto updateBookInfo(Long id, BookInfoDto details) {
        log.info("Updating information for book with ID: {}", id);
        BookInfo bookInfo = bookInfoRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        bookInfo.setBorrowTime(details.getBorrowTime() != null ? details.getBorrowTime() : LocalDateTime.now());
        bookInfo.setReturnTime(details.getReturnTime() != null ? details.getReturnTime() : LocalDateTime.now().plusWeeks(2));
        bookInfo.setStatus(details.getStatus());
        BookInfo updatedBookInfo = bookInfoRepository.save(bookInfo);
        return BookInfoMapper.INSTANCE.toDto(updatedBookInfo);
    }

}
