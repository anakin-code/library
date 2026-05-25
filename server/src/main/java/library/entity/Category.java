package library.entity;

import jakarta.persistence.*;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private String name;

    public Long getId() { return id; }
    public Integer getNumber() { return number; }
    public String getName() { return name; }
}
