<%@ page import="com.ruc.bookstoreweb.pojo.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.ruc.bookstoreweb.pojo.OrderItem" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Page" %>
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
            <td><b>订单编号</b></td>
            <td><b>创建日期</b></td>
            <td><b>订单金额</b></td>
            <td><b>订单状态</b></td>
            <td><b>下单用户</b></td>
            <td><b>订单发货</b></td>
            <td><b>订单详情</b></td>
        </tr>

        <%
            Map<Integer, String> statusMap = (Map<Integer, String>) application.getAttribute("orderStatusMap");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Order order : ((Page<Order>) request.getAttribute("page")).getItems()) {
        %>
            <tr>
                <td><%=order.getOrderId()%></td>
                <td><%=dateFormat.format(order.getCreateTime())%></td>
                <td><%=order.getPrice()%></td>
                <td><%=statusMap.get(order.getStatus())%></td>
                <td><%=order.getUserId()%></td>
                <td>
                    <% if (order.getStatus() == 0) {%>
                        <a href="order?action=sendOrder&id=<%=order.getOrderId()%>" class="join_cart">现在发货</a>
                    <% } else { %>
                        <p class="join_cart" style="background: grey;">已经发货</p>
                    <% } %>
                </td>
                <td><a href="order?action=showOrderDetail&id=<%=order.getOrderId()%>" class="show_detail">查看详情</a></td>
            </tr>
        <%
            }
        %>
    </table>

    <%@ include file="/pages/common/bottom_bar.jsp"%>
<!-- 公共部分的页脚 -->
<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>
