package org.tbk.jackson.datatype.nostr;


import org.tbk.nostr.proto.Response;
import org.tbk.nostr.proto.json.JsonWriter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class ResponseSerializer extends StdSerializer<Response> {

    protected ResponseSerializer() {
        super(Response.class);
    }

    @Override
    public void serialize(Response value, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
