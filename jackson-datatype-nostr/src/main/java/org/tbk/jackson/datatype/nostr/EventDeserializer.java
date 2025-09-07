package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.json.JsonReader;

import java.io.IOException;

public class EventDeserializer extends StdDeserializer<Event> {

    protected EventDeserializer() {
        super(Event.class);
    }

    @Override
    public Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Event.newBuilder());
    }
}
