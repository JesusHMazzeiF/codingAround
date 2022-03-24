package com.jmazzei.rabbitmq.util;

public interface QueuePublisher {

    void publish(String message);
    void connect();

}
