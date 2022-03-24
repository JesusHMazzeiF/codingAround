package com.jmazzei.rabbitmq;

import com.jmazzei.rabbitmq.util.QueueConsumerImpl;
import com.jmazzei.rabbitmq.util.QueuePublisherImpl;
import com.jmazzei.rabbitmq.util.RabbitMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class RabbitmqApplication {

	@Autowired
	Environment environment;

	@Autowired
	QueueConsumerImpl queueConsumer;

	@Autowired
	QueueConsumerImpl queuePublisher;

	@Bean
	public QueueConsumerImpl queueConsumer(){
		QueueConsumerImpl queueConsumer = new QueueConsumerImpl(environment.getProperty("queue.name", "my-queue"),
									 environment.getProperty("rabbit.host", "localhost"),
									 Integer.valueOf(environment.getProperty("rabbit.port", "5732")),
									 environment.getProperty("rabbit.user", "guest"),
									 environment.getProperty("rabbit.password", "guest"));
		queueConsumer.setConnection();
		queueConsumer.setChannel();
		queueConsumer.connect();
		queueConsumer.listen();
		return queueConsumer;
	}

	@Bean
	public QueuePublisherImpl queuePublisher(){
		QueuePublisherImpl queuePublisher = new QueuePublisherImpl(environment.getProperty("queue.name", "my-queue"),
									  environment.getProperty("queue.exchange", "my-queue"),
									 environment.getProperty("rabbit.host", "localhost"),
									 Integer.valueOf(environment.getProperty("rabbit.port", "5732")),
									 environment.getProperty("rabbit.user", "guest"),
									 environment.getProperty("rabbit.password", "guest"));
		queuePublisher.setConnection();
		queuePublisher.setChannel();
		queuePublisher.connect();
		return queuePublisher;
	}

	@PreDestroy
	public void shutdown() {
		try {
			RabbitMQUtil.closeConnections(queueConsumer.getConnection(), queueConsumer.getChannel());
			RabbitMQUtil.closeConnections(queuePublisher.getConnection(), queuePublisher.getChannel());
		} catch (Exception ignored) {}

	}


	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}

}
