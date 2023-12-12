<%--
  Created by IntelliJ IDEA.
  User: 3590
  Date: 2023/11/15
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Integer pageNo = ((Page) request.getAttribute("page")).getPageNo();
    Integer pageTotal = ((Page) request.getAttribute("page")).getPageTotal();
%>

<!-- 注意：form表单 post 似乎只能通过 input 传参，我们使用hidden即可，不要使用 ?action=page 这种传参方式 -->
<!--注意：进一步优化需要用到 JSTL，但是这是过时的技术，所以我们使用 思维上简单，结构上复杂的方式去实现即可  -->
    <nav>
        <ul class="horizontal">
            <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=1&min=${param.min}&max=${param.max}">首页</a></li>

            <% if (pageNo > 1) { %>
                <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageNo - 1}&min=${param.min}&max=${param.max}">
                    上一页
                </a></li>
            <%}%>

            <% if (pageNo > 2) { %>
                <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageNo - 2}&min=${param.min}&max=${param.max}">${requestScope.page.pageNo - 2}</a></li>
            <%}%>

            <% if (pageNo > 1) { %>
            <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageNo - 1}&min=${param.min}&max=${param.max}">${requestScope.page.pageNo - 1}</a></li>
            <%}%>

            <li style="background-color: #00BBFF"><span style="color: white">${requestScope.page.pageNo}</span> </li>

            <% if (pageNo < pageTotal) { %>
                <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageNo + 1}&min=${param.min}&max=${param.max}">${requestScope.page.pageNo + 1}</a></li>
            <%}%>
            <% if (pageNo + 1 < pageTotal) { %>
            <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageNo + 2}&min=${param.min}&max=${param.max}">${requestScope.page.pageNo + 2}</a></li>
            <%}%>
            <% if (pageNo < pageTotal) { %>
            <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageTotal}&min=${param.min}&max=${param.max}">
                    下一页
                </a></li>
            <%}%>
            <li><a href="${requestScope.page.url}?action=page&mode=${param.mode}&pageNo=${requestScope.page.pageTotal}&min=${param.min}&max=${param.max}">末页</a></li>
            <li><span>共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录</span></li>
            <form action="${requestScope.page.url}" method="post" style="margin: 0; padding: 0;">
                <!-- 与视频不一样的地方：这里我完全使用 HTML 实现，不引入 JavaScript -->
                <li><span>到第<input value="${requestScope.page.pageNo}" name="pageNo" id="pn_input" style="display: inline;"/>页</span></li>
                <li><span><input type="submit" value="确定" style="display: inline;font-size:15px;margin:0;padding:0" class="submit_input"></span></li>
                <li><span>${requestScope.errorMsg}</span></li>
                <input type="hidden" name="action" value="page">
                <input type="hidden" name="mode" value="${param.mode}">
                <input type="hidden" name="min" value="${param.min}">
                <input type="hidden" name="max" value="${param.max}">
                <input type="hidden" name="curPageNo" value="${requestScope.page.pageNo}">
            </form>

        </ul>
    </nav>
