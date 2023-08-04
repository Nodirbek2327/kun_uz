package com.example.controller;

import com.example.dto.CommentLikeDTO;
import com.example.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping(value = {"/open/like"})
    public ResponseEntity<?> createLike(@RequestBody CommentLikeDTO dto) {
        return ResponseEntity.ok(commentLikeService.createLike(dto, 1));
    }

    @PostMapping(value = {"/open/dislike"})
    public ResponseEntity<?> createDislike(@RequestBody CommentLikeDTO dto) {
        return ResponseEntity.ok(commentLikeService.createDislike(dto, 1));
    }

    @DeleteMapping(value = "/admin/remove")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        Boolean response = commentLikeService.delete(id);
        if (response) {
            return ResponseEntity.ok("like deleted");
        }
        return ResponseEntity.badRequest().body("like Not Found");
    }
}
