package entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="isbn")
    private String isbn;
    @Column(name="name")
    private String name;
    @Column(name="genre")
    private String genre;
    @Column(name="description")
    private String description;
    @Column(name="author")
    private String author;

}
