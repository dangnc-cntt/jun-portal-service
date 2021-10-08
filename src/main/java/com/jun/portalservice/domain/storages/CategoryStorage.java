package com.jun.portalservice.domain.storages;

import com.jun.portalservice.domain.entities.mongo.Category;
import com.jun.portalservice.domain.utils.CacheKey;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryStorage extends BaseStorage {
  public Category save(Category category) {
    category = categoryRepository.save(category);

    List<Category> categories = categoryRepository.findAll();
    if (categories.size() > 0) {
      caching.put(CacheKey.genListCategoryKey(), categories);
    }
    return category;
  }

  public void delete(Integer categoryId) {
    categoryRepository.deleteById(categoryId);
    List<Category> categories = caching.getList(CacheKey.genListCategoryKey(), Category.class);
    if (categories != null && categories.size() > 0) {
      categories.removeIf(p -> p.getId().equals(categoryId));
    }
    caching.put(CacheKey.genListCategoryKey(), categories);
  }
}
