package com.poly.easylearning.service;

import com.poly.easylearning.exception.DataNotFoundException;
import com.poly.easylearning.payload.request.PackageUpgradeRequest;
import com.poly.easylearning.payload.response.ListResponse;
import com.poly.easylearning.payload.response.PackageUpgradeResponse;
import com.poly.easylearning.payload.response.RestResponse;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IPackageUpgradeService {
    RestResponse<ListResponse<PackageUpgradeResponse>> getListPackageUpgrade(String keyword, PageRequest pageRequest);

    RestResponse<PackageUpgradeResponse> getOnePackageUpgrade(UUID id) throws DataNotFoundException;

    RestResponse<PackageUpgradeResponse> createPackageUpgrade(PackageUpgradeRequest PackageUpgradeRequest);

    RestResponse<PackageUpgradeResponse> updatePackageUpgrade(UUID id, PackageUpgradeRequest PackageUpgradeRequest) throws DataNotFoundException;

    void deletePackageUpgrade(UUID id) throws DataNotFoundException;
}