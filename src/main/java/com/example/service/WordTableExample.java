package com.example.service;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign.CENTER;


/**
 * @ClassName: WordTableExample
 * @Description:
 * @Author: Administrator
 * @Date: 2023年02月23日 15:07
 * @Version: 1.0
 **/
public class WordTableExample {
    public static void createWord(List<List<Double>> data) throws IOException {
        // 创建新的Word文档
        XWPFDocument document = new XWPFDocument();
        // 创建段落对象
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);

        // 创建标题
        XWPFRun titleRun = paragraph.createRun();
        titleRun.setBold(true);
        titleRun.setFontSize(10);
        titleRun.setColor("009966");
        titleRun.setText("表5.2-7   有组织污染源估算模型计算结果表");
        int rowCount = data.size();
        // 创建表格对象
        XWPFTable table = document.createTable(rowCount, 3);
        table.setTableAlignment(TableRowAlign.CENTER);

        //垂直居中
        XWPFTableCell cell = table.getRow(0).getCell(0);
        cell.setVerticalAlignment(CENTER);
        // 合并第1行的23列 合并第1列的12行
        mergeCellsHorizontal(table, 0, 1, 2);
        mergeCellsVertically(table, 0, 0, 1);
        // 设置表格宽度
        table.setWidth("100%");
        // 创建表头行1
        XWPFTableRow headerRow1 = table.getRow(0);
        headerRow1.getCell(0).setText("下风向距离（m）");
        headerRow1.getCell(0).getParagraphs().get(0).getRuns().get(0).setBold(true);
        headerRow1.getCell(0).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        headerRow1.getCell(0).getParagraphs().get(0).getRuns().get(0).setColor("006699");
        headerRow1.getCell(1).setText("造粒车间排放口（DA001）（非甲烷总烃）");
        headerRow1.getCell(1).getParagraphs().get(0).getRuns().get(0).setBold(true);
        headerRow1.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        headerRow1.getCell(1).getParagraphs().get(0).getRuns().get(0).setColor("006699");
        // 创建表头行2
        XWPFTableRow headerRow = table.getRow(1);
        headerRow.getCell(1).setText("预测质量浓度（μg/m3）");
        headerRow.getCell(1).getParagraphs().get(0).getRuns().get(0).setBold(true);
        headerRow.getCell(1).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        headerRow.getCell(1).getParagraphs().get(0).getRuns().get(0).setColor("006699");
        headerRow.getCell(2).setText("占标率（%）");
        headerRow.getCell(2).getParagraphs().get(0).getRuns().get(0).setBold(true);
        headerRow.getCell(2).getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        headerRow.getCell(2).getParagraphs().get(0).getRuns().get(0).setColor("006699");

        // 设置表格内容
        for (int i = 2; i < rowCount; i++) {
            for (int j = 0; j < 3; j++) {
                XWPFTableCell cel = table.getRow(i).getCell(j);
                cel.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(1500));
                cel.setVerticalAlignment(CENTER);//垂直居中
                cel.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);//水平居中
                cel.setText(data.get(i).get(j).toString());
            }
        }

        // 将文档写入文件
        FileOutputStream out = new FileOutputStream("table.docx");
        document.write(out);
        out.close();
    }

    /**
     * word单元格行合并
     *
     * @param table    表格
     * @param col      合并行所在列
     * @param startRow 开始行
     * @param endRow   结束行
     */
    public static void mergeCellsVertically(XWPFTable table, int col, int startRow, int endRow) {
        for (int i = startRow; i <= endRow; i++) {
            XWPFTableCell cell = table.getRow(i).getCell(col);
            if (i == startRow) {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * word单元格列合并
     *
     * @param table     表格
     * @param row       合并列所在行
     * @param startCell 开始列
     * @param endCell   结束列
     */
    public static void mergeCellsHorizontal(XWPFTable table, int row, int startCell, int endCell) {
        for (int i = startCell; i <= endCell; i++) {
            XWPFTableCell cell = table.getRow(row).getCell(i);
            if (i == startCell) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
}
