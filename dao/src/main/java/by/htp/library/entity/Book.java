package by.htp.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "T_BOOK")
public class Book implements Serializable{
    @Id
    @GeneratedValue
    @Column(name = "F_BOOK_ID")
    private Integer id;

    @Column(name = "F_TITLE")
    private String title;

    @ManyToOne
    @JoinColumn(name = "F_GENRE_ID")
    private Genre genre;

    @ManyToMany(mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> authors = new ArrayList<>();

    public Book(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (!id.equals(other.id))
            return false;
        if (!title.equals(other.title))
            return false;
        if (!genre.equals(other.genre))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + title.hashCode();
        result = prime * result + genre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Book[id=" + id.toString() + ", title=" + title + "]";
    }

}
