package com.example.controller;

import com.example.config.CustomUserDetails;
import com.example.dto.CommentLikeDTO;
import com.example.service.CommentLikeService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/like"})
    public ResponseEntity<?> createLike(@RequestBody CommentLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentLikeService.createLike(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER', 'ROLE_USER')")
    @PostMapping(value = {"/dislike"})
    public ResponseEntity<?> createDislike(@RequestBody CommentLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(commentLikeService.createDislike(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/admin/remove")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        Boolean response = commentLikeService.delete(id);
        if (response) {
            return ResponseEntity.ok("like deleted");
        }
        return ResponseEntity.badRequest().body("like Not Found");
    }
}
