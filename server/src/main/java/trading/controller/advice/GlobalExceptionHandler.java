package trading.controller.advice;

import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import trading.Error.BadRequestException;
import trading.Error.TickerAlreadyTakenException;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationError(MethodArgumentNotValidException e) {
        Stream<String> allErrors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> objectError instanceof FieldError
                        ? "%s: %s".formatted(
                                ((FieldError) objectError).getField(),
                                messageSource.getMessage(objectError, Locale.getDefault())
                        )
                        : messageSource.getMessage(objectError, Locale.getDefault())
                );

        String message = allErrors.collect(Collectors.joining("\n"));

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(message);
        return problemDetail;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(TickerAlreadyTakenException.class)
    public ProblemDetail handleTickerAlreadyTakenException(TickerAlreadyTakenException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setDetail(e.getMessageCode());
        return problemDetail;
    }

    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
