package org.example.model;

import org.example.repo.GenreRepository;

import java.util.List;
import java.util.Objects;

public class Book {
    private Long id;
    private Long goodreadsId;
    private String title;
    private String description;
    private String isbn;
    private String isbn13;
    private Integer pageCount;
    private Integer publicationYear;
    private String publisher;
    private String languageCode;
    private String coverImage;
    private String goodreadsLink;
    private List<Author> authors;
    private List<String> genres;

    public Book() {
    }

    public Book(Long id, Long goodreadsId, String title, String description, String isbn, String isbn13, Integer pageCount, Integer publicationYear, String publisher, String languageCode, String coverImage, String goodreadsLink) {
        this.id = id;
        this.goodreadsId = goodreadsId;
        this.title = title;
        this.description = description;
        this.isbn = isbn;
        this.isbn13 = isbn13;
        this.pageCount = pageCount;
        this.publicationYear = publicationYear;
        this.publisher = publisher;
        this.languageCode = languageCode;
        this.coverImage = coverImage;
        this.goodreadsLink = goodreadsLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getGoodreadsLink() {
        return goodreadsLink;
    }

    public void setGoodreadsLink(String goodreadsLink) {
        this.goodreadsLink = goodreadsLink;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getGoodreadsId() {
        return goodreadsId;
    }

    public void setGoodreadsId(Long goodreadsId) {
        this.goodreadsId = goodreadsId;
    }

    public List<Genre> getUniqueGenres() {
        var list = genres.stream()
                .map(GenreRepository::getGenreByName)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        System.out.println(list);
        return list;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", description='" + description + '\'' + ", isbn='" + isbn + '\'' + ", isbn13='" + isbn13 + '\'' + ", pageCount=" + pageCount + ", publicationYear=" + publicationYear + ", languageCode='" + languageCode + '\'' + ", coverImage='" + coverImage + '\'' + ", authors=" + authors + ", genres=" + genres + '}';
    }
}
