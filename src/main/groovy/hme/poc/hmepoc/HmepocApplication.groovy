package hme.poc.hmepoc

import com.fasterxml.jackson.databind.ObjectMapper
import hme.poc.hmepoc.provider.TestMessagesProvider
import hme.poc.hmepoc.spammers.AbstractSpammer
import hme.poc.hmepoc.spammers.KafkaSpammer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
class HmepocApplication {

	static void main(String[] args) {
		SpringApplication.run HmepocApplication, args
	}

	@Bean
	AbstractSpammer spammer(final TestMessagesProvider testMessagesProvider,
							final KafkaTemplate<String, byte[]> kafkaTemplate,
							final ObjectMapper objectMapper ) {
		new KafkaSpammer(kafkaTemplate, objectMapper, testMessagesProvider)
	}

}
