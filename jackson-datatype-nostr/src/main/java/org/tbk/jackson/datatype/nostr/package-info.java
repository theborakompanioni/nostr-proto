/**
 * Jackson support for the nostr-proto types.
 *
 * <p>{@link NostrModule} registers serializers and deserializers for {@link org.tbk.nostr.proto.Event},
 * {@link org.tbk.nostr.proto.Request}, {@link org.tbk.nostr.proto.Response} and
 * {@link org.tbk.nostr.proto.ProfileMetadata}. Conversion is delegated to
 * {@code org.tbk.nostr.proto.json.JsonReader} and {@code org.tbk.nostr.proto.json.JsonWriter}.
 *
 * <pre>{@code
 * ObjectMapper mapper = JsonMapper.builder()
 *         .addModule(new NostrModule())
 *         .build();
 *
 * String json = mapper.writeValueAsString(event);
 * Event parsed = mapper.readValue(json, Event.class);
 * }</pre>
 *
 * @see org.tbk.nostr.proto.json.JsonReader
 * @see org.tbk.nostr.proto.json.JsonWriter
 * @see <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
 */
package org.tbk.jackson.datatype.nostr;
