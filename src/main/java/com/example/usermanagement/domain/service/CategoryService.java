package com.example.usermanagement.domain.service;

import com.example.usermanagement.domain.entity.Category;
import com.example.usermanagement.domain.repo.CategoryRepository;
import com.example.usermanagement.dto.user.input.CategoryInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        log.info("Get all categories success");
        return categoryRepository.findAll();
    }
    public Category getCategorybyID(String categoryId){
        Category existCategory = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NullPointerException("not found this category" + categoryId)
        );
        log.info("Get category success");
//        return categoryRepository.findById(categoryId);
        return existCategory;
    }

    public Category createCategory(CategoryInput categoryInput){
        Category category = new Category();
        category.setCategoryName(categoryInput.getCategoryName());
        category.setCodeName(categoryInput.getCodeName());

        categoryRepository.save(category);
        log.info("Create category success");
        return category;
    }

    public Category updateCategory(String categoryId, Category category){
        Category existCategory = getCategorybyID(categoryId);
        if(existCategory == null){
            log.error("Category not exist");
        } else {
            existCategory.setCategoryName(category.getCategoryName());
            existCategory.setCodeName(category.getCodeName());
            log.info("Update Category successfully");
        }
        return existCategory;
    }
    public void deleteCategory(String categoryId){
        log.info("Delete successfully");
        categoryRepository.deleteById(categoryId);
    }
}
