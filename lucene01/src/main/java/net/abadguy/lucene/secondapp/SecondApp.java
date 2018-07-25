package net.abadguy.lucene.secondapp;

import net.abadguy.lucene.entity.Article;
import net.abadguy.lucene.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.List;

/**
 * 重构FirstApp
 */
public class SecondApp {

    /**
     * 创建索引库
     * @throws Exception
     */
    public static void createIndexDB() throws Exception{
//        Article article=new Article(1,"测试","测试，这是第一个测试文本");
//        Article article=new Article(2,"测试","测试，这是第二个测试文本");
        Article article=new Article(3,"测试","测试，这是第三个测试文本");
        Document document=LuceneUtil.javaBeanToDocument(article);
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    /**
     * 根据关键字从索引库中查询符合条件的数据库
     * @throws Exception
     */
    public static void findIndexDB()throws Exception{
        String keyWord="测";
        List<Article> articleList=new ArrayList<Article>();

        QueryParser queryParser=new QueryParser(LuceneUtil.getVersion(),"content",LuceneUtil.getAnalyzer());
        Query query=queryParser.parse(keyWord);
        IndexSearcher indexSearcher=new IndexSearcher(LuceneUtil.getDirectory());
        TopDocs topDocs=indexSearcher.search(query,100);
        for(int i=0;i<topDocs.scoreDocs.length;i++){
            ScoreDoc scoreDoc=topDocs.scoreDocs[i];
            int no=scoreDoc.doc;
            Document document=indexSearcher.doc(no);
            Article article= (Article) LuceneUtil.documentToJavaBean(document,Article.class);
            System.out.println(article);
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception {
//        createIndexDB();
        findIndexDB();
    }
}
