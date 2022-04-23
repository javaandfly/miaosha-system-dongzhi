package com.dong.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VCodeVo {
    private String mobile;
    private String email;
    private String vCode;
}
