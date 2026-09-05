package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.json.JsonReader;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public class EventDeserializer extends StdDeserializer<Event> {

    protected EventDeserializer() {
        super(Event.class);
    }

    @Override
    public Event deserialize(JsonParser p, DeserializationContext ctxt) {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Event.newBuilder());
    }
}
