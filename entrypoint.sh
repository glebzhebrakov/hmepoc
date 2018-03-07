#!/bin/bash

CMD="java \
    -server \
    -XX:+UnlockExperimentalVMOptions \
    -XX:+UseCGroupMemoryLimitForHeap \
    -XX:MinHeapFreeRatio=20 \
    -XX:MaxHeapFreeRatio=40 \
    -XX:GCTimeRatio=4 \
    -XX:AdaptiveSizePolicyWeight=90
    -Dspring.profiles.active=kafka \
    $*"

echo ${CMD}
exec ${CMD}
