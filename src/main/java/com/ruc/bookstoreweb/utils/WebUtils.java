package com.ruc.bookstoreweb.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.beans.JavaBean;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author 3590
 * @Date 2023/11/12 19:33
 * @Description
 * @Version
 */
public class WebUtils {
    /**
     * @descriotion 将 request 包含的表单参数值注入 javaBean
     * @param request 请求对象
     * @T bean 对应的javaBean
     * */
    public static <T> void copyParamToBean(HttpServletRequest request, T bean) {
        try {
            BeanUtils.populate(bean, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @descriotion 对楼上函数的优化，这样做的理由是，javaWeb 代码有三层，
     * Dao Service Web，如果我把参数写为楼上的 HttpServletRequest request
     * 那么 Dao Service 层无法使用，因为 request 是 Web 层的 Servlet 程序才有的东西。
     * 改成 Map 后， Dao Service 也可以使用，耦合度更低，扩展性更好
     * @param value request 请求对象报文包含的参数
     * @T bean 对应的 javaBean
     * */
    public static <T> T copyParamToBean(Map value, T bean) {
        try {
            BeanUtils.populate(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static Integer parseValue(String requestParam, Integer defaultValue) {
        if (requestParam == null) {
            return defaultValue;
        } else {
            return Integer.parseInt(requestParam);
        }
    }
}
