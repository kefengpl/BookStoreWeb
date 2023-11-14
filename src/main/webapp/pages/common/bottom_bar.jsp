<%--
  Created by IntelliJ IDEA.
  User: 3590
  Date: 2023/11/15
  Time: 0:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- 注意：form表单 post 似乎只能通过 input 传参，我们使用hidden即可，不要使用 ?action=page 这种传参方式 -->
<form action="${requestScope.page.url}" method="post" style="margin: 0; padding: 0;">
    <div id="page_nav">
        <a href="${requestScope.page.url}?action=page&pageNo=1">首页</a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo > 1 ? requestScope.page.pageNo - 1 : 1}">
            ${requestScope.page.pageNo > 1 ? "上一页" : null}
        </a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo - 2}">${requestScope.page.pageNo - 2 > 0 ? requestScope.page.pageNo - 2 : null}</a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo - 1}">${requestScope.page.pageNo - 1 > 0 ? requestScope.page.pageNo - 1 : null}</a>
        【${requestScope.page.pageNo}】
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo + 1}">${requestScope.page.pageNo >= requestScope.page.pageTotal ? null : requestScope.page.pageNo + 1}</a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo + 2}">${requestScope.page.pageNo + 1 >= requestScope.page.pageTotal ? null : requestScope.page.pageNo + 2}</a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageNo >= requestScope.page.pageTotal ? requestScope.page.pageTotal : requestScope.page.pageNo + 1}">
            ${requestScope.page.pageNo >= requestScope.page.pageTotal ? null : "下一页"}
        </a>
        <a href="${requestScope.page.url}?action=page&pageNo=${requestScope.page.pageTotal}">末页</a>
        共${requestScope.page.pageTotal}页，${requestScope.page.pageTotalCount}条记录
        <!-- 与视频不一样的地方：这里我完全使用 HTML 实现，不引入 JavaScript -->
        到第<input value="${requestScope.page.pageNo}" name="pageNo" id="pn_input" style="display: inline;"/>页
        <input type="submit" value="确定" style="display: inline;">
        ${requestScope.errorMsg}
        <input type="hidden" name="action" value="page">
        <input type="hidden" name="curPageNo" value="${requestScope.page.pageNo}">
    </div>
</form>
