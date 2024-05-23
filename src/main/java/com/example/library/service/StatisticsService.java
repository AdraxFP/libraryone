package com.example.library.service;

import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    @Autowired
    private BookRepository bookRepository;

    public long getBookCountByLanguage(String language) {
        return bookRepository.countByLanguage(language);
    }
}
