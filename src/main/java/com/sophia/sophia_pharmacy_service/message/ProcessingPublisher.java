package com.sophia.sophia_pharmacy_service.message;

import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessingPublisher {
  private final RabbitTemplate rabbitTemplate;

  @Value("${rabbitmq.exchange}")
  private String exchange;

  @Value("${rabbitmq.routing.outgoing}")
  private String responseRouting;

  public void publishResponse(ProcessingResponse response) {
    rabbitTemplate.convertAndSend(exchange, responseRouting, response);

    System.out.println("Response published");
  }
}