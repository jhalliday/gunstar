package gunstar.api.trading;

public enum OrderDirection {

    BUY("buy"), SELL("sell");

    public String display;

    OrderDirection(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
