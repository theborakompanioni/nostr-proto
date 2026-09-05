package org.tbk.jackson.datatype.nostr;

import com.google.protobuf.ByteString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tbk.nostr.identity.Signer;
import org.tbk.nostr.identity.SimpleSigner;
import org.tbk.nostr.proto.*;
import org.tbk.nostr.util.MoreEvents;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.jr.ob.JSON;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class NostrModuleTest {
    private static final Signer testSigner = SimpleSigner.fromPrivateKeyHex("958c7ed568943914f3763e1034883710d8d33eb2ad20b41b0db7babff50a238e");

    private static final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new NostrModule())
            .build();

    @Test
    void itShouldRegisterModuleCorrectly() {
        Assertions.assertThatCollection(objectMapper.registeredModules().stream()
                        .map(it -> it.getClass().getName())
                        .toList())
                .contains("org.tbk.jackson.datatype.nostr.NostrModule");

        Assertions.assertThatCollection(objectMapper.registeredModules().stream()
                        .map(JacksonModule::getModuleName)
                        .toList())
                .contains("NostrModule");
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
    void itShouldDeserializeNostrEvent() {
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

    @Test
    void itShouldSerializeNostrRequest() {
        Request request = Request.newBuilder()
                .setEvent(EventRequest.newBuilder()
                        .setEvent(MoreEvents.withEventId(Event.newBuilder()
                                        .setCreatedAt(1)
                                        .setPubkey(ByteString.fromHex(testSigner.getPublicKey().value.toHex()))
                                        .setKind(1)
                                        .setContent("GM"))
                                .build())
                        .build())
                .build();

        String json = objectMapper.writeValueAsString(request);

        assertThat(JSON.std.anyFrom(json)).isEqualTo(JSON.std.anyFrom("""
                [
                  "EVENT",
                  {
                    "id" : "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                    "pubkey" : "493557ea5445d54298010d895d964e286c5d8fd704ac03823c6ddb0317643cef",
                    "created_at" : 1,
                    "kind" : 1,
                    "tags" : [ ],
                    "content" : "GM",
                    "sig" : ""
                  }
                ]
                """));
    }

    @Test
    void itShouldDeserializeNostrRequest() {
        Request request = Request.newBuilder()
                .setEvent(EventRequest.newBuilder()
                        .setEvent(MoreEvents.withEventId(Event.newBuilder()
                                        .setCreatedAt(1)
                                        .setPubkey(ByteString.fromHex(testSigner.getPublicKey().value.toHex()))
                                        .setKind(1)
                                        .setContent("GM"))
                                .build())
                        .build())
                .build();

        Request parsedRequest = objectMapper.readValue("""
                [
                  "EVENT",
                  {
                    "id" : "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                    "pubkey" : "493557ea5445d54298010d895d964e286c5d8fd704ac03823c6ddb0317643cef",
                    "created_at" : 1,
                    "kind" : 1,
                    "tags" : [ ],
                    "content" : "GM",
                    "sig" : ""
                  }
                ]
                """, Request.class);

        assertThat(parsedRequest).isEqualTo(request);
    }

    @Test
    void itShouldSerializeNostrResponse() {
        Response response = Response.newBuilder()
                .setOk(OkResponse.newBuilder()
                        .setEventId(ByteString.fromHex("40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e"))
                        .setSuccess(true)
                        .setMessage("test")
                        .build())
                .build();

        String json = objectMapper.writeValueAsString(response);

        assertThat(JSON.std.anyFrom(json)).isEqualTo(JSON.std.anyFrom("""
                [
                  "OK",
                  "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                  true,
                  "test"
                ]
                """));
    }

    @Test
    void itShouldDeserializeNostrResponse() {
        Response response = Response.newBuilder()
                .setOk(OkResponse.newBuilder()
                        .setEventId(ByteString.fromHex("40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e"))
                        .setSuccess(true)
                        .setMessage("test")
                        .build())
                .build();

        Response parsedResponse = objectMapper.readValue("""
                [
                  "OK",
                  "40a1d1223bc059a54185c097b4f6f352cf24e27a483fd60d39e635883a09091e",
                  true,
                  "test"
                ]
                """, Response.class);

        assertThat(parsedResponse).isEqualTo(response);
    }

    @Test
    void itShouldSerializeNostrProfileMetadata() {
        ProfileMetadata profileMetadata = ProfileMetadata.newBuilder()
                .setName("name")
                .setDisplayName("displayName")
                .setBot(true)
                .build();

        String json = objectMapper.writeValueAsString(profileMetadata);

        assertThat(JSON.std.anyFrom(json)).isEqualTo(JSON.std.anyFrom("""
                {
                  "name": "name",
                  "display_name": "displayName",
                  "bot": true
                }
                """));
    }

    @Test
    void itShouldDeserializeNostrProfileMetadata() {
        ProfileMetadata profileMetadata = ProfileMetadata.newBuilder()
                .setName("name")
                .setDisplayName("displayName")
                .setBot(true)
                .build();

        ProfileMetadata parsedProfileMetadata = objectMapper.readValue("""
                {
                  "name": "name",
                  "display_name": "displayName",
                  "bot": true
                }
                """, ProfileMetadata.class);

        assertThat(parsedProfileMetadata).isEqualTo(profileMetadata);
    }
}
