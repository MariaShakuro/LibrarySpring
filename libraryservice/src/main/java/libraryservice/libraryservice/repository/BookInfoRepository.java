package libraryservice.libraryservice.repository;

import libraryservice.libraryservice.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo,Long> {
  Optional<BookInfo>findByBookId(Long bookId);
  List<BookInfo>findByStatus(String status);
}
