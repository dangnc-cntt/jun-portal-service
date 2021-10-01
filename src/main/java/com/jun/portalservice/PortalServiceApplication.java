package com.jun.portalservice;

import com.jun.portalservice.domain.repositories.base.impl.MongoResourceRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(repositoryBaseClass = MongoResourceRepositoryImpl.class)
public class PortalServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PortalServiceApplication.class, args);
  }
}
