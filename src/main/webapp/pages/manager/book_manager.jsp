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
					<td><a href="manager/book?pageType=modify&id=<%=book.getId()%>&action=getBook">修改</a></td>
					<td><a href="#">删除</a></td>
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
				<td><a href="pages/manager/book_edit.jsp?pageType=add">添加图书</a></td>
			</tr>	
		</table>
	</div>

	<div id="page_nav">
		<a href="manager/book?action=page&pageNo=1">首页</a>
		<a href="manager/book?action=page&pageNo=${requestScope.page.pageNo - 1}">上一页</a>
		<a href="manager/book?action=page&pageNo=${requestScope.page.pageNo - 1}">${requestScope.page.pageNo - 1 > 0 ? requestScope.page.pageNo - 1 : null}</a>
		【${requestScope.page.pageNo}】
		<a href="manager/book?action=page&pageNo=${requestScope.page.pageNo + 1}">${requestScope.page.pageNo + 1}</a>
		<a href="manager/book?action=page&pageNo=${requestScope.page.pageNo + 1}">下一页</a>
		<a href="manager/book?action=page&pageNo=${requestScope.page.pageTotal}">末页</a>
		共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录 到第<input value="4" name="pn" id="pn_input"/>页
		<input type="button" value="确定">
	</div>

	<!-- 公共部分的页脚 -->
	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>