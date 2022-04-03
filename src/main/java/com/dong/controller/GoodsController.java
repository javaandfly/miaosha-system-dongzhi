package com.dong.controller;

import com.dong.pojo.SkUser;
import com.dong.service.ISkGoodsService;
import com.dong.service.ITUserService;
import com.dong.vo.DetailVo;
import com.dong.vo.GoodVo;
import com.dong.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DongZhi
 * @since 2022-03-09
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 跳转到商品列表页面
     * @param session
     * @param model
     * @param
     * @return
     */
    @Autowired
    ISkGoodsService iSkGoodsService;
    @Autowired
    ITUserService  itUserService;
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @RequestMapping(value = "/toList",//
            //produces属性可以设置返回数据的类型以及编码，可以是json或者xml
            produces = "text/html;charset=utf-8"  )
    @ResponseBody//直接返回到客户端
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, SkUser user){

        //redis的方法获取这个key对应的值 因为做的是页面缓存 所以强转为String类型
        ValueOperations<String, String> vs = redisTemplate.opsForValue();
        String html= vs.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("goodsList",iSkGoodsService.findGoodVo());
        model.addAttribute("user",user);
        //如果获取不到 页面为空 那么需要手动渲染存入redis 使用thymeleaf引擎
        WebContext webContext = new WebContext(request, response, request.getServletContext(),request.getLocale()
        ,model.asMap());
        //注意 这里的参数为页面的名字不上上面的goodsList；
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
            if (!StringUtils.isEmpty(html)){
               vs.set("goodsList",html,60, TimeUnit.MINUTES);
            }
        return html;
    }
    /**
     * 跳转到商品详情页面
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8" )
    @ResponseBody
    public String toDetail(HttpServletRequest request,HttpServletResponse response,Model model,SkUser user,@PathVariable Long goodsId){
        ValueOperations<String, String> ssv = redisTemplate.opsForValue();
        String html = ssv.get("goodsDetail");
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        model.addAttribute("user",user);
        GoodVo goodVo = iSkGoodsService.findGoodsVoByGoodsId(goodsId);
        //拿到对象开始调用方法获取到开始时间和结束时间
        Date startDate = goodVo.getStartDate();
        Date endDate = goodVo.getEndDate();
        //获取现在的时间
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            //当现在时间在开始时间前边，秒杀时间为开始时间减去现在时间
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
//如果现在时间小于最终时间，与前端呼应 等于0秒杀开始 等于1秒杀进行 等于2秒杀结束
          //所以当秒杀结束时 对secKillStatus 赋值为2
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //所以当秒杀中时 对secKillStatus 赋值为1
            secKillStatus = 1;
            remainSeconds = 0;
        }
        //把对象和获取的时间传入到前端 前端就可以调用对象里的方法
        model.addAttribute("goods",goodVo);
        model.addAttribute("seckillStatus",secKillStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        WebContext webContext = new WebContext(request, response, request.getServletContext(),request.getLocale()
                ,model.asMap());
        //注意 这里的参数为页面的名字不上上面的goodsList；
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", webContext);
        if (!StringUtils.isEmpty(html)){
            ssv.set("goodsDetail",html,60, TimeUnit.MINUTES);
        }
        return html;
    }


    @RequestMapping( "/toDetail2/{goodsId}")
    @ResponseBody
    public RespBean toDetail2(HttpServletRequest request, HttpServletResponse response, Model model, SkUser user, @PathVariable Long goodsId){
        GoodVo goodVo = iSkGoodsService.findGoodsVoByGoodsId(goodsId);
        //拿到对象开始调用方法获取到开始时间和结束时间
        Date startDate = goodVo.getStartDate();
        Date endDate = goodVo.getEndDate();
        //获取现在的时间
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        //秒杀还未开始
        if (nowDate.before(startDate)) {
            //当现在时间在开始时间前边，秒杀时间为开始时间减去现在时间
            remainSeconds = ((int) ((startDate.getTime() - nowDate.getTime()) / 1000));
        } else if (nowDate.after(endDate)) {
        //如果现在时间小于最终时间，与前端呼应 等于0秒杀开始 等于1秒杀进行 等于2秒杀结束
            //所以当秒杀结束时 对secKillStatus 赋值为2
            secKillStatus = 2;
            remainSeconds = -1;
        } else {
            //所以当秒杀中时 对secKillStatus 赋值为1
            secKillStatus = 1;
            remainSeconds = 0;
        }
        DetailVo detail = new DetailVo();
        detail.setGoodVo(goodVo);
        detail.setUser(user);
        detail.setSecKillStatus(secKillStatus);
        detail.setRemainSeconds(remainSeconds);
        return RespBean.success(detail);
    }

}
