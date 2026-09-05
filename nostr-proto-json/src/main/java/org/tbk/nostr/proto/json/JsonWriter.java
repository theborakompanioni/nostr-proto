package org.tbk.nostr.proto.json;

import org.tbk.nostr.proto.*;

import static org.tbk.nostr.proto.json.Json.json;

public final class JsonWriter {

    private JsonWriter() {
        throw new UnsupportedOperationException();
    }

    public static String toJson(Response val) {
        return JsonResponseWriter.toJson(val);
    }

    public static String toJson(Request val) {
        return JsonRequestWriter.toJson(val);
    }

    public static String toJson(ProfileMetadata val) {
        return JsonRequestWriter.toJson(val);
    }

    public static String toJsonForSigning(EventOrBuilder event) {
        return JsonRequestWriter.toJsonForSigning(event);
    }

    public static String toJson(Event val) {
        return json
                .composeString()
                .addPOJO(Json.asMap(val))
                .finish();
    }
}
