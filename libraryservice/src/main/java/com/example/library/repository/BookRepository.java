package com.example.library.repository;

import com.example.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book,Long> {
}
