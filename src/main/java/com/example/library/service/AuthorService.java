package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Author> getLivingAuthorsInYear(int year) {
        return authorRepository.findByBirthYearLessThanEqualAndDeathYearGreaterThanEqualOrDeathYearIsNull(year, year);
    }
}
