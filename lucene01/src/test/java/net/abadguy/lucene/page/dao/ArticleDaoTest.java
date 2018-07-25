package net.abadguy.lucene.page.dao;

import net.abadguy.lucene.page.dao1.ArticleDao;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleDaoTest {

    private ArticleDao articleDao;

    @Test
    public void getAllRecord() throws Exception {
        String keyword="测试";
        int number=articleDao.getAllRecord(keyword);
        System.out.println(number);
    }

    @Test
    public void findAll() {
    }
}