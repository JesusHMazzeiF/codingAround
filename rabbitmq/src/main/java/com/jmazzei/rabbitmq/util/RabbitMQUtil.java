package com.jmazzei.rabbitmq.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQUtil {

    public static Connection getConnection(String userName, String password,
                                     String host, int port) throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        try {
            Connection connection = connectionFactory.newConnection();
            return connection;
        } catch (Exception e) {
            throw e;
        }
    }

    public static Channel getChannel(Connection connection) throws IOException, TimeoutException {
            try {
                Channel channel = connection.createChannel();
                return channel;
            } catch (Exception e) {
                throw e;
            }
    }

    public static void closeConnections(Connection connection, Channel channel) throws IOException, TimeoutException {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            throw e;
        }
    }

}
