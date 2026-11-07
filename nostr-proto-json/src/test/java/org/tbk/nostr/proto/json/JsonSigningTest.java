package org.tbk.nostr.proto.json;

import com.google.protobuf.ByteString;
import org.junit.jupiter.api.Test;
import org.tbk.nostr.identity.Signer;
import org.tbk.nostr.identity.SimpleSigner;
import org.tbk.nostr.proto.Event;
import org.tbk.nostr.proto.TagValue;
import org.tbk.nostr.util.MoreEvents;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class JsonSigningTest {
    private static final Signer testSigner = SimpleSigner.fromPrivateKeyHex("958c7ed568943914f3763e1034883710d8d33eb2ad20b41b0db7babff50a238e");

    @Test
    void itShouldSerializeSimpleEventForSigning() {
        Event event = newEvent("GM");

        String json = JsonWriter.toJsonForSigning(event);

        assertThat(json, is("""
                [0,"%s",1,1,[],"GM"]""".formatted(testSigner.getPublicKey().value.toHex())));
        assertThat(sha256Hex(json), is(HexFormat.of().formatHex(event.getId().toByteArray())));
    }

    @Test
    void itShouldSerializeEventWithTagsForSigning() {
        Event event = MoreEvents.finalize(testSigner, Event.newBuilder()
                .setCreatedAt(1)
                .setPubkey(pubkey())
                .setKind(1)
                .addTags(TagValue.newBuilder().setName("e").addValues("event-id").build())
                .addTags(TagValue.newBuilder().setName("p").addValues("pubkey-1").addValues("pubkey-2").build())
                .addTags(TagValue.newBuilder().setName("t").build())
                .addTags(TagValue.newBuilder().setName("a").addValues("30023:pubkey:identifier").addValues("relay").build())
                .setContent("GM"));

        assertThat(sha256Hex(JsonWriter.toJsonForSigning(event)), is(HexFormat.of().formatHex(event.getId().toByteArray())));
    }

    @Test
    void itShouldSerializeEventWithSpecialCharactersForSigning() {
        String content = "He said: \"Hello\" \\ backslash\nnewline\ttab\rcarriage return unicode: äöü € 😀";
        Event event = newEvent(content);

        assertThat(sha256Hex(JsonWriter.toJsonForSigning(event)), is(HexFormat.of().formatHex(event.getId().toByteArray())));
    }

    @Test
    void itShouldSerializeEventWithSpecialCharactersInTagsForSigning() {
        Event event = MoreEvents.finalize(testSigner, Event.newBuilder()
                .setCreatedAt(1)
                .setPubkey(pubkey())
                .setKind(1)
                .addTags(TagValue.newBuilder()
                        .setName("t")
                        .addAllValues(List.of("quote\"", "backslash\\", "newline\n", "tab\t", "unicode: äöü 😀"))
                        .build())
                .setContent("GM"));

        assertThat(sha256Hex(JsonWriter.toJsonForSigning(event)), is(HexFormat.of().formatHex(event.getId().toByteArray())));
    }

    @Test
    void itShouldSerializeEventWithEmptyContentAndTagsForSigning() {
        Event event = MoreEvents.finalize(testSigner, Event.newBuilder()
                .setCreatedAt(0)
                .setPubkey(pubkey())
                .setKind(0)
                .setContent(""));

        assertThat(sha256Hex(JsonWriter.toJsonForSigning(event)), is(HexFormat.of().formatHex(event.getId().toByteArray())));
    }

    private static Event newEvent(String content) {
        return MoreEvents.finalize(testSigner, Event.newBuilder()
                .setCreatedAt(1)
                .setPubkey(pubkey())
                .setKind(1)
                .setContent(content));
    }

    private static ByteString pubkey() {
        return ByteString.fromHex(testSigner.getPublicKey().value.toHex());
    }

    private static String sha256Hex(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }
}
