package library.dto.response;

import java.time.LocalDate;

public record AdminCheckoutResponse(
        String serialNumber,
        String title,
        String hrid,
        String email,
        String division,
        LocalDate dueDate
) {}
