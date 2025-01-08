package libraryservice.libraryservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Version;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name="book_info")
public class BookInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookId;
    private String status;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;


}
