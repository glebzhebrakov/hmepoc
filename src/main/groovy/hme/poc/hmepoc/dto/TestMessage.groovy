package hme.poc.hmepoc.dto

import groovy.transform.Canonical

@Canonical
class TestMessage {
    String payload
    String id
    long timestamp
}
