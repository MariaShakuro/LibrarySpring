package libraryservice.libraryservice.controller;


import libraryservice.libraryservice.BaseTest;
import libraryservice.libraryservice.dto.BookInfoDto;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.repository.BookInfoRepository;
import libraryservice.libraryservice.service.BookInfoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Integration Tests for NotifyController")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotifyControllerIntegrationTest extends BaseTest {

    BookInfoService bookInfoService;
    BookInfoRepository bookInfoRepository;

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Should Get Available Books")
    void shouldGetAvailableBooks() throws Exception {

        mockMvc.perform(get("/api/book_info/available")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should Update Book Status")
    void shouldUpdateBookStatus() throws Exception {
        BookInfo bookInfo = BookInfo.builder()
                .status("available")
                .build();
        bookInfoRepository.save(bookInfo);
        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .bookId(bookInfo.getBookId())
                .status("taken")
                .build();


        mockMvc.perform(patch("/api/book_info/updateStatus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookInfoDto)))
                .andExpect(status().isOk());
        BookInfo updatedBookInfo = bookInfoRepository.findById(bookInfo.getBookId()).orElseThrow();
        assertThat(updatedBookInfo.getStatus()).isEqualTo("taken");
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    @DisplayName("Should Get All Books Info")
    void shouldGetAllBooksInfo() throws Exception {

        mockMvc.perform(get("/api/book_info")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    @DisplayName("Should Update Book Info")
    void shouldUpdateBookInfo() throws Exception {
        BookInfo bookInfo = BookInfo.builder()
                .build();
        bookInfoRepository.save(bookInfo);
        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .borrowTime(LocalDateTime.parse("2023-01-01T00:00:00"))
                .returnTime(LocalDateTime.parse("2023-01-10T00:00:00"))
                .status("taken")
                .build();


        mockMvc.perform(patch("/api/book_info/{bookId}", bookInfo.getBookId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookInfoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.borrowTime").value("2023-01-01T00:00:00"))
                .andExpect(jsonPath("$.returnTime").value("2023-01-10T00:00:00"))
                .andExpect(jsonPath("$.status").value("taken"));

        BookInfo updatedBookInfo = bookInfoRepository.findById(bookInfo.getBookId()).orElseThrow();
        assertThat(updatedBookInfo.getBorrowTime()).isEqualTo("2023-01-01T00:00:00");
        assertThat(updatedBookInfo.getReturnTime()).isEqualTo("2023-01-10T00:00:00");
        assertThat(updatedBookInfo.getStatus()).isEqualTo("taken");
    }
}
