package gunstar.api;

import gunstar.GunstarContext;

import java.io.IOException;
import java.util.Date;

public abstract class JsonPOJO {

    @Override
    public String toString() {
        try {
            return GunstarContext.objectMapper.writeValueAsString(this);
        } catch (IOException e) {
            return super.toString();
        }
    }

    public DetailedDate parse(String date) {
        return DetailedDate.parse(date);
    }
}
