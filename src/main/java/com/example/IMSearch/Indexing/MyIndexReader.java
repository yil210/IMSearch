package com.example.IMSearch.Indexing;

import com.example.IMSearch.Classes.Path;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class MyIndexReader {

    protected File dir;
    private Directory directory;
    private DirectoryReader directoryReader;
    private IndexSearcher indexSearcher;


    public MyIndexReader() throws IOException {
        directory = FSDirectory.open(Paths.get(Path.multiFieldIndexDir));
        directoryReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(directoryReader);

    }

    public int getDocid( String docno ) throws IOException {
        // you should implement this method.
        Query query = new TermQuery(new Term("DOCNO", docno));
        TopDocs tops= indexSearcher.search(query,1);
        return tops.scoreDocs[0].doc;
    }

    public String getDocno( int docid ) throws IOException {
        // you should implement this method.
        Document doc = directoryReader.document(docid);
        return (doc==null)?null:doc.get("DOCNO");
    }

    public int[][] getPostingList( String token ) throws IOException {
        // you should implement this method.
        Term tm = new Term("CONTENT", token);
        int df = directoryReader.docFreq(tm);
        if(df==0)
            return null;
        Query query = new TermQuery(tm);
        TopDocs tops= indexSearcher.search(query,df);
        ScoreDoc[] scoreDoc = tops.scoreDocs;
        int[][] posting = new int[df][];
        int ix = 0;
        Terms vector;
        TermsEnum termsEnum;
        BytesRef text;
        for (ScoreDoc score : scoreDoc){
            int id = score.doc;
            int freq=0;
            vector = directoryReader.getTermVector(id, "CONTENT");
            termsEnum = vector.iterator();
            while ((text = termsEnum.next()) != null) {
                if(text.utf8ToString().equals(token))
                    freq+= (int) termsEnum.totalTermFreq();
            }
            posting[ix] = new int[] { id, freq };
            ix++;
        }
        return posting;
    }

    public int DocFreq( String token ) throws IOException {
        Term tm = new Term("CONTENT", token);
        int df = directoryReader.docFreq( tm );
        return df;
    }

    public long CollectionFreq( String token ) throws IOException {
        // you should implement this method.
        Term tm = new Term("CONTENT", token);
        return directoryReader.totalTermFreq(tm);
    }

    public int docLength( int docid ) throws IOException {
        int doc_length = 0;
        Terms vector = directoryReader.getTermVector( docid, "CONTENT" );
        TermsEnum termsEnum = vector.iterator();
        BytesRef text;
        while ((text = termsEnum.next()) != null) {
            doc_length+= (int) termsEnum.totalTermFreq();
        }
        return doc_length;
    }

    public long totalTermFreq() throws IOException {
        return directoryReader.getSumTotalTermFreq("CONTENT");
    }

    public void close() throws IOException {
        // you should implement this method when necessary
        directoryReader.close();
        directory.close();
    }


}
