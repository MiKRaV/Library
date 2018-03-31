package by.htp.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "T_USER")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_USER_ID")
    private Integer id;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Book> basket;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (!id.equals(other.id))
            return false;
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
        result = prime * result + id.hashCode();
        result = prime * result + login.hashCode();
        result = prime * result + password.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User[id=" + id + ", login=" + login + ", type=" + type + ", status=" + status + "]";
    }
}
