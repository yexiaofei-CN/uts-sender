package com.uts.netty.handler;

import com.uts.proptocal.Response;
import com.uts.service.impl.TradeDetailService;
import com.uts.util.SpringUtil;
import com.uts.util.commont.CommonConst;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by yxf on 2017/9/4.
 */
@Component
@ChannelHandler.Sharable
@Qualifier("clientHandler")
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端连接了");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        /*ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req,"utf-8");
        System.out.println("Client:" +body);*/
        try {
            Response resp = (Response) msg;
            String id = resp.getId();
            String tag = resp.getTag();
            String type = resp.getType();
            String responseCode = resp.getResponseCode();
            String responseMessage = resp.getResponseMessage();

            if (CommonConst.TRADE_DETAIL.equals(tag)) {
                TradeDetailService tradeDetailService = (TradeDetailService) SpringUtil.getBean("tradeDetailService");

                if (CommonConst.RESPONSE_CODE_OK.equals(responseCode)) {
                    if (CommonConst.UPDATE.equals(type)) {
                        System.err.println("更新同步标志-----------: id: " + id);
                        tradeDetailService.updateStatusById(id);
                    }
                }
            }

        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    //发生异常时被调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
