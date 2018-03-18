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
@Entity @Table(name = "T_AUTHOR")
public class Author implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "F_AUTHOR_ID")
    private Integer id;

    @Column(name = "F_SURNAME")
    private String surname;

    @Column(name = "F_NAME")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "T_AUTHOR_BOOK", joinColumns = {@JoinColumn(name = "F_AUTHOR_ID")},
            inverseJoinColumns = {@JoinColumn(name = "F_BOOK_ID")})
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        if (!id.equals(other.id))
            return false;
        if (!surname.equals(other.surname))
            return false;
        if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + surname.hashCode();
        result = prime * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Author[id=" + id.toString() + ", surname=" + surname + ", name=" + name + "]";
    }
}
