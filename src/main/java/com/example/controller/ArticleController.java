package com.example.controller;

import com.example.dto.*;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping(value = {"/moderator/create"})
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto) {
        return ResponseEntity.ok(articleService.create(dto, null));
    }

    @PutMapping(value = "/moderator/{id}")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                          @PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.update(id, dto, null));
    }

    @DeleteMapping(value = "/moderator/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id) {
        Boolean response = articleService.delete(id);
        if (response) {
            return ResponseEntity.ok("article deleted");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PutMapping(value = "/publisher/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestParam("id") String id) {
        Boolean response = articleService.changeStatus(id);
        if (response) {
            return ResponseEntity.ok("article status changed");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @GetMapping(value = "/open/last5")
    public ResponseEntity<?> getLast5(@RequestParam("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getLast5ByTypes(typeId));
    }

    @GetMapping(value = "/open/last3")
    public ResponseEntity<?> getLast3(@RequestParam("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getLast3Types(typeId));
    }

    @GetMapping(value = "/open/last8")
    public ResponseEntity<?> getLast8(@RequestBody List<String> ids) {
        return ResponseEntity.ok(articleService.getLast8ExceptSome(ids));
    }

    @GetMapping(value = "/open/id/lang")
    public ResponseEntity<?> getLast4(@RequestParam("lang") Language language, @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getById(artcileId, language));
    }

    @GetMapping(value = "/open/last4")
    public ResponseEntity<?> getLast4(@RequestParam("typeId") Integer typeId, @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getLast4(typeId, artcileId));
    }

    @GetMapping(value = "/open/mostRead")
    public ResponseEntity<?> mostRead() {
        return ResponseEntity.ok(articleService.mostRead4());
    }

    @GetMapping(value = "/open/last4_tag")
    public ResponseEntity<?> getLast4ByTag(@RequestParam("tagId") Integer tagId,
                                           @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getLast4ByTag(artcileId, tagId));
    }

    @GetMapping(value = "/open/last5/region")
    public ResponseEntity<?> getLast5ByTypeAndRegion(@RequestParam("typeId") Integer typeId, @RequestParam("regionId") Integer regionId ) {
        return ResponseEntity.ok(articleService.getLast5ByTypeAndRegion(typeId, regionId));
    }

    @GetMapping(value = "/open/paginationByRegion")
    public ResponseEntity<?> paginationByRegion(@RequestParam("regionId") Integer regionId,
                                        @RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(articleService.getListByRegionPagination(regionId, to, from-1));
    }

    @GetMapping(value = "/open/last5ByCategory")
    public ResponseEntity<?> getLast4ByCategory(@RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLast5ByCategory(categoryId));
    }


    @GetMapping(value = "/open/paginationByCategory")
    public ResponseEntity<?> paginationByCategory(@RequestParam("categoryId") Integer categoryId,
                                        @RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(articleService.getListByCategoryPagination(categoryId, to, from-1));
    }

    @PutMapping(value = "/open/viewCount")
    public ResponseEntity<String> viewCount(@RequestParam("id") String id) {
        Boolean response = articleService.increaseViewCount(id);
        if (response) {
            return ResponseEntity.ok("increased");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PutMapping(value = "/open/sharedCount")
    public ResponseEntity<String> sharedCount(@RequestParam("id") String id) {
        Boolean response = articleService.increaseSharedCount(id);
        if (response) {
            return ResponseEntity.ok("increased");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PostMapping(value = "/open/filter")
    public ResponseEntity<PageImpl<ArticleDTO>> filter(@RequestBody ArticleFilterDTO filterDTO,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size) {
        PageImpl<ArticleDTO> response = articleService.filter(filterDTO, page - 1, size);
        return ResponseEntity.ok(response);
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
