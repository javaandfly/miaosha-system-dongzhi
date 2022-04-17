package com.dong.vo;

import com.dong.pojo.SkOrder;
import com.dong.pojo.SkOrderInfo;
import com.dong.pojo.SkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private GoodVo      goodVo;
    private SkOrderInfo     order;
    private SkUser  user;
  }
