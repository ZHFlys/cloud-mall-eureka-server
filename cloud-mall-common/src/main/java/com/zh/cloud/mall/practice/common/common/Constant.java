package com.zh.cloud.mall.practice.common.common;

import com.google.common.collect.Sets;
import com.zh.cloud.mall.practice.common.exception.ZhMallException;
import com.zh.cloud.mall.practice.common.exception.ZhMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 描述：  常量值
 */
@Component
public class Constant {
    public static final String ZH_MALL_USER = "zh_mall_user";
    public static final String SALT = "asfa.+652*";

    public static String FILE_UPLOAD_DIR;

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet(
"price desc", "price asc"
        );
    }

    public interface SaleStatus{
        int NOT_SALE = 0;//商品下架状态
        int SALE = 1;//商品上架状态
    }

    public interface Cart{
        int UN_CHECKED = 0;//购物车未选中状态
        int CHECKED = 1;//购物车选中状态
    }

    public enum OrderStatusEnum{
        CANCELED(0,"用户已取消"),
        NOT_PAID(10,"未付款"),
        PAID(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");

        private int code;
        private String value;

        OrderStatusEnum(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum :
                    values()) {
                if(orderStatusEnum.code == code){
                    return orderStatusEnum;
                }
            }
            throw new ZhMallException(ZhMallExceptionEnum.NO_ENUM);
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
