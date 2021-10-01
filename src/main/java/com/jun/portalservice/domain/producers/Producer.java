package com.jun.portalservice.domain.producers;

import lombok.extern.log4j.Log4j2;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Producer {

  @Autowired private KafkaTemplate<Long, SpecificRecord> kafkaTemplate;
}
