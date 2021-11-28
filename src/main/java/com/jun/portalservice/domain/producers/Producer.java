package com.jun.portalservice.domain.producers;

import com.jun.portalservice.domain.config.TopicConfig;
import jun.message.ReviewMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Producer {

  @Autowired private KafkaTemplate<Long, SpecificRecord> kafkaTemplate;

  public void sendApproveReviewMessage(ReviewMessage message) {
    try {
      log.info("===========================" + message);
      kafkaTemplate.send((TopicConfig.SEND_APPROVED_REIVEW), message);
    } catch (Exception e) {
      log.error(String.valueOf(e));
      throw e;
    }
  }
}
