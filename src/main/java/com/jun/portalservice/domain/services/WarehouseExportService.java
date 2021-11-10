package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.WarehouseDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.mongo.ProductOption;
import com.jun.portalservice.domain.entities.mongo.WarehouseExport;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import com.jun.portalservice.domain.utils.Helper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WarehouseExportService extends BaseService {

  public PageResponse<WarehouseExport> fillter(
      Integer id, String gte, String lte, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    if (id != null) {
      andConditions.add(Criteria.where("id").is(id));
    }
    if (StringUtils.isNotEmpty(gte) && StringUtils.isNotEmpty(lte)) {
      LocalDate localDateGte = LocalDate.parse(gte);
      LocalDate localDateLte = LocalDate.parse(lte);
      String gteDate =
          Helper.convertLocalDateTimeToString(Helper.startOfDay(localDateGte.atStartOfDay()));
      String lteDate =
          Helper.convertLocalDateTimeToString(Helper.endOfDay(localDateLte.atStartOfDay()));

      LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
      LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);
      andConditions.add(Criteria.where("giftDay").gte(beforeDate));
      andConditions.add(Criteria.where("giftDay").lte(afterDate));
    }
    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "id"));

    Page<WarehouseExport> warehouseExports = warehouseExportRepository.findAll(query, pageable);
    return PageResponse.createFrom(warehouseExports);
  }

  public WarehouseExport create(WarehouseDTO warehouseDTO, int userId) {
    WarehouseExport warehouseExport = modelMapper.toWarehouseExport(warehouseDTO);
    warehouseExport.setId((int) generateSequence(WarehouseExport.SEQUENCE_NAME));
    warehouseExport.setCreatedBy(userId);
    warehouseExport = warehouseExportStorage.save(warehouseExport);
    if (warehouseDTO.getProducts().size() > 0) {
      Map<Integer, ProductOption> optionMap = new HashMap<>();
      for (ProductView product : warehouseExport.getProducts()) {
        if (product.getOptions() != null && product.getOptions().size() > 0) {
          for (ProductView.Option option : product.getOptions()) {
            ProductOption productOption = optionMap.get(option.getId());
            if (productOption == null) {
              productOption = productOptionRepository.findProductOptionById(option.getId());
            }
            productOption.setAmount(productOption.getAmount() - option.getAmount());
            optionMap.put(productOption.getId(), productOption);
          }
        }
      }
      if (optionMap.size() > 0) {
        productOptionRepository.saveAll(optionMap.values());
      }
    }
    return warehouseExport;
  }

  public WarehouseExport findById(Integer id) {
    WarehouseExport warehouseExport = warehouseExportRepository.findWarehouseExportById(id);
    if (warehouseExport == null) {
      throw new ResourceNotFoundException("No Warehouse Export found!");
    }
    return warehouseExport;
  }
}
