package sit.int221.TimeUpBackend.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser", nullable = false)
    private Integer idUser;

    @Column(name = "name", nullable = false, length = 100)
    private String nameUser;

    @Column(name = "email", nullable = false, length = 50)
    private String emailUser;

    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Lob
    @Column(name = "`role enum`")
    private String RoleUser;
    @Column(name = "createdOn" , nullable = false , insertable = false , updatable = false)
    private Instant createOn;

    @Column(name = "updatedOn" , nullable = false , insertable = false ,  updatable = false)
    private Instant updateOn;

}