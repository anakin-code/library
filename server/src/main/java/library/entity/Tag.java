package library.entity;

import jakarta.persistence.*;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Long getId() { return id; }
    public String getName() { return name; }
}
