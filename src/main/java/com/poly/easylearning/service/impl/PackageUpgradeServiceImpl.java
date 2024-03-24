package com.poly.easylearning.service.impl;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.entity.PackageUpgrade;
import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.PackageUpgradeRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.PackageUpgradeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.repo.IPackageUpgradeRepo;
import com.poly.easylearning.service.IPackageUpgradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class PackageUpgradeServiceImpl implements IPackageUpgradeService {

    private final IPackageUpgradeRepo packageUpgradeRepo;

    @Override
    public RestResponse<ListResponse<PackageUpgradeResponse>> getListPackageUpgrade(String keyword, PageRequest pageRequest) {
        Page<PackageUpgrade> pageResponse = packageUpgradeRepo.searchPackageUpgrade(keyword, pageRequest);
        List<PackageUpgradeResponse> packageUpgradeResponses = pageResponse.get().map(PackageUpgradeResponse::fromPackageUpgrade).toList();
        ListResponse<PackageUpgradeResponse> listResponse = ListResponse.build(pageResponse.getTotalPages(), packageUpgradeResponses);
        return RestResponse.ok(ResourceBundleConstant.PKU_6003,
                listResponse);
    }

    @Override
    public RestResponse<PackageUpgradeResponse> getOnePackageUpgrade(UUID id) throws DataNotFoundException {
        PackageUpgrade packageUpgrade = packageUpgradeRepo.getPackageUpgradeById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.PKU_6001));
        PackageUpgradeResponse packageUpgradeResponse = PackageUpgradeResponse.fromPackageUpgrade(packageUpgrade);

        return RestResponse.ok(ResourceBundleConstant.PKU_6004, packageUpgradeResponse);
    }

    @Override
    public RestResponse<PackageUpgradeResponse> createPackageUpgrade(PackageUpgradeRequest packageUpgradeRequest) {
        PackageUpgrade newPackageUpgrade = PackageUpgrade.builder()
                .name(packageUpgradeRequest.getName())
                .description(packageUpgradeRequest.getDescription())
                .price(packageUpgradeRequest.getPrice())
                .sale(packageUpgradeRequest.getSale())
                .expiry(packageUpgradeRequest.getExpiry())
                .build();

        PackageUpgrade packageUpgrade = packageUpgradeRepo.save(newPackageUpgrade);
        PackageUpgradeResponse response = PackageUpgradeResponse.fromPackageUpgrade(packageUpgrade);
        return RestResponse.created(ResourceBundleConstant.PKU_6002, response);
    }

    @Override
    public RestResponse<PackageUpgradeResponse> updatePackageUpgrade(UUID id, PackageUpgradeRequest packageUpgradeRequest) throws DataNotFoundException {
        PackageUpgrade existingPackageUpgrade = packageUpgradeRepo.getPackageUpgradeById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.PKU_6001));
        existingPackageUpgrade.setName(packageUpgradeRequest.getName());
        existingPackageUpgrade.setDescription(packageUpgradeRequest.getDescription());
        existingPackageUpgrade.setPrice(packageUpgradeRequest.getPrice());
        existingPackageUpgrade.setSale(packageUpgradeRequest.getSale());
        existingPackageUpgrade.setExpiry(packageUpgradeRequest.getExpiry());

        PackageUpgrade packageUpgrade = packageUpgradeRepo.save(existingPackageUpgrade);

        PackageUpgradeResponse response = PackageUpgradeResponse.fromPackageUpgrade(packageUpgrade);
        return RestResponse.accepted(ResourceBundleConstant.PKU_6008, response);
    }

    @Override
    public void deletePackageUpgrade(UUID id) throws DataNotFoundException {
        //Xóa mềm
        PackageUpgrade existingPackageUpgrade = packageUpgradeRepo.getPackageUpgradeById(id).orElseThrow(() -> new DataNotFoundException(ResourceBundleConstant.PKU_6001));
        existingPackageUpgrade.setIsDeleted(true);
        packageUpgradeRepo.save(existingPackageUpgrade);
    }
}