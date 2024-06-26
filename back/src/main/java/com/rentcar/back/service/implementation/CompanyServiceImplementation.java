package com.rentcar.back.service.implementation;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rentcar.back.dto.request.company.PostCompanyRequestDto;
import com.rentcar.back.dto.request.company.PutCompanyRequestDto;
import com.rentcar.back.dto.response.ResponseDto;
import com.rentcar.back.dto.response.company.GetCompanyDetailResponseDto;
import com.rentcar.back.dto.response.company.GetCompanyListResponseDto;
import com.rentcar.back.dto.response.company.GetSearchCompanyListResponseDto;
import com.rentcar.back.entity.CompanyEntity;
import com.rentcar.back.repository.CompanyRepository;
import com.rentcar.back.repository.UserRepository;
import com.rentcar.back.service.CompanyService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImplementation implements CompanyService { 
    
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetCompanyListResponseDto> getCompanyList() {

        try {

            List<CompanyEntity> companyEntities = companyRepository.findByOrderByRegistDateDesc();
            
            return GetCompanyListResponseDto.success(companyEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetCompanyDetailResponseDto> getCompanyDetail(int CompanyCode) {

        try {

            CompanyEntity companyEntities = companyRepository.findByCompanyCode(CompanyCode);
            if (companyEntities == null) return ResponseDto.noExistCompany();

            return GetCompanyDetailResponseDto.sucess(companyEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<? super GetSearchCompanyListResponseDto> getSearchCompanyList(String searchWord) {

        try{

            List<CompanyEntity> companyEntities = companyRepository.findByRentCompanyContainsOrderByRegistDateDesc(searchWord);

            return GetSearchCompanyListResponseDto.success(companyEntities);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
    }

    @Override
    public ResponseEntity<ResponseDto> postCompany(PostCompanyRequestDto dto, String userId) {
        
        try {
            
            boolean isExistUser = userRepository.existsById(userId);
            if (!isExistUser) return ResponseDto.authenticationFailed();

            boolean isExistsCompanyCode = companyRepository.existsByCompanyCode(dto.getCompanyCode());
            if (isExistsCompanyCode) return ResponseDto.registrationCompany();
            
            CompanyEntity companyEntity = new CompanyEntity(dto, userId);
            companyRepository.save(companyEntity);

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError(); 
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> putCompany(PutCompanyRequestDto dto, int companyCode, String userId) {
        
        try {
            
            CompanyEntity companyEntity = companyRepository.findByCompanyCode(companyCode);

            if(companyEntity == null) return ResponseDto.noExistCompany();

            companyEntity.update(dto);
            companyRepository.save(companyEntity);           

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError(); 
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteCompany(int companyCode, String userId) {
        try {
            
            CompanyEntity companyEntity = companyRepository.findByCompanyCode(companyCode);

            if(companyEntity == null) return ResponseDto.noExistCompany();

            companyRepository.delete(companyEntity);        

        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError(); 
        }

        return ResponseDto.success();
    }
}