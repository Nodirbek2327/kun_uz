package com.example.controller;


import com.example.dto.JwtDTO;
import com.example.dto.RegionDTO;
import com.example.dto.RegionJwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping(value = {""})
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(regionService.createWithJwt(dto, regionJwtDTO.getId()));
    }

    @PutMapping(value = "/update/jwt")
    public ResponseEntity<Boolean> update(@RequestBody RegionDTO dto,
                                          @RequestHeader("Authorization") String authToken) {
        RegionJwtDTO regionJwtDTO = SecurityUtil.getRegionJwtDTO(authToken);
        return ResponseEntity.ok(regionService.updateWithJwt(regionJwtDTO.getId(), dto));
    }


    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO profileDTO) {
        RegionDTO response = regionService.add(profileDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> update(@RequestParam("id") Integer id,
                                    @RequestBody RegionDTO regionDTO) {
        return ResponseEntity.ok(regionService.update(id, regionDTO));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        Boolean response = regionService.delete(id);
        if (response) {
            return ResponseEntity.ok("region deleted");
        }
        return ResponseEntity.badRequest().body("region Not Found");
    }

    @GetMapping("/all")
    public List<RegionDTO> all() {
        return regionService.getAll();
    }

    @GetMapping(value = "/language")
    public ResponseEntity<?> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }

}
