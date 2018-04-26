package by.htp.library.entity;

import by.htp.library.entity.helper.BookStatus;
import by.htp.library.entity.helper.Genre;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "authors,user,order, register")
@ToString(exclude = "authors,user,order, register")
@Entity @Table(name = "T_BOOK")
public class Book implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_BOOK_ID")
    private Integer id;

    @Column(name = "F_TITLE")
    private String title;

    @Column(name = "F_GENRE")
    private Genre genre;

    @Column(name = "F_STATUS")
    private BookStatus status;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "books", cascade = CascadeType.ALL)
    private List<Author> authors = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "F_ORDER_ID")
    private Order order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private List<Note> register;

    public Book(String title) {
        this.title = title;
    }

}
