package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Order;
import com.jun.portalservice.domain.entities.types.OrderState;
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
import java.util.List;

@Service
public class OrderService extends BaseService {

  public PageResponse<Order> fillter(Integer accountId, String gte, String lte, Pageable pageable) {

    List<Criteria> andConditions = new ArrayList<>();
    andConditions.add(Criteria.where("id").ne(null));
    if (accountId != null) {
      andConditions.add(Criteria.where("orderedBy").is(accountId));
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
      andConditions.add(Criteria.where("createdAt").gte(beforeDate));
      andConditions.add(Criteria.where("createdAt").lte(afterDate));
    }
    Query query = new Query();
    Criteria andCriteria = new Criteria();
    query.addCriteria(andCriteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "id"));

    Page<Order> orders = orderRepository.findAll(query, pageable);

    return PageResponse.createFrom(orders);
  }

  public Order findById(long orderId) {
    Order order = orderRepository.findOrderById(orderId);
    if (order == null) {
      throw new ResourceNotFoundException("No order found");
    }

    return order;
  }

  public Order update(long orderId, OrderState state) {
    Order order = orderRepository.findOrderById(orderId);
    if (order == null) {
      throw new ResourceNotFoundException("No order found");
    }

    order.setState(state);

    return orderRepository.save(order);
  }

  //  public void processOrder(List<OrderMessage> orderMessages) {
  //    if (orderMessages != null && orderMessages.size() > 0) {
  //      List<ProductOption> optionList = new ArrayList<>();
  //      List<Order> orders = new ArrayList<>();
  //      List<VoucherAccount> voucherAccounts = new ArrayList<>();
  //      for (OrderMessage orderMessage : orderMessages) {
  //        Long orderId =
  //            Long.parseLong(orderMessage.getId().substring(5, orderMessage.getId().length()));
  //        Order order = orderRepository.findOrderById(orderId);
  //        if (order != null) {
  //          throw new ResourceExitsException("Order is exist!");
  //        }
  //        order = new Order();
  //
  //        if (orderMessage.getProductList() == null || orderMessage.getProductList().size() == 0)
  // {
  //          throw new ResourceExitsException("List product null");
  //        }
  //
  //        List<Order.ProductView> productViews = new ArrayList<>();
  //
  //        for (CartProduct cartProduct : orderMessage.getProductList()) {
  //          Product product = productStorage.findById(cartProduct.getId());
  //          if (product == null) {
  //            throw new ResourceNotFoundException("No product found!");
  //          }
  //          Order.ProductView productView = new Order.ProductView();
  //          productView.setId(product.getId());
  //          productView.setCode(product.getCode());
  //          productView.setPrice(product.getPrice());
  //          productView.setCostPrice(product.getCostPrice());
  //          productView.setName(product.getName());
  //          productView.setDiscount(product.getDiscount());
  //          ProductOption option =
  //              productOptionRepository.findProductOptionByIdAndProductId(
  //                  cartProduct.getOptionId(), product.getId());
  //          if (option == null) {
  //            throw new ResourceNotFoundException("No option found!");
  //          }
  //
  //          int amount = option.getAmount() - orderMessage.getAmount();
  //          if (amount < 0) {
  //            throw new ResourceNotFoundException("Out of product with option " + option.getId());
  //          }
  //          option.setAmount(amount);
  //          optionList.add(option);
  //
  //          productView.setOption(option);
  //          productView.getOption().setAmount(orderMessage.getAmount());
  //          productViews.add(productView);
  //        }
  //        Voucher voucher = voucherStorage.findById(orderMessage.getVoucherId());
  //
  //        if (voucher == null) {
  //          throw new ResourceNotFoundException("Voucher not found");
  //        }
  //
  //        if (orderMessage.getVoucherId() != null && orderMessage.getVoucherId() != -1) {
  //          Order.VoucherView voucherView = new Order.VoucherView();
  //          voucherView.setCode(voucher.getCode());
  //          voucherView.setType(voucher.getType());
  //          voucherView.setId(voucher.getId());
  //          voucherView.setDiscount(voucher.getDiscount());
  //          voucher.setExpiryDate(voucher.getExpiryDate());
  //
  //          VoucherAccount voucherAccount =
  //              voucherAccountRepository.findVoucherAccountByAccountIdAndVoucherId(
  //                  orderMessage.getOrderedBy(), voucher.getId());
  //          voucherAccounts.add(voucherAccount);
  //
  //          order.assign(productViews, orderId, voucherView, orderMessage);
  //        } else {
  //          order.assign(productViews, orderId, null, orderMessage);
  //        }
  //      }
  //
  //      orderRepository.saveAll(orders);
  //      productOptionRepository.saveAll(optionList);
  //      voucherAccountRepository.deleteAll(voucherAccounts);
  //    }
  //  }
}
