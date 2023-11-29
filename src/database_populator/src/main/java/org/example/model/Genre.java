package org.example.model;

import org.example.Configuration;

import java.util.List;
import java.util.Objects;

public class Genre {

    private Long id;
    private String name;

    public Genre() {
        this(null, null);
    }

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Genre fromResultSet(java.sql.ResultSet resultSet) throws java.sql.SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPossibleNames() {
        return Configuration.getInstance()
                .getGenres()
                .get(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Genre(id=" + id + ", name='" + name + "')";
    }
}
