package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.dto.RegionJwtDTO;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article_type")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;

    @PostMapping(value = {""})
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO dto,
                                    @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(articleTypeService.createWithJwt(dto, regionJwtDTO.getId()));
    }

    @PutMapping(value = "/update/jwt")
    public ResponseEntity<Boolean> update(@RequestBody ArticleTypeDTO dto,
                                          @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(articleTypeService.updateWithJwt(regionJwtDTO.getId(), dto));
    }
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody ArticleTypeDTO articleTypeDTO) {
        ArticleTypeDTO response = articleTypeService.add(articleTypeDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody ArticleTypeDTO articleTypeDTO) {
        return ResponseEntity.ok(articleTypeService.update(id, articleTypeDTO));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = articleTypeService.delete(id);
        if (response) {
            return ResponseEntity.ok("articleType deleted");
        }
        return ResponseEntity.badRequest().body("articleType Not Found");
    }

    @GetMapping("/all")
    public List<ArticleTypeDTO> all() {
        return articleTypeService.getAll();
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getByLanguage(lang));
    }
}
