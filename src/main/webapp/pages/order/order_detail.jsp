<%@ page import="com.ruc.bookstoreweb.pojo.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.ruc.bookstoreweb.pojo.OrderItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>
    <!-- 静态包含 base标签，css样式，jQuery文件 -->
    <%@include file="/pages/common/head.jsp"%>
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<%@ include file="/pages/common/login_success_menu.jsp"%>


    <table class="table">

        <tr>
            <td><b>图书名称</b></td>
            <td><b>购买数量</b></td>
            <td><b>图书单价</b></td>
            <td><b>图书总价</b></td>
        </tr>

        <%
            for (OrderItem orderItem : (List<OrderItem>) request.getAttribute("orderDetails")) {
        %>
            <tr>
                <td><%=orderItem.getName()%></td>
                <td><%=orderItem.getCount()%></td>
                <td><%=orderItem.getPrice()%></td>
                <td><%=orderItem.getTotalPrice()%></td>
            </tr>
        <%
            }
        %>

        <tr>
            <td><b>订单编号</b></td>
            <td><b>${param.id}</b></td>
            <td></td>

            <td><a href="<%=request.getHeader("Referer")%>" class="show_detail">返回</a></td>
        </tr>

    </table>


<!-- 公共部分的页脚 -->
<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>
