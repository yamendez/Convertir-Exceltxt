package CRUDTablas;

import java.util.HashMap;
import java.util.Map;

public class Notificador {
    private Map<String, Subscriptor> listeners;

    public Notificador() {
        listeners = new HashMap<>();
    }

    public void subscribe(String type, Subscriptor listener) {
        listeners.put(type, listener);
    }

    public void unsubscribe (String type, Subscriptor listener) {
        listeners.remove(type, listener);
    }

    public void notificar (String type, String[][] data) {
        listeners.forEach((key, value) -> {
            if(key.equals(type)) {
                value.update(data);
            }
        });
    }
}
