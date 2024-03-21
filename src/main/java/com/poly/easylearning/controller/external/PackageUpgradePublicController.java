package com.poly.easylearning.controller.external;

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
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_PACKAGE_UPGRADE)
public class PackageUpgradePublicController {

    private final IPackageUpgradeService packageUpgradeService;

    @GetMapping("")
    public ResponseEntity<?> searchPackageUpgrade(
            @RequestParam(value = "key", defaultValue = "") String key,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity.ok(packageUpgradeService.getListPackageUpgrade(key, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<?> getOnePackageUpgrade(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(packageUpgradeService.getOnePackageUpgrade(id));
    }
}
