package gunstar.api;

import gunstar.GunstarContext;

import java.io.IOException;

public abstract class JsonPOJO {

    @Override
    public String toString() {
        try {
            return GunstarContext.objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            return super.toString();
        }
    }
}
