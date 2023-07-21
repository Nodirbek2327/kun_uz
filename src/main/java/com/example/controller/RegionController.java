package com.example.controller;


import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PostMapping(value = {"/admin"})
    public ResponseEntity<?> create(@RequestBody RegionDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.createWithJwt(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody RegionDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.updateWithJwt(id, dto));
    }

    @DeleteMapping(value = "admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id,  HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = regionService.delete(id);
        if (response) {
            return ResponseEntity.ok("region deleted");
        }
        return ResponseEntity.badRequest().body("region Not Found");
    }

    @GetMapping(value = "admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }

}
