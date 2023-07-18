package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionJwtDTO;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @PostMapping(value = {""})
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                    @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(categoryService.createWithJwt(dto, regionJwtDTO.getId()));
    }

    @PutMapping(value = "/update/jwt")
    public ResponseEntity<Boolean> update(@RequestBody CategoryDTO dto,
                                          @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(categoryService.updateWithJwt(regionJwtDTO.getId(), dto));
    }

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
