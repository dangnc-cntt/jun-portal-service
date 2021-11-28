package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.responses.Metadata;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.app.responses.RevenueResponse;
import com.jun.portalservice.app.responses.WarehouseStatisticsResponse;
import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.mongo.*;
import com.jun.portalservice.domain.exceptions.BadRequestException;
import com.jun.portalservice.domain.utils.Helper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticService extends BaseService {
  public RevenueResponse statisticRevenue(String gte, String lte, Pageable pageable) {
    if (StringUtils.isEmpty(gte) && StringUtils.isEmpty(lte)) {
      throw new BadRequestException("Param is null!");
    }

    RevenueResponse response = new RevenueResponse();
    LocalDate localDateGte = LocalDate.parse(gte);
    LocalDate localDateLte = LocalDate.parse(lte);
    String gteDate =
        Helper.convertLocalDateTimeToString(Helper.startOfDay(localDateGte.atStartOfDay()));
    String lteDate =
        Helper.convertLocalDateTimeToString(Helper.endOfDay(localDateLte.atStartOfDay()));

    LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
    LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);

    List<Order> orders =
        orderRepository.findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
            beforeDate, afterDate);

    if (orders == null) {
      return response;
    }

    for (Order order : orders) {
      response.setTotal(response.getTotal() + order.getPrice());
      response.setDiscount(response.getDiscount() + order.getDiscount());
      if (order.getProducts() != null) {
        for (Order.ProductView productView : order.getProducts()) {
          response.setSpending(response.getSpending() + productView.getCostPrice());
          response.setTotalProduct(
              response.getTotalProduct() + productView.getOption().getAmount());
        }
      }
    }

    response.setTotalOrder(orders.size());
    response.setActual(response.getTotal() - response.getDiscount() - response.getSpending());

    Page<Order> orderPage =
        orderRepository.findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
            beforeDate, afterDate, pageable);
    response.setOrders(PageResponse.createFrom(orderPage));

    return response;
  }

  public WarehouseStatisticsResponse warehouseStatistics(
      String gte, String lte, Pageable pageable) {
    if (StringUtils.isEmpty(gte) && StringUtils.isEmpty(lte)) {
      throw new BadRequestException("Param is null!");
    }
    WarehouseStatisticsResponse response = new WarehouseStatisticsResponse();
    LocalDate localDateGte = LocalDate.parse(gte);
    LocalDate localDateLte = LocalDate.parse(lte);
    String gteDate =
        Helper.convertLocalDateTimeToString(Helper.startOfDay(localDateGte.atStartOfDay()));
    String lteDate =
        Helper.convertLocalDateTimeToString(Helper.endOfDay(localDateLte.atStartOfDay()));

    LocalDateTime beforeDate = Helper.convertFromStringToLocalDateTime(gteDate);
    LocalDateTime afterDate = Helper.convertFromStringToLocalDateTime(lteDate);

    List<WarehouseExport> warehouseExports =
        warehouseExportRepository.findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
            beforeDate, afterDate);
    List<WarehouseReceipt> warehouseReceipts =
        warehouseReceiptRepository.findByCreatedAtIsAfterAndCreatedAtIsBeforeOrderByCreatedAt(
            beforeDate, afterDate);

    if (warehouseExports != null && warehouseExports.size() > 0) {
      for (WarehouseExport warehouseExport : warehouseExports) {
        if (warehouseExport.getProducts() != null && warehouseExport.getProducts().size() > 0) {
          for (ProductView productView : warehouseExport.getProducts()) {
            if (productView.getOptions() != null && productView.getOptions().size() > 0) {
              for (ProductView.Option option : productView.getOptions()) {
                response.setExport(response.getExport() + option.getAmount());
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
                response.setReceipt(response.getReceipt() + option.getAmount());
              }
            }
          }
        }
      }
    }
    Page<ProductOption> productOptions = productOptionRepository.findAll(pageable);

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
    }
    return response;
  }
}
