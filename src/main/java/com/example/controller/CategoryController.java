package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.CategoryDTO;
import com.example.enums.Language;
import com.example.service.CategoryService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/admin/create"})
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(categoryService.createWithJwt(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody CategoryDTO dto,
                                          @PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.updateWithJwt(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = categoryService.delete(id);
        if (response) {
            return ResponseEntity.ok("category deleted");
        }
        return ResponseEntity.badRequest().body("category Not Found");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(categoryService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") Language lang) {
        return ResponseEntity.ok(categoryService.getByLanguage(lang));
    }
}
