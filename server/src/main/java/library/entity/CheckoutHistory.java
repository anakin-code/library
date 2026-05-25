package library.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "checkout_history")
public class CheckoutHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_collection_id")
    private BookCollection bookCollection;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "checked_in_at")
    private LocalDateTime checkedInAt;

    protected CheckoutHistory() {}

    public CheckoutHistory(AppUser user, BookCollection bookCollection, LocalDateTime borrowedAt, LocalDate dueDate) {
        this.user = user;
        this.bookCollection = bookCollection;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
    }

    public void checkin(LocalDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public BookCollection getBookCollection() { return bookCollection; }
    public LocalDateTime getBorrowedAt() { return borrowedAt; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDateTime getCheckedInAt() { return checkedInAt; }
}
