package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.Response;
import org.tbk.nostr.proto.json.JsonReader;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public class ResponseDeserializer extends StdDeserializer<Response> {

    protected ResponseDeserializer() {
        super(Response.class);
    }

    @Override
    public Response deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Response.newBuilder());
    }
}
