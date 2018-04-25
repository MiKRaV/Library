package by.htp.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_NOTE")
public class Note implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_NOTE_ID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "F_USER")
    private User user;

    @ManyToOne
    @JoinColumn(name = "F_BOOK")
    private Book book;

    @Column(name = "F_BOOK_ISSUE_TIME")
    private LocalDateTime bookIssueTime;

    @Column(name = "F_IS_RETURNED")
    private boolean returned;

    @Column(name = "F_BOOK_RETURNED_TIME")
    private LocalDateTime bookReturnedTime;
}
