package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import hme.poc.hmepoc.dto.TestMessage
import hme.poc.hmepoc.provider.TestMessagesProvider
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate

abstract class AbstractSpammer {

    protected final static Logger logger = LoggerFactory.getLogger(AbstractSpammer)

    protected final TestMessagesProvider testMessagesProvider
    protected final ObjectMapper objectMapper

    AbstractSpammer(TestMessagesProvider testMessagesProvider, ObjectMapper objectMapper) {
        this.testMessagesProvider = testMessagesProvider
        this.objectMapper = objectMapper
    }

    abstract void spam()
}
