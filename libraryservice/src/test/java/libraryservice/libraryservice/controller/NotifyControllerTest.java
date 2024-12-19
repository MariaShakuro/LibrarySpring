package libraryservice.libraryservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Arrays;

import libraryservice.libraryservice.controller.NotifyController;
import libraryservice.libraryservice.dto.BookInfoDTO;
import libraryservice.libraryservice.entity.BookInfo;
import libraryservice.libraryservice.service.BookInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(NotifyController.class)
@ExtendWith(SpringExtension.class)
public class NotifyControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private BookInfoService bookInfoService;

    @InjectMocks
    private NotifyController notifyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetAvailableBooks() throws Exception {
        when(bookInfoService.getAvailableBooks(anyString())).thenReturn(Arrays.asList(new BookInfoDTO()));

        mockMvc.perform(get("/api/book_info/available")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateBookStatus() throws Exception {
        BookInfoDTO bookInfoDTO = new BookInfoDTO();
        bookInfoDTO.setBookId(1L);
        bookInfoDTO.setStatus("available");

        doNothing().when(bookInfoService).updateBookStatus(anyString(), any(BookInfoDTO.class));

        mockMvc.perform(patch("/api/book_info/updateStatus")
                        .header("Authorization", "Bearer valid-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"bookId\": 1, \"status\": \"available\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    void testGetAllBookInfo() throws Exception {
        when(bookInfoService.getAllBookInfo(anyString())).thenReturn(Arrays.asList(new BookInfo()));

        mockMvc.perform(get("/api/book_info")
                        .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateBookInfo() throws Exception {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(1L);

        when(bookInfoService.updateBookInfo(anyLong(), any(BookInfo.class))).thenReturn(bookInfo);

        mockMvc.perform(patch("/api/book_info/{bookId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"taken\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateBookInfo_NotFound() throws Exception {
        when(bookInfoService.updateBookInfo(anyLong(), any(BookInfo.class))).thenThrow(new RuntimeException("Book not found"));

        mockMvc.perform(patch("/api/book_info/{bookId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"status\": \"taken\"}"))
                .andExpect(status().isNotFound());
    }
}
