<%--
  Created by IntelliJ IDEA.
  User: 3590
  Date: 2023/11/12
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <nav>
        <ul class="horizontal">
            <!-- 直接超链接到 BookServlet ，用最朴素的 ？传参 -->
            <li><a href="manager/book?action=page">图书管理</a></li>
            <li><a href="order?action=page&mode=manager">订单管理</a></li>
            <li><a href="index.jsp">返回商城</a></li>
        </ul>
    </nav>
</div>