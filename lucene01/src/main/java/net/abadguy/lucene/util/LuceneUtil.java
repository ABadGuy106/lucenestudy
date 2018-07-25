package net.abadguy.lucene.util;

import net.abadguy.lucene.entity.Article;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class LuceneUtil {

    private static Directory directory;
    private static Version version;
    private static Analyzer analyzer;
    private static IndexWriter.MaxFieldLength maxPayloadFunction;
    static {
        try {
            directory = FSDirectory.open(new File("D:/IndexDBDBDB"));
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        version=Version.LUCENE_30;
        analyzer = new StandardAnalyzer(version);
        maxPayloadFunction = IndexWriter.MaxFieldLength.LIMITED;
    }

    public static Directory getDirectory() {
        return directory;
    }

    public static void setDirectory(Directory directory) {
        LuceneUtil.directory = directory;
    }

    public static Version getVersion() {
        return version;
    }

    public static void setVersion(Version version) {
        LuceneUtil.version = version;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }

    public static void setAnalyzer(Analyzer analyzer) {
        LuceneUtil.analyzer = analyzer;
    }

    public static IndexWriter.MaxFieldLength getMaxPayloadFunction() {
        return maxPayloadFunction;
    }

    public static void setMaxPayloadFunction(IndexWriter.MaxFieldLength maxPayloadFunction) {
        LuceneUtil.maxPayloadFunction = maxPayloadFunction;
    }

    //防止外界实力化该类
    private LuceneUtil(){}
    //将JavaBean转化为document对象
    public static Document javaBeanToDocument(Object object)throws  Exception{
        //创建document对象
        Document document=new Document();

        //获取object引用的对象字节码
        Class clazz=object.getClass();
        //通过子界面获取私有的字段
        Field[] fields=clazz.getDeclaredFields();
        for(Field f:fields){
            //强制反射
            f.setAccessible(true);
            //获取属性名
            String name=f.getName();
            //手工拼接方法名
            String methodName="get"+name.substring(0,1).toUpperCase()+name.substring(1);
            //获取方法
            Method method=clazz.getMethod(methodName,null);
            //执行方法
            String value=method.invoke(object,null).toString();
            //加入到document对象中去
            document.add(new org.apache.lucene.document.Field(name,value, org.apache.lucene.document.Field.Store.YES, org.apache.lucene.document.Field.Index.ANALYZED));
        }
        //返回document对象
        return document;
    }
    //将document对象转化为JavaBean对象
    public static Object documentToJavaBean(Document document,Class clazz) throws Exception{
        Object object=clazz.newInstance();
        Field[] fields=clazz.getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
            String name=f.getName();
            String value=document.get(name);
            BeanUtils.setProperty(object,name,value);
        }
        return object;
    }

    //测试
    public static void main(String[] args) throws Exception{
        Article article=new Article(1,"测试","这是一个测试文本");
        Document document=LuceneUtil.javaBeanToDocument(article);
//        System.out.println(document.get("id"));
//        System.out.println(document.get("title"));
//        System.out.println(document.get("content"));
        Article article1= (Article) LuceneUtil.documentToJavaBean(document,Article.class);
        System.out.println(article1);


    }
}
