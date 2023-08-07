package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.ArticleLikeDTO;
import com.example.service.ArticleLikeService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/like"})
    public ResponseEntity<?> createLike(@RequestBody ArticleLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleLikeService.createLike(dto, userDetails.getProfile().getId()));
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/dislike"})
    public ResponseEntity<?> createDislike(@RequestBody ArticleLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(articleLikeService.createDislike(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/admin/remove")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = articleLikeService.delete(id);
        if (response) {
            return ResponseEntity.ok("like deleted");
        }
        return ResponseEntity.badRequest().body("like Not Found");
    }
}
