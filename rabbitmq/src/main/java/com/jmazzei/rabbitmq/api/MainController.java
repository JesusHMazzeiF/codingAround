package com.jmazzei.rabbitmq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmazzei.rabbitmq.util.QueueConsumerImpl;
import com.jmazzei.rabbitmq.util.QueuePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

//@SecurityRequirement(name = "Auth_Bearer")
@RestController
@RequestMapping(path = "/rabbit-mq")
public class MainController {
    Logger logger = Logger.getLogger("rabbit-mq-logger");
    @Autowired
    QueuePublisher queuePublisher;

    @PostMapping
    public @ResponseBody ResponseEntity<String> postMessageToQueue(
            @RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        try {
            queuePublisher.publish(new ObjectMapper().writeValueAsString(body));
            return ResponseEntity.status(HttpStatus.OK).body(new String("Message published"));
        } catch (Exception e) {
            logger.severe("Exception trying to publish message: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new String("Whoopsie"));
        }
    }

    @PostMapping(path = "/batch")
    public @ResponseBody ResponseEntity<String> postBatchMessageToQueue(
            @RequestBody List<Map<String, Object>> batch, HttpServletRequest request) throws Exception {

        for(Map<String, Object> body: batch){
            try {
                queuePublisher.publish(new ObjectMapper().writeValueAsString(body));
            } catch (Exception e) {
                e.printStackTrace();
                logger.severe("Exception trying to publish message: " + e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                                     body(new String("Error posting messages to queue"));
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new String("All messages published"));
    }

}
