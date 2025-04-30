package com.prabhu.myapp.utils.testdata;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvDataReader {
    public static List<String[]> readCsv(String filePath) throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            return new ArrayList<>(reader.readAll());
        }
    }
}
