package by.htp.library.entity;

import by.htp.library.entity.helper.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_ORDER")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "F_ORDER_ID")
    private Integer id;

    @Column(name = "F_ORDER_CREATION_TIME")
    private LocalDateTime orderCreationTime;

    @Column(name = "F_ORDER_UPDATE_TIME")
    private LocalDateTime orderUpdateTime;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    private List<Book> booksInOrder;

    @ManyToOne
    @JoinColumn(name = "F_USER_ID")
    private User user;

    @Column(name = "F_STATUS")
    private OrderStatus status;
}
