package com.jack.kxb.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.jack.kxb.model.KxWinning;

public class ExcelUtil {
	public static Workbook exportExcel(Workbook wb ,String fileName, List<KxWinning> list, List<String> titleList) {
		try {
			FileOutputStream out = new FileOutputStream("/home/wwwroot/ekxb/" + fileName);
			
			Sheet s = wb.createSheet();
			Row r = null;
			Cell c = null;
			r = s.createRow(0);
			for (int i = 0; i < titleList.size(); i++) {
				c = r.createCell(i);
				c.setCellValue(titleList.get(i));
			}

			for (int j = 1; j <= list.size(); j++) {
				r = s.createRow(j);
				KxWinning win = list.get(j - 1);

				c = r.createCell(0);
				c.setCellValue(win.getName());
				c = r.createCell(1);
				c.setCellValue(win.getPhone());
				c = r.createCell(2);
				c.setCellValue(win.getWinLevel());
				c = r.createCell(3);
				c.setCellValue(win.getCreateDt());
			}

			wb.write(out);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return wb;
	}

	public static void main(String[] args) throws FileNotFoundException {
		List<KxWinning> list = new ArrayList<KxWinning>();
		KxWinning win = new KxWinning();
		win.setName("abc");
		win.setPhone("123123");
		win.setWinLevel(1);
		list.add(win);
		
		win = new KxWinning();
		win.setName("efb");
		win.setPhone("132312312");
		win.setWinLevel(1);
		list.add(win);
		List<String> titleList = new ArrayList<String>();
		titleList.add("姓名");
		titleList.add("手机号");
		titleList.add("中奖级别");
		
		Workbook wb = new HSSFWorkbook();
		exportExcel(wb,"test.xls",list,titleList);
	}
}
