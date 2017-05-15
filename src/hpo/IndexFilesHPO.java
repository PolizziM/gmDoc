package hpo;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import java.io.*;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by meh on 11/05/2017.
 */
public class IndexFilesHPO {

    private IndexFilesHPO() {}

    /** Index all text files under a directory. */
    public static void indexHpo() {
        String usage = "java org.apache.lucene.demo.IndexFiles"
                + " [-index INDEX_PATH] [-docs DOCS_PATH] [-update]\n\n"
                + "This indexes the documents in DOCS_PATH, creating a Lucene index"
                + "in INDEX_PATH that can be searched with SearchFiles";
        String indexPath = "indexHPO";
        String docsPath = "ressources/hpo/hp.obo";
        boolean create = true;

        final File docDir = new File(docsPath);
        if (!docDir.exists() || !docDir.canRead()) {
            System.out.println("Document directory '" +docDir.getAbsolutePath()+ "' does not exist or is not readable, please check the path");
            System.exit(1);
        }

        Date newDoc = new Date();
        try {
            //System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

            if (create) {
                // Create a new index in the directory, removing any
                // previously indexed documents:

                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            } else {
                // Add new documents to an existing index:
                iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            }

            // Optional: for better indexing performance, if you
            // are indexing many documents, increase the RAM
            // buffer.  But if you do this, increase the max heap
            // size to the JVM (eg add -Xmx512m or -Xmx1g):
            //
            // iwc.setRAMBufferSizeMB(256.0);

            IndexWriter writer = new IndexWriter(dir, iwc);
            ;
            indexDocs(writer, docDir);

            // NOTE: if you want to maximize search performance,
            // you can optionally call forceMerge here.  This can be
            // a terribly costly operation, so generally it's only
            // worth it when your index is relatively static (ie
            // you're done adding documents to it):
            //
            // writer.forceMerge(1);

            writer.close();

            Date end = new Date();
           // System.out.println(end.getTime() - newDoc.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }
    }

    /**
     * Indexes the given file using the given writer, or if a directory is given,
     * recurses over files and directories found under the given directory.
     *
     * NOTE: This method indexes one document per input file.  This is slow.  For good
     * throughput, put multiple documents into your input file(s).  An example of this is
     * in the benchmark module, which can create "line doc" files, one document per line,
     * using the
     * <a href="../../../../../contrib-benchmark/org/apache/lucene/benchmark/byTask/tasks/WriteLineDocTask.html"
     * >WriteLineDocTask</a>.
     *
     * @param writer Writer to the index where the given file/dir info will be stored
     * @param file The file to index, or the directory to recurse into to find files to index
     * @throws IOException If there is a low-level I/O error
     */
    static void indexDocs(IndexWriter writer, File file)
            throws IOException {
        // do not try to index files that cannot be read
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                // an IO error could occur
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(writer, new File(file, files[i]));
                    }
                }
            } else {

                FileInputStream fis;
                try {
                    fis = new FileInputStream(file);
                } catch (FileNotFoundException fnfe) {
                    // at least on windows, some temporary files raise this exception with an "access denied" message
                    // checking if the file can be read doesn't help
                    return;
                }

                try {

                    InputStreamReader   ipsr        = new InputStreamReader(fis);
                    BufferedReader      br          = new BufferedReader(ipsr);
                    String              line;
                    Document            doc         = new Document();
                    String              id          = "";
                    boolean             newDoc      = true;
                    int                 count       = 0;

                    while ((line=br.readLine()) !=  null) {


                        count++;


                        if (line.startsWith("[Term]")) {

                            if(newDoc) {
                                doc     = new Document();
                                line    = br.readLine();
                                id = line.substring(7);
                                doc.add(new StoredField("id", id));
                                newDoc   = false;
                            } else {
                                //System.out.println("adding " + id);
                                writer.addDocument(doc);
                                doc     = new Document();
                                line    = br.readLine();
                                id = line.substring(7);
                                doc.add(new StoredField("id", id));
                            }


                            //System.out.println("\nid:" +id);
                        }

                        if(line.startsWith("name:")) {
                            String str = "";
                            str=line.substring(6);
//                            if(line.matches("^.*: "))
//                                break;
//                            else {
//                                str += line + " ";
                                doc.add(new TextField("name", str, Field.Store.YES));
                                //System.out.println("\nname: " + str.substring(6) + "\n");
//                            }
                        }

                        int i = 0;
                        if(line.startsWith("synonym:")) {
                        	String synonymes=line.substring(line.indexOf("\"") +1, line.lastIndexOf("\""));
                            while((line=br.readLine()).startsWith("synonym:")) {
                                synonymes = synonymes + ";"+ line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                            }
                            doc.add(new StoredField("synonym", synonymes));
                        }


                    }

                    writer.addDocument(doc);
                    br.close();

                } finally {

                    fis.close();
                }
            }
        }
    }
}
