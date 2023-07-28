package com.example.controller;

import com.example.dto.CommentLikeDTO;
import com.example.dto.JwtDTO;
import com.example.service.CommentLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping(value = {"/admin/like"})
    public ResponseEntity<?> createLike(@RequestBody CommentLikeDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(commentLikeService.createLike(dto, jwtDTO.getId()));
    }

    @PostMapping(value = {"/admin/dislike"})
    public ResponseEntity<?> createDislike(@RequestBody CommentLikeDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(commentLikeService.createDislike(dto, jwtDTO.getId()));
    }

    @DeleteMapping(value = "admin/remove")
    public ResponseEntity<String> delete(@RequestParam("id") String id,  HttpServletRequest request) {
        SecurityUtil.hasRole(request, null);
        Boolean response = commentLikeService.delete(id);
        if (response) {
            return ResponseEntity.ok("like deleted");
        }
        return ResponseEntity.badRequest().body("like Not Found");
    }
}
