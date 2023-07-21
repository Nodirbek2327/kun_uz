package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.CategoryDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping(value = {"/admin"})
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.createWithJwt(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody ArticleTypeDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.updateWithJwt(id, dto));
    }

    @DeleteMapping(value = "admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id,  HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = articleTypeService.delete(id);
        if (response) {
            return ResponseEntity.ok("articleType deleted");
        }
        return ResponseEntity.badRequest().body("articleType Not Found");
    }

    @GetMapping(value = "admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleTypeService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getByLanguage(lang));
    }
}
