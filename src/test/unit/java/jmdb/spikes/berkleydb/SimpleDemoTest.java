package jmdb.spikes.berkleydb;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static com.sleepycat.bind.tuple.StringBinding.entryToString;
import static com.sleepycat.bind.tuple.StringBinding.stringToEntry;
import static com.sleepycat.je.OperationStatus.SUCCESS;
import static java.lang.String.format;
import static java.lang.System.out;
import static java.util.UUID.randomUUID;


public class SimpleDemoTest {

    private static String SIMPLE_DATASTORE_FILENAME = "~/tmp/berkleydb/simple.db";

    private static String EXAMPLE_UUID = randomUUID().toString();
    private static String EXAMPLE_JSON = "{\n" +
            "  \"is\" : \"vcard\",\n" +
            "  \"postcode\" : \"TR8 5ET\",\n" +
            "}";



    /**
     * BerkleyDB databases are like collections in mongodb - you can store objects of a certain types in there, you then
     * create other's to do the mapping They are basically key value "buckets"
     */
    @Test
    public void create_a_simple_datastore() throws Exception {

        Environment environment = new Environment(createDatastoreFile(),
                                                  createEnvironmentConfig());

        Transaction tx = environment.beginTransaction(null, null);

        Database db = environment.openDatabase(tx,
                                               "simpleDb",
                                               createDbConfig());

        tx.commit();


        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        tx = environment.beginTransaction(null, null);

        stringToEntry(EXAMPLE_UUID, keyEntry);
        stringToEntry(EXAMPLE_JSON, dataEntry);

        out.println("Inserting an object ...");
        OperationStatus status = db.put(tx, keyEntry, dataEntry);
        validateSuccess(status, "put");
        out.println("ok.");

        tx.commit();

        out.println("Reading all objects...");
        Cursor cursor = db.openCursor(null, null);
        while (cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT) == SUCCESS) {
            out.println("key = " + entryToString(keyEntry) + ":\n  " + entryToString(dataEntry));
        }
        cursor.close();
        out.println("Ok.");


        out.println(format("Now retrieving the key [%s] ...", EXAMPLE_UUID));
        status = db.get(null, keyEntry, dataEntry, LockMode.DEFAULT);
        validateSuccess(status, "get");

        out.println(format("Data from [%s]:\n", EXAMPLE_UUID, entryToString(dataEntry)));

        db.close();
        environment.close();


    }

    private static void validateSuccess(OperationStatus status, String message) {
        if (status != SUCCESS) {
            throw new RuntimeException(format("DB operation [%s] failed with status [%s]", status));
        }
    }

    private static DatabaseConfig createDbConfig() {
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.setTransactional(true);
        dbConfig.setAllowCreate(true);
        dbConfig.setSortedDuplicates(true); return dbConfig;
    }

    private static EnvironmentConfig createEnvironmentConfig() {
        EnvironmentConfig envConfig = new EnvironmentConfig();
        envConfig.setTransactional(true);
        envConfig.setAllowCreate(true);
        return envConfig;
    }

    private static File createDatastoreFile() {
        out.println();
        File dbFile = new File(SIMPLE_DATASTORE_FILENAME);
        if (dbFile.exists()) {
            dbFile.delete();
        }
        dbFile.mkdirs(); return dbFile;
    }
}