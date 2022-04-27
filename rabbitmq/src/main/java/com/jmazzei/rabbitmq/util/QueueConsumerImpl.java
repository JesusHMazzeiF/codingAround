package com.jmazzei.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class QueueConsumerImpl implements QueueConsumer{

    String queueName;
    String host;
    int port;
    String userName;
    String password;

    Logger logger = Logger.getLogger("rabbit-mq-logger");
    private Channel channel;
    private Connection connection;

    DeliverCallback process = (consumerTag, delivery) -> {
        String message = new String(delivery.getBody(), "UTF-8");
        logger.info("[x] Received message: " + message);
    };

    public QueueConsumerImpl(String queueName, String host, int port, String userName, String password) {
        this.queueName = queueName;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
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

    @Override
    public void listen() {
        try {
            channel.basicConsume(queueName, true, process, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
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

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
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
