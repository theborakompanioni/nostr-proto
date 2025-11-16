package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tbk.nostr.proto.Response;
import org.tbk.nostr.proto.json.JsonReader;

import java.io.IOException;

public class ResponseDeserializer extends StdDeserializer<Response> {

    protected ResponseDeserializer() {
        super(Response.class);
    }

    @Override
    public Response deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Response.newBuilder());
    }
}
