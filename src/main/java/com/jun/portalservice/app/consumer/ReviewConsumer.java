package com.jun.portalservice.app.consumer;

import com.jun.portalservice.domain.config.TopicConfig;
import com.jun.portalservice.domain.services.ReviewService;
import jun.message.ReviewMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class ReviewConsumer {
  @Autowired private ReviewService reviewService;

  @KafkaListener(topics = TopicConfig.SEND_REVIEW)
  public void processsSave(List<ReviewMessage> messages, Acknowledgment acknowledgment) {
    log.info("==========" + messages);
    reviewService.processSaveReview(messages);
    acknowledgment.acknowledge();
  }

  @KafkaListener(topics = TopicConfig.SEND_APPROVED_REIVEW)
  public void approvedState(List<ReviewMessage> messages, Acknowledgment acknowledgment) {
    log.info("==========" + messages);
    reviewService.processApprovedState(messages);
    acknowledgment.acknowledge();
  }
}
