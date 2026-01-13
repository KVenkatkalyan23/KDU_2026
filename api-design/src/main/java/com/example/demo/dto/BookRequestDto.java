package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;

public class BookRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

