package library.service;

import library.dto.request.CheckinRequest;
import library.dto.request.CheckoutRequest;
import library.dto.response.AdminCheckoutResponse;
import library.dto.response.CheckoutResponse;
import library.entity.AppUser;
import library.entity.BookCollection;
import library.entity.CheckoutHistory;
import library.repository.AppUserRepository;
import library.repository.BookCollectionRepository;
import library.repository.CheckoutHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckoutService {
    private final AppUserRepository appUserRepository;
    private final BookCollectionRepository bookCollectionRepository;
    private final CheckoutHistoryRepository checkoutHistoryRepository;

    public CheckoutService(
            AppUserRepository appUserRepository,
            BookCollectionRepository bookCollectionRepository,
            CheckoutHistoryRepository checkoutHistoryRepository
    ) {
        this.appUserRepository = appUserRepository;
        this.bookCollectionRepository = bookCollectionRepository;
        this.checkoutHistoryRepository = checkoutHistoryRepository;
    }

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        AppUser user = appUserRepository.findByHrid(request.hrid())
                .orElseThrow(() -> new IllegalArgumentException("指定されたHRIDの利用者が存在しません"));

        BookCollection book = bookCollectionRepository.findBySerialNumber(request.serialNumber())
                .orElseThrow(() -> new IllegalArgumentException("指定されたシリアルナンバーの蔵書が存在しません"));

        if (checkoutHistoryRepository.countByUserIdAndCheckedInAtIsNull(user.getId()) >= 5) {
            throw new IllegalArgumentException("1人の利用者が同時に借りられる冊数は5冊までです");
        }

        if (checkoutHistoryRepository.existsActiveSameTitle(user.getId(), book.getBookTitle().getId())) {
            throw new IllegalArgumentException("同じ書籍タイトルを同時に借りることはできません");
        }

        book.checkout();

        LocalDateTime now = LocalDateTime.now();
        CheckoutHistory history = new CheckoutHistory(user, book, now, now.toLocalDate().plusWeeks(2));
        CheckoutHistory saved = checkoutHistoryRepository.save(history);

        return toCheckoutResponse(saved);
    }

    @Transactional
    public CheckoutResponse checkin(CheckinRequest request) {
        AppUser user = appUserRepository.findByHrid(request.hrid())
                .orElseThrow(() -> new IllegalArgumentException("指定されたHRIDの利用者が存在しません"));

        BookCollection book = bookCollectionRepository.findBySerialNumber(request.serialNumber())
                .orElseThrow(() -> new IllegalArgumentException("指定されたシリアルナンバーの蔵書が存在しません"));

        CheckoutHistory history = checkoutHistoryRepository
                .findByBookCollectionIdAndCheckedInAtIsNull(book.getId())
                .orElseThrow(() -> new IllegalArgumentException("この蔵書は現在貸し出し中ではありません"));

        if (!history.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("この蔵書を借りている利用者ではないため返却できません");
        }

        history.checkin(LocalDateTime.now());
        book.checkin();

        return toCheckoutResponse(history);
    }

    @Transactional(readOnly = true)
    public List<CheckoutResponse> getActiveCheckouts(String hrid) {
        return checkoutHistoryRepository
                .findByUserHridAndCheckedInAtIsNullOrderByDueDateAsc(hrid)
                .stream()
                .map(this::toCheckoutResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CheckoutResponse> getHistory(String hrid) {
        return checkoutHistoryRepository
                .findByUserHridOrderByBorrowedAtDesc(hrid)
                .stream()
                .map(this::toCheckoutResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AdminCheckoutResponse> getAdminActiveCheckouts() {
        return checkoutHistoryRepository
                .findByCheckedInAtIsNullOrderByDueDateAsc()
                .stream()
                .map(history -> new AdminCheckoutResponse(
                        history.getBookCollection().getSerialNumber(),
                        history.getBookCollection().getBookTitle().getTitle(),
                        history.getUser().getHrid(),
                        history.getUser().getEmail(),
                        history.getUser().getDivision(),
                        history.getDueDate()
                ))
                .toList();
    }

    private CheckoutResponse toCheckoutResponse(CheckoutHistory history) {
        return new CheckoutResponse(
                history.getId(),
                history.getUser().getHrid(),
                history.getUser().getEmail(),
                history.getUser().getDivision(),
                history.getBookCollection().getSerialNumber(),
                history.getBookCollection().getBookTitle().getTitle(),
                history.getBookCollection().getState().name(),
                history.getBorrowedAt(),
                history.getDueDate(),
                history.getCheckedInAt()
        );
    }
}
