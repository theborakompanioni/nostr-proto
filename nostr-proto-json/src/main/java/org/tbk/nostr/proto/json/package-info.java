/**
 * JSON support for the protobuf messages in {@code org.tbk.nostr.proto}.
 *
 * <p>{@link org.tbk.nostr.proto.json.JsonReader} reads NIP-01 client and relay messages and
 * {@link org.tbk.nostr.proto.json.JsonWriter} writes them. Both operate on the generated protobuf
 * types, so no additional model classes are needed.
 *
 * @see <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>
 */
package org.tbk.nostr.proto.json;
