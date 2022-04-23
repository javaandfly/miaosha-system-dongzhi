package com.dong.vo;

import com.dong.pojo.SkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private GoodVo goodVo;

    private SkUser user;
    private  Integer secKillStatus;
    private  Integer remainSeconds;
}
