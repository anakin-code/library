package library.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CheckinRequest(
        @NotBlank String hrid,
        @NotBlank String serialNumber
) {}
