package com.uts.controller;

import com.uts.entity.TradeDetail;
import com.uts.service.ITradeDetailService;
import com.uts.util.GeneratorDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yxf on 2017/9/5.
 */
@RestController
public class TreadC {

    @Autowired
    private ITradeDetailService tradeDetailService;

    @RequestMapping("/sender")
    public void sender(@RequestParam("count") Integer count) throws Exception {

        for(int i =0; i < count ; i ++){
            TradeDetail td = GeneratorDataUtil.generator();
            ///拿到数据以后， 调用service
            tradeDetailService.sender(td);
        }
    }
}
