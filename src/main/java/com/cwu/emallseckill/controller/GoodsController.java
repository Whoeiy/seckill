package com.cwu.emallseckill.controller;

import com.cwu.emallseckill.bo.GoodsBo;
import com.cwu.emallseckill.entity.User;
import com.cwu.emallseckill.redis.RedisService;
import com.cwu.emallseckill.redis.UserKey;
import com.cwu.emallseckill.result.CodeMsg;
import com.cwu.emallseckill.result.Result;
import com.cwu.emallseckill.service.ISeckillGoodsService;
import com.cwu.emallseckill.util.CookieUtil;
import com.cwu.emallseckill.vo.GoodsDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;

    @Autowired
    private RedisService redisService;

    /** 获取秒杀商品列表 **/
    @RequestMapping("/list")
    @ResponseBody
    public Result<List<GoodsBo>> list(){
        List<GoodsBo> seckillGoodsList = this.seckillGoodsService.getSeckillGoodsList();
        return new Result<List<GoodsBo>>(seckillGoodsList);
    }

    /** 获取秒杀商品的详细信息 **/
    @RequestMapping("/detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> goodsDetail(@PathVariable("goodsId") Long goodsId, HttpServletRequest request){
        String loginToken = CookieUtil.readLoginToken(request);
        User user = this.redisService.get(UserKey.getByName, loginToken, User.class);

        GoodsBo goods = this.seckillGoodsService.getSeckillGoodsBoByGoodsId(goodsId);
        if(ObjectUtils.isEmpty(goods)){
            return Result.error(CodeMsg.NO_GOODS);
        }else{

            Long seckillStartAt = goods.getStartDate().getTime();
            Long seckillEndAt = goods.getEndDate().getTime();
            Long now = System.currentTimeMillis();

            int seckillStatus = 0;
            int remainSeconds = 0;
            if(now < seckillStartAt){   // 秒杀还没开始，倒计时
                seckillStatus = 0;
                remainSeconds = (int)((seckillStartAt - now) / 1000);
            }else if(now > seckillEndAt){   // 秒杀已经结束
                seckillStatus = 2;
                remainSeconds = -1;
            }else{  // 秒杀中
                seckillStatus = 1;
                remainSeconds = 0;
            }

            GoodsDetailVo vo = new GoodsDetailVo();
            vo.setGoods(goods);
            vo.setUser(user);
            vo.setRemainSeconds(remainSeconds);
            vo.setSeckillStatus(seckillStatus);

            // 获取日期格式
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.getGoods().setCreateDateStr(formatter.format(vo.getGoods().getCreateDate()));
            vo.getGoods().setStartDateStr(formatter.format(vo.getGoods().getStartDate()));
            vo.getGoods().setEndDateStr(formatter.format(vo.getGoods().getEndDate()));

            return Result.success(vo);
        }

    }
}
