package at.jps.sanction.core.util.string;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.jps.sanction.model.wl.entities.SL_Entry;

public class LuceneIndex {

    static final Logger               logger = LoggerFactory.getLogger(LuceneIndex.class);

    static HashMap<String, Directory> searchIndices;

    public void buildIndex(final String indexName, List<SL_Entry> searchListEntries) {

        if (searchListEntries != null) {
            Directory index = getSearchIndices().get(indexName);

            if (index == null) {
                index = new RAMDirectory();
                getSearchIndices().put(indexName, index);
            }

            final StandardAnalyzer analyzer = new StandardAnalyzer();  // TODO: add here some more cool stuff
            final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);

            try {

                final IndexWriter indexWriter = new IndexWriter(index, indexWriterConfig);

                for (final SL_Entry sl_entry : searchListEntries) {
                    addDoc(indexWriter, "field", sl_entry.getSearchValue(), sl_entry.getReferencedEntity().getWL_Id(), sl_entry.getReferencedEntity().getListName());

                    if (logger.isDebugEnabled()) {
                        logger.debug("Index:" + indexName + " / " + sl_entry.getReferencedEntity().getListName() + "  Add field " + sl_entry.getSearchValue());
                    }
                }

                indexWriter.close();
            }
            catch (final Exception x) {
                logger.error("Lucene Index [" + indexName + "] Building failed with" + x.toString());
                logger.debug("Lucene Index Building failed ", x);
            }
        }
        else {
            logger.info("Lucene Index [" + indexName + "] not build due to NULL list");
        }
    }

    private static void addDoc(IndexWriter w, final String fieldName, final String value, final String refId, final String refList) throws IOException {
        final Document doc = new Document();

        doc.add(new TextField(fieldName, value, Field.Store.YES));
        // the watchlist reference
        doc.add(new StoredField("refId", refId));
        doc.add(new StoredField("refList", refList));

        w.addDocument(doc);
    }

    public static HashMap<String, Directory> getSearchIndices() {

        if (searchIndices == null) {
            searchIndices = new HashMap<>();
        }

        return searchIndices;
    }

    public static void setSearchIndices(HashMap<String, Directory> searchIndices) {
        LuceneIndex.searchIndices = searchIndices;
    }

    public static void searchIndex(String searchString) throws IOException {
        /*
         * for (final String key : getSearchIndices().keySet())
         * {
         * final Directory directory = getSearchIndices().get(key);
         * final IndexReader indexReader = IndexReader. open(directory);
         * final IndexSearcher indexSearcher = new IndexSearcher(indexReader);
         * final Analyzer analyzer = new StandardAnalyzer();
         * final QueryParser queryParser = new QueryParser(FIELD_CONTENTS, analyzer);
         * final Query query = queryParser.parse(searchString);
         * final Hits hits = indexSearcher.search(query);
         * System.out.println("Number of hits: " + hits.length());
         * }
         */
        // System.out.println("Searching for '" + searchString + "'");
        // Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
        // IndexReader indexReader = IndexReader.open(directory);
        // IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //
        //
        // Iterator<Hit> it = hits.iterator();
        // while (it.hasNext()) {
        // Hit hit = it.next();
        // Document document = hit.getDocument();
        // String path = document.get(FIELD_PATH);
        // System.out.println("Hit: " + path);

    }

}
