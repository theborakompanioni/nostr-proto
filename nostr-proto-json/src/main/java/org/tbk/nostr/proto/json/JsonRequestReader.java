package org.tbk.nostr.proto.json;

import org.tbk.nostr.proto.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

final class JsonRequestReader {

    private JsonRequestReader() {
        throw new UnsupportedOperationException();
    }

    static Request fromJson(String val, Request.Builder request) {
        Object[] array = Json.arrayFrom(val);
        if (array.length < 1) {
            throw new IllegalArgumentException("Could not parse passed arg");
        }
        if (array[0] instanceof String kindVal) {
            Request.KindCase kindCase = Request.KindCase.valueOf(kindVal);

            return switch (kindCase) {
                // ["EVENT", <event JSON as defined above>]
                case EVENT -> {
                    if (array.length < 2) {
                        throw new IllegalArgumentException("Could not parse passed arg");
                    }
                    Map<String, Object> eventMap = Json.asJsonObject(array[1], "event");
                    yield request.setEvent(EventRequest.newBuilder()
                                    .setEvent(Json.eventFromMap(eventMap, Event.newBuilder()))
                                    .build())
                            .build();
                }
                // ["REQ", <subscription_id>, <filters1>, <filters2>, ...]
                case REQ -> {
                    if (array.length < 3) {
                        throw new IllegalArgumentException("Could not parse passed arg");
                    }

                    String subscriptionId = String.valueOf(array[1]);

                    yield request.setReq(ReqRequest.newBuilder()
                                    .setId(subscriptionId)
                                    .addAllFilters(filtersFromArray(array))
                                    .build())
                            .build();
                }
                // ["CLOSE", <subscription_id>]
                case CLOSE -> {
                    if (array.length < 2) {
                        throw new IllegalArgumentException("Could not parse passed arg");
                    }
                    String subscriptionId = String.valueOf(array[1]);

                    yield request.setClose(CloseRequest.newBuilder()
                                    .setId(subscriptionId)
                                    .build())
                            .build();
                }
                // ["COUNT", <subscription_id>, <filters1>, <filters2>, ...]
                case COUNT -> {
                    if (array.length < 3) {
                        throw new IllegalArgumentException("Could not parse passed arg");
                    }
                    String subscriptionId = String.valueOf(array[1]);

                    yield request.setCount(CountRequest.newBuilder()
                                    .setId(subscriptionId)
                                    .addAllFilters(filtersFromArray(array))
                                    .build())
                            .build();
                }
                case AUTH -> {
                    if (array.length < 2) {
                        throw new IllegalArgumentException("Could not parse passed arg");
                    }

                    Map<String, Object> eventMap = Json.asJsonObject(array[1], "event");
                    yield request.setAuth(AuthRequest.newBuilder()
                                    .setEvent(Json.eventFromMap(eventMap, Event.newBuilder()))
                                    .build())
                            .build();
                }
                case KIND_NOT_SET -> throw new IllegalArgumentException("Kind not set");
            };
        } else {
            throw new IllegalArgumentException("Could not parse passed arg");
        }
    }

    private static List<Filter> filtersFromArray(Object[] array) {
        return Stream.of(array).skip(2)
                .map(it -> Json.asJsonObject(it, "filter"))
                .distinct()
                .map(it -> Json.filterFromMap(it, Filter.newBuilder()))
                .distinct()
                .toList();
    }
}
