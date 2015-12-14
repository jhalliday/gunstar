package gunstar.api.trading;

public enum OrderType {

    LIMIT("limit"), MARKET("market"), FoK("fill-or-kill"), IoC("immediate-or-cancel");

    public String display;

    OrderType(String display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return display;
    }
}
