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
<%
	request.getRequestDispatcher("/client/book?action=page").forward(request, response);
%>
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
				<form action="" method="get">
					价格：<input id="min" type="text" name="min" value=""> 元 - 
						<input id="max" type="text" name="max" value=""> 元 
						<input type="submit" value="查询" />
				</form>
			</div>
			<div style="text-align: center">
				<span>您的购物车中有3件商品</span>
				<div>
					您刚刚将<span style="color: red">时间简史</span>加入到了购物车中
				</div>
			</div>
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/default.jpg" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">时间简史</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">霍金</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥30.00</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">230</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">1000</span>
					</div>
					<div class="book_add">
						<button>加入购物车</button>
					</div>
				</div>
			</div>
			
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/default.jpg" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">时间简史</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">霍金</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥30.00</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">230</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">1000</span>
					</div>
					<div class="book_add">
						<button>加入购物车</button>
					</div>
				</div>
			</div>
			
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/default.jpg" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">时间简史</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">霍金</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥30.00</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">230</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">1000</span>
					</div>
					<div class="book_add">
						<button>加入购物车</button>
					</div>
				</div>
			</div>
			
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="static/img/default.jpg" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">时间简史</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">霍金</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥30.00</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">230</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">1000</span>
					</div>
					<div class="book_add">
						<button>加入购物车</button>
					</div>
				</div>
			</div>
		</div>
		
		<div id="page_nav">
		<a href="#">首页</a>
		<a href="#">上一页</a>
		<a href="#">3</a>
		【4】
		<a href="#">5</a>
		<a href="#">下一页</a>
		<a href="#">末页</a>
		共10页，30条记录 到第<input value="4" name="pn" id="pn_input"/>页
		<input type="button" value="确定">
		</div>
	
	</div>
	
	<div id="bottom">
		<span>
			尚硅谷书城.Copyright &copy;2015
		</span>
	</div>
</body>
</html>

<!--
	补充学习：JavaEE的三层架构 *****十分重要！
	服务器中由javaEE的代码，即javaEE的三层架构
	①WEB层：视图展现层(我们的servlet程序在这一层/SpringMVC也是在这一层)
	②Service层：业务层(Spring框架)
	③Dao：持久层，将数据写到数据库(JDBC/Mybatis/Hibernate)

	三层架构下面是数据库：常用的数据库MySQL Oracle DB2 SQLServer

	代码请求从客户端发起：http://ip:port/工程路径/资源路径
	发送到①WEB层：1.获取请求参数，封装成为Bean对象(先理解为request对象吧..)；
			     2.调用service层处理业务
			     3.响应数据给客户端：请求转发/重定向
	     ②service层：1.处理业务逻辑
	     			 2.调用持久层保存到数据库
	     ③Dao持久层：只负责和数据库交互，CRUD操作，即增删改查
	从上面我们可以发现：这三层是递进关系，①调用②调用③调用数据库。
	Dao查到数据后[逐层]回传，最终把结果响应给客户端，并解析在展示页面上
	---------------------------------
	web 层 com.atguigu.web/servlet/controller
	service层 com.atguigu.service service接口包
	          com.atguigu.service.impl service接口实现类
	dao持久层 com.atguigu.dao Dao接口包
			 com.atguigu.dao.impl Dao接口实现类
	实体bean对象 com.atguigu.pojo/entity/domain/bean javaBean类
	测试包  com.atguigu.test/junit
	工具类  com.atguigu.utils
-->
