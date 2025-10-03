package com.raj.jewellers.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raj.jewellers.dto.JewelleryItemRequestDTO;
import com.raj.jewellers.dto.LoginDto;
import com.raj.jewellers.dto.LoginRequestDto;
import com.raj.jewellers.dto.response.JewelleryItemResponseDto;

@Service
public interface ApiService {

    LoginDto loginUser(LoginRequestDto dto);

    Object saveItems(JewelleryItemRequestDTO itemRequestDTO,
            MultipartFile[] files);

    List<JewelleryItemResponseDto> fetchAllItems();

    Object dashBoradItemCount();
    
    Object deleteItemById(Long itemId);

}
