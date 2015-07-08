package jp.hirontweet.dblookup;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private DBOpenHelper mOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_commit = (Button)findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);

        mOpenHelper = new DBOpenHelper(this);
    }

    @Override
    public void onClick(View v) {

        EditText et_query_bar = (EditText)findViewById(R.id.et_sql_query);
        String query = et_query_bar.getText().toString();

        if(query.equals("")){
            Toast.makeText(this, "Query Bar is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        runQuery(query);

    }

    public void runQuery(String query){

        Cursor cursor = null;
        try{
            cursor = mOpenHelper.getWritableDatabase().rawQuery(query, null);
        }catch(SQLiteException e){
            exceptionCaught("SQLiteException");
        }

        List<String> columnNames;
        int resultRows = 0;
        String stringResult = "";
        StringBuffer strBuffer;
        if(cursor != null){
            columnNames = Arrays.asList(cursor.getColumnNames());
            resultRows = cursor.getCount();
            Log.v("ResultRows", resultRows + "");
            Log.v("columnNames", columnNames.size() + "");
            for(String column: columnNames){
                Log.v("columns", column);
            }
            Toast.makeText(this, "Found " + resultRows + " of rows", Toast.LENGTH_SHORT).show();
            cursor.moveToFirst();
            for(int i = 0; i < resultRows; i++){
                strBuffer = new StringBuffer("Row " + i + "\n");
                for(int j = 0; j < columnNames.size(); j++){
                    Log.v("columnString",cursor.getString(cursor.getColumnIndex(columnNames.get(j))));
                    strBuffer.append(cursor.getString(cursor.getColumnIndex(columnNames.get(j))));
                    strBuffer.append(", ");
                }
                strBuffer.append("\n");
                stringResult += strBuffer.toString();
                cursor.moveToNext();
            }
            TextView tv_result = (TextView) findViewById(R.id.tv_result);
            tv_result.setText(stringResult);
        }
    }

    public void exceptionCaught(String msg){

        TextView tv_result = (TextView)findViewById(R.id.tv_result);
        tv_result.setText(msg);

        Toast.makeText(this, "Exception Caught", Toast.LENGTH_SHORT).show();
    }
}
