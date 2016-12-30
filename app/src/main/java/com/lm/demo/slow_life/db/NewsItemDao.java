package com.lm.demo.slow_life.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.lm.demo.slow_life.bean.LifeBean;

import java.util.ArrayList;
import java.util.List;

public class NewsItemDao {

    public static final int PERPAGE_ITEM_COUNT = 6;
    private DBHelper mHelper;

    public static final String[] COLUMNS = {"title", "link", "imgLink", "content", "date", "newsType"};

    public NewsItemDao(Context context) {
        mHelper = new DBHelper(context);
    }


    /**
     * 更新表数据
     *
     * @param newsType
     * @param items
     */
    public void refreshData(int newsType, List<LifeBean> items) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String sql = "delete from " + mHelper.TABLE_LIFE + " where newsType=?";
            db.execSQL(sql, new Object[]{newsType});
            for (LifeBean item : items) {
                if (null != item) {
                    ContentValues values = new ContentValues();
                    values.put(COLUMNS[0], item.getTitle());
                    values.put(COLUMNS[1], item.getLink());
                    values.put(COLUMNS[2], item.getImgLink());
                    values.put(COLUMNS[3], item.getContent());
                    values.put(COLUMNS[4], item.getTime());
                    values.put(COLUMNS[5], item.getLifeType());
                    db.insert(mHelper.TABLE_LIFE, null, values);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 添加新闻列表
     *
     * @param items
     */
    public void addNewsItems(List<LifeBean> items) {
        if (null == items || items.isEmpty()) {
            return;
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            for (LifeBean item : items) {
                if (null == item) {
                    continue;
                }
                ContentValues values = new ContentValues();
                values.put(COLUMNS[0], item.getTitle());
                values.put(COLUMNS[1], item.getLink());
                values.put(COLUMNS[2], item.getImgLink());
                values.put(COLUMNS[3], item.getContent());
                values.put(COLUMNS[4], item.getTime());
                values.put(COLUMNS[5], item.getLifeType());
                db.insert(mHelper.TABLE_LIFE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<LifeBean> getNewsItems(int page, int newsType) {
        List<LifeBean> items = new ArrayList<>();
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int offset = (page - 1) * PERPAGE_ITEM_COUNT;
        String sql = "select title,link,imgLink,content,date,newsType "+" from " + mHelper.TABLE_LIFE + " where newsType=? limit ?,?";
        try {
            db.beginTransaction();
            Cursor cursor = db.rawQuery(sql, new String[]{newsType + "", offset + "", PERPAGE_ITEM_COUNT + ""});
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                LifeBean item = new LifeBean();
                item.setTitle(cursor.getString(0));
                item.setLink(cursor.getString(1));
                item.setImgLink(cursor.getString(2));
                item.setContent(cursor.getString(3));
                item.setTime(cursor.getString(4));
                item.setLifeType(cursor.getInt(5));
                items.add(item);
                cursor.moveToNext();
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return items;
    }


}
