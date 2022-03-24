package com.jmazzei.rabbitmq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmazzei.rabbitmq.util.QueueConsumerImpl;
import com.jmazzei.rabbitmq.util.QueuePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

//@SecurityRequirement(name = "Auth_Bearer")
@RestController
@RequestMapping(path = "/rabbit-mq")
public class MainController {


    @Autowired
    QueuePublisher queuePublisher;

    @PostMapping
    public @ResponseBody ResponseEntity<String> postMessageToQueue(
            @RequestBody Map<String, Object> body, HttpServletRequest request) throws Exception {
        try {
            queuePublisher.publish(new ObjectMapper().writeValueAsString(body));
            return ResponseEntity.status(HttpStatus.OK).body(new String("Message published"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new String("Whoopsie"));
        }
    }



}
