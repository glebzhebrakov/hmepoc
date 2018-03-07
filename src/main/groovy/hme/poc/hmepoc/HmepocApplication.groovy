package hme.poc.hmepoc

import com.fasterxml.jackson.databind.ObjectMapper
import hme.poc.hmepoc.provider.TestMessagesProvider
import hme.poc.hmepoc.spammers.AbstractSpammer
import hme.poc.hmepoc.spammers.KafkaSpammer
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import hme.poc.hmepoc.spammers.RabbitMQSpammer
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@SpringBootApplication
@EnableScheduling
class HmepocApplication {

	static void main(String[] args) {
		SpringApplication.run HmepocApplication, args
	}

	@Value('${kafka.bootstrap-servers}')
	private String bootstrapServers;

//	@Bean
//	public Map<String, Object> producerConfigs() {
//		Map<String, Object> props = new HashMap<>();
//		// list of host:port pairs used for establishing the initial connections to the Kakfa cluster
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//				bootstrapServers);
//
//		return props;
//	}
//
//	@Bean
//	public ProducerFactory<String, byte[]> producerFactory() {
//		return new DefaultKafkaProducerFactory<>(producerConfigs());
//	}
//
//	@Bean
//	public KafkaTemplate<String, byte[]> kafkaTemplate() {
//		return new KafkaTemplate<>(producerFactory());
//	}

    @Profile('kafka')
	@Bean
	AbstractSpammer kafkaSpammer(final TestMessagesProvider testMessagesProvider,
							final KafkaTemplate<String, byte[]> kafkaTemplate,
							final ObjectMapper objectMapper ) {
		new KafkaSpammer(kafkaTemplate, objectMapper, testMessagesProvider)
	}

	@Bean
	RabbitTemplate rabbitTemplate(@Value('${rabbit.queueName}') String queueName, ConnectionFactory connectionFactory ) {
		def rabbitTemplate = new RabbitTemplate(connectionFactory)
		rabbitTemplate.setQueue(queueName)
		rabbitTemplate
	}

	@Profile('rabbitmq')
	@Bean
	AbstractSpammer rabbitSpammer(@Value('${rabbit.queueName}') String queueName,
								  final TestMessagesProvider testMessagesProvider,
							final RabbitTemplate rabbitTemplate,
							final ObjectMapper objectMapper ) {
		new RabbitMQSpammer(queueName, rabbitTemplate, testMessagesProvider, objectMapper)
	}


	@Bean
	Queue queue(@Value('${rabbit.queueName}') String queueName) {
		return new Queue(queueName, false)
	}

	@Bean
	TopicExchange exchange(@Value('${rabbit.exchangeName}') String exchangeName) {
		new TopicExchange(exchangeName)
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange, @Value('${rabbit.queueName}') String queueName) {
		BindingBuilder.bind(queue).to(exchange).with(queueName)
	}

}
