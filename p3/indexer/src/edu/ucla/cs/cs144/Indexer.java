package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
 
    private IndexWriter indexWriter = null;
    private String indexDir = "/var/lib/lucene/p3_index";

    /** Creates a new instance of Indexer */
    public Indexer() {
    }
 
    /** Returns an IndexWriter, or creates one if none exists **/
    public IndexWriter getIndexWriter() throws IOException {
        if(indexWriter==null) {
            Directory d = FSDirectory.open(new File(this.indexDir));
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, 
                new StandardAnalyzer());
            this.indexWriter = new IndexWriter(d, config);
        }

        return this.indexWriter;
    }

    /** Given the input item, write each to the index **/
    public void writeToIndex(Item item) {
        try {
            IndexWriter idw = this.getIndexWriter();
            Document d = new Document();
            d.add(new StringField("id", item.getID()+"", Field.Store.YES));
            d.add(new StringField("name", item.getName(), Field.Store.YES));
            String total = item.getName() + " " + item.getCategories() + 
            " " + item.getDescription();
            d.add(new TextField("searchable", total, Field.Store.NO));
            idw.addDocument(d);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /** Returns the result set of querying for items **/
    public ResultSet getItems(Connection conn, int limit, int offset) {
        ResultSet rs = null;
        try {
            String getQ = "select item_id, name, description, category from Item," + 
            "ItemCategory where item_id=item order by item_id";
            PreparedStatement getItems = conn.prepareStatement(getQ);
            //getItems.setInt(1, limit);
            //getItems.setInt(2, offset);

            rs = getItems.executeQuery();
        }
        catch(SQLException sqle) {
            sqle.printStackTrace();
            System.exit(-1);
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return rs;
    }

    /** close index writer, if exists **/
    public void closeIndexWriter() throws IOException {
        if(this.indexWriter!=null) indexWriter.close();
    }

    public void rebuildIndexes() {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
    	try {
    	    conn = DbManager.getConnection(true);
    	} catch (SQLException ex) {
    	    System.out.println(ex);
            System.exit(-1);
    	}


    	/*
    	 * Add your code here to retrieve Items using the connection
    	 * and add corresponding entries to your Lucene inverted indexes.
             *
             * You will have to use JDBC API to retrieve MySQL data from Java.
             * Read our tutorial on JDBC if you do not know how to use JDBC.
             *
             * You will also have to use Lucene IndexWriter and Document
             * classes to create an index and populate it with Items data.
             * Read our tutorial on Lucene as well if you don't know how.
             *
             * As part of this development, you may want to add 
             * new methods and create additional Java classes. 
             * If you create new classes, make sure that
             * the classes become part of "edu.ucla.cs.cs144" package
             * and place your class source files at src/edu/ucla/cs/cs144/.
    	 * 
    	 */

        //used to incremeent values accordingly to iteratively get all items
        int limit = 4;//deprecated
        int offset = 0;//dep
        ResultSet rs = null;
        int prevID, id;                    //holds previous id checked
        String name, description, categories, cat;
        Item curr = null;                  //represents the current item

        try {

            rs = getItems(conn, limit, offset); //get items
            if(!rs.next()) System.out.println("No items"); //no items retrieved from DB
            prevID = -1;
            categories = "";
            do {
                //grab needed data
                id = rs.getInt("item_id");
                name = rs.getString("name");
                cat = rs.getString("category");
                description = rs.getString("description");
                
                if(prevID < 0) {
                    prevID = id;     //only occurs once in entire loop life
                    curr = new Item(id, name, cat, description);
                }

                //main index logic
                if(prevID==id) {    //if the item id is same as the last one
                    categories += (" " + cat);  //concat the category
                }
                else {              //a different item visited, so write previous one
                    //finalize and write current item to index
                    curr.setCategories(categories);
                    writeToIndex(curr);        

                    //reset vars and init to this new item
                    curr = new Item(id, name, cat, description);
                    categories = curr.getCategories();
                }

                prevID = id;    //update previous id visited   


            } while(rs.next());

            //need to add the last item, writes only occurred before if id != prevID
            if(curr != null && !(curr.getCategories().equals(categories)))
                curr.setCategories(categories);

            writeToIndex(curr);

            //all "writing" has been queued/concluded, close index to finalize
            closeIndexWriter();
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }


            // close the database connection
    	try {
    	    conn.close();
    	} catch (SQLException ex) {
    	    System.out.println(ex);
    	}

    }    

    public static void main(String args[]) {
        Indexer idx = new Indexer();
        idx.rebuildIndexes();
    }   
}
