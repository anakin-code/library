package library.dto.response;

import java.time.LocalDateTime;

public record ProblemDetails(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {}
