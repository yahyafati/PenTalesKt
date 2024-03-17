package org.example.model;

public class Author {

    private Long id;
    private String name;
    private Long goodReadsAuthorId;

    public Author() {
        this(0L, "", 0L);
    }

    public Author(Long id, String name, Long goodReadsAuthorId) {
        this.id = id;
        this.name = name;
        this.goodReadsAuthorId = goodReadsAuthorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGoodReadsAuthorId() {
        return goodReadsAuthorId;
    }

    public void setGoodReadsAuthorId(Long goodReadsAuthorId) {
        this.goodReadsAuthorId = goodReadsAuthorId;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", name='" + name + '\'' + ", goodReadsAuthorId=" + goodReadsAuthorId + '}';
    }
}
