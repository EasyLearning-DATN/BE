package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.PackageUpgradeRequest;
import com.poly.easylearning.service.IPackageUpgradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_PACKAGE_UPGRADE)
public class PackageUpgradeAdminController {

    private final IPackageUpgradeService packageUpgradeService;

    @PostMapping("")
    public ResponseEntity<?> createPackUpgrade(
            @Valid @RequestBody PackageUpgradeRequest requestBody,
            BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(packageUpgradeService.createPackageUpgrade(requestBody));
    }

    @PutMapping(SystemConstant.PATH_ID)
    public ResponseEntity<?> updateQuestionType(
            @PathVariable(name = "id") UUID id,
            @Valid @RequestBody PackageUpgradeRequest requestBody,
            BindingResult result) throws BindException {
        if (result.hasErrors()) {
            throw new BindException(result);
        }
        return ResponseEntity.ok(packageUpgradeService.updatePackageUpgrade(id, requestBody));
    }

    @DeleteMapping(SystemConstant.PATH_ID)
    public ResponseEntity<Void> deleteQuestionType(@PathVariable(name = "id") UUID id) {
        packageUpgradeService.deletePackageUpgrade(id);
        return ResponseEntity.noContent().build();
    }

}
