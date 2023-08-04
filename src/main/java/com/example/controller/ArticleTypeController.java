package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.Language;
import com.example.service.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping(value = {"/admin/create"})
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.createWithJwt(dto,null));
    }

    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody ArticleTypeDTO dto,
                                          @PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.updateWithJwt(id, dto));
    }

    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = articleTypeService.delete(id);
        if (response) {
            return ResponseEntity.ok("articleType deleted");
        }
        return ResponseEntity.badRequest().body("articleType Not Found");
    }

    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(articleTypeService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") Language lang) {
        return ResponseEntity.ok(articleTypeService.getByLanguage(lang));
    }
}
