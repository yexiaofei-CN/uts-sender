package com.uts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.uts.dao.mysql.TradeDetailDao;
import com.uts.entity.TradeDetail;
import com.uts.proptocal.Request;
import com.uts.service.ITradeDetailService;
import com.uts.util.commont.CommonConst;
import io.netty.channel.ChannelFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yxf on 2017/9/5.
 */
@Service
public class TradeDetailService implements ITradeDetailService {

    @Autowired
    TradeDetailDao tradeDetailDao;

    @Resource
    ChannelFuture cf;
    @Override
    public void sender(TradeDetail tradeDetail) {

        tradeDetailDao.insert(tradeDetail);

    //   ChannelFuture cf = NettyClient.getInstance().getChannelFuture();
        Request request = new Request();
        request.setId(tradeDetail.getId());
        request.setRequestMessage(JSONObject.toJSONString(tradeDetail));
        request.setTarget(CommonConst.TRADE_DETAIL);
        request.setType(CommonConst.SAVE);
        String s = JSON.toJSONString(request);
        cf.channel().writeAndFlush(request);
    }

    public void updateStatusById(String id) {
        tradeDetailDao.updateStatusById(id);
    }
}
