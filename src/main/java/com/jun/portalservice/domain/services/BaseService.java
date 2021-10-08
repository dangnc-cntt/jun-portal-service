package com.jun.portalservice.domain.services;

import com.jun.portalservice.domain.ModelMapper;
import com.jun.portalservice.domain.entities.mongo.Sequence;
import com.jun.portalservice.domain.repositories.CategoryRepository;
import com.jun.portalservice.domain.repositories.ConfigRepository;
import com.jun.portalservice.domain.repositories.UserRepository;
import com.jun.portalservice.domain.storages.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BaseService {

  @Autowired @Lazy protected UserRepository userRepository;

  @Autowired protected VoucherStorage voucherStorage;

  @Autowired protected AccountStorage accountStorage;

  @Autowired protected CategoryStorage categoryStorage;
  @Autowired protected CategoryRepository categoryRepository;
  @Autowired protected ConfigRepository configRepository;
  @Autowired protected BannerStorage bannerStorage;
  @Autowired protected ConfigStorage configStorage;
  @Autowired protected ModelMapper modelMapper;
  @Autowired private MongoOperations mongoOperations;

  public long generateSequence(String seqName) {
    Sequence counter =
        mongoOperations.findAndModify(
            Query.query(Criteria.where("_id").is(seqName)),
            new Update().inc("seq", 1),
            FindAndModifyOptions.options().returnNew(true).upsert(true),
            Sequence.class);
    return !Objects.isNull(counter) ? counter.getSeq() : 1;
  }
}
