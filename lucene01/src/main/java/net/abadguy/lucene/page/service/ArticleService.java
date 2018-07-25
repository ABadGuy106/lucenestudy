package net.abadguy.lucene.page.service;

import net.abadguy.lucene.entity.Article;

import net.abadguy.lucene.page.dao1.ArticleDao;
import net.abadguy.lucene.page.entity.Page;

import java.util.List;

/**
 * 逻辑业务层
 */
public class ArticleService {

    private ArticleDao articleDao=new ArticleDao();

    public Page show(String keywords,int currPageNo) throws Exception{
        Page page=new Page();
        //封装当前页号
        page.setCurrPageNo(currPageNo);
        //封装总记录数
        int number=articleDao.getAllRecord(keywords);
        page.setAllRecordNo(number);
        //封装总页数
        int allPageNo=0;
        if(page.getAllRecordNo()%page.getPerPageSize()==0){
            allPageNo=page.getAllRecordNo()/page.getPerPageSize();
        }else{
            allPageNo=page.getAllRecordNo()/page.getPerPageSize()+1;
        }
        page.setAllPageNo(allPageNo);
        //封装内容
        int size=page.getPerPageSize();
        int start=(page.getCurrPageNo()-1)*size;
        List<Article> articleList=articleDao.findAll(keywords,start,size);
        page.setArticleList(articleList);

        return page;
    }


    public static void main(String[] agrs) throws Exception {
        ArticleService test=new ArticleService();
        Page page=test.show("测试",1);
        System.out.println(page.getCurrPageNo());
        System.out.println(page.getPerPageSize());
        System.out.println(page.getAllRecordNo());
        System.out.println(page.getAllPageNo());
        for (Article article:page.getArticleList()){
            System.out.println(article);
        }
    }
}
