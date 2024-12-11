package libraryservice.libraryservice.repository;

import libraryservice.libraryservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book,Long> {
}
