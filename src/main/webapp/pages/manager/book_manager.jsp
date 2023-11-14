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
	
	<div id="header">
			<img class="logo_img" alt="" src="../../static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
		<%@include file="/pages/common/manager_menu.jsp"%>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>名称</td>
				<td>价格</td>
				<td>作者</td>
				<td>销量</td>
				<td>库存</td>
				<td colspan="2">操作</td>
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
					<td><a href="manager/book?pageType=modify&id=<%=book.getId()%>&action=getBook&pageNo=${requestScope.page.pageNo}">修改</a></td>
					<td><a href="manager/book?pageType=modify&id=<%=book.getId()%>&action=delete&pageNo=${requestScope.page.pageNo}">删除</a></td>
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
				<td><a href="pages/manager/book_edit.jsp?pageType=add&pageNo=${requestScope.page.pageNo}">添加图书</a></td>
			</tr>	
		</table>
	</div>

	<!-- 底部分页条 -->
	<%@ include file="/pages/common/bottom_bar.jsp"%>

	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>