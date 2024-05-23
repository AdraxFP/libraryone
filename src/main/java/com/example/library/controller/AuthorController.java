package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/living/{year}")
    public List<Author> getLivingAuthorsInYear(@PathVariable int year) {
        return authorService.getLivingAuthorsInYear(year);
    }
}
