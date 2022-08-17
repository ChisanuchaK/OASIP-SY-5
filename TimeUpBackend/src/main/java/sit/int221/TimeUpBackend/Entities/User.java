package sit.int221.TimeUpBackend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

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

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role", nullable = false)
    private RoleUser roleUser;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "createdOn" , nullable = false)
    private Timestamp createOn;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "updateOn" , nullable = false)
    private Timestamp updateOn;
}