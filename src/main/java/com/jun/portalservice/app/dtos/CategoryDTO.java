package com.jun.portalservice.app.dtos;

import com.jun.portalservice.domain.entities.types.CategoryState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CategoryDTO {
  @NotNull private String name;
  @NotNull private String imageUrl;
  @NotNull private String description;
  @NotNull private CategoryState state;
}
