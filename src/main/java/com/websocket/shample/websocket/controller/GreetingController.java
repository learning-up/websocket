package com.websocket.shample.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.websocket.shample.websocket.model.Greeting;
import com.websocket.shample.websocket.model.HelloMessage;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate template;
    
    @RequestMapping("/helloSocket")    
    public String index(){        
        return "index";    
    }    

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        return new Greeting("Hello, " + message.getName() + "! -- by server1");
    }
    
    @RequestMapping("/hello2")
    //@SendTo("/topic/greetings")
    @ResponseBody
    public HelloMessage greeting2(HelloMessage message) throws Exception {
        template.convertAndSend("/topic/greetings", new Greeting("Hello, " + message.getName() + "! -- by server2"));
        return message;
    }

}
