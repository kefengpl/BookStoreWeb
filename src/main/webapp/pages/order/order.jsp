<%@ page import="com.ruc.bookstoreweb.pojo.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
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
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">我的订单</span>
			<%@ include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
		<!-- 如果没有登录，就直接显示错误信息 -->
		${requestScope.myOrderErrorMsg}

		<table>

			<%
				if (request.getAttribute("myOrderErrorMsg") != null) {
			%>
					<tr>
						<td>日期</td>
						<td>金额</td>
						<td>状态</td>
						<td>详情</td>
					</tr>
			<%
				}
			%>

			<%
				Map<Integer, String> statusMap = new HashMap<>();
				statusMap.put(0, "未发货");
				statusMap.put(1, "已发货");
				statusMap.put(2, "已签收");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (Order item : (List<Order>) request.getAttribute("myOrders")) {
			%>
					<tr>
						<td><%=sdf.format(item.getCreateTime())%></td>
						<td><%=item.getPrice()%></td>
						<td><%=statusMap.get(item.getStatus())%></td>
						<td><a href="#">查看详情</a></td>
					</tr>

			<%
				}
			%>

		</table>
		
	
	</div>

	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>