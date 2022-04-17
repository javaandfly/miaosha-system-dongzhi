package com.dong.vo;

import com.dong.validator.IsMobile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
@Data
public class RegisterVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    private String nickname;
    @NotNull
    @Length(min = 32)
    private String password;
}
