package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.enums.ProfileRole;
import com.example.service.AttachService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
//        String fileName = attachService.saveToSystem(file);
//        return ResponseEntity.ok().body(fileName);
//    }
//
//    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
//    public byte[] open(@PathVariable("fileName") String fileName) {
//        return attachService.loadImage(fileName);
//    }

    @PostMapping("/upload2")
    public ResponseEntity<AttachDTO> upload2(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(attachService.save(file));
    }

    @GetMapping(value = "/open/{id}/img", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open2(@PathVariable("id") String id) {
        return attachService.loadImageById(id);
    }

    @GetMapping(value = "/open/{id}/general", produces = MediaType.ALL_VALUE)
    public byte[] openByIdGeneral(@PathVariable("id") String id) {
        return attachService.loadByIdGeneral(id);
    }


    @GetMapping(value = "admin/delete")
    public ResponseEntity<?> delete(@RequestParam("id") String id,
                                        HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.delete(id));
    }


    @GetMapping(value = "admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam("from") int from,
                                        @RequestParam("to") int to,
                                        HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(attachService.attachPagination(from-1, to));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }

}
