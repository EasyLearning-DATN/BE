package com.poly.easylearning.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BaseResponse {
    private UUID id;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("created_by")
    private UUID createdBy;

    @JsonProperty("last_modified_date")
    private LocalDateTime lastModifiedDate;

    @JsonProperty("last_modified_by")
    private UUID lastModifiedBy;
}
