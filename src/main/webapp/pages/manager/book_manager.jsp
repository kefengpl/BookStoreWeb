<%@ page import="java.util.List" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Book" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>图书管理</title>
	<!-- 静态包含 base标签，css样式，jQuery文件 -->
	<%@include file="/pages/common/head.jsp"%>
</head>
<body>
	<%@include file="/pages/common/manager_menu.jsp"%>
		<table class="table">
			<tr>
				<th>名称</th>
				<th>价格</th>
				<th>作者</th>
				<th>销量</th>
				<th>库存</th>
				<th colspan="2">操作</th>
			</tr>

			<%
				for (Book book : ((Page<Book>) request.getAttribute("page")).getItems()) {
					%>
				<tr>
					<td><%=book.getName()%></td>
					<td><%=book.getPrice()%></td>
					<td><%=book.getAuthor()%></td>
					<td><%=book.getSales()%></td>
					<td><%=book.getStock()%></td>
					<!-- 将 GET 请求转发到 Servlet 程序当中 -->
					<td><a href="manager/book?pageType=modify&id=<%=book.getId()%>&action=getBook&pageNo=${requestScope.page.pageNo}" class="show_detail">修改</a></td>
					<td><a href="manager/book?pageType=modify&id=<%=book.getId()%>&action=delete&pageNo=${requestScope.page.pageNo}" class="show_detail">删除</a></td>
				</tr>
			<%
				}
			%>

			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_edit.jsp?pageType=add&pageNo=${requestScope.page.pageNo}" class="join_cart">添加图书</a></td>
			</tr>	
		</table>

	<!-- 底部分页条 -->
	<%@ include file="/pages/common/bottom_bar.jsp"%>

	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>