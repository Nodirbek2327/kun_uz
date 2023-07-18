package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO profileDTO) {
        CategoryDTO response = categoryService.add(profileDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(id, categoryDTO));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = categoryService.delete(id);
        if (response) {
            return ResponseEntity.ok("category deleted");
        }
        return ResponseEntity.badRequest().body("category Not Found");
    }

    @GetMapping("/all")
    public List<CategoryDTO> all() {
        return categoryService.getAll();
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(categoryService.getByLanguage(lang));
    }
}
