package com.poly.easylearning.payload.response;

import com.poly.easylearning.entity.Image;
import com.poly.easylearning.entity.Question;
import com.poly.easylearning.entity.UserInfo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserInfoResponse {
    private Integer id;
    private String fullName;
    private Image avatar;

    public static UserInfoResponse fromUserInfo(UserInfo userInfo) {
        if(userInfo != null) {
            return UserInfoResponse.builder()
                    .id(userInfo.getId())
                    .fullName(userInfo.getFullName())
                    .avatar(userInfo.getAvatar())
                    .build();
        }

        return null;
    }
}
