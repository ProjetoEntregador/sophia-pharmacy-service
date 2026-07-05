package com.sophia.sophia_pharmacy_service.message;

import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ProcessingPublisher {
  @Autowired
  RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.routing.outgoing}")
  private String responseRouting;

  @Value("${rabbitmq.routing.audit}")
  private String auditRouting;

  public void publishResponse(ProcessingResponse response) {

    rabbitTemplate.convertAndSend(exchange, responseRouting, response);

    log.info("Response Published");
  }

  public void publishAudit(String response) {

    rabbitTemplate.convertAndSend(exchange, auditRouting, response);

    log.info("Audit Published");
  }
}