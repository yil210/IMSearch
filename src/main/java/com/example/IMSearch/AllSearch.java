package com.example.IMSearch;
import org.apache.lucene.analysis.Analyzer;
import com.example.IMSearch.Classes.Path;
import com.example.IMSearch.Classes.Stemmer;
import com.example.IMSearch.Indexing.MyIndexReader;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class AllSearch {
    private Directory directory;
//    private IndexWriter indexWriter;

    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    private FieldType type;


    public AllSearch() throws IOException {
        directory = FSDirectory.open(Paths.get(Path.multiFieldIndexDir));
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

        type = new FieldType();
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        type.setStored(false);
        type.setStoreTermVectors(true);

        indexReader = DirectoryReader.open(directory);

        indexSearcher = new IndexSearcher(indexReader);


//        indexWriter = new IndexWriter(directory, indexWriterConfig);
    }

    public ArrayList<String> generalSearch(String queryStr) throws IOException, ParseException {
        ArrayList<String> jsons = new ArrayList<>();

        ArrayList<String> ids = new ArrayList<>();

//        HashMap<String, String> mp = new HashMap<>();

        MyIndexReader myIndexReader = new MyIndexReader();


        Analyzer analyzer = new StandardAnalyzer();

        QueryBuilder builder = new QueryBuilder(analyzer);


        Query query = new QueryParser("CONTENT", analyzer).parse(stemmer(queryStr));


        TopDocs topDocs = indexSearcher.search(query, 1000);

//        System.out.println(query.toString());

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;

        System.out.println("Total number of documents: " + scoreDocs.length);

        for (ScoreDoc doc : scoreDocs) {
            int docid = doc.doc;
            Document document = indexReader.document(docid);

            String json = document.get("JSON");
            String docno = document.get("DOCNO");


            if (!ids.contains(docno)) {

                ids.add(docno);

                jsons.add(json);

            }

//            mp.put(docno, json);
        }

//        for (String key: mp.keySet()) {
//            jsons.add(mp.get(key));
//        }

        return jsons;
    }
    public ArrayList<String> advancedSearch(String title, String genre, String keywords, String ratingLower, String ratingUpper, String dateLower, String dateUpper) throws ParseException, IOException {

        ArrayList<String> jsons = new ArrayList<>();

        Analyzer analyzer = new StandardAnalyzer();

        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        if (!title.isEmpty()) {
            Query q1 = new QueryParser("TITLE", analyzer).parse(title);
            builder.add(q1, BooleanClause.Occur.MUST);
        }

        if (genre != null) {
            Query q2 = new QueryParser("GENRE", analyzer).parse(genre);
            builder.add(q2, BooleanClause.Occur.MUST);
        }

        if (!keywords.isEmpty()) {
            Query q3 = new QueryParser("KEYWORDS", analyzer).parse(keywords);
            builder.add(q3, BooleanClause.Occur.SHOULD);
        }

        if (!ratingLower.isEmpty() && !ratingUpper.isEmpty()) {
            TermRangeQuery q4 = TermRangeQuery.newStringRange("RATING", ratingLower, ratingUpper, true, true);
            builder.add(q4, BooleanClause.Occur.SHOULD);
        }

        if (!dateLower.isEmpty() && !dateUpper.isEmpty()) {
            TermRangeQuery q5 = TermRangeQuery.newStringRange("DATE", dateLower, dateUpper, true, true);
            builder.add(q5, BooleanClause.Occur.SHOULD);
        }

        ScoreDoc[] scoreDocs = indexSearcher.search(builder.build(), 100).scoreDocs;

        for (ScoreDoc scoreDoc: scoreDocs) {
            String json = indexReader.document(scoreDoc.doc).get("JSON");
            jsons.add(json);
        }
        return jsons;


    }

    public ArrayList<String> collabrationByName(String str1, String str2) throws ParseException, IOException {
        ArrayList jsons = new ArrayList();

        Analyzer analyzer = new StandardAnalyzer();

        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        if (!str1.isEmpty()) {
            Query q1 = new QueryParser("ACTOR", analyzer).parse(str1);
            builder.add(q1, BooleanClause.Occur.MUST);
        }

        if (!str2.isEmpty()) {
            Query q2 = new QueryParser("ACTOR", analyzer).parse(str2);
            builder.add(q2, BooleanClause.Occur.MUST);
        }

//        BooleanQuery booleanQuery = new BooleanQuery.Builder()
//                .add(q1, BooleanClause.Occur.MUST)
//                .add(q2, BooleanClause.Occur.MUST)
//                .build();

        ScoreDoc[] scoreDocs = indexSearcher.search(builder.build(), 100).scoreDocs;


        System.out.println("Total documents retrieved: " + scoreDocs.length);



        for (ScoreDoc scoreDoc : scoreDocs) {
            String json = indexReader.document(scoreDoc.doc).get("JSON");
            jsons.add(json);
        }

        return jsons;
    }

    public ArrayList<String> collabrationByKeywords(String str1, String str2) throws ParseException, IOException {
        ArrayList jsons = new ArrayList();

        Analyzer analyzer = new StandardAnalyzer();

        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        if (!str1.isEmpty()) {
            Query q1 = new QueryParser("KEYWORDS", analyzer).parse(str1);
            builder.add(q1, BooleanClause.Occur.MUST);
        }

        if (!str2.isEmpty()) {
            Query q2 = new QueryParser("KEYWORDS", analyzer).parse(str2);
            builder.add(q2, BooleanClause.Occur.MUST);
        }

        ScoreDoc[] scoreDocs = indexSearcher.search(builder.build(), 100).scoreDocs;


        System.out.println("Total documents retrieved: " + scoreDocs.length);



        for (ScoreDoc scoreDoc : scoreDocs) {
            String json = indexReader.document(scoreDoc.doc).get("JSON");
            jsons.add(json);
        }

        return jsons;
    }

    public ArrayList<String> wildcardSearch(String queryStr) throws IOException {
        ArrayList<String> jsons = new ArrayList<>();

        Analyzer analyzer = new StandardAnalyzer();

        Term term = new Term("CONTENT", queryStr);

        WildcardQuery wildcardQuery = new WildcardQuery(term);

        ScoreDoc[] scoreDocs = indexSearcher.search(wildcardQuery, 100).scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            String json = indexReader.document(scoreDoc.doc).get("JSON");
            jsons.add(json);
        }

        return jsons;
    }

    public ArrayList<String> genreSearch(String queryStr) throws ParseException, IOException {
        ArrayList<String> jsons = new ArrayList<>();

        Analyzer analyzer = new StandardAnalyzer();

        Query q = new QueryParser("GENRE", analyzer).parse(queryStr);

        ScoreDoc[] scoreDocs = indexSearcher.search(q, 100).scoreDocs;

        for (ScoreDoc scoreDoc : scoreDocs) {
            String json = indexReader.document(scoreDoc.doc).get("JSON");
            jsons.add(json);
        }

        return jsons;
    }

    private String stemmer(String str) {
        StringBuilder res = new StringBuilder();
        String[] contents = str.split(" ");
        for (String s: contents) {
            Stemmer stemmer = new Stemmer();
            char[] chars = s.toCharArray();
            stemmer.add(chars, chars.length);
            stemmer.stem();
            res.append(stemmer.toString());
            res.append(" ");
        }
        return res.toString();
    }


}

