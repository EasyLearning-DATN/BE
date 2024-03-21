package com.poly.easylearning.controller.admin;
import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.entity.User;
import com.poly.easylearning.payload.request.InvoiceRQ;
import com.poly.easylearning.service.IInvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        @RequestParam(value = SystemConstant.LIMIT_PAGE, required = false) Optional<Integer> limitPage)   {
        return ResponseEntity
            .status(SystemConstant.STATUS_CODE_SUCCESS)
            .body(invoiceService.getAllInvoice(currentPage, limitPage));
    }

// create invoice
@PostMapping("/create")
public ResponseEntity<?> createInvoice(@RequestBody InvoiceRQ invoiceRQ) {
    // Kiểm tra orderId đã tồn tại hay chưa
    if (invoiceService.existsByOrderId(invoiceRQ.getOrderID())) {
        // Nếu orderId đã tồn tại, trả về ResponseEntity với thông báo lỗi
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("OrderId đã tồn tại. Vui lòng chọn orderId khác.");
    }

    // Nếu orderId chưa tồn tại, tiến hành tạo invoice và trả về ResponseEntity thành công
    return ResponseEntity.status(SystemConstant.STATUS_CODE_SUCCESS)
            .body(invoiceService.createInvoice(invoiceRQ, new User()));
}

}
