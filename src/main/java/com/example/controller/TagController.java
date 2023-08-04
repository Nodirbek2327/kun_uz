package com.example.controller;

import com.example.dto.TagDTO;
import com.example.enums.Language;
import com.example.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;
    @PostMapping(value = {"/admin"})
    public ResponseEntity<?> create(@RequestBody TagDTO dto) {
       // JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.createWithJwt(dto, 1));
    }

    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody TagDTO dto,
                                          @PathVariable("id") Integer id) {
     //   SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.updateWithJwt(id, dto));
    }

    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
       // SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = tagService.delete(id);
        if (response) {
            return ResponseEntity.ok("tag deleted");
        }
        return ResponseEntity.badRequest().body("tag Not Found");
    }

    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to) {
      //  SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") Language lang) {
        return ResponseEntity.ok(tagService.getByLanguage(lang));
    }

}
