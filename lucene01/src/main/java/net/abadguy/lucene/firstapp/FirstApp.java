package net.abadguy.lucene.firstapp;

import net.abadguy.lucene.entity.Article;

public class FirstApp {
    /**
     * 创建索引库，并肩Article对象放入索引库中的原始记录表中，
     * 从而形成词汇表
     */
    public void createIndexDB() throws Exception{
        Article article=new Article(1,"测试","这是一个测试文本，编号1");
    }
}
