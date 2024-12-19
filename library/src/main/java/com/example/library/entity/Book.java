package com.example.library.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String name;
    private String genre;
    private String description;
    private String author;
   private boolean deleted=false;

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted){
        this.deleted=deleted;
    }
}
