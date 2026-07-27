package com.sirajchaudhary.library.service.impl;

import com.sirajchaudhary.library.request.CategoryInput;
import com.sirajchaudhary.library.entity.Category;
import com.sirajchaudhary.library.exception.ResourceNotFoundException;
import com.sirajchaudhary.library.repository.CategoryRepository;
import com.sirajchaudhary.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public Category getCategory(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Category not found with id: " + id));
    }

    @Override
    public List<Category> getCategories() {
        return repository.findAll();
    }

    @Override
    public Category createCategory(CategoryInput input) {

        log.info("Creating category: {}", input.getName());

        if (repository.existsByName(input.getName())) {
            throw new IllegalArgumentException(
                    "Category '" + input.getName() + "' already exists.");
        }

        Category category = Category.builder()
                .name(input.getName())
                .description(input.getDescription())
                .build();

        return repository.save(category);
    }

    @Override
    public Category updateCategory(Long id, CategoryInput input) {

        Category category = getCategory(id);

        category.setName(input.getName());
        category.setDescription(input.getDescription());

        return repository.save(category);
    }

    @Override
    public boolean deleteCategory(Long id) {

        repository.deleteById(id);

        return true;
    }
}