package net.abadguy.lucene.firstapp;

import net.abadguy.lucene.entity.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FirstApp {

    public static void main(String[] args) throws Exception {
//        createIndexDB();
        findIndexDB();
    }



    /**
     * 创建索引库，并肩Article对象放入索引库中的原始记录表中，
     * 从而形成词汇表
     */
    public static void createIndexDB() throws Exception{
        Article article=new Article(1,"测试","这是一个测试文本，编号1");
        //创建一个document对象
        Document document=new Document();
        //讲Article中的三个属性值分别绑定到document对象中
        /**
         * 参数1：document对象中的属性名叫xid,article对象中的属性名叫id
         * 参数2：document对象中的属性xid值,article对象中相同
         * 参数3：是否讲xid属性值存入由原始记录表中转存入词汇表
         *          Field.Store.YES：表示该属性值会存储词汇表
         *          Field.Store.NO：表示该属性值不会存储词汇表
         * 参数4：是否讲xid属性进行分词策略
         *          Field.Index.ANALYZED：表示该属性会进行词汇拆分
         *          Field.Index.NOT_ANALYZED:表示该属性会进行词汇拆分
         */
        document.add(new Field("xid",article.getId().toString(),Field.Store.YES,Field.Index.ANALYZED));
        document.add(new Field("xtitle",article.getTitle(),Field.Store.YES,Field.Index.ANALYZED));
        document.add(new Field("xcontent",article.getContent(),Field.Store.YES,Field.Index.ANALYZED));
        //将document写入lucene索引库
        /**
         * 参数1：lucene索引库最终对应于硬盘中的目录，例如E://indexDBDB
         * 参数2：采用什么策略讲文本拆分
         * 参数3：最多将文本拆分出多少词汇
         */
        Directory directory = FSDirectory.open(new File("D:/IndexDBDBDB"));
        Version version=Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(version);
        IndexWriter.MaxFieldLength maxPayloadFunction = IndexWriter.MaxFieldLength.LIMITED;
        IndexWriter indexWriter=new IndexWriter(directory,analyzer,maxPayloadFunction);
        //将document对象写入luence索引库
        indexWriter.addDocument(document);
        //关闭indexWriter字符流对象
        indexWriter.close();

    }

    /**
     * 根据关键字从索引库中中搜索符合条件的记录
     */
    public static void findIndexDB() throws Exception{
        String keyword="测试本";
        List<Article> articleList=new ArrayList<Article>();
        Directory directory = FSDirectory.open(new File("D:/IndexDBDBDB"));
        //创建indexSearcher字符流对象
        IndexSearcher indexSearcher=new IndexSearcher(directory);
        Version version=Version.LUCENE_30;
        Analyzer analyzer = new StandardAnalyzer(version);
        /**
         * 参数1：使用分词器的版本，建议使用jar包中的最高版本
         * 参数2：针对document对象的那个属性进行搜索
         */

        QueryParser queryParser=new QueryParser(version,"xcontent",analyzer);
        //封装查询关键字
        Query query=queryParser.parse(keyword);
        /**
         * 根据关键字，去索引库中的词汇表搜索
         * 参数1：表示查询对象，即封装关键字的对象,其它QueryParser表示查询解析器
         * 参数2：MAX_RECORD表示如果根据关键字搜索i出来的内容较多，只取前MAX_RECORD个内容
         * 不足MAX_RECORD个数的话，以实际为准
         */
        int MAX_RECORD=100;
        TopDocs topDocs=indexSearcher.search(query,MAX_RECORD);
        //迭代词汇表中符合条件的编号
        for(int i=0;i<topDocs.scoreDocs.length;i++){
            //取出封装编号和分数的ScoreDoc对象
            ScoreDoc scoreDoc=topDocs.scoreDocs[i];
            //取出每个编号
            int no=scoreDoc.doc;
            //根据编号去索引库中的院士记录表中查询对应的document对象
            Document document=indexSearcher.doc(no);
            //获取document对象中的三个属性值
            String xid=document.get("xid");
            String xtitle=document.get("xtitle");
            String xcontent=document.get("xcontent");
            //封装到article对象中
            Article article=new Article(Integer.parseInt(xid),xtitle,xcontent);
            //将article对象加入到list集合中
            articleList.add(article);
        }
        for (Article article:articleList){
            System.out.println(article);
        }
    }
}
