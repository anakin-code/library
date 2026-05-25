package library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_title_tag")
public class BookTitleTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_title_id")
    private BookTitle bookTitle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public Long getId() { return id; }
    public BookTitle getBookTitle() { return bookTitle; }
    public Tag getTag() { return tag; }
}
