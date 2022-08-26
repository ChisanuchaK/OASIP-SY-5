package sit.int221.TimeUpBackend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @Column(name = "name", length = 100 ,nullable = false , unique = true)
    private String nameUser;

    @Column(name = "email", length = 50 ,nullable = false , unique = true)
    private String emailUser;

    @Column(name = "password", length = 90 ,nullable = false)
    private String password;


    //@Enumerated(EnumType.STRING)
    @Column(name = "role")
    private String roleUser;

    @Column(name = "createdOn" , nullable = false , insertable = false , updatable = false)
    private Instant createOn;

    @Column(name = "updatedOn" , nullable = false , insertable = false ,  updatable = false)
    private Instant updateOn;
}