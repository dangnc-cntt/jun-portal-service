package com.jun.portalservice.domain.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
  //  public static final String CALCULATOR_POINT_PER_VOTE_TOPIC = "wiinvent_calculator_per_vote";
  //
  //  @Value("${spring.kafka.topic.replication-factor}")
  //  private short replicationFactor;
  //
  //  @Value("${spring.kafka.topic.num-partitions}")
  //  private short numPartitions;
  //
  //  private static Map<String, String> defaultConfigs = new HashMap<>();
  //
  //  static {
  //    defaultConfigs.put("retention.ms", "604800000"); // 7 day
  //  }
  //
  //  @Bean
  //  public NewTopic createCalculatorPointPerVote() {
  //    NewTopic topic =
  //        new NewTopic(CALCULATOR_POINT_PER_VOTE_TOPIC, numPartitions, replicationFactor);
  //    topic.configs(defaultConfigs);
  //    return topic;
  //  }
}
