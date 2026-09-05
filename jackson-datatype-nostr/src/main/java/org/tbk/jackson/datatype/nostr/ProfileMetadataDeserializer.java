package org.tbk.jackson.datatype.nostr;

import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.json.JsonReader;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

public class ProfileMetadataDeserializer extends StdDeserializer<ProfileMetadata> {

    protected ProfileMetadataDeserializer() {
        super(ProfileMetadata.class);
    }

    @Override
    public ProfileMetadata deserialize(JsonParser p, DeserializationContext ctxt) {
        return JsonReader.fromJson(p.readValueAsTree().toString(), ProfileMetadata.newBuilder());
    }
}
