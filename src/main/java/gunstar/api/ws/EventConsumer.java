package gunstar.api.ws;

public interface EventConsumer<T> {

    void onEvent(T event);
}
