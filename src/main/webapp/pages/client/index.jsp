<%@ page import="com.ruc.bookstoreweb.web.BookServlet" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Page" %>

<%--
只要TOMCAT启动，就会直接显示 index.jsp
进来这个页面就需要查询好的分页数据，但是 jsp 无法查询，肯定需要访问 一个 Servlet 程序
我们需要准备一个 ClientBookServlet 程序，用于处理分页
问题是，http://localhost:8080/BookStoreWeb_war_exploded/这种请求地址如何让它访问一个 Servlet 程序？
我们使用 web/pages/client/index.jsp，目录中的 index.jsp 与 web/index.jsp 一样
web/index.jsp 只做请求转发，先转发到 ClientBookServlet ，然后再转发到  web/pages/client/index.jsp

首页还需要什么功能？价格区间搜索，搜索出对应列表后，仍然需要做分页处理
在首页的价格区间点击查询后，进入 public void pageByPrice() {} 处理价格区间的分页
	1. 获取请求的参数
	2. 调用 bookService.pageByPrice (pageNo, pageSize, minVal, maxVal)
	3. 保存分页对象到 request 域中
	4. 请求转发到 /pages/client/index.jsp
在 service 层，需要实现 public Page pageByPrice (pageNo, pageSize, minVal, maxVal)
	主要求总记录数，总页码和当前页数据
	总记录数： select count(*) from t_book where price between 10 and 50;
	当前页数据： select * from t_book where price between 10 and 50 limit begin, size
在 DAO 层，我们需要完成 queryForPageTotalCount(min, max) 求总记录数
		  还需要获取当前页的数据 queryForPageItems(begin, size, min, max) 求当前页数据
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>书城首页</title>
	<%@include file="/pages/common/head.jsp"%>
</head>
<body>
	<div id="header">
			<!--<img class="logo_img" alt="" src="static/img/logo.gif" >-->
			<span class="wel_word">网上书城</span>
			<div>
				<a href="pages/user/login.jsp">登录</a> |
				<a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
				<a href="pages/cart/cart.jsp">购物车</a>
				<a href="pages/manager/manager.jsp">后台管理</a>
			</div>
	</div>
	<div id="main">
		<div id="book">
			<div class="book_cond">
				<form action="client/book?pageNo=${requestScope.page.pageNo}" method="get">
					价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
						<input id="max" type="text" name="max" value="${param.max}"> 元
						<input type="hidden" name="action" value="pageByPrice">
						<input type="hidden" name="curPageNo" value="${requestScope.page.pageNo}">
						<input type="submit" value="查询" />
						${requestScope.writeErrorMsg}
				</form>
			</div>
			<div style="text-align: center">
				<span>您的购物车中有3件商品</span>
				<div>
					您刚刚将<span style="color: red">时间简史</span>加入到了购物车中
				</div>
			</div>

			<%
				((Page<Book>) request.getAttribute("page")).getItems();
				for (Book book : ((Page<Book>) request.getAttribute("page")).getItems()) {
			%>
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/default.jpg" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2"><%=book.getName()%></span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2"><%=book.getAuthor()%></span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥<%=book.getPrice()%></span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2"><%=book.getSales()%></span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2"><%=book.getStock()%></span>
					</div>
					<div class="book_add">
						<button>加入购物车</button>
					</div>
				</div>
			</div>
			<%
				}
			%>

		</div>

		<!-- 底部分页条 -->
		<%@ include file="/pages/common/bottom_bar.jsp"%>
	
	</div>
	<%@include file="/pages/common/footer.jsp" %>

</body>
</html>
