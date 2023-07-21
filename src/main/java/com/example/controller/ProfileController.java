package com.example.controller;


import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.dto.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = {"/admin", "/"})
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto, HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwtDTO.getId()));
    }

    @PutMapping(value = "admin/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(id, dto));
    }

    @PutMapping(value = "/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto,
                                                @RequestHeader("Authorization") String authToken) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(authToken, null);
        return ResponseEntity.ok(profileService.updateDetail(jwtDTO.getId(), dto));
    }



    @GetMapping(value = "admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.profilePagination(from-1, to));
    }

    @DeleteMapping(value = "admin/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id,
                                         HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        Boolean response = profileService.delete(id);
        if (response) {
            return ResponseEntity.ok("profile deleted");
        }
        return ResponseEntity.badRequest().body("profile Not Found");
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO,
                                                       @RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "size", defaultValue = "2") int size,
                                                       HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);    
        PageImpl<ProfileDTO> response = profileService.filter(filterDTO, page - 1, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO profileDTO) {
        ProfileDTO response = profileService.add(profileDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PutMapping(value = "/update")
//    public ResponseEntity<?> update(@RequestParam("id") Integer id,
//                                     @RequestBody ProfileDTO profileDTO) {
//        return ResponseEntity.ok(profileService.update(id, profileDTO));
//    }

}
