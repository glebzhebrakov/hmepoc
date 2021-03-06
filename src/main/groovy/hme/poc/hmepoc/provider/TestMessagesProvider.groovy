package hme.poc.hmepoc.provider

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.javafaker.Faker
import hme.poc.hmepoc.dto.TestMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TestMessagesProvider {

    protected final static Logger logger = LoggerFactory.getLogger(TestMessagesProvider)
    private static List<byte[]> messages
    protected final static Faker faker = new Faker()
    private final ObjectMapper objectMapper

    TestMessagesProvider( @Value('${spammer.numOfMessages}') final Integer messagesCount,
                         final ObjectMapper objectMapper ) {
        this.objectMapper = objectMapper
        logger.info("build $messagesCount messages")
        messages = (1..messagesCount).collect {
            this.objectMapper.writeValueAsBytes( buildTestMessage())
        }
    }

    List<byte[]> getMessages() {
        messages
    }

    protected static TestMessage buildTestMessage() {
        new TestMessage().with {
            timestamp = System.currentTimeMillis()
            id = UUID.randomUUID().toString()
            payload = faker.chuckNorris().fact()
            it
        }
    }

}
