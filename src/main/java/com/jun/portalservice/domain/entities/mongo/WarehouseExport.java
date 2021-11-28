package com.jun.portalservice.domain.entities.mongo;

import com.jun.portalservice.domain.data.ProductView;
import com.jun.portalservice.domain.entities.types.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "warehouse_export")
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseExport extends BaseEntity {
  @Transient public static final String SEQUENCE_NAME = "warehouse_export_sequence";
  @Id private int id;

  @Field(name = "created_by")
  private Integer createdBy;

  @Field(name = "description")
  private String description;

  @Field(name = "products")
  List<ProductView> products;

  @Field(name = "type")
  private PaymentMethod type;
}
