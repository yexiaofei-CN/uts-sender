package com.uts.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by yxf on 2017/9/4.
 */
@Component
public class NettyClient {
    private static Logger logger = LoggerFactory.getLogger(NettyClient.class);

    @Autowired
    @Qualifier("bootstrap")
    private Bootstrap bootstrap;

    @Autowired
    @Qualifier("hostPortSocketAddress")
    private InetSocketAddress tcpHostPort;

    private ChannelFuture cf;

    private static class SingletionHolder {
        private static NettyClient instance = new NettyClient();
    }

    public static NettyClient getInstance() {
        return SingletionHolder.instance;
    }

    private NettyClient() {
    }

    public void connect() throws InterruptedException {
        try {
            logger.debug("===== inital nettyClient connetc...=====================");
            cf = bootstrap.connect(tcpHostPort).sync();
            logger.debug("===== inital nettyClient success...=====================");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ChannelFuture getChannelFuture() {
        if (this.cf == null) {
            bootstrap.connect();
        }
        if (!this.cf.channel().isActive()) {
            bootstrap.connect();
        }
        return this.cf;
    }
}
