package libraryservice.libraryservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import libraryservice.libraryservice.dto.BookInfoDTO;
import libraryservice.libraryservice.service.BookInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


public class NotifyControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookInfoService bookInfoService;

    @InjectMocks
    private NotifyController notifyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notifyController).build();
    }

    @Test
    @WithMockUser(roles = {"USER"})
    void testGetAvailableBooks() throws Exception {
        String token = "valid-token";
        BookInfoDTO bookInfoDTO = new BookInfoDTO();
        bookInfoDTO.setBookId(1L);
        bookInfoDTO.setStatus("available");

        when(bookInfoService.getAvailableBooks(token)).thenReturn(Collections.singletonList(bookInfoDTO));

        mockMvc.perform(get("/api/book_info/available")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookId").value(1L))
                .andExpect(jsonPath("$[0].status").value("available"));
    }
}



