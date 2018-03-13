package by.htp.library.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "T_USER")
public class User implements Serializable{
    @Id
    @Column(name = "F_LOGIN", unique = true)
    private String login;

    @Column(name = "F_PASSWORD")
    private String password;

    @Column(name = "F_TYPE")
    private String type;

    @Column(name = "F_STATUS")
    private String status;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private UserData userData;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!login.equals(other.login))
            return false;
        if (!password.equals(other.password))
            return false;
        if (!type.equals(other.type))
            return false;
        if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + login.hashCode();
        result = prime * result + password.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User[login=" + login + ", type=" + type + ", status=" + status + "]";
    }
}
