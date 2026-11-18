package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.json.JsonWriter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

/**
 * Serializes an {@link Event} as NIP-01 JSON.
 *
 * <p>Delegates to {@link JsonWriter#toJson(Event)}.
 *
 * @see EventDeserializer
 */
public class EventSerializer extends StdSerializer<Event> {

    protected EventSerializer() {
        super(Event.class);
    }

    @Override
    public void serialize(Event value, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
