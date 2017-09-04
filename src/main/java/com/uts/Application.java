package com.uts;

import com.uts.netty.NettyClient;
import com.uts.netty.handler.SimpleChannelInitializer;
import com.uts.util.SpringUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@SpringBootApplication
@Configuration        // xml配置類 这个类是xml配置类 让spring boot 项目启动时识别当前配置类
@ComponentScan({"com.uts.*"})    // 扫描该包下 得注解 全局扫描  @RestController 等
@MapperScan(basePackages = "com.uts.dao")  //扫描dao
public class Application {

    @Bean(name = "bootstrap")
    public Bootstrap bootstrap() {
        Bootstrap b = new Bootstrap();
        b.group(bossGroup())
                .channel(NioSocketChannel.class)
                .handler(simpleChannelInitializer);
        return b;
    }

    @Autowired
    @Qualifier("simpleChannelInitializer")
    private SimpleChannelInitializer simpleChannelInitializer;

    @Value("${tcp.port}")
    private int tcpPort;

    @Value("${tcp.host}")
    private String tcpHost;

    @Bean(name = "bossGroup", destroyMethod = "shutdownGracefully")
    public NioEventLoopGroup bossGroup() {
        return new NioEventLoopGroup();
    }

    @Bean(name = "hostPortSocketAddress")
    public InetSocketAddress tcphost() {
        return new InetSocketAddress(tcpHost,tcpPort);
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        SpringUtil.setConfigContext(ctx);
        ctx.getBean(NettyClient.class).connect();
    }
}
