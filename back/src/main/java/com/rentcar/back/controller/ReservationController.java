package com.rentcar.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rentcar.back.dto.request.reservation.PatchReservationStateRequestDto;
import com.rentcar.back.dto.request.reservation.PostReservationRequestDto;
import com.rentcar.back.dto.response.ResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationCancelListResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationDetailMyListResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationDetailResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationMyListResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationPopularListResponseDto;
import com.rentcar.back.dto.response.reservation.GetReservationUserListResponseDto;
import com.rentcar.back.dto.response.reservation.GetSearchReservationCarListResponseDto;
import com.rentcar.back.dto.response.reservation.GetSearchReservationCarPriceListResponseDto;
import com.rentcar.back.dto.response.reservation.GetSearchReservationDetailListResponseDto;
import com.rentcar.back.dto.response.reservation.GetSearchReservationListResponseDto;
import com.rentcar.back.dto.response.reservation.PostReservationResponseDto;
import com.rentcar.back.service.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rentcar/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 하기
    @PostMapping("/regist")
    ResponseEntity<? super PostReservationResponseDto> postReservation (
        @RequestBody @Valid PostReservationRequestDto requestBody, 
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super PostReservationResponseDto> response = reservationService.postReservation(requestBody, userId);
        return response;
    }

    // 나의 예약 내역 보기
    @GetMapping("/mylist")
    public ResponseEntity<? super GetReservationMyListResponseDto> getReservationList (
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetReservationMyListResponseDto> response = reservationService.getReservationMyList(userId);
        return response;
    }

    // 나의 예약 내역 상세보기
    @GetMapping("/mylist/{reservationCode}")
    public ResponseEntity<? super GetReservationDetailMyListResponseDto> getReservaitonDetailList (
        @AuthenticationPrincipal String userId,
        @PathVariable ("reservationCode") int reservationCode
    ) {
        ResponseEntity<? super GetReservationDetailMyListResponseDto> response = reservationService.getReservationDetailMyList(reservationCode, userId);
        return response;
    }

    // 취소 신청 예약 리스트 불러오기
    @GetMapping("/cancel/{reservationState}")
    public ResponseEntity<? super GetReservationCancelListResponseDto> GetReservationCancelList (
        @AuthenticationPrincipal String userId,
        @PathVariable ("reservationState") String reservationState
    ) {
        ResponseEntity<? super GetReservationCancelListResponseDto> response = reservationService.getReservationCancelList(userId, reservationState);
        return response;
    }

    // 전체 예약 목록 리스트 불러오기(관리자)
    @GetMapping("/list")
    public ResponseEntity<? super GetReservationUserListResponseDto> GetReservationUserList (
        @AuthenticationPrincipal String userId
    ) {
        ResponseEntity<? super GetReservationUserListResponseDto> response = reservationService.getReservationUserList(userId);
        return response;
    }

    // 예약 상세 불러오기(관리자)
    @GetMapping("/{reservationCode}")
    public ResponseEntity<? super GetReservationDetailResponseDto> GetReservationDetail (
        @PathVariable ("reservationCode") int reservationCode
    ) {
        ResponseEntity<? super GetReservationDetailResponseDto> response = reservationService.getReservationDetail(reservationCode);
        return response;
    }

    // 예약 검색 리스트 불러오기(관리자)
    @GetMapping("/list/search")
    public ResponseEntity<? super GetSearchReservationListResponseDto> getSearchReservationList (
        @RequestParam(value="word", required=false) Integer word 
    ) {
        ResponseEntity<? super GetSearchReservationListResponseDto> response = reservationService.getSearchReservationList(word);
        return response;
    }

    // 인기 차량 리스트 불러오기
    @GetMapping("/popular")
    public ResponseEntity<? super GetReservationPopularListResponseDto> GetReservationPopularList (){
        ResponseEntity<? super GetReservationPopularListResponseDto> response = reservationService.getReservationPopularList();
        return response;
    }

    // 차량 검색 결과 불러오기
    @GetMapping("/search")
    public ResponseEntity<? super GetSearchReservationCarListResponseDto> getSearchReservationCarList (
        @RequestParam ("reservationStart") String reservationStart, 
        @RequestParam ("reservationEnd") String reservationEnd
    ) { 
        ResponseEntity<? super GetSearchReservationCarListResponseDto> response = reservationService.getSearchReservationCarList(reservationStart, reservationEnd);
        return response;
    }

    // 보험별(업체) 가격 검색 결과 불러오기
    @GetMapping("/search/{carName}")
    public ResponseEntity<? super GetSearchReservationCarPriceListResponseDto> getSearchReservationCarPriceList (
        @RequestParam ("reservationStart") String reservationStart, 
        @RequestParam ("reservationEnd") String reservationEnd,
        @PathVariable ("carName") String carName
    ) {
        ResponseEntity<? super GetSearchReservationCarPriceListResponseDto> response = reservationService.getSearchReservationCarPriceList(reservationStart, reservationEnd, carName);
        return response;
    }
    
    // (보험별)차량 예약 상세 검색 결과 불러오기
    @GetMapping("/search/{carName}/{rentCompany}")
    public ResponseEntity<? super GetSearchReservationDetailListResponseDto> getSearchReservationDetailList (
        @RequestParam ("reservationStart") String reservationStart, 
        @RequestParam ("reservationEnd") String reservationEnd,
        @PathVariable ("carName") String carName,
        @PathVariable ("rentCompany") String rentCompany
    ) {
        ResponseEntity<? super GetSearchReservationDetailListResponseDto> response = reservationService.getSearchReservationDetailList(reservationStart, reservationEnd, carName, rentCompany);
        return response;
    }

    // 예약 취소하기
    @PatchMapping("/mylist/{reservationCode}/cancel")
    public ResponseEntity<ResponseDto> PatchReservationCancel (
        @AuthenticationPrincipal String userId,
        @PathVariable ("reservationCode") int reservationCode,
        @RequestBody @Valid PatchReservationStateRequestDto requestBody 
    ) {
        ResponseEntity<ResponseDto> response = reservationService.patchReservation(requestBody, reservationCode, userId);
        return response;
    }

    // 예약 취소 신청 승인하기
    @PatchMapping("/cancel/{reservationCode}")
    public ResponseEntity<ResponseDto> patchReservationCancelApprove (
        @PathVariable ("reservationCode") int reservationCode,
        @RequestBody @Valid PatchReservationStateRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = reservationService.patchReservationCancel(requestBody, reservationCode);
        return response;
    }

    // 예약 목록 리스트 삭제하기
    @DeleteMapping("/{reservationCode}")
    public ResponseEntity<ResponseDto> DeleteReservtionList (
        @AuthenticationPrincipal String userId,
        @PathVariable ("reservationCode") int reservationCode
    ) {
        ResponseEntity<ResponseDto> response = reservationService.deleteReservationList(reservationCode, userId);
        return response;
    }
}