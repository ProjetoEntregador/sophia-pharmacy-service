package com.sophia.sophia_pharmacy_service.message;

import java.io.IOException;

import com.rabbitmq.client.Channel;

import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingRequest;
import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessingConsumer {
  private final ProcessingPublisher publisher;

  @RabbitListener(queues = "pharmacy.incoming.queue")
  public void consume(ProcessingRequest request, Message message, Channel channel) throws IOException {
    long deliveryTag = message.getMessageProperties().getDeliveryTag();

    try {
      System.out.println("Processing job: " + request.getJobId());
      System.out.println("Processing job: " + request.getType());

      // PROCESS

      ProcessingResponse response = new ProcessingResponse();

      response.setJobId(request.getJobId());
      response.setStatus("SUCCESS");
      
      publisher.publishResponse(response);

      channel.basicAck(deliveryTag, false);
    } catch (Exception e) {
      System.out.println("Processing failed");

      channel.basicNack(deliveryTag, false, true);
    }
  }
}