package trading.model;

public enum Side {
    SELL,
    BUY;

    public static Side getSide(String side) {
        if (side == null) {
            return null;
        }

        try {
            return Side.valueOf(side.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
