package com.poly.easylearning.payload.response;

import com.poly.easylearning.entity.ViewResultType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ViewResultTypeResponse extends BaseResponse {
    private String code;
    private String name;

    public static ViewResultTypeResponse fromViewResultType(ViewResultType viewResultType) {
        return ViewResultTypeResponse.builder()
                .id(viewResultType.getId())
                .createdDate(viewResultType.getCreatedDate())
                .createdBy(viewResultType.getCreatedBy())
                .lastModifiedDate(viewResultType.getLastModifiedDate())
                .lastModifiedBy(viewResultType.getLastModifiedBy())
                .name(viewResultType.getName())
                .code(viewResultType.getCode())
                .build();
    }

}
