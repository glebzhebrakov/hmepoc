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
        def messages = testMessagesProvider.realMessages
        logger.info('send messages to azure')
        messages.each {
            eventHubClient.sendSync(EventData.create(it))
        }
//        messages.collate(7).each { chank ->
//            eventHubClient.sendSync(chank.collect {EventData.create(it)})
//        }
//        messages.collect {EventData.create(it)}.each {
//            eventHubClient.sendSync(it)
//        }

//        eventHubClient.send
//        messages.parallelStream().forEach{
//            eventHubClient.sendSync(EventData.create(it))
//        }
    }
}
