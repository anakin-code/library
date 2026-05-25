package library.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_collection")
public class BookCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private BookState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_title_id")
    private BookTitle bookTitle;

    public void checkout() {
        if (state != BookState.AVAILABLE) {
            throw new IllegalStateException("貸出可能な状態ではありません");
        }
        this.state = BookState.CHECKED_OUT;
    }

    public void checkin() {
        if (state != BookState.CHECKED_OUT) {
            throw new IllegalStateException("貸出中ではないため返却できません");
        }
        this.state = BookState.AVAILABLE;
    }

    public void deaccession() {
        if (state == BookState.CHECKED_OUT) {
            throw new IllegalStateException("貸出中の蔵書は廃棄できません");
        }
        if (state == BookState.DEACCESSIONED) {
            throw new IllegalStateException("この蔵書はすでに廃棄済みです");
        }
        this.state = BookState.DEACCESSIONED;
    }

    public Long getId() { return id; }
    public String getSerialNumber() { return serialNumber; }
    public BookState getState() { return state; }
    public BookTitle getBookTitle() { return bookTitle; }
}
