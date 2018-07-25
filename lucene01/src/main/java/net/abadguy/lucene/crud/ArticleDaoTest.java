package net.abadguy.lucene.crud;

import net.abadguy.lucene.entity.Article;
import net.abadguy.lucene.util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.util.ArrayList;
import java.util.List;


/**
 * 增删改查索引库
 */
public class ArticleDaoTest {

    public static void add() throws Exception{
        Article article=new Article(1,"测试","测试，这是第1个测试文本test");
        Document document=LuceneUtil.javaBeanToDocument(article);
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());
        indexWriter.addDocument(document);
        indexWriter.close();
    }

    public static void addAll() throws Exception{
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());

        Article article=new Article(1,"测试","测试，这是第1个测试文本test");
        Document document=LuceneUtil.javaBeanToDocument(article);
        indexWriter.addDocument(document);
        Article article1=new Article(2,"测试","测试，这是第2个测试文本test");
        Document document1=LuceneUtil.javaBeanToDocument(article1);
        indexWriter.addDocument(document1);
        Article article2=new Article(3,"测试","测试，这是第3个测试文本test");
        Document document2=LuceneUtil.javaBeanToDocument(article2);
        indexWriter.addDocument(document2);
        Article article3=new Article(4,"测试","测试，这是第4个测试文本test");
        Document document3=LuceneUtil.javaBeanToDocument(article3);
        indexWriter.addDocument(document3);
        Article article4=new Article(5,"测试","测试，这是第5个测试文本test");
        Document document4=LuceneUtil.javaBeanToDocument(article4);
        indexWriter.addDocument(document4);
        Article article5=new Article(6,"测试","测试，这是第6个测试文本test");
        Document document5=LuceneUtil.javaBeanToDocument(article5);
        indexWriter.addDocument(document5);
        Article article6=new Article(7,"测试","测试，这是第7个测试文本test");
        Document document6=LuceneUtil.javaBeanToDocument(article6);
        indexWriter.addDocument(document6);
        Article article7=new Article(8,"测试","测试，这是第8个测试文本test");
        Document document7=LuceneUtil.javaBeanToDocument(article7);
        indexWriter.addDocument(document7);
        Article article8=new Article(9,"测试","测试，这是第9个测试文本test");
        Document document8=LuceneUtil.javaBeanToDocument(article8);
        indexWriter.addDocument(document8);
        Article article9=new Article(10,"测试","测试，这是第10个测试文本test");
        Document document9=LuceneUtil.javaBeanToDocument(article9);
        indexWriter.addDocument(document9);

        indexWriter.close();
    }

    public static void update() throws Exception{
        Article article=new Article(7,"测试","这是一条修改的测试文本，这是第七条");
        Document document=LuceneUtil.javaBeanToDocument(article);
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());
        //更新id为7的document
        /**
         * 参数1：trem需要更新的document对象，id表示decument对象中的id属性，7表示该id属性的值
         * 参数2：新的document对象
         */
        indexWriter.updateDocument(new Term("id","7"),document);
        indexWriter.close();
    }
    public static void delete() throws Exception{
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());
        indexWriter.deleteDocuments(new Term("id","10"));
        indexWriter.close();
    }

    public static void deleteAll() throws Exception{
        IndexWriter indexWriter=new IndexWriter(LuceneUtil.getDirectory(),LuceneUtil.getAnalyzer(),LuceneUtil.getMaxPayloadFunction());
        indexWriter.deleteAll();
        indexWriter.close();
    }

    public static void findAllByKeyWord() throws Exception{
        String keyWord="测试";
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

    //测试
    public static void main(String[] args) throws Exception{
//        add();
        addAll();
//        findAllByKeyWord();
//        update();
//        delete();
//        deleteAll();
    }
}
