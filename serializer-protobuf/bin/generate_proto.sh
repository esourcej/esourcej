#!/usr/bin/env bash

docker run --rm -v $(PWD):/data znly/protoc --java_out=/data/src/test/java/ \
    -I /data/src/main/resources/ \
    /data/src/main/resources/fixture.proto