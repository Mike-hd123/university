package com.Mike.gateway.Filter;

import com.Mike.common.util.MyError;
import com.Mike.gateway.Service.Imp.TokenServiceImp;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class MyGlobalFilter implements GlobalFilter {
    @Autowired
    private TokenServiceImp tokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();
        System.out.println("进入了全局过滤器,path:[" + path + "]");
        // 放行资源
        String[] ex = {"/login"};
        for (String temp : ex) {
            if (path.equals(temp))
                return chain.filter(exchange.mutate().build());
        }
        // 检查token
        try {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (token == null)
                throw new MyError(400, "你还未登录!");
            tokenService.check(token);
        } catch (MyError e) {
            // 该方法，不会对为空的对象进行json化
            String json = JSONObject.toJSONString(e.getMyResponce());
            ServerHttpResponse response = exchange.getResponse();
            DataBuffer buffer = response.bufferFactory().wrap(json.getBytes());
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange.mutate().build());
    }
}
