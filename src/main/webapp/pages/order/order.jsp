<%@ page import="com.ruc.bookstoreweb.pojo.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
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

	<!-- <div id="main"> -->
		<table class="table">

			<%
				if (request.getAttribute("myOrderErrorMsg") == null) {
			%>
					<tr>
						<td><b>日期</b></td>
						<td><b>金额</b></td>
						<td><b>状态</b></td>
						<td><b>详情</b></td>
						<td><b>收货</b></td>
					</tr>
			<%
				}
			%>

			<%
				Map<Integer, String> statusMap = (Map<Integer, String>) application.getAttribute("orderStatusMap");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (Order item : ((Page<Order>) request.getAttribute("page")).getItems()) {
			%>
					<tr>
						<td><%=sdf.format(item.getCreateTime())%></td>
						<td><%=item.getPrice()%></td>
						<td><%=statusMap.get(item.getStatus())%></td>
						<td><a href="order?action=showOrderDetail&id=<%=item.getOrderId()%>" class="show_detail">查看详情</a></td>
						<td>
							<%if (item.getStatus() == 0) {%>
								<p class="join_cart" style="background: grey;">尚未发货</p>
							<%} else if (item.getStatus() == 1) {%>
								<a href="order?action=receiveOrder&id=<%=item.getOrderId()%>" class="join_cart">确认收货</a>
							<%} else {%>
							<p class="join_cart" style="background: grey;">已经收货</p>
							<%}%>
						</td>
					</tr>

			<%
				}
			%>

		</table>
	<!-- 底部分页条 -->
	<%@ include file="/pages/common/bottom_bar.jsp"%>
	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>