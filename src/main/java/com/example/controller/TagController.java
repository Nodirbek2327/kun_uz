package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.TagDTO;
import com.example.enums.Language;
import com.example.service.TagService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/admin"})
    public ResponseEntity<?> create(@RequestBody TagDTO dto) {
       // JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(tagService.createWithJwt(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody TagDTO dto,
                                          @PathVariable("id") Integer id) {
     //   SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(tagService.updateWithJwt(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
       // SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = tagService.delete(id);
        if (response) {
            return ResponseEntity.ok("tag deleted");
        }
        return ResponseEntity.badRequest().body("tag Not Found");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
