package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.json.JsonReader;

import java.io.IOException;

public class RequestDeserializer extends StdDeserializer<Request> {

    protected RequestDeserializer() {
        super(Request.class);
    }

    @Override
    public Request deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return JsonReader.fromJson(p.readValueAsTree().toString(), Request.newBuilder());
    }
}
