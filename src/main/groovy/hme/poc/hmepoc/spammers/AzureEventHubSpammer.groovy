package hme.poc.hmepoc.spammers

import com.fasterxml.jackson.databind.ObjectMapper
import com.microsoft.azure.eventhubs.EventData
import com.microsoft.azure.eventhubs.EventHubClient
import hme.poc.hmepoc.provider.TestMessagesProvider

class AzureEventHubSpammer extends AbstractSpammer {

    private final EventHubClient eventHubClient


    AzureEventHubSpammer(EventHubClient eventHubClient, TestMessagesProvider testMessagesProvider, ObjectMapper objectMapper) {
        super(testMessagesProvider, objectMapper)
        this.eventHubClient = eventHubClient
    }

    @Override
    void spam() {
        def messages = testMessagesProvider.messages
        logger.info('send messages to azure')
        eventHubClient.sendSync(messages.collect {EventData.create(it)})
//        eventHubClient.send
//        messages.parallelStream().forEach{
//            eventHubClient.sendSync(EventData.create(it))
//        }
    }
}
