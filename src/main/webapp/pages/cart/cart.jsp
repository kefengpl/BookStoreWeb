<%@ page import="com.ruc.bookstoreweb.pojo.Cart" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.ruc.bookstoreweb.pojo.CartItem" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<!-- 静态包含 base标签，css样式，jQuery文件 -->
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {
			// a 表示 a标签，.deleteItemBtn 表示 class
			$("a.deleteItemBtn").click(function () {
				confirm("你确定要删除 [" + $(this).parent().parent().find("td:first").text() + "] 吗？");
			});
			$("a#clear-cart").click(function () {
				confirm("你确定要清空购物车吗？");
			});
			// 有一个onchange事件：修改输入框的值，然后输入框失去焦点，就会发生
			$(".updateCount").change(function () {
				// 获取商品名称
				var name = $(this).parent().parent().find("td:first").text();
				var value = $(this).val();
				var id = $(this).attr("bookid");
				if (value < 1 || !(value % 1 === 0)) {
					// defaultValue 表示 HTML 的默认 value 属性值
					this.value = $(this).attr("defaultval");
					alert("数据不合法，禁止修改");
					return;
				}
				confirm("你确定要将 [" + name + "] 商品数量修改为 [" + value + "] 吗？");
				// 发起请求，给服务器保存修改
				location.href = "http://localhost:8080/BookStoreWeb_war_exploded/cart?action=updateCount" +
						         "&id=" + id + "&count=" + value;
			});
		});
	</script>
</head>
<body>

	<%@ include file="/pages/common/login_success_menu.jsp"%>

		<table class="table">
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>

			<%
				Cart cart = (Cart) request.getSession().getAttribute("cart");
				if (cart != null) {
					for (Map.Entry<Integer, CartItem> item : cart.getItems().entrySet()) {
						%>
						<tr>
							<td><%=item.getValue().getName()%></td>
							<td><input class="updateCount" style="width: 80px;" bookid="<%=item.getValue().getId()%>"
									   defaultval="<%=item.getValue().getCount()%>"
									   type="text" value="<%=item.getValue().getCount()%>"></td>
							<td><%=item.getValue().getPrice()%></td>
							<td><%=item.getValue().getTotalPrice()%></td>
							<td><a class="join_cart" href="cart?action=deleteItem&id=<%=item.getValue().getId()%>">删除</a></td>
						</tr>
			<%
					}
				}
			%>
			<tr>
				<td>购物车中共有<span style="color:red;"><%=cart == null ? 0 : cart.getTotalCount()%></span>件商品</td>
				<td>总金额<span class="b_price"><%=cart == null ? 0.00 : cart.getTotalPrice()%></span>元</td>
				<td><a href="cart?action=clear" class="show_detail">清空购物车</a></td>
				<td><a href="order?action=createOrder" class="join_cart">去结账</a></td>
			</tr>

		</table>

	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>