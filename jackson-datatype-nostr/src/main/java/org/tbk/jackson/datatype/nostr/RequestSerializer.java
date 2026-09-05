package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.json.JsonWriter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class RequestSerializer extends StdSerializer<Request> {

    protected RequestSerializer() {
        super(Request.class);
    }

    @Override
    public void serialize(Request value, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
