package com.example.controller;


import com.example.config.CustomUserDetails;
import com.example.dto.RegionDTO;
import com.example.enums.Language;
import com.example.service.RegionService;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = {"/admin"})
    public ResponseEntity<?> create(@RequestBody RegionDTO dto/*, HttpServletRequest request*/) {
       // JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        return ResponseEntity.ok(regionService.createWithJwt(dto, userDetails.getProfile().getId()));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/admin/update")
    public ResponseEntity<Boolean> update(@RequestBody RegionDTO dto,
                                          @PathVariable("id") Integer id /*,
                                          HttpServletRequest request*/) {
       // SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.updateWithJwt(id, dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id /*,  HttpServletRequest request*/) {
     //   SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = regionService.delete(id);
        if (response) {
            return ResponseEntity.ok("region deleted");
        }
        return ResponseEntity.badRequest().body("region Not Found");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to
                                        /*HttpServletRequest request*/) {
//        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.regionPagination(from-1, to));
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") Language lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }

}
