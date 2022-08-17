package sit.int221.TimeUpBackend.Entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser", nullable = false)
    private Integer idUser;

    @Column(name = "name", length = 100 ,nullable = false)
    private String nameUser;

    @Column(name = "email", length = 50 ,nullable = false)
    private String emailUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleUser roleUser;

    @CreationTimestamp
    @Column(name = "createdOn" , nullable = false)
    private Timestamp createOn;

    @CreationTimestamp
    @Column(name = "updatedOn" , nullable = false)
    private Timestamp updateOn;
}