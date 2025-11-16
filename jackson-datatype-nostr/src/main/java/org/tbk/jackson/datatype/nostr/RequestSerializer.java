package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.json.JsonWriter;

import java.io.IOException;

public class RequestSerializer extends StdSerializer<Request> {

    protected RequestSerializer() {
        super(Request.class);
    }

    @Override
    public void serialize(Request value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
