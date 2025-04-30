package com.prabhu.myapp.utils.testdata;

import com.google.gson.Gson;
import java.io.FileReader;

public class JsonDataReader {
    public static <T> T readJson(String filePath, Class<T> clazz) throws Exception {
        return new Gson().fromJson(new FileReader(filePath), clazz);
    }
}
