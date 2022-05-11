package com.guilhermefernandes.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqlHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fitness_tracker.db";
    private static final int DB_VERSION = 1;



    private static SqlHelper INSTANCE;

    static SqlHelper getInstance(Context context){
        if (INSTANCE == null)
            INSTANCE = new SqlHelper(context);
        return INSTANCE;
    }




// colocamos privado para evitar que o contrutor usa do SQLHELPER, ou seja não vamos conseguir usar o SQL helper fora da classe
    private SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        db.execSQL(
                "CREATE TABLE calc (id INTEGER primary key, type_calc TEXT, res DECIMAL, created_date DATETIME)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.d("Teste","on Upgrade disparado");
    }

    List<Register> getRegisterBy(String type){
        List<Register> registers = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM calc WHERE type_calc = ?", new String[]{type});

        try {
            if (cursor.moveToFirst()) {
                do {
            Register register = new Register();

            register.type = cursor.getString(cursor.getColumnIndex("type_calc"));
            register.response = cursor.getDouble(cursor.getColumnIndex("res"));
            register.createdDate = cursor.getString(cursor.getColumnIndex("created_date"));

                    registers.add(register);
                } while (cursor.moveToNext());
            }

        }catch (Exception e){
            Log.e("SQLite", e.getMessage(), e);
        }finally {
            if (cursor != null && !cursor.isClosed())
                cursor.close();
        }

        return registers;

    }

    long addItem(String type, double response){
        SQLiteDatabase db = getWritableDatabase();  // Writab para escrever dados

        long calcId = 0;
        try {
            db.beginTransaction();

            ContentValues values = new ContentValues();
            values.put("type_calc", type);
            values.put("res", response);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("pt", "BR"));
            String now = sdf.format(new Date()); // para passar com a data atual

            values.put("created_date", now);
            calcId = db.insertOrThrow("calc", null, values); //ou insere ou lança uma execção caso der um erro nesta sintasse SQL
            db.setTransactionSuccessful();

        }catch (Exception e){
            Log.e("SQLite", e.getMessage(), e);

        } finally {
            if (db.isOpen())
                db.endTransaction();
        }

        return calcId;

    }
}
