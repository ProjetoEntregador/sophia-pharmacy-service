package com.sophia.sophia_pharmacy_service.message;

import java.io.IOException;

import com.rabbitmq.client.Channel;

import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingRequest;
import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

    @Slf4j
    @Component
    public class ProcessingConsumer {

      @Autowired
      ProcessingPublisher publisher;

      @Autowired
      PharmacyOrchestratorService orchestratorService;

      @RabbitListener(queues = "${rabbitmq.queue.incoming}")
      public void consume(ProcessingRequest request, Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();

        try {
          log.info("Processing request {}", request.getJobId());

          ProcessingResponse response = orchestratorService.process(request);

          publisher.publishResponse(response);

          channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
          log.error("Processing failed", e);

          channel.basicNack(deliveryTag, false, false);
        }
      }
}