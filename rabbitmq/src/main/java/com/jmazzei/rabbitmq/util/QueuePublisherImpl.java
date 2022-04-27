package com.jmazzei.rabbitmq.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class QueuePublisherImpl implements QueuePublisher {

    String queueName;
    String host;
    int port;
    String userName;
    String password;
    String exchange;

    Logger logger = Logger.getLogger("rabbit-mq-logger");
    private Channel channel;
    private Connection connection;

    public QueuePublisherImpl(String queueName, String exchange, String host, int port, String userName,
                              String password) {
        this.queueName = queueName;
        this.exchange = exchange;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;

    }

    @Override
    public void publish(String content) {
        try {
            channel.basicPublish(exchange, queueName, null, content.getBytes(
                    StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.severe("Could not publish message: " + e);
            return;
        }
        logger.info("[x] Published: ".concat(content));
        return;
    }

    @Override
    public void connect() {
        try {
            channel.queueDeclare(queueName, false, false, false, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe("Error getting channel" + e);
        }
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public void setChannel() {
        try {
            this.channel = RabbitMQUtil.getChannel(getConnection());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public Channel getChannel() {
        return channel;
    }

    public void setConnection() {
        try {
            this.connection = RabbitMQUtil.getConnection(getUserName(), getPassword(), getHost(), getPort());
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
