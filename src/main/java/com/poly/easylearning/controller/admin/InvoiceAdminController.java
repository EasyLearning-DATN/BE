package com.poly.easylearning.controller.admin;

import com.poly.easylearning.constant.ResourceBundleConstant;
import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.Invoice;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.InvoiceRQ;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_ADMIN + SystemConstant.VERSION_1 + SystemConstant.API_INVOICE)
public class InvoiceAdminController {
    private final IInvoiceService invoiceService;
//    get all invoice
@GetMapping("/get-all")
public ResponseEntity<?> getAllInvoice(
        @RequestParam(value = SystemConstant.CURRENT_PAGE, required = false) Optional<Integer> currentPage,
        @RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage
){
    return ResponseEntity
            .status(SystemConstant.STATUS_CODE_SUCCESS)
            .body(invoiceService.getAllInvoice(currentPage, limitPage));
}

// create invoice
    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRQ invoiceRQ){
        return ResponseEntity.status(SystemConstant.STATUS_CODE_SUCCESS)
                .body(invoiceService.createInvoice(invoiceRQ, new User()));
    }


}
