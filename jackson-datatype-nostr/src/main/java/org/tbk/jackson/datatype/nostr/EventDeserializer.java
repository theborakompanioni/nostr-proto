package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.json.JsonReader;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

/**
 * Deserializes an {@link Event} from NIP-01 JSON.
 *
 * <p>Delegates to {@link JsonReader#fromJson(String, Event.Builder)}.
 *
 * @see EventSerializer
 */
public class EventDeserializer extends StdDeserializer<Event> {

    protected EventDeserializer() {
        super(Event.class);
    }

    @Override
    public Event deserialize(JsonParser p, DeserializationContext ctxt) {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Event.newBuilder());
    }
}
