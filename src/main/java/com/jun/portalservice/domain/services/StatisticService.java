package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.responses.Metadata;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.RevenueResponse;
import com.jun.portalservice.app.responses.WarehouseStatisticsResponse;
import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.mongo.*;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.utils.Helper;
import lombok.extern.log4j.Log4j2;
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
import java.util.List;

@Service
@Log4j2
public class StatisticService extends BaseService {
  public RevenueResponse statisticRevenue(String gte, String lte, Pageable pageable) {
    if (StringUtils.isEmpty(gte) && StringUtils.isEmpty(lte)) {
      throw new BadRequestException("Param is null!");
    }

    RevenueResponse response = new RevenueResponse();

    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    LocalDate localDateGte = LocalDate.parse(gte);
    LocalDate localDateLte = LocalDate.parse(lte);
    String gteDate =
        Helper.convertLocalDateTimeToString(Helper.startOfDay(localDateGte.atStartOfDay()));
    String lteDate =
        Helper.convertLocalDateTimeToString(Helper.endOfDay(localDateLte.atStartOfDay()));

    LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
    LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);

    andConditions.add(Criteria.where("createdAt").gte(beforeDate));
    andConditions.add(Criteria.where("createdAt").lte(afterDate));

    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    List<Order> orders = orderRepository.findAll(query);

    if (orders == null || orders.size() == 0) {
      return response;
    }

    List<RevenueResponse.Day> days = new ArrayList<>();
    List<LocalDate> datesBetween = Helper.getDatesBetween(localDateGte, localDateLte);
    if (datesBetween != null && datesBetween.size() > 0) {
      for (LocalDate date : datesBetween) {
        RevenueResponse.Day day = new RevenueResponse.Day();
        day.setStatistic(getDaily(date));
        day.setDate(date.toString());
        days.add(day);
      }
    }
    RevenueResponse.Statistic statistic = new RevenueResponse.Statistic();
    for (Order order : orders) {
      statistic.setTotal(statistic.getTotal() + order.getPrice());
      statistic.setDiscount(statistic.getDiscount() + order.getDiscount());
      if (order.getProducts() != null) {
        for (Order.ProductView productView : order.getProducts()) {
          statistic.setSpending(statistic.getSpending() + productView.getCostPrice());
          statistic.setTotalProduct(
              statistic.getTotalProduct() + productView.getOption().getAmount());
        }
      }
    }

    statistic.setTotalOrder(orders.size());
    statistic.setActual(statistic.getTotal() - statistic.getDiscount() - statistic.getSpending());

    Page<Order> orderPage = orderRepository.findAll(query, pageable);
    response.setStatistic(statistic);
    response.setOrders(PageResponse.createFrom(orderPage));
    response.setDays(days);

