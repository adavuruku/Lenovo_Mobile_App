package com.example.lenovoapp;

import android.provider.BaseColumns;

import java.util.List;

/**
 * Created by sherif146 on 03/01/2018.
 */

public class dbColumnList {

    public static List<myModels> allLenovo;
    public static int itemIdClick;
    public static class lenovoData implements BaseColumns {
        public static final String TABLE_NAME = "lenovodata";
        public static final String COLUMN_RECORDID= "id";
        public static final String COLUMN_RECORDTITLE = "brand";
        public static final String COLUMN_RECORDCONTENT = "description";
        public static final String COLUMN_IMAGE = "image";
    }

}
