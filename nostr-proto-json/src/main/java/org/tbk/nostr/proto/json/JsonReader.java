package org.tbk.nostr.proto.json;

import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.ProfileMetadata;
import org.tbk.nostr.proto.Request;
import org.tbk.nostr.proto.Response;

/**
 * Reads NIP-01 messages from JSON.
 *
 * <p>The methods operate on the generated protobuf types and return the parsed message:
 *
 * <pre>{@code
 * Request request = JsonReader.fromJson("""
 *         ["REQ", "subscription_id", {"kinds": [1], "limit": 10}]
 *         """, Request.newBuilder());
 * }</pre>
 *
 * <p>Invalid JSON is rejected by the underlying parser with an unchecked exception.
 *
 * @see JsonWriter
 * @see <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
 */
public final class JsonReader {

    private JsonReader() {
        throw new UnsupportedOperationException();
    }

    /**
     * Reads a client message.
     *
     * <p>Supported commands are {@code EVENT}, {@code REQ}, {@code CLOSE}, {@code COUNT} and
     * {@code AUTH}.
     *
     * @param val     the JSON message
     * @param request the builder to populate
     * @return the parsed request
     * @throws IllegalArgumentException if the message is not a valid client message
     */
    public static Request fromJson(String val, Request.Builder request) {
        return JsonRequestReader.fromJson(val, request);
    }

    /**
     * Reads a relay message.
     *
     * <p>Supported messages are {@code EVENT}, {@code OK}, {@code EOSE}, {@code CLOSED},
     * {@code NOTICE}, {@code COUNT} and {@code AUTH}.
     *
     * @param val      the JSON message
     * @param response the builder to populate
     * @return the parsed response
     * @throws IllegalArgumentException if the message is not a valid relay message
     */
    public static Response fromJson(String val, Response.Builder response) {
        return JsonResponseReader.fromJson(val, response);
    }

    /**
     * Reads profile metadata, the content of a kind {@code 0} event.
     *
     * <p>Unknown properties are ignored.
     *
     * @param val      the JSON object
     * @param metadata the builder to populate
     * @return the parsed metadata
     * @throws IllegalArgumentException if a known property has an invalid type
     */
    public static ProfileMetadata fromJson(String val, ProfileMetadata.Builder metadata) {
        return JsonResponseReader.fromJson(val, metadata);
    }

    /**
     * Reads a complete event.
     *
     * <p>All event properties are required. Use {@link #fromJsonPartial(String, Event.Builder)} to
     * read an event with missing properties.
     *
     * @param val   the JSON event
     * @param event the builder to populate
     * @return the parsed event
     * @throws IllegalArgumentException if a required property is missing or has an invalid value
     */
    public static Event fromJson(String val, Event.Builder event) {
        return Json.eventFromMap(Json.mapFrom(val), event);
    }

    /**
     * Reads an event that may contain only a subset of its properties.
     *
     * <p>Properties missing from the JSON keep their current value on the builder. A missing
     * {@code tags} property keeps the tags on the builder, while an empty array clears them.
     * Unknown properties are ignored.
     *
     * @param val   the JSON event
     * @param event the builder to populate
     * @return the given builder
     * @throws IllegalArgumentException if a property has an invalid value
     */
    public static Event.Builder fromJsonPartial(String val, Event.Builder event) {
        return Json.eventFromMapPartial(Json.mapFrom(val), event);
    }
}
