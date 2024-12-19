package libraryservice.libraryservice.entity;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class BookInfoTest {

    @Test
    public void testNoArgsConstructor() {
        BookInfo bookInfo = new BookInfo();
        assertNotNull(bookInfo);
    }

    @Test
    public void testGettersAndSetters() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1L);
        bookInfo.setBorrowTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        bookInfo.setReturnTime(LocalDateTime.of(2024, 12, 12, 10, 0));

        assertEquals(1L, bookInfo.getId());
        assertEquals(LocalDateTime.of(2024, 12, 5, 10, 0), bookInfo.getBorrowTime());
        assertEquals(LocalDateTime.of(2024, 12, 12, 10, 0), bookInfo.getReturnTime());
    }

    @Test
    public void testEqualsAndHashCode() {
        BookInfo bookInfo1 = new BookInfo();
        bookInfo1.setId(1L);
        bookInfo1.setBorrowTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        bookInfo1.setReturnTime(LocalDateTime.of(2024, 12, 12, 10, 0));

        BookInfo bookInfo2 = new BookInfo();
        bookInfo2.setId(1L);

        bookInfo2.setBorrowTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        bookInfo2.setReturnTime(LocalDateTime.of(2024, 12, 12, 10, 0));

        BookInfo bookInfo3 = new BookInfo();
        bookInfo3.setId(2L);

        bookInfo3.setBorrowTime(LocalDateTime.of(2024, 12, 6, 10, 0));
        bookInfo3.setReturnTime(LocalDateTime.of(2024, 12, 13, 10, 0));

        assertEquals(bookInfo1, bookInfo2);
        assertNotEquals(bookInfo1, bookInfo3);
        assertEquals(bookInfo1.hashCode(), bookInfo2.hashCode());
        assertNotEquals(bookInfo1.hashCode(), bookInfo3.hashCode());
    }

    @Test
    public void testToString() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setId(1L);

        bookInfo.setBorrowTime(LocalDateTime.of(2024, 12, 5, 10, 0));
        bookInfo.setReturnTime(LocalDateTime.of(2024, 12, 12, 10, 0));

        String expectedString = "BookInfo(id=1, idBook=2, borrowTime=2024-12-05T10:00, returnTime=2024-12-12T10:00)";
        assertEquals(expectedString, bookInfo.toString());
    }
}