    return response;
  }

  public RevenueResponse.Statistic getDaily(LocalDate date) {
    List<Criteria> criteriaList = new ArrayList<>();
    criteriaList.add(Criteria.where("id").ne(null));
    String startOfDate = Helper.convertLocalDateTimeToString(Helper.startOfDay(date.atStartOfDay()));
    String endOfDate = Helper.convertLocalDateTimeToString(Helper.endOfDay(date.atStartOfDay()));

    LocalDateTime start = Helper.convertFromStringToLocalDateTime(startOfDate);
    LocalDateTime end = Helper.convertFromStringToLocalDateTime(endOfDate);


    criteriaList.add(Criteria.where("createdAt").gte(start));
    criteriaList.add(Criteria.where("createdAt").lte(end));

    Query qr = new Query();
    Criteria andCriteria1 = new Criteria();
    qr.addCriteria(andCriteria1.andOperator((criteriaList.toArray(new Criteria[0]))));
    qr.with(Sort.by(Sort.Direction.DESC, "createdAt"));
    List<Order> orders = orderRepository.findAll(qr);
    RevenueResponse.Statistic statistic = new RevenueResponse.Statistic();
    for (Order order : orders) {
      statistic.setTotal(statistic.getTotal() + order.getPrice());
      statistic.setDiscount(statistic.getDiscount() + order.getDiscount());
      if (order.getProducts() != null) {
        for (Order.ProductView productView : order.getProducts()) {
          statistic.setSpending(statistic.getSpending() + productView.getCostPrice());
          statistic.setTotalProduct(
              statistic.getTotalProduct() + productView.getOption().getAmount());
        }
      }
    }
    statistic.setTotalOrder(orders.size());
    statistic.setActual(statistic.getTotal() - statistic.getDiscount() - statistic.getSpending());

    log.error(statistic);
    return statistic;
  }

  public WarehouseStatisticsResponse warehouseStatistics(
      String gte, String lte, Pageable pageable) {
    if (StringUtils.isEmpty(gte) && StringUtils.isEmpty(lte)) {
      throw new BadRequestException("Param is null!");
    }
    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));

    WarehouseStatisticsResponse response = new WarehouseStatisticsResponse();
    LocalDate localDateGte = LocalDate.parse(gte);
    LocalDate localDateLte = LocalDate.parse(lte);
    String gteDate =
        Helper.convertLocalDateTimeToString(Helper.startOfDay(localDateGte.atStartOfDay()));
    String lteDate =
        Helper.convertLocalDateTimeToString(Helper.endOfDay(localDateLte.atStartOfDay()));

    LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
    LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);

    andConditions.add(Criteria.where("createdAt").gte(beforeDate));
    andConditions.add(Criteria.where("createdAt").lte(afterDate));

    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    List<WarehouseExport> warehouseExports = warehouseExportRepository.findAll(query);
    List<WarehouseReceipt> warehouseReceipts = warehouseReceiptRepository.findAll(query);

    List<WarehouseStatisticsResponse.Day> days = new ArrayList<>();
    List<LocalDate> datesBetween = Helper.getDatesBetween(localDateGte, localDateLte);
    if (datesBetween != null && datesBetween.size() > 0) {
      for (LocalDate date : datesBetween) {
        WarehouseStatisticsResponse.Day day = new WarehouseStatisticsResponse.Day();
        day.setAmount(getAmount(date));
        day.setDate(date.toString());
        days.add(day);
      }
    }
    WarehouseStatisticsResponse.Amount amount = new WarehouseStatisticsResponse.Amount();
    if (warehouseExports != null && warehouseExports.size() > 0) {
      for (WarehouseExport warehouseExport : warehouseExports) {
        if (warehouseExport.getProducts() != null && warehouseExport.getProducts().size() > 0) {
          for (ProductView productView : warehouseExport.getProducts()) {
            if (productView.getOptions() != null && productView.getOptions().size() > 0) {
              for (ProductView.Option option : productView.getOptions()) {
                amount.setExport(amount.getExport() + option.getAmount());
              }
            }
          }
        }
      }
    }
    if (warehouseReceipts != null && warehouseReceipts.size() > 0) {
      for (WarehouseReceipt warehouseReceipt : warehouseReceipts) {
        if (warehouseReceipt.getProducts() != null && warehouseReceipt.getProducts().size() > 0) {
          for (ProductView productView : warehouseReceipt.getProducts()) {
            if (productView.getOptions() != null && productView.getOptions().size() > 0) {
              for (ProductView.Option option : productView.getOptions()) {
                amount.setReceipt(amount.getReceipt() + option.getAmount());
              }
            }
          }
        }
      }
    }

    Page<ProductOption> productOptions = productOptionRepository.findAllByOrderByIdDesc(pageable);

    if (productOptions.getContent().size() > 0) {

      List<WarehouseStatisticsResponse.ProductStatistic> productStatistics = new ArrayList<>();
      for (ProductOption productOption : productOptions.getContent()) {
        Product product = productRepository.findProductById(productOption.getProductId());
        if (product != null) {
          WarehouseStatisticsResponse.ProductStatistic statistic =
              new WarehouseStatisticsResponse.ProductStatistic();
          statistic.assign(productOption, product);
          productStatistics.add(statistic);
        }
      }

      response.setProducts(
          new PageResponse<>(productStatistics, Metadata.createFrom(productOptions)));
      response.setAmount(amount);
      response.setDays(days);
    }
    return response;
  }

  public WarehouseStatisticsResponse.Amount getAmount(LocalDate date) {
    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    String gteDate = Helper.convertLocalDateTimeToString(Helper.startOfDay(date.atStartOfDay()));
    String lteDate = Helper.convertLocalDateTimeToString(Helper.endOfDay(date.atStartOfDay()));

    LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
    LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);

    andConditions.add(Criteria.where("createdAt").gte(beforeDate));
    andConditions.add(Criteria.where("createdAt").lte(afterDate));

    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    List<WarehouseExport> warehouseExports = warehouseExportRepository.findAll(query);
    List<WarehouseReceipt> warehouseReceipts = warehouseReceiptRepository.findAll(query);

    WarehouseStatisticsResponse.Amount amount = new WarehouseStatisticsResponse.Amount();
    if (warehouseExports != null && warehouseExports.size() > 0) {
      for (WarehouseExport warehouseExport : warehouseExports) {
        if (warehouseExport.getProducts() != null && warehouseExport.getProducts().size() > 0) {
          for (ProductView productView : warehouseExport.getProducts()) {
            if (productView.getOptions() != null && productView.getOptions().size() > 0) {
              for (ProductView.Option option : productView.getOptions()) {
                amount.setExport(amount.getExport() + option.getAmount());
              }
            }
          }
        }
      }
    }
    if (warehouseReceipts != null && warehouseReceipts.size() > 0) {
      for (WarehouseReceipt warehouseReceipt : warehouseReceipts) {
        if (warehouseReceipt.getProducts() != null && warehouseReceipt.getProducts().size() > 0) {
          for (ProductView productView : warehouseReceipt.getProducts()) {
            if (productView.getOptions() != null && productView.getOptions().size() > 0) {
              for (ProductView.Option option : productView.getOptions()) {
                amount.setReceipt(amount.getReceipt() + option.getAmount());
              }
            }
          }
        }
      }
    }
    return amount;
  }

//  public static void main(String[] args) throws InterruptedException {
//    LocalDateTime localDateTime = LocalDateTime.now();
//    LocalDateTime localDateTime1 = localDateTime.plusMinutes(5).plusSeconds(50);
//
//    long duration =
//        localDateTime1.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli()
//            - localDateTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
//    System.out.println(localDateTime1);
//    System.out.println(localDateTime);
//    System.out.println(duration / 1000 / 60);
//  }
}
