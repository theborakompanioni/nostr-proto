package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.json.JsonWriter;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class ProfileMetadataSerializer extends StdSerializer<ProfileMetadata> {

    protected ProfileMetadataSerializer() {
        super(ProfileMetadata.class);
    }

    @Override
    public void serialize(ProfileMetadata value, JsonGenerator jsonGenerator, SerializationContext ctxt) throws JacksonException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
