package com.raj.jewellers.service.impl;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.raj.jewellers.dto.JewelleryItemRequestDTO;
import com.raj.jewellers.dto.LoginDto;
import com.raj.jewellers.dto.LoginRequestDto;
import com.raj.jewellers.dto.response.JewelleryItemResponseDto;
import com.raj.jewellers.entity.JewelleryItemEntity;
import com.raj.jewellers.entity.MasterCategoryEntity;
import com.raj.jewellers.entity.MasterMaterialEntity;
import com.raj.jewellers.entity.UserLoginEntity;
import com.raj.jewellers.enums.HttpStatusEnum;
import com.raj.jewellers.exception.CustomException;
import com.raj.jewellers.respository.JewelleryItemRepository;
import com.raj.jewellers.respository.MasterCategoryRepository;
import com.raj.jewellers.respository.MasterMaterialRepository;
import com.raj.jewellers.respository.UserLoginRepository;
import com.raj.jewellers.service.ApiService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Autowired
    private UserLoginRepository loginRepository;

    @Autowired
    private JewelleryItemRepository itemRepository;

    @Autowired
    private MasterCategoryRepository categoryRepository;

    @Autowired
    private MasterMaterialRepository materialRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.upload_prefix}")
    private String uploadPrefix;

    @Override
    public LoginDto loginUser(LoginRequestDto dto) {
        UserLoginEntity loginDetailsDb =
                loginRepository.findByLoginId(dto.getEmailId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatusEnum.BAD_REQUEST,
                                "Invalid email id !!!"));
        if (!loginDetailsDb.getUserPassword().equals(dto.getPassword())) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Invalid password !!!");
        }
        return LoginDto.builder().loginId(loginDetailsDb.getLoginId())
                .userName(loginDetailsDb.getUserName())
                .id(loginDetailsDb.getId()).build();
    }

    @Override
    public Object saveItems(final JewelleryItemRequestDTO itemRequestDTO,
            final MultipartFile[] files) {

        StringBuilder allDocumentPath = new StringBuilder();
        for (MultipartFile multipartFile : files) {

            try {
                Map uploadResult = cloudinary.uploader().upload(
                        multipartFile.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto",
                                "folder", uploadPrefix));

                String documentPath =
                        uploadResult.get("secure_url").toString();

                allDocumentPath.append(documentPath).append(";");
            } catch (Exception e) {
                log.error("Error while uploading file: {}",
                        e.getMessage());
                throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                        "Error while uploading file.");
            }

        }
        allDocumentPath.deleteCharAt(allDocumentPath.length() - 1);

        MasterCategoryEntity categoryEntity =
                categoryRepository.findById(itemRequestDTO.getCategoryId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatusEnum.BAD_REQUEST,
                                "Invalid Category Id !!!"));

        MasterMaterialEntity materialEntity =
                materialRepository.findById(itemRequestDTO.getMaterialId())
                        .orElseThrow(() -> new CustomException(
                                HttpStatusEnum.BAD_REQUEST,
                                "Invalid Material id !!"));

        JewelleryItemEntity entiry =
                convertDtoToEntiry(itemRequestDTO, categoryEntity,
                        materialEntity, allDocumentPath.toString());

        return itemRepository.save(entiry);
    }

    private JewelleryItemEntity convertDtoToEntiry(
            JewelleryItemRequestDTO dto,
            MasterCategoryEntity categoryEntity,
            MasterMaterialEntity materialEntity, String imagePaths) {

        return JewelleryItemEntity.builder().itemName(dto.getItemName())
                .category(categoryEntity).material(materialEntity)
                .purity(dto.getPurity()).weight(dto.getWeight())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity() != null
                        ? dto.getStockQuantity() : 0)
                .description(dto.getDescription()).imagePaths(imagePaths)
                .stockAvailable(dto.getStockAvailable() != null).build();

    }

    @Override
    public List<JewelleryItemResponseDto> fetchAllItems() {
        List<JewelleryItemEntity> dbItems = itemRepository.findAll();

        return dbItems.stream().map(item -> JewelleryItemResponseDto
                .builder().id(item.getId()).itemName(item.getItemName())
                .categoryId(item.getCategory() != null
                        ? item.getCategory().getCategoryId() : null)
                .categoryName(item.getCategory() != null
                        ? item.getCategory().getCategoryName() : null)
                .materialId(item.getMaterial() != null
                        ? item.getMaterial().getMaterialId() : null)
                .materialName(item.getMaterial() != null
                        ? item.getMaterial().getMaterialName() : null)
                .purity(item.getPurity()).weight(item.getWeight())
                .price(item.getPrice()).description(item.getDescription())
                .images(Arrays.asList(item.getImagePaths().split(";")))
                .build()).toList();
    }

    @Override
    public Object dashBoradItemCount() {
        List<JewelleryItemResponseDto> dbItems = fetchAllItems();

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("total", dbItems.size());

        // 2. Category total (number of unique categories)
        long categoryTotal = dbItems.stream()
                .map(JewelleryItemResponseDto::getCategoryName).distinct()
                .count();
        result.put("categoryTotal", categoryTotal);

        // 3. Material total (number of unique materials)
        long materialTotal = dbItems.stream()
                .map(JewelleryItemResponseDto::getMaterialName).distinct()
                .count();
        result.put("materialTotal", materialTotal);

        // 4. Category-wise data with nested material counts
        List<Map<String, Object>> categoryWise = dbItems.stream()
                .collect(Collectors.groupingBy(
                        JewelleryItemResponseDto::getCategoryName,
                        Collectors.toList()))
                .entrySet().stream().map(entry -> {
                    String categoryName = entry.getKey();
                    List<JewelleryItemResponseDto> categoryItems =
                            entry.getValue();

                    Map<String, Object> categoryData =
                            new LinkedHashMap<>();
                    categoryData.put("categoryName", categoryName);
                    categoryData.put("categoryCount",
                            (long) categoryItems.size());

                    // Material counts for this category
                    Map<String, Long> categoryMaterial = categoryItems
                            .stream()
                            .collect(Collectors.groupingBy(
                                    JewelleryItemResponseDto::getMaterialName,
                                    Collectors.counting()));
                    categoryData.put("categoryMaterial", categoryMaterial);

                    return categoryData;
                }).toList();

        result.put("categoryWise", categoryWise);

        // 5. Total material count (sum of all items across all materials)
        long totalMaterialCount = dbItems.stream()
                .collect(Collectors.groupingBy(
                        JewelleryItemResponseDto::getMaterialName,
                        Collectors.counting()))
                .values().stream().mapToLong(Long::longValue).sum();
        result.put("totalMaterialCount", totalMaterialCount);
        List<String> materialList = dbItems.stream()
                .map(JewelleryItemResponseDto::getMaterialName).distinct()
                .sorted().collect(Collectors.toList());
        result.put("materialList", materialList);

        return result;
    }

    @Override
    public Object deleteItemById(Long itemId) {
        itemRepository.findById(itemId).ifPresentOrElse(
                item -> itemRepository.deleteById(itemId),
                () -> new CustomException(HttpStatusEnum.BAD_REQUEST,
                        "Invalid item id."));
        return "Data is deleted for the Item Id: " + itemId;
    }

}
