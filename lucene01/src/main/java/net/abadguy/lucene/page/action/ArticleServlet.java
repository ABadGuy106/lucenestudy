package net.abadguy.lucene.page.action;

import net.abadguy.lucene.page.entity.Page;
import net.abadguy.lucene.page.service.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        //获取关键字
        String keywords=req.getParameter("keywords");
        if(keywords==null || keywords.trim().length()==0){
            keywords="没有词";
        }
        //获取当前页号
        String tmp=req.getParameter("currPageNo");
        if(tmp==null || tmp.trim().length()==0){
            tmp="1";//默认值
        }
        //调用业务层
        ArticleService articleService=new ArticleService();
        try {
             Page page=articleService.show(keywords,Integer.parseInt(tmp));
             //将page对象绑定到request域对象中
            req.setAttribute("page",page);
            //将keyword变量绑定到request域对象中
            req.setAttribute("keywords",keywords);
            //转发到list.jsp页面中
            req.getRequestDispatcher("/list.jsp").forward(req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
