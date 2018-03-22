package by.htp.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserData {
    @Id
    @GenericGenerator(
            name = "one-one",
            strategy = "foreign",
            parameters = @Parameter(name = "property", value = "user"))
    @GeneratedValue(generator = "one-one")
    private Integer id;
    private String name;
    private String surname;

    @Column(unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private User user;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserData other = (UserData) obj;
        if (!id.equals(other.id))
            return false;
        if (!name.equals(other.name))
            return false;
        if (!surname.equals(other.surname))
            return false;
        if (!email.equals(other.email))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id.hashCode();
        result = prime * result + name.hashCode();
        result = prime * result + surname.hashCode();
        result = prime * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserData[id=" + id + ", name=" + name + ", surname=" + surname +
                ", email=" + email + "]";
    }
}
