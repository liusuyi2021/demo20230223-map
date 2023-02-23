package com.example.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: ReadOutFile
 * @Description:
 * @Author: Administrator
 * @Date: 2023年02月23日 14:49
 * @Version: 1.0
 **/
public class ReadOutFile {

    public static void main(String[] args) {
        try {
            List<List<Double>> data = new ArrayList<>();
            FileInputStream fileInputStream = new FileInputStream("test10.out");
            Scanner scanner = new Scanner(fileInputStream);
            boolean startParsing = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line == "")
                {
                    continue;
                }
                if (line.contains("AERSCREEN MAXIMUM IMPACT SUMMARY"))
                {
                    startParsing = false;
                    continue;
                }
                if (line.contains("(m)    (ug/m3)     (m)"))
                {
                    startParsing = true;
                    continue;
                }
                if (startParsing && !line.contains("---"))
                {
                    List<Double> row=new ArrayList<>();
                    // Split line into columns
                    String[] columns = line.split("\\s+");

                    // Parse columns as floats and add to list
                    switch(columns.length/3)
                    {
                        case 1:
                            row.add(Double.parseDouble(columns[0]));
                            row.add(Double.parseDouble(columns[1]));
                            row.add(Double.parseDouble(columns[2]));
                            data.add(row);break;
                        case 2:
                            row.add(Double.parseDouble(columns[0]));
                            row.add(Double.parseDouble(columns[1]));
                            row.add(Double.parseDouble(columns[2]));
                            data.add(row);
                            row = new ArrayList<>();
                            row.add(Double.parseDouble(columns[3]));
                            row.add(Double.parseDouble(columns[4]));
                            row.add(Double.parseDouble(columns[5]));
                            data.add(row); break;
                    }
                }
            }
            // 按子列表的第一个元素排序
            Collections.sort(data, new Comparator<List<Double>>() {
                @Override
                public int compare(List<Double> o1, List<Double> o2) {
                    return o1.get(0).compareTo(o2.get(0));
                }
            });
            System.out.println(data);
            WordTableExample.createWord(data);
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
