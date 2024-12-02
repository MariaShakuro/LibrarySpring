package com.example.library.repository;

import com.example.library.entity.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInfoRepository extends JpaRepository<BookInfo,Long> {
}
