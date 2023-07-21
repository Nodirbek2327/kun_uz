package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping(value = {"/admin", "/"})
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO dto,
                                          @PathVariable("id") UUID id,
                                          HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") UUID id,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        Boolean response = articleService.delete(id);
        if (response) {
            return ResponseEntity.ok("article deleted");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PutMapping(value = "/admin/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestParam("id") UUID id,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.PUBLISHER);
        Boolean response = articleService.changeStatus(id);
        if (response) {
            return ResponseEntity.ok("article status changed");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @GetMapping(value = "/last5")
    public ResponseEntity<?> getLast5() {
        return ResponseEntity.ok(articleService.getLast5());
    }

    @GetMapping(value = "/last3")
    public ResponseEntity<?> getLast3() {
        return ResponseEntity.ok(articleService.getLast3());
    }

    @GetMapping(value = "/last8")
    public ResponseEntity<?> getLast8(@RequestBody List<UUID> ids) {
        return ResponseEntity.ok(articleService.getLast8(ids));
    }

    @GetMapping(value = "/last4")
    public ResponseEntity<?> getLast4(@RequestBody  UUID id) {
        return ResponseEntity.ok(articleService.getLast4(id));
    }

    @GetMapping(value = "/mostRead")
    public ResponseEntity<?> mostRead() {
        return ResponseEntity.ok(articleService.mostRead4());
    }





//    @GetMapping(value = "admin/pagination")
//    public ResponseEntity<?> pagination(@RequestParam("from") int from,
//                                        @RequestParam("to") int to,
//                                        HttpServletRequest request) {
//        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
//        return ResponseEntity.ok(articleService.profilePagination(from-1, to));
//    }
//
//
//
//    @PostMapping(value = "/filter")
//    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO,
//                                                       @RequestParam(value = "page", defaultValue = "1") int page,
//                                                       @RequestParam(value = "size", defaultValue = "2") int size,
//                                                       HttpServletRequest request) {
//        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
//        PageImpl<ProfileDTO> response = articleService.filter(filterDTO, page - 1, size);
//        return ResponseEntity.ok(response);
//    }

}
