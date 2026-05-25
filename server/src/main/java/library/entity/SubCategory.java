package library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sub_category")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Long getId() { return id; }
    public Integer getNumber() { return number; }
    public String getName() { return name; }
    public Category getCategory() { return category; }
}
