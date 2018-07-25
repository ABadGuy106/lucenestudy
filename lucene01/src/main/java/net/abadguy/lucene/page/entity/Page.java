package net.abadguy.lucene.page.entity;

import net.abadguy.lucene.entity.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页类
 */
public class Page {
    private Integer currPageNo;//当前页号；
    private  Integer perPageSize=2;//每页显示记录数，默认为2条
    private Integer allRecordNo;//总记录数
    private Integer allPageNo;//总页数
    private List<Article> articleList =new ArrayList<Article>();

    public Integer getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(Integer currPageNo) {
        this.currPageNo = currPageNo;
    }

    public Integer getPerPageSize() {
        return perPageSize;
    }

    public void setPerPageSize(Integer perPageSize) {
        this.perPageSize = perPageSize;
    }

    public Integer getAllRecordNo() {
        return allRecordNo;
    }

    public void setAllRecordNo(Integer allRecordNo) {
        this.allRecordNo = allRecordNo;
    }

    public Integer getAllPageNo() {
        return allPageNo;
    }

    public void setAllPageNo(Integer allPageNo) {
        this.allPageNo = allPageNo;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }
}
