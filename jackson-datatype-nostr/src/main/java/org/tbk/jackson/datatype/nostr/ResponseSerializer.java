package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.tbk.nostr.proto.Response;
import org.tbk.nostr.proto.json.JsonWriter;

import java.io.IOException;

public class ResponseSerializer extends StdSerializer<Response> {

    protected ResponseSerializer() {
        super(Response.class);
    }

    @Override
    public void serialize(Response value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
