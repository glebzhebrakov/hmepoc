package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import hme.poc.hmepoc.dto.TestMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate

abstract class AbstractSpammer {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractSpammer)
    protected final static Faker faker = new Faker()

    protected final KafkaTemplate<String, byte[]> kafkaTemplate
    protected final ObjectMapper objectMapper

    AbstractSpammer(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate
        this.objectMapper = objectMapper
    }

    abstract void spam()

    protected static TestMessage buildTestMessage() {
        new TestMessage().with {
            timestamp = System.currentTimeMillis()
            id = UUID.randomUUID().toString()
            payload = faker.chuckNorris().fact()
            it
        }
    }
}
