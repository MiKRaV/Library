package by.htp.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserData {
    @Column(name = "F_NAME")
    private String name;

    @Column(name = "F_SURNAME")
    private String surname;

    @Column(name = "F_EMAIL", unique = true)
    private String email;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserData other = (UserData) obj;
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
        result = prime * result + name.hashCode();
        result = prime * result + surname.hashCode();
        result = prime * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserData[name=" + name + ", surname=" + surname +
                ", email=" + email + "]";
    }
}
