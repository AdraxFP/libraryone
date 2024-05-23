package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.model.Book;
import com.example.library.repository.AuthorRepository;
import com.example.library.repository.BookRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Iterator;

@Service
public class GutendexService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public GutendexService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void fetchAndSaveBooks() throws IOException {
        String url = "https://gutendex.com/books/";
        String response = restTemplate.getForObject(url, String.class);

        JsonNode root = objectMapper.readTree(response);
        JsonNode results = root.path("results");

        Iterator<JsonNode> elements = results.elements();
        while (elements.hasNext()) {
            JsonNode bookNode = elements.next();
            Book book = parseBook(bookNode);
            bookRepository.save(book);
        }
    }

    private Book parseBook(JsonNode bookNode) {
        Book book = new Book();
        book.setTitle(bookNode.path("title").asText());
        book.setLanguage(bookNode.path("languages").get(0).asText());
        book.setDownloadLink(bookNode.path("formats").path("text/plain").asText());

        JsonNode authorNode = bookNode.path("authors").get(0);
        Author author = new Author();
        author.setName(authorNode.path("name").asText());
        author.setBirthYear(authorNode.path("birth_year").asInt());
        author.setDeathYear(authorNode.path("death_year").isNull() ? null : authorNode.path("death_year").asInt());

        authorRepository.save(author);
        book.setAuthor(author);

        return book;
    }
}
