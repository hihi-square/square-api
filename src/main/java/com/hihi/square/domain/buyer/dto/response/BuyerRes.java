package com.hihi.square.domain.buyer.dto.response;

import com.hihi.square.domain.user.entity.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerRes {
    private Integer usrId;
    private String nickname;
    private String email;
    private String mainAddress;
}
