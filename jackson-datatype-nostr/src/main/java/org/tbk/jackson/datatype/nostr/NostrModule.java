package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.fasterxml.jackson.databind.module.SimpleSerializers;
import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.Response;

public class NostrModule extends Module {
    @Override
    public String getModuleName() {
        return "NostrModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext context) {
        SimpleSerializers serializers = new SimpleSerializers();
        serializers.addSerializer(new EventSerializer());
        serializers.addSerializer(new RequestSerializer());
        serializers.addSerializer(new ResponseSerializer());
        serializers.addSerializer(new ProfileMetadataSerializer());
        context.addSerializers(serializers);

        SimpleDeserializers deserializers = new SimpleDeserializers();
        deserializers.addDeserializer(Event.class, new EventDeserializer());
        deserializers.addDeserializer(Request.class, new RequestDeserializer());
        deserializers.addDeserializer(Response.class, new ResponseDeserializer());
        deserializers.addDeserializer(ProfileMetadata.class, new ProfileMetadataDeserializer());
        context.addDeserializers(deserializers);
    }
}
