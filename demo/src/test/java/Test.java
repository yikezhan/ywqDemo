import com.alibaba.fastjson.JSONObject;
import com.yuwuquan.demo.util.common.StringUtil;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test{
    private Test(){

    }
    private static class Single{
        private static final Test t = new Test();
    }
    public static Test getTest(){
        return Single.t;
    }

    public static void main(String[] args) {
        String addition ="{\"nsOrderId\":\"\",\"nsOrderItemId\":\"\",\"orderId\":\"\",\"orderItemId\":\"\",\"suppOrderId\":\"\"}";
        Map map = JSONObject.parseObject(addition, Map.class);
        Object subOrderId = map.get("nsOrderItemId");
        String vstSupplierOrderId = String.valueOf(map.get("suppOrderId"));
        if(subOrderId == null || vstSupplierOrderId == null){
            return;
        }
        System.out.println(subOrderId + "" + vstSupplierOrderId);
    }
}
