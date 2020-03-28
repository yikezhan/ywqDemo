//package com.yuwuquan.demo.nio.nettydemo;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.ChannelHandlerContext;
//import java.util.Date;
//
//public class TimeServerHandler extends ChannelHandlerAdapter {
//    //        @Override
//
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req,"UTF-8");
//        System.out.println("The time server receive order : " + body);
//        String currentTime = "Query time order".equalsIgnoreCase(body)? (new Date()).toString() : "Bad order";
//        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//        ctx.write(resp);
//    }
//
//    @Override
//    public void channeReadComplete(ChannelHandlerContext ctx){
//        ctx.flush();
//    }
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
//        ctx.close();
//    }
//}
