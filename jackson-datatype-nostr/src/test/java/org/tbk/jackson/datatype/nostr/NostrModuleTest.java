package org.tbk.jackson.datatype.nostr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.protobuf.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.tbk.nostr.identity.Signer;
import org.tbk.nostr.identity.SimpleSigner;
import org.tbk.nostr.proto.Event;
import org.tbk.nostr.util.MoreEvents;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NostrModuleTest {
    private static final Signer testSigner = SimpleSigner.fromPrivateKeyHex("958c7ed568943914f3763e1034883710d8d33eb2ad20b41b0db7babff50a238e");

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setUp() {
        objectMapper.registerModule(new NostrModule());
    }

    @Test
    void itShouldRegisterModuleCorrectly() {
        Assertions
                .assertThatCollection(objectMapper.getRegisteredModuleIds())
                .contains("org.tbk.jackson.datatype.nostr.NostrModule");
    }

    @Test
    void itShouldSerializeNostrEvent() throws IOException {
        Event event = MoreEvents.withEventId(Event.newBuilder()
                        .setCreatedAt(1)
                        .setPubkey(ByteString.fromHex(testSigner.getPublicKey().value.toHex()))
                        .setKind(1)
                        .setContent("GM"))
                .build();

        String json = objectMapper.writeValueAsString(event);

        assertThat(JSON.std.anyFrom(json)).isEqualTo(JSON.std.anyFrom("""
                {
                  "id": "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                  "pubkey": "493557ea5445d54298010d895d964e286c5d8fd704ac03823c6ddb0317643cef",
                  "created_at": 1,
                  "kind": 1,
                  "tags": [],
                  "content": "GM",
                  "sig": ""
                }
                """));
    }

    @Test
    void itShouldDeserializeNostrEvent() throws JsonProcessingException {
        Event event = MoreEvents.withEventId(Event.newBuilder()
                        .setCreatedAt(1)
                        .setPubkey(ByteString.fromHex(testSigner.getPublicKey().value.toHex()))
                        .setKind(1)
                        .setContent("GM"))
                .build();

        Event parsedEvent = objectMapper.readValue("""
                {
                  "id": "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                  "pubkey": "493557ea5445d54298010d895d964e286c5d8fd704ac03823c6ddb0317643cef",
                  "created_at": 1,
                  "kind": 1,
                  "tags": [],
                  "content": "GM",
                  "sig": ""
                }
                """, Event.class);

        assertThat(parsedEvent).isEqualTo(event);
    }
}
