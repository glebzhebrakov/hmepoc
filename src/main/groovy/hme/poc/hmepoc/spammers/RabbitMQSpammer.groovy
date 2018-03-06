package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import hme.poc.hmepoc.provider.TestMessagesProvider
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value

class RabbitMQSpammer extends AbstractSpammer {

    final RabbitTemplate rabbitTemplate
    String queueName

    RabbitMQSpammer(final String queueName, final RabbitTemplate rabbitTemplate, TestMessagesProvider testMessagesProvider, ObjectMapper objectMapper) {
        super(testMessagesProvider, objectMapper)
        this.rabbitTemplate = rabbitTemplate
        this.queueName = queueName
    }

    @Override
    void spam() {
        def messages = testMessagesProvider.getMessages()
        logger.info("sending $messages.size() messages")
        messages.parallelStream().forEach(){
            rabbitTemplate.convertAndSend(queueName,it)
//            rabbitTemplate.send(queueName, new Message(it, new MessageProperties()))
        }
        logger.info("$messages.size() messages sent")

    }
}
