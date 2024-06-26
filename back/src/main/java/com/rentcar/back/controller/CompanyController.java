package com.rentcar.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentcar.back.dto.request.company.PostCompanyRequestDto;
import com.rentcar.back.dto.request.company.PutCompanyRequestDto;
import com.rentcar.back.dto.response.ResponseDto;
import com.rentcar.back.dto.response.company.GetCompanyDetailResponseDto;
import com.rentcar.back.dto.response.company.GetCompanyListResponseDto;
import com.rentcar.back.dto.response.company.GetSearchCompanyListResponseDto;
import com.rentcar.back.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/rentcar/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // 업체 등록하기
    @PostMapping("/regist")
    ResponseEntity<ResponseDto> postCompany(
            @RequestBody @Valid PostCompanyRequestDto requestBody,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = companyService.postCompany(requestBody, userId);
        return response;
    }

    // 업체리스트 불러오기
    @GetMapping("/list")
    public ResponseEntity<? super GetCompanyListResponseDto> getCompanyList(
    ) {
        ResponseEntity<? super GetCompanyListResponseDto> response = companyService.getCompanyList();
        return response;
    }

    // 업체 상세 정보 불러오기
    @GetMapping("/list/{companyCode}")
    public ResponseEntity<? super GetCompanyDetailResponseDto> getCompanyDetail(
            @PathVariable("companyCode") int companyCode
    ) {
        ResponseEntity<? super GetCompanyDetailResponseDto> response = companyService.getCompanyDetail(companyCode);
        return response;
    }

    // 업체 검색하기
    @GetMapping("/list/search")
    public ResponseEntity<? super GetSearchCompanyListResponseDto> getSearchCompanyList(
            @RequestParam("word") String searchWord
    ) {
        ResponseEntity<? super GetSearchCompanyListResponseDto> response = companyService
                .getSearchCompanyList(searchWord);
        return response;
    }

    // 업체 수정하기
    @PutMapping("/{companyCode}")
    ResponseEntity<ResponseDto> putCompany(
            @RequestBody @Valid PutCompanyRequestDto requestBody,
            @PathVariable("companyCode") int companyCode,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = companyService.putCompany(requestBody, companyCode, userId);
        return response;
    }

    // 업체 삭제하기
    @DeleteMapping("/{companyCode}")
    public ResponseEntity<ResponseDto> deleteCompany(
            @PathVariable("companyCode") int companyCode,
            @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<ResponseDto> response = companyService.deleteCompany(companyCode, userId);
        return response;
    }
}
