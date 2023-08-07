package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.service.SavedArticleService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saved_article")
public class SavedArticleController {

    @Autowired
    private SavedArticleService savedArticleService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/create"})
    public ResponseEntity<?> create(@RequestParam("articleId") String articleId) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(savedArticleService.create(articleId, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String articleId) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        Boolean response = savedArticleService.delete(articleId, userDetails.getProfile().getId());
        if (response) {
            return ResponseEntity.ok("article deleted");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/list"})
    public ResponseEntity<?> list() {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(savedArticleService.get(userDetails.getProfile().getId()));
    }


}
