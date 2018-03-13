package by.htp.library.pojo;

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
@Entity @Table(name = "T_GENRE")
public class Genre implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "F_GENRE_ID")
    private Integer id;

    @Column(name = "F_GENRE")
    private String genre;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Genre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Genre other = (Genre) obj;
        if (!id.equals(other.id)) return false;
        if (!genre.equals(other.genre)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + genre.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Genre[id=" + id.toString() + ", genre=" + genre + "]";
    }
}
