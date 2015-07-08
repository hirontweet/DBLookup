package jp.hirontweet.dblookup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Hirofumi on 7/8/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="database";
    private static final String DB_FILE_NAME = "database.sqlite";
    private static final int DB_VERSION = 1;
    private Context mContext;
    private File mDBPath;
    private boolean mDBExist = true;

    public DBOpenHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        mDBPath = context.getDatabasePath(DB_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onOpen(db);
        mDBExist = false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String databasePath = this.mDBPath.getAbsolutePath();
        File file = new File(databasePath);

        if(file.exists()){
            file.delete();
        }
        mDBExist = false;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {

        SQLiteDatabase database = super.getWritableDatabase();

        if(!mDBExist){
            try{
                database.close();
                database = copyDatabaseFromAssets();
                mDBExist = true;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return database;
    }

    private SQLiteDatabase copyDatabaseFromAssets() throws IOException {
        InputStream inputStream = this.mContext.getAssets().open(DB_FILE_NAME);
        OutputStream outputStream = new FileOutputStream(mDBPath);

        byte[] buffer = new byte[1024];
        int size;
        while((size = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, size);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        return super.getWritableDatabase();
    }
}
