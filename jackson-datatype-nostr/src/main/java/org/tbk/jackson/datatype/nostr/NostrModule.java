package org.tbk.jackson.datatype.nostr;


import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.Response;
import tools.jackson.core.Version;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.module.SimpleDeserializers;
import tools.jackson.databind.module.SimpleSerializers;

public class NostrModule extends JacksonModule {
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
