package com.sirajchaudhary.library.service;

import com.sirajchaudhary.library.request.CategoryInput;
import com.sirajchaudhary.library.entity.Category;

import java.util.List;

public interface CategoryService {

    Category getCategory(Long id);

    List<Category> getCategories();

    Category createCategory(CategoryInput input);

    Category updateCategory(Long id, CategoryInput input);

    boolean deleteCategory(Long id);

}