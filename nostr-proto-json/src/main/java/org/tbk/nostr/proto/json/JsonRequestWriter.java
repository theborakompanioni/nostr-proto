package org.tbk.nostr.proto.json;

import com.google.protobuf.Descriptors.FieldDescriptor;
import org.tbk.nostr.proto.*;
import tools.jackson.jr.ob.JSONComposer;
import tools.jackson.jr.ob.comp.ArrayComposer;
import tools.jackson.jr.ob.comp.ObjectComposer;

import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import static org.tbk.nostr.proto.json.Json.json;
import static org.tbk.nostr.proto.json.Json.jsonForSigning;

final class JsonRequestWriter {

    private JsonRequestWriter() {
        throw new UnsupportedOperationException();
    }

    static String toJson(Request val) {
        return switch (val.getKindCase()) {
            case EVENT -> toJson(val.getEvent());
            case REQ -> toJson(val.getReq());
            case CLOSE -> toJson(val.getClose());
            case COUNT -> toJson(val.getCount());
            case AUTH -> toJson(val.getAuth());
            case KIND_NOT_SET -> throw new IllegalArgumentException("Kind not set");
        };
    }

    static String toJson(ProfileMetadata val) {
        ObjectComposer<JSONComposer<String>> builder = json
                .composeString()
                .startObject();

        Map<FieldDescriptor, Object> allFields = val.getAllFields();
        for (Map.Entry<FieldDescriptor, Object> entry : allFields.entrySet()) {
            builder.putObject(entry.getKey().getJsonName(), entry.getValue());
        }
        return builder.end().finish();
    }

    private static String toJson(CloseRequest val) {
        return json
                .composeString()
                .startArray()
                .add("CLOSE")
                .add(val.getId())
                .end()
                .finish();
    }

    private static String toJson(EventRequest val) {
        return toJsonWithEvent("EVENT", val.getEvent());
    }

    private static String toJson(AuthRequest val) {
        return toJsonWithEvent("AUTH", val.getEvent());
    }

    private static String toJson(ReqRequest val) {
        return toJsonWithSubscriptionIdAndFilter("REQ", val.getId(), val.getFiltersList());
    }

    private static String toJson(CountRequest val) {
        return toJsonWithSubscriptionIdAndFilter("COUNT", val.getId(), val.getFiltersList());
    }

    /**
     * Serializes the given event as JSON.
     *
     * @param event an event
     * @return given event serialized as json
     */
    // @VisibleForTesting
    static String toJson(Event event) {
        return json.asString(Json.eventAsMap(event));
    }

    // @VisibleForTesting
    static String toJson(Filter filter) {
        return json.asString(Json.filterAsMap(filter));
    }

    /**
     * See: <a href="https://github.com/nostr-protocol/nips/blob/master/01.md">NIP-01</a>.
     *
     * <p>
     * <code>
     * [
     * 0,
     * &lt;pubkey, as a lowercase hex string&gt;,
     * &lt;created_at, as a number&gt;,
     * &lt;kind, as a number&gt;,
     * &lt;tags, as an array of arrays of non-null strings&gt;,
     * &lt;content, as a string&gt;
     * ]
     * </code>
     */
    static String toJsonForSigning(EventOrBuilder e) {
        return jsonForSigning.composeString()
                .startArray()
                .add(0)
                .add(HexFormat.of().formatHex(e.getPubkey().toByteArray()))
                .add(e.getCreatedAt())
                .add(e.getKind())
                .addPOJO(Json.listFromTags(e.getTagsList()))
                .add(e.getContent())
                .end()
                .finish();
    }

    private static String toJsonWithEvent(String cmd, Event event) {
        return json
                .composeString()
                .startArray()
                .add(cmd)
                .addPOJO(Json.eventAsMap(event))
                .end()
                .finish();
    }

    private static String toJsonWithSubscriptionIdAndFilter(String cmd, String subscriptionId, List<Filter> filters) {
        ArrayComposer<JSONComposer<String>> arrayComposer = json
                .composeString()
                .startArray()
                .add(cmd)
                .add(subscriptionId);

        for (Filter it : filters) {
            arrayComposer.addPOJO(Json.filterAsMap(it));
        }
        return arrayComposer.end().finish();
    }
}
