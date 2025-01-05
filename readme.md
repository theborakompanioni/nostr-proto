[![Build](https://github.com/theborakompanioni/nostr-proto/actions/workflows/build.yml/badge.svg)](https://github.com/theborakompanioni/nostr-proto/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/release/theborakompanioni/nostr-proto.svg?maxAge=3600)](https://github.com/theborakompanioni/nostr-proto/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.theborakompanioni/nostr-proto.svg?maxAge=3600)](https://central.sonatype.com/artifact/io.github.theborakompanioni/nostr-proto)
[![License](https://img.shields.io/github/license/theborakompanioni/nostr-proto.svg?maxAge=2592000)](https://github.com/theborakompanioni/nostr-proto/blob/master/LICENSE)


<p align="center">
    <img src="https://github.com/theborakompanioni/nostr-proto/blob/master/docs/assets/images/logo.png" alt="Logo" width="255" />
</p>


nostr-proto
===

See [nostr-proto](./nostr/nostr-proto/src/main/proto/event.proto) for protobuf definitions of core nostr messages.

```protobuf
message Event {
  bytes id = 1 [json_name = "id"];
  bytes pubkey = 2 [json_name = "pubkey"];
  uint64 created_at = 3 [json_name = "created_at"];
  uint32 kind = 4 [json_name = "kind"];
  repeated TagValue tags = 5 [json_name = "tags"];
  string content = 6 [json_name = "content"];
  bytes sig = 7 [json_name = "sig"];
}

[...]
```


## Table of Contents

- [Install](#install)
- [Development](#development)
- [Contributing](#contributing)
- [Resources](#resources)
- [License](#license)


## Install

[Download](https://search.maven.org/#search|g%3A%22io.github.theborakompanioni%22) from Maven Central.

### Gradle
```groovy
repositories {
    mavenCentral()
}
```

```groovy
implementation "io.github.theborakompanioni:nostr-proto:${nostrProtoVersion}"
implementation "io.github.theborakompanioni:nostr-proto-json:${nostrProtoVersion}"
```

## Development

### Requirements
- java >=21

### Build
```shell script
./gradlew build -x test
```

### Test
```shell script
./gradlew test integrationTest --rerun-tasks --no-parallel
```

Run full test suite:
```shell script
CI=true ./gradlew test integrationTest e2eTest --rerun-tasks --no-parallel
```

## Contributing
All contributions and ideas are always welcome. For any question, bug or feature request,
please create an [issue](https://github.com/theborakompanioni/nostr-proto/issues).
Before you start, please read the [contributing guidelines](contributing.md).

## Resources

- nostr (GitHub): https://github.com/nostr-protocol/nostr
- NIPs (GitHub): https://github.com/nostr-protocol/nips

---

- nostr.com: https://nostr.com
- nostr Relay Registry: https://nostr-registry.netlify.app
- awesome-nostr (GitHub): https://github.com/aljazceru/awesome-nostr
- protocol-buffers: https://developers.google.com/protocol-buffers/docs/proto3#json

## License

The project is licensed under the Apache License. See [LICENSE](LICENSE) for details.
