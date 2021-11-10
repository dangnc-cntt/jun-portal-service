package com.jun.portalservice.domain.services;

import com.jun.portalservice.app.dtos.CategoryDTO;
import com.jun.portalservice.app.responses.PageResponse;
import com.jun.portalservice.domain.entities.mongo.Category;
import com.jun.portalservice.domain.entities.types.CategoryState;
import com.jun.portalservice.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService extends BaseService {
  public PageResponse<Category> filter(
      Integer id, CategoryState state, String name, Pageable pageable) {
    List<Criteria> andConditions = new ArrayList<>();

    andConditions.add(Criteria.where("id").ne(null));
    if (id != null) {
      andConditions.add(Criteria.where("id").is(id));
    }
    if (state != null) {
      andConditions.add(Criteria.where("state").is(state));
    }
    if (name != null) {
      andConditions.add(Criteria.where("name").regex(name, "i"));
    }

    Query query = new Query();
    Criteria criteria = new Criteria();
    query.addCriteria(criteria.andOperator((andConditions.toArray(new Criteria[0]))));
    query.with(Sort.by(Sort.Direction.DESC, "createdAt"));

    Page<Category> categories = categoryRepository.findAll(query, pageable);
    return PageResponse.createFrom(categories);
  }

  public Category findById(Integer id) {
    Category category = categoryRepository.findCategoriesById(id);
    if (category == null) {
      throw new ResourceNotFoundException("Category " + id + " not found");
    }
    return category;
  }

  public Category create(CategoryDTO dto, int userId) {
    Category category = modelMapper.toCategory(dto);
    category.setId((int) generateSequence(Category.SEQUENCE_NAME));
    category.setCreatedBy(userId);
    return categoryStorage.save(category);
  }

  public Category update(CategoryDTO dto, int categoryId) {
    Category category = findById(categoryId);
    category = modelMapper.toCategory(dto);
    category.setId(categoryId);
    return categoryStorage.save(category);
  }

  public boolean delete(Integer categoryId) {
    categoryStorage.delete(categoryId);
    return true;
  }
}
