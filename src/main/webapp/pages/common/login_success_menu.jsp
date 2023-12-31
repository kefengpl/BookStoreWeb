<%--
  Created by IntelliJ IDEA.
  User: 3590
  Date: 2023/11/12
  Time: 16:11
  To change this template use File | Settings | File Templates.
  用于静态包含登录欢迎部分的公共内容。
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <!-- 注意：仅使用 request域中的参数进行用户名回显，将使得除了 login_success.jsp 以外
     的文件的这个导航条都不能显示用户名。因此，需要扩大范围，改为 session 级别的显示
     -->
    <nav>
        <ul class="horizontal">
            <li><span>欢迎${sessionScope.username}光临书城</span></li>
            <li><a href="order?action=page&mode=user">我的订单</a></li>
            <li><a href="user?action=logout">退出登录</a></li>
            <li><a href="index.jsp">返回商城</a></li>
        </ul>
    </nav>
</div>
