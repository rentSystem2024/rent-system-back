package com.rentcar.back.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rentcar.back.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor

public class ImageContorller {

    private final FileService testService;

    // 파일 업로드
    @PostMapping("/upload")
    public String upload(
            @RequestParam("file") MultipartFile file) {
        String url = testService.upload(file);
        return url;
    }

    // 파일 다운로드
    @GetMapping(value = "/file/{fileName}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public Resource getFile(
            @PathVariable("fileName") String fileName) {
        Resource resource = testService.getFile(fileName);
        return resource;
    }
}
