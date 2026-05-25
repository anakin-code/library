package library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hrid;
    private String email;
    private String division;

    @Column(name = "is_admin")
    private boolean admin;

    public Long getId() { return id; }
    public String getHrid() { return hrid; }
    public String getEmail() { return email; }
    public String getDivision() { return division; }
    public boolean isAdmin() { return admin; }
}
