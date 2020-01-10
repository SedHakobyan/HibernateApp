package com.bee.am;

import java.io.IOException;
import java.text.ParseException;

public class TestExcel {

    public static void main(String [] as) throws IOException, ParseException {

        ExcelWriter tr = new ExcelWriter();
        tr.savetoExcel_one();

    }




}
