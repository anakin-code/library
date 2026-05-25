package library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_title")
public class BookTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public SubCategory getSubCategory() { return subCategory; }
}
