package com.example.usermanagement.web;

import com.example.usermanagement.domain.entity.Category;
import com.example.usermanagement.domain.service.CategoryService;
import com.example.usermanagement.dto.user.input.CategoryInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(){
        log.info("Starting get all categories");
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategorybyID(@PathVariable String categoryId){
        log.info("Starting to find category by id");
        Category category = categoryService.getCategorybyID(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryInput category){
        log.info("Requesting to create a new category");
        Category newCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(newCategory,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/update")
    public ResponseEntity<Category> updateCategory(@PathVariable String categoryId, @RequestBody Category category){
        log.info("Requesting to update a category");
        Category updateCategory = categoryService.updateCategory(categoryId, category);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}/delete")
    public ResponseEntity<Category> deleteCategory(@PathVariable String categoryId){
        log.info("Requesting to delete a category");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
