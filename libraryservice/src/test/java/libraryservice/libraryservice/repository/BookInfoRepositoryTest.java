package libraryservice.libraryservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import libraryservice.libraryservice.entity.BookInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookInfoRepositoryTest {

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Test
    void testFindByBookId() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(1L);
        bookInfo.setStatus("available");
        bookInfoRepository.save(bookInfo);

        Optional<BookInfo> foundBookInfo = bookInfoRepository.findByBookId(1L);

        assertThat(foundBookInfo).isPresent();
        assertThat(foundBookInfo.get().getBookId()).isEqualTo(1L);
    }

    @Test
    void testFindByStatus() {
        BookInfo bookInfo = new BookInfo();
        bookInfo.setBookId(1L);
        bookInfo.setStatus("available");
        bookInfoRepository.save(bookInfo);

        List<BookInfo> foundBooks = bookInfoRepository.findByStatus("available");

        assertThat(foundBooks).isNotEmpty();
        assertThat(foundBooks.get(0).getStatus()).isEqualTo("available");
    }
}
