package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.json.JsonWriter;

import java.io.IOException;

public class ProfileMetadataSerializer extends StdSerializer<ProfileMetadata> {

    protected ProfileMetadataSerializer() {
        super(ProfileMetadata.class);
    }

    @Override
    public void serialize(ProfileMetadata value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeRawValue(JsonWriter.toJson(value));
    }
}
