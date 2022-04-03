package com.dong.vo;

import com.dong.pojo.SkGoods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class GoodVo extends SkGoods {
    private BigDecimal seckillPrice;
    private Integer    stockCount;
    private Date       startDate;
    private Date       endDate;
}
