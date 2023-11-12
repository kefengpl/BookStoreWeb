<%--
  Created by IntelliJ IDEA.
  User: 3590
  Date: 2023/11/12
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 需要动态获取 base 标签 -->
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":"
            + request.getServerPort() + "/"
            + request.getContextPath() + "/"; // 1. 获取协议 HTTP 2. 获取服务器ip
%>
<!-- 注意：表达式可以直接嵌入 HTML的属性值里面！ -->
<base href="<%=basePath%>">
<link type="text/css" rel="stylesheet" href="static/css/style.css" >

<script type="text/javascript" src="pages/script/jquery-1.7.2.js"></script>
