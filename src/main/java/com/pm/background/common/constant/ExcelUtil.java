package com.pm.background.common.constant;
import com.pm.background.common.utils.util.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Larry
 * @date 2020/6/4 0004 8:52
 * @description
 */
public final class ExcelUtil {


    private ExcelUtil(){

    }
    // 正文字体
    public  static Font contextFont(HSSFWorkbook wb){
        Font contextFont = wb.createFont();
        contextFont.setFontName("微软雅黑");
        contextFont.setFontHeightInPoints((short) 12);
        contextFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contextFont.setColor(HSSFColor.BLACK.index);
        return  contextFont;
    }
    // 表头字体
    public static  Font  headerFont(HSSFWorkbook wb){
        Font headerFont = wb.createFont();
        headerFont.setFontName("微软雅黑");
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setColor(HSSFColor.BLACK.index);
        return  headerFont;
    }


        // 表头样式，左右上下居中
        public static  CellStyle  headerStyle(HSSFWorkbook wb) {
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFont(headerFont(wb));
            headerStyle.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中
            headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
            headerStyle.setLocked(true);
            headerStyle.setWrapText(true);// 自动换行
            return headerStyle;
        }

        // 单元格样式，左右上下居中 边框
        public static  CellStyle  commonStyle(HSSFWorkbook wb){
        CellStyle commonStyle = wb.createCellStyle();
        commonStyle.setFont(contextFont(wb));
        commonStyle.setAlignment(CellStyle.ALIGN_CENTER);// 左右居中
        commonStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 上下居中
        commonStyle.setLocked(true);
        commonStyle.setWrapText(true);// 自动换行
        commonStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
        commonStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
        commonStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
        commonStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
        return commonStyle;
        }

        public static String setFileName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        // 特殊编码转译
        // 处理文件名包含特殊字符出现的乱码问题
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isNotBlank(userAgent)) {
            userAgent = userAgent.toLowerCase();
            if (userAgent.contains("msie") || userAgent.contains("trident") || userAgent.contains("edge")) {
                if (fileName.length() > 150) {// 解决IE 6.0问题
                    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
                } else {
                    fileName = URLEncoder.encode(fileName, "UTF-8");
                }
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            }
        return fileName;
        }

}
