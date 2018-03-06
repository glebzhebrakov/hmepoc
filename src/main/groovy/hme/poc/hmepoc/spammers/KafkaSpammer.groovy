package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async

class KafkaSpammer extends AbstractSpammer {


    @Value('${spammer.numOfMessages}')
    private Integer numOfMessages

    @Value('${kafka.topic.poc}')
    private String topic


    KafkaSpammer(KafkaTemplate<String, byte[]> kafkaTemplate, ObjectMapper objectMapper) {
        super(kafkaTemplate, objectMapper)
    }

    @Async
    @Override
    void spam() {
        logger.info("build $numOfMessages messages")
        def messages = (1..numOfMessages).collect{
            objectMapper.writeValueAsBytes( buildTestMessage() )
        } as List<byte[]>

        logger.info("sending $numOfMessages messages")
        messages.forEach{
            kafkaTemplate.send(topic, it )
        }
        logger.info("$numOfMessages messages built and sent")
    }
}
