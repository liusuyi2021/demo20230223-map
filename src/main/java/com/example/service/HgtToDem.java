package com.example.service;

/**
 * @ClassName HgtToDem
 * @Description:
 * @Author 刘苏义
 * @Date 2023/2/23 20:49
 * @Version 1.0
 */

import com.sun.media.jfxmediaimpl.platform.Platform;
import org.gdal.gdal.Band;
import org.gdal.gdal.Dataset;
import org.gdal.gdal.WarpOptions;
import org.gdal.gdal.gdal;
import org.gdal.gdalconst.gdalconstConstants;
import org.gdal.osr.SpatialReference;

import java.io.IOException;
import java.lang.annotation.Native;
import java.util.Arrays;
import java.util.Vector;

public class HgtToDem {
    public static String demConvert(String inHgtFile, String outDemFile, double[] xy) {
        try {
            // 初始化GDAL
            gdal.AllRegister();
            // 输入文件路径
//            String inHgtFile = "F:\\desktop\\hgt转dem\\N41E080.hgt";
//            // 输出文件路径
//            String outDemFile = "F:\\desktop\\hgt转dem\\java.dem";
            gdal.AllRegister();
            gdal.SetConfigOption("GDAL_DATA", System.getProperty("user.dir") + "\\gdal-data"); // 设置GDAL_DATA路径
            gdal.AllRegister();
            Dataset hgtDs = gdal.Open(inHgtFile, gdalconstConstants.GA_ReadOnly); // 打开HGT文件
            // 设置 WarpOptions
            String wkt = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]]";
            double minX = xy[0] - 0.5;
            double minY = xy[1] - 0.5;
            double maxX = xy[0] + 0.5;
            double maxY = xy[1] + 0.5;
            String[] warpOptions = new String[]
                    {
                            "-of", "USGSDEM", // 输出格式为 USGS DEM
                            "-t_srs", wkt, // 投影为 WGS84
                            "-r", "cubic", // 三次样条内插法
                            "-te", String.valueOf(minX), String.valueOf(minY), String.valueOf(maxX), String.valueOf(maxY), // 地理范围
                            "-ts", "1200", "1200" // 输出图像大小
                    };
            Vector warpOptionsVector = new Vector(Arrays.asList(warpOptions));
            Dataset demDs = gdal.Warp(outDemFile, new Dataset[]{hgtDs}, new WarpOptions(warpOptionsVector), null); // 进行转换

            demDs.FlushCache();
            hgtDs.delete();
            demDs.delete();
            System.out.println("转换成功！" + outDemFile);
            return outDemFile;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return "";
        }
    }

    public static void main(String[] args)
     {
        try {
            System.setProperty("java.library.path", System.getProperty("user.dir") + "\\gdal");
            System.load(System.getProperty("user.dir")+"/gdal/gdalalljni.dll");
           // System.loadLibrary("gdalalljni");

            // 初始化GDAL
            gdal.AllRegister();
            // 输入文件路径
            String inHgtFile = "F:\\desktop\\hgt转dem\\N41E080.hgt";
            // 输出文件路径
            String outDemFile = "F:\\desktop\\hgt转dem\\java.dem";
            gdal.AllRegister();
            gdal.SetConfigOption("GDAL_DATA", System.getProperty("user.dir") + "\\gdal-data"); // 设置GDAL_DATA路径
            gdal.AllRegister();
            Dataset hgtDs = gdal.Open(inHgtFile, gdalconstConstants.GA_ReadOnly); // 打开HGT文件
            // 设置 WarpOptions
            String wkt = "GEOGCS[\"WGS 84\",DATUM[\"WGS_1984\",SPHEROID[\"WGS 84\",6378137,298.257223563,AUTHORITY[\"EPSG\",\"7030\"]],TOWGS84[0,0,0,0,0,0,0],AUTHORITY[\"EPSG\",\"6326\"]],PRIMEM[\"Greenwich\",0,AUTHORITY[\"EPSG\",\"8901\"]],UNIT[\"degree\",0.01745329251994328,AUTHORITY[\"EPSG\",\"9122\"]],AUTHORITY[\"EPSG\",\"4326\"]]";
            double minX = 80.2 - 0.5;
            double minY = 41.7 - 0.5;
            double maxX = 80.2 + 0.5;
            double maxY = 41.7 + 0.5;
            String[] warpOptions = new String[]
                    {
                            "-of", "USGSDEM", // 输出格式为 USGS DEM
                            "-t_srs", wkt, // 投影为 WGS84
                            "-r", "cubic", // 三次样条内插法
                            "-te", String.valueOf(minX), String.valueOf(minY), String.valueOf(maxX), String.valueOf(maxY), // 地理范围
                            "-ts", "1200", "1200" // 输出图像大小
                    };
            Vector warpOptionsVector = new Vector(Arrays.asList(warpOptions));
            Dataset demDs = gdal.Warp(outDemFile, new Dataset[]{hgtDs}, new WarpOptions(warpOptionsVector), null); // 进行转换

            demDs.FlushCache();
            hgtDs.delete();
            demDs.delete();
            System.out.println("转换成功！" + outDemFile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
