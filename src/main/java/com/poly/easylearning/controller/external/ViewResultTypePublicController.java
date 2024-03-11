
package com.poly.easylearning.controller.external;

import com.poly.easylearning.constant.SystemConstant;
import com.poly.easylearning.payload.response.RestResponse;
import com.poly.easylearning.payload.response.ViewResultTypeResponse;
import com.poly.easylearning.service.IViewResultTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(SystemConstant.API_PUBLIC + SystemConstant.VERSION_1 + SystemConstant.API_VIEW_RESULT_TYPE)
public class ViewResultTypePublicController {
    private final IViewResultTypeService viewResultTypeService;

    @GetMapping("")
    public ResponseEntity<RestResponse<List<ViewResultTypeResponse>>> getListViewResultType() {
        return ResponseEntity.ok(viewResultTypeService.getListViewResultType());
    }

    @GetMapping(SystemConstant.PATH_ID)
    public ResponseEntity<RestResponse<ViewResultTypeResponse>> getOneQuestionType(
            @PathVariable(name = "id") String code) {
        return ResponseEntity.ok(viewResultTypeService.getOneViewResultType(code));
    }
}
