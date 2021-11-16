// package com.jun.portalservice.domain.services;
//
// import com.jun.portalservice.domain.entities.mongo.*;
// import com.jun.portalservice.domain.exceptions.ResourceExitsException;
// import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
// import jun.message.CartProduct;
// import jun.message.OrderMessage;
// import org.springframework.stereotype.Service;
//
// import java.util.ArrayList;
// import java.util.List;
//
// @Service
// public class OrderService extends BaseService {
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
//        if (orderMessage.getProductList() == null || orderMessage.getProductList().size() == 0) {
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
// }
