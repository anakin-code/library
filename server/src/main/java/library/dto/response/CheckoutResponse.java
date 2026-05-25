package library.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CheckoutResponse(
        Long checkoutId,
        String hrid,
        String email,
        String division,
        String serialNumber,
        String title,
        String state,
        LocalDateTime borrowedAt,
        LocalDate dueDate,
        LocalDateTime checkedInAt
) {}
