package org.tbk.nostr.proto.json;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.Response;

public final class JsonReader {

    private JsonReader() {
        throw new UnsupportedOperationException();
    }

    public static Request fromJson(String val, Request.Builder request) {
        return JsonRequestReader.fromJson(val, request);
    }

    public static Response fromJson(String val, Response.Builder response) {
        return JsonResponseReader.fromJson(val, response);
    }

    public static ProfileMetadata fromJson(String val, ProfileMetadata.Builder metadata) {
        return JsonResponseReader.fromJson(val, metadata);
    }

    public static Event fromJson(String val, Event.Builder event) {
        return Json.eventFromMap(Json.mapFrom(val), event);
    }

    public static Event.Builder fromJsonPartial(String val, Event.Builder event) {
        return Json.eventFromMapPartial(Json.mapFrom(val), event);
    }
}
