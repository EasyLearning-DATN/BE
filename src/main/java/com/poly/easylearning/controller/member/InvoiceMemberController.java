package com.poly.easylearning.controller.member;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.request.InvoiceRequest;
import com.poly.easylearning.payload.response.InvoiceResponse;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_MEMBER + SystemConstant.VERSION_1 + SystemConstant.API_INVOICE)
public class InvoiceMemberController {
    private final IInvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<?> getListInvoice(
            @RequestParam(value = "status", defaultValue = "") String status,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "sort", defaultValue = "desc") String sort,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "10") int limit,
            @RequestParam(value = "userInfoId", required = false) Integer userInfoId,
            @RequestParam(value = "dateStart", defaultValue = "") String dateStart,
            @RequestParam(value = "dateEnd", defaultValue = "") String dateEnd,
            @RequestParam(value = "orderId", defaultValue = "") String orderId,
            @RequestParam(value = "transId", defaultValue = "") String transId
    ) {
        Sort.Direction direction = Sort.Direction.DESC;
        if (sort.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }
        PageRequest pageRequest = PageRequest.of(page, limit, direction, sortBy);
        return ResponseEntity
                .status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(invoiceService.getListInvoice(status, userInfoId, dateStart, dateEnd, orderId, transId, pageRequest));
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<InvoiceResponse>> getOneInvoice(
            @PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(invoiceService.getOneInvoice(id));
    }
    @PostMapping()
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        return ResponseEntity.status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(invoiceService.createInvoice(invoiceRequest));
    }

}
