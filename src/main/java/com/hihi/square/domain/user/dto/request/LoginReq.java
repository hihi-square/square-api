package com.hihi.square.domain.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginReq {
   @NotNull
   @Size(min = 3, max = 50)
   private String uid;

   @NotNull
   @Size(min = 3, max = 100)
   private String password;
}