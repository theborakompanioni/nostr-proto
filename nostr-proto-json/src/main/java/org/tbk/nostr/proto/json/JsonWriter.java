package org.tbk.nostr.proto.json;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.EventOrBuilder;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.Response;

import static org.tbk.nostr.proto.json.Json.json;

/**
 * Writes NIP-01 messages as JSON.
 *
 * <p>Output is compact and deterministic; event properties follow NIP-01 order.
 *
 * <pre>{@code
 * String json = JsonWriter.toJson(Response.newBuilder()
 *         .setNotice(NoticeResponse.newBuilder().setMessage("hello"))
 *         .build());
 * // ["NOTICE","hello"]
 * }</pre>
 *
 * @see JsonReader
 * @see <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
 */
public final class JsonWriter {

    private JsonWriter() {
        throw new UnsupportedOperationException();
    }

    /**
     * Writes a relay message.
     *
     * <p>Supported messages are {@code EVENT}, {@code OK}, {@code EOSE}, {@code CLOSED},
     * {@code NOTICE}, {@code COUNT} and {@code AUTH}.
     *
     * @param val the message to write
     * @return the JSON message
     * @throws IllegalArgumentException if no message kind is set
     */
    public static String toJson(Response val) {
        return JsonResponseWriter.toJson(val);
    }

    /**
     * Writes a client message.
     *
     * <p>Supported messages are {@code EVENT}, {@code REQ}, {@code CLOSE}, {@code COUNT} and
     * {@code AUTH}.
     *
     * @param val the message to write
     * @return the JSON message
     * @throws IllegalArgumentException if no message kind is set
     */
    public static String toJson(Request val) {
        return JsonRequestWriter.toJson(val);
    }

    /**
     * Writes profile metadata.
     *
     * <p>Only properties set on the message are written; empty strings and {@code false} are
     * omitted.
     *
     * @param val the metadata to write
     * @return the JSON object
     */
    public static String toJson(ProfileMetadata val) {
        return JsonRequestWriter.toJson(val);
    }

    /**
     * Writes a complete event.
     *
     * @param val the event to write
     * @return the JSON event
     */
    public static String toJson(Event val) {
        return json
                .composeString()
                .addPOJO(Json.eventAsMap(val))
                .finish();
    }

    /**
     * Writes an event in the canonical form used for signing.
     *
     * <p>The result is the array {@code [0, pubkey, created_at, kind, tags, content]}. Its SHA-256
     * hash is the event id and the data that gets signed.
     *
     * <pre>{@code
     * Event event = Event.newBuilder()
     *         .setPubkey(pubkey)
     *         .setCreatedAt(1714851835)
     *         .setKind(1)
     *         .setContent("GM")
     *         .build();
     *
     * String json = JsonWriter.toJsonForSigning(event);
     * // [0,"<pubkey>",1714851835,1,[],"GM"]
     * }</pre>
     *
     * @param event the event to write
     * @return the serialized event
     * @see <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
     */
    public static String toJsonForSigning(EventOrBuilder event) {
        return JsonRequestWriter.toJsonForSigning(event);
    }
}
