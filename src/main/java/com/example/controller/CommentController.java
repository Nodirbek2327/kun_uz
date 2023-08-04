package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping(value = {"/open/create"})
    public ResponseEntity<?> create(@RequestBody CommentDTO dto) {
        return ResponseEntity.ok(commentService.create(dto, null));
    }

    @PutMapping(value = "/open/{id}")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    @PathVariable("id") String id) {
        return ResponseEntity.ok(commentService.update(id, dto, null));
    }

    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        Boolean response = commentService.delete(id);
        if (response) {
            return ResponseEntity.ok("article deleted");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @GetMapping(value = "/open/list")
    public ResponseEntity<?> getList(@RequestParam("id") String articleId) {
        return ResponseEntity.ok(commentService.getList(articleId));
    }

    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<?> paginationList(@RequestParam("from") int from,
                                            @RequestParam("to") int to) {
        return ResponseEntity.ok(commentService.getListPagination(to, from-1));
    }

    @PostMapping(value = "/open/filter")
    public ResponseEntity<PageImpl<CommentDTO>> filter(@RequestBody CommentFilterDTO filterDTO,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        PageImpl<CommentDTO> response = commentService.filter(filterDTO, page - 1, size);
        return ResponseEntity.ok(response);
    }

}
