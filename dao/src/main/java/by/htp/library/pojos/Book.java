package by.htp.library.pojos;

import by.htp.library.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Book implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private ArrayList<Author> authors;
    @Column
    private String title;
    @Column
    private String status;
    @Column
    private String genre;

}
