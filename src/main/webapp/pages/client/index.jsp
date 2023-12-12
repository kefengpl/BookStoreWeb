<%@ page import="com.ruc.bookstoreweb.web.BookServlet" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Book" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Page" %>
<%@ page import="com.ruc.bookstoreweb.pojo.Cart" %>

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
			<nav>

				<ul>
				<%
					if (request.getSession().getAttribute("username") != null) {
					%>
					<li><span>欢迎${sessionScope.username}光临尚硅谷书城</span></li>
					<li><a href="order?action=page&mode=user">我的订单</a></li>
					<li><a href="user?action=logout">退出登录</a></li>
				<%
					} else {
						%>
					<li><a href="pages/user/login.jsp">登录</a></li>
					<li><a href="pages/user/regist.jsp">注册</a></li>
				<%
					}
				%>
					<li><a href="pages/cart/cart.jsp">购物车</a></li>
					<li><a href="pages/manager/manager.jsp">后台管理</a></li>
						<form action="client/book?pageNo=${requestScope.page.pageNo}" method="get"
						style="margin: 0;padding: 0">
							<li class="right-li"><span><input type="submit" value="查询" style="display: inline;font-size:15px;margin:0;padding:0" /></span></li>
							<li class="right-li"><span class="right-span">元</span></li>
							<li class="right-li"><span class="right-span"><input class="pn_input" id="max" type="text" name="max" value="${param.max}"></span></li>
							<li class="right-li"><span class="right-span">元 -- </span></li>
							<li class="right-li"><span class="right-span"><input class="pn_input" id="min" type="text" name="min" value="${param.min}"></span></li>
							<li class="right-li"><span class="right-span">价格：</span></li>
							<li class="right-li"><span style="font-weight: 200;">${requestScope.writeErrorMsg}</span></li>
							<input type="hidden" name="action" value="pageByPrice">
							<input type="hidden" name="curPageNo" value="${requestScope.page.pageNo}">
						</form>

				</ul>
			</nav>

			<table class="index-table" style="height: 10px;margin-top: 10px;">
				<!-- 注意：如果没有三目表达式，将有可能导致 null.getTotalCount()，这显然是错误的，会直接导致页面崩溃，强烈推荐使用 EL 表达式 -->
				<tr><td style="border-bottom: 1px solid #DDDDDD;">您的购物车中有${sessionScope.cart.totalCount == null ? 0 : sessionScope.cart.totalCount}件商品</td></tr>
			<!-- 如果购物车是空，也不可以显示 您刚刚将 XXX 添加到购物车 -->
				<tr><td><span style="color: red"> ${(sessionScope.cart.totalCount == null || sessionScope.cart.totalCount == 0) ?
					"您还没有添加任何物品到购物车" : "您刚刚将".concat(sessionScope.lastbook).concat("加入了购物车")}</span></td></tr>
			</table>
	<table class="index-table">
		<tr>
			<%
				((Page<Book>) request.getAttribute("page")).getItems();
				for (Book book : ((Page<Book>) request.getAttribute("page")).getItems()) {
			%>
			<td>
				<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/future-city.jpg" />
				</div>
					书名：<%=book.getName()%><br>
					作者：<%=book.getAuthor()%><br>
					价格：<%=book.getPrice()%><br>
					销量：<%=book.getSales()%><br>
					库存: <%=book.getStock()%>

					<div class="book_add">
                        <form action="cart" method="post">
                            <input type="hidden" name="action" value="addItem">
                            <input type="hidden" name="id" value="<%=book.getId()%>">
							<!-- 优化：不需要 pageNo了！只需要借助 request 请求头的 Referer 字段 即可！ -->
							<input type="hidden" name="pageNo" value="${requestScope.page.pageNo}">
                            <input type="submit" value="加入购物车" class="join_cart">
                        </form>
					</div>
				</div>
			</td>
			<%
				}
			%>
		</tr>
	</table>
		<!-- 底部分页条 -->
		<%@ include file="/pages/common/bottom_bar.jsp"%>
	<%@include file="/pages/common/footer.jsp" %>

</body>
</html>
