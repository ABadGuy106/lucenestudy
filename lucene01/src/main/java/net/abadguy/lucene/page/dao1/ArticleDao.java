package net.abadguy.lucene.page.dao1;

import net.abadguy.lucene.entity.Article;
import net.abadguy.lucene.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.List;

/**
 * 持久层
 */
public class ArticleDao {

    /**
     * 根据关键字获取总记录数
     * @param keywords
     * @return 总记录数
     * @throws Exception
     */
    public int getAllRecord(String keywords) throws Exception{
        List<Article> articleList=new ArrayList<Article>();
        QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query=queryParser.parse(keywords);
        IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
        TopDocs topDocs=indexSearcher.search(query,2);
        //返回符合条件的记录数
        return topDocs.totalHits;
    }

    /**
     * 根据关键字，批量查询记录
     * @param keywords
     * @param start 从第几条记录的索引号开始查询，索引号从0开始
     * @param size 最多查询多少条记录，不满足最多时，以实际为准
     * @return 集合
     * @throws Exception
     */
    public List<Article> findAll(String keywords,int start,int size) throws Exception{
        List<Article> articleList=new ArrayList<Article>();
        QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query=queryParser.parse(keywords);
        IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
        TopDocs topDocs=indexSearcher.search(query,100);
        int middle=Math.min(start+size,topDocs.totalHits);
        for(int i=start;i<middle;i++){
            ScoreDoc scoreDoc=topDocs.scoreDocs[i];
            int no=scoreDoc.doc;
            Document document=indexSearcher.doc(no);
            Article article= (Article) LuceneUtil.documentToJavaBean(document,Article.class);
            articleList.add(article);
        }

        return articleList;
    }


    public static void main(String[] args) throws Exception {
        ArticleDao articleDao=new ArticleDao();
        System.out.println(articleDao.getAllRecord("测试"));
        System.out.println("-----------------------------------------------------");
        List<Article> articles=articleDao.findAll("测试",0,2);
        System.out.println("第一页");
        for(Article a:articles){
            System.out.println(a);
        }
        System.out.println("第二页");
        articles=articleDao.findAll("测试",2,2);
        for(Article a:articles){
            System.out.println(a);
        }
        System.out.println("第三页");
        articles=articleDao.findAll("测试",4,2);
        for(Article a:articles){
            System.out.println(a);
        }
        System.out.println("第四页");
        articles=articleDao.findAll("测试",6,2);
        for(Article a:articles){
            System.out.println(a);
        }
    }
}
