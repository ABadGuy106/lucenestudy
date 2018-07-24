package net.abadguy.lucene.firstapp;

import net.abadguy.lucene.entity.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

public class FirstApp {

    public static void main(String[] args) throws Exception {
        createIndexDB();
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
}
