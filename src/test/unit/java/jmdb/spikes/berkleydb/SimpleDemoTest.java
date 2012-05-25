package jmdb.spikes.berkleydb;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;
import org.junit.After;
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

    private static String SIMPLE_DATASTORE_FILENAME = System.getProperty("user.home") + "/tmp/berkleydb/simple.db";

    private static String EXAMPLE_UUID = randomUUID().toString();
    private static String EXAMPLE_JSON = "{\n" +
            "  \"is\" : \"vcard\",\n" +
            "  \"postcode\" : \"TR8 5ET\",\n" +
            "}";
    private Environment environment;
    private Database db;

    @Before
    public void createAndOpenDb() {
        environment = new Environment(createDatastoreDir(),
                                      createEnvironmentConfig());

        Transaction tx = environment.beginTransaction(null, null);

        db = environment.openDatabase(tx,
                                      "simpleDb",
                                      createDbConfig());

        tx.commit();

        populateASimpleJsonObject();
    }

    @After
    public void closeDb() {
        db.close();
        environment.close();
    }

    /**
     * BerkleyDB databases are like collections in mongodb - you can store objects of a certain types in there, you then
     * create other's to do the mapping They are basically key value "buckets"
     */
    @Test
    public void read_with_a_cursor() {

        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        out.println("Reading all objects...");
        Cursor cursor = db.openCursor(null, null);
        while (cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT) == SUCCESS) {
            out.println("key = " + entryToString(keyEntry) + ":\n" + entryToString(dataEntry));
        }
        cursor.close();
        out.println("Ok.");
    }

    @Test
    public void read_by_key() {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        stringToEntry(EXAMPLE_UUID, keyEntry);
        OperationStatus status; out.println(format("Now retrieving the key [%s] ...", EXAMPLE_UUID));

        status = db.get(null, keyEntry, dataEntry, LockMode.DEFAULT);


        validateSuccess(status, "get");

        out.println(format("Data from [%s]:\n%s", EXAMPLE_UUID, entryToString(dataEntry)));
        out.println("Ok.\n");

    }

    private void populateASimpleJsonObject() {
        DatabaseEntry keyEntry = new DatabaseEntry();
        DatabaseEntry dataEntry = new DatabaseEntry();

        Transaction tx = db.getEnvironment().beginTransaction(null, null);

        stringToEntry(EXAMPLE_UUID, keyEntry);
        stringToEntry(EXAMPLE_JSON, dataEntry);

        out.println("Inserting an object ...");
        OperationStatus status = db.put(tx, keyEntry, dataEntry);
        validateSuccess(status, "put");
        out.println("ok.");

        tx.commit();
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

    private static File createDatastoreDir() {
        out.println(format("Creating clean database file [%s]", SIMPLE_DATASTORE_FILENAME));
        File dbFile = new File(SIMPLE_DATASTORE_FILENAME);
        if (dbFile.exists()) {
            cleanDirectory(dbFile);
        }
        dbFile.mkdirs();
        return dbFile;
    }

    private static void cleanDirectory(File dir) {
        File[] files = dir.listFiles();

        for (int i = 0; i < files.length; ++i) {
            File f = files[i];
            deleteFile(f);
        }

        deleteFile(dir);
    }

    private static void deleteFile(File f) {
        if (!f.delete()) throw new RuntimeException(format("Could not delete file [%s]", f.getAbsolutePath()));
    }
}