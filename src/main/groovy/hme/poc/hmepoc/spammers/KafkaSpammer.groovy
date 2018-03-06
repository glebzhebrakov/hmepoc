package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import hme.poc.hmepoc.provider.TestMessagesProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async

class KafkaSpammer extends AbstractSpammer {

    @Value('${kafka.topic.poc}')
    private String topic

    protected final KafkaTemplate<String, byte[]> kafkaTemplate

    KafkaSpammer(final KafkaTemplate<String, byte[]> kafkaTemplate,
                 final ObjectMapper objectMapper,
                 final TestMessagesProvider testMessagesProvider) {
        super(testMessagesProvider, objectMapper)
        this.kafkaTemplate = kafkaTemplate
    }

    @Async
    @Override
    void spam() {
        def messages = testMessagesProvider.getMessages()
        logger.info("sending $messages.size() messages")
        messages.parallelStream().forEach(){
            kafkaTemplate.send(topic, it )
        }
        logger.info("$messages.size() messages sent")
    }
}
