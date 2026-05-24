package trading.Error;

public class TickerAlreadyTakenException extends RuntimeException {

    private static final String messageCode = "Stock.ticker.AlreadyTaken";

    public TickerAlreadyTakenException(Throwable cause) {
        super(messageCode, cause);
    }

    public String getMessageCode() {
        return messageCode;
    }
}
