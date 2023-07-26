package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto,
                                          @PathVariable("id") String id,
                                          HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(id, dto, jwtDTO.getId()));
    }

    @DeleteMapping(value = "/admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") String id,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        Boolean response = articleService.delete(id);
        if (response) {
            return ResponseEntity.ok("article deleted");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @PutMapping(value = "/admin/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestParam("id") String id,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.PUBLISHER);
        Boolean response = articleService.changeStatus(id);
        if (response) {
            return ResponseEntity.ok("article status changed");
        }
        return ResponseEntity.badRequest().body("article Not Found");
    }

    @GetMapping(value = "/last5")
    public ResponseEntity<?> getLast5(@RequestParam("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getLast5ByTypes(typeId));
    }

    @GetMapping(value = "/last3")
    public ResponseEntity<?> getLast3(@RequestParam("id") Integer typeId) {
        return ResponseEntity.ok(articleService.getLast3Types(typeId));
    }

    @GetMapping(value = "/last8")
    public ResponseEntity<?> getLast8(@RequestBody List<String> ids) {
        return ResponseEntity.ok(articleService.getLast8ExceptSome(ids));
    }

    @GetMapping(value = "/id/lang")
    public ResponseEntity<?> getLast4(@RequestParam("lang") Language language, @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getById(artcileId, language));
    }

    @GetMapping(value = "/last4")
    public ResponseEntity<?> getLast4(@RequestParam("typeId") Integer typeId, @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getLast4(typeId, artcileId));
    }

    @GetMapping(value = "/mostRead")
    public ResponseEntity<?> mostRead() {
        return ResponseEntity.ok(articleService.mostRead4());
    }

    @GetMapping(value = "/last4_tag")
    public ResponseEntity<?> getLast4ByTag(@RequestParam("tagId") Integer tagId,
                                           @RequestParam("articleId") String artcileId) {
        return ResponseEntity.ok(articleService.getLast4ByTag(artcileId, tagId));
    }

    @GetMapping(value = "/last5/region")
    public ResponseEntity<?> getLast5ByTypeAndRegion(@RequestParam("typeId") Integer typeId, @RequestParam("regionId") Integer regionId ) {
        return ResponseEntity.ok(articleService.getLast5ByTypeAndRegion(typeId, regionId));
    }

    @GetMapping(value = "/paginationByRegion")
    public ResponseEntity<?> paginationByRegion(@RequestParam("regionId") Integer regionId,
                                        @RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(articleService.getListByRegionPagination(regionId, to, from-1));
    }

    @GetMapping(value = "/last5ByCategory")
    public ResponseEntity<?> getLast4ByCategory(@RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLast5ByCategory(categoryId));
    }


    @GetMapping(value = "/paginationByCategory")
    public ResponseEntity<?> paginationByCategory(@RequestParam("categoryId") Integer categoryId,
                                        @RequestParam("from") int from,
                                        @RequestParam("to") int to) {
        return ResponseEntity.ok(articleService.getListByCategoryPagination(categoryId, to, from-1));
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
