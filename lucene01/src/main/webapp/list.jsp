<%--
  Created by IntelliJ IDEA.
  User: who
  Date: 2018/7/25
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>同步分页</title>
</head>
<body>
    <!--输入区-->
    <form action="${pageContext.request.contextPath}/article" method="post">
        <input type="hidden" name="currPageNo" value="1">
        <table border="2" align="center">
            <tr>
                <th>输入关键字</th>
                <td><input type="text" name="keywords" value="${requestScope.keywords}" maxlength="10"></td>
                <td><input id="search" type="button" value="站内搜索"></td>
            </tr>
        </table>
    </form>
    <!--显示区-->
    <table border="2" align="center" width="70%">
        <tr>
            <th>编号</th>
            <th>标题</th>
            <th>内容</th>
        </tr>
        <c:forEach var="article" items="${requestScope.page.articleList}">
            <tr>
                <td>${article.id}</td>
                <td>${article.title}</td>
                <td>${article.content}</td>
            </tr>
        </c:forEach>
        <tr>
            <th colspan="3" align="center">
                <a onclick="fy(1)" style="cursor:hand;color: aqua">首页</a>
                <c:choose>
                    <c:when test="${requestScope.page.currPageNo+1<=requestScope.page.allPageNo}">
                        <a onclick="fy(${requestScope.page.currPageNo+1})" style="cursor:hand;color: aqua">下一页</a>

                    </c:when>
                    <c:otherwise>
                        下一页
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${requestScope.page.currPageNo-1>0}">
                        <a onclick="fy(${requestScope.page.currPageNo-1})" style="cursor:hand;color: aqua">上一页</a>
                    </c:when>
                    <c:otherwise>
                        上一页
                    </c:otherwise>
                </c:choose>
                <a onclick="fy(${requestScope.page.allPageNo})" style="cursor:hand;color: aqua">尾页</a>
            </th>
        </tr>
    </table>
    <script type="text/javascript">
        function fy(currPageNo) {
            //定位表单
            var formElement=document.forms[0];
            //修改当前页号
            formElement.currPageNo.value=currPageNo;
            //提交表单
            formElement.submit();
        }
    </script>
    <script type="text/javascript">
        //去空格
        function trim(str){
            //先去左边空格
            str=str.replace(/^\s*/,"");
            //后去右边空格
            str=str.replace(/\s*$/,"");
            //返回str
            return str;
        }

        //定位搜索按钮，同时提供单击事件
        document.getElementById("search").onclick=function () {
            //定位表单
            var formElement=document.forms[0];
            //获取关键字
            var keywords=formElement.keywords.value;
            //去空格
            keywords=trim(keywords);
            //判断长度
            if(keywords.length ==0){
                alert("请输入关键字！");
            }else{
                //提交表单
                formElement.submit();
            }

        }
    </script>

</body>
</html>
