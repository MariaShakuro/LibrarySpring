package libraryservice.libraryservice.repository;

import libraryservice.libraryservice.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInfoRepository extends JpaRepository<BookInfo,Long> {
}
