package library.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckoutRequest(
        @NotBlank String hrid,
        @NotBlank String serialNumber
) {}
