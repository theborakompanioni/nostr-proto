package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.json.JsonReader;

import java.io.IOException;

public class ProfileMetadataDeserializer extends StdDeserializer<ProfileMetadata> {

    protected ProfileMetadataDeserializer() {
        super(ProfileMetadata.class);
    }

    @Override
    public ProfileMetadata deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return JsonReader.fromJson(p.readValueAsTree().toString(), ProfileMetadata.newBuilder());
    }
}
