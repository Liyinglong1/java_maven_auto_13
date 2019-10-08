package com.lemonban.base.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.lemonban.base.pojo.CellData;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {

	/**
	 * 读取excel文档
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param sheetIndex
	 *            索引,从0开始
	 * @param clazz
	 *            字节码对象
	 * @return
	 */
	public static ArrayList<Object> readExcel(String excelPath, int sheetIndex, Class clazz) {
		InputStream inputStream = null;
		try {
			// 来一个容器，把所有数据行的数据都保存起来
			ArrayList<Object> dataList = new ArrayList<Object>();
			// 讲文件加载成字节输入流
			inputStream = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得一个工作簿对象
			Workbook workbook = null;
			workbook = WorkbookFactory.create(inputStream);
			// 获取指定的表单
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			/*** --------------------获取第一行数据（获取所有的属性） */
			// 获得第一行数据
			Row firstRow = sheet.getRow(0);
			// 确定第一行有多少列(最大列的标号)
			int lastCellNum = firstRow.getLastCellNum();
			// 创建一个容器，按顺序保存所有的属性名称
			String[] fieldArray = new String[lastCellNum];
			// 循环遍历获得所有的列
			for (int i = 0; i < lastCellNum; i++) {
				// 获得当前索引对应的列
				Cell cell = firstRow.getCell(i, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// 设置cell的类型，全部当成字符串处理
				cell.setCellType(CellType.STRING);
				// 获得该cell的值
				String fieldName = cell.getStringCellValue();
				// 把当前对应的属性名称添加到属性容器中去
				fieldArray[i] = fieldName;
			}
			// 获取最大的行数
			int lastRowNum = sheet.getLastRowNum();
			// 循环数据行每一行
			for (int i = 1; i <= lastRowNum; i++) {
				// 创建一个用户对象来保存数据行的信息
				Object object = clazz.newInstance();
				
				//-------------------- 通过反射设值行号-----------------------
				//当前行的行号
				int rowNo = i+1;
				String setRowNoMethodName = "setRowNo";
				//获得方法对象
				Method setRowNoMethod = clazz.getMethod(setRowNoMethodName, int.class);
				//反射设值行号
				setRowNoMethod.invoke(object, rowNo);
				//-------------------- 通过反射设值行号-----------------------
				
				// 获得当前行
				Row currentRow = sheet.getRow(i);
				// 遍历当前数据行的每一列
				for (int j = 0; j < lastCellNum; j++) {
					// 获得当前列
					Cell currentCell = currentRow.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置cell的类型
					currentCell.setCellType(CellType.STRING);
					// 得到当前列的值
					String cellValue = currentCell.getStringCellValue();
					
					//先进行参数替换
					cellValue = ParamUtils.getReplacedStr(cellValue);
					
					// 获得当前数据列对应的属性名
					String fieldName = fieldArray[j];
					// 获取属性名对应的setter方法：
					String methodName = "set" + fieldName;
					// 获得方法对应的反射对象
					Method setMethod = clazz.getMethod(methodName, String.class);
					// 反射调用
					setMethod.invoke(object, cellValue);
				}

				// 把这个用户保存到容器中间去
				dataList.add(object);
			}
			// 返回所有的数据
			return dataList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	//excel的源路径，目标路径、表单的索引、行的索引、列的索引
	public static void main(String[] args) throws Exception{
	/*	InputStream inputStream = ExcelUtils.class.getResourceAsStream("/case17/test_case_01.xlsx");;
		Workbook workbook =  WorkbookFactory.create(inputStream);
		//在第1个表单的第一行的第一列写入：Java13期的同学们大家好
		Sheet sheet = workbook.getSheetAt(0);
		Row firstRow = sheet.getRow(0);
		Cell cell = firstRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellType(CellType.STRING);
		//修改cell的值
		cell.setCellValue("Java13期的同学们大家好");
		
		OutputStream outputStream = new FileOutputStream(new File("D:\\test_case_01.xlsx"));
		workbook.write(outputStream);
		
		outputStream.close();
		workbook.close();
		inputStream.close();*/
		
	}

	public static void writeExcel(String sourceExcelPath, String targetExcelPath, int sheetIndex, CellData cellData) throws Exception{
		InputStream inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);;
		Workbook workbook =  WorkbookFactory.create(inputStream);
		//在第1个表单的第一行的第一列写入：Java13期的同学们大家好
		Sheet sheet = workbook.getSheetAt(sheetIndex);
		Row firstRow = sheet.getRow(cellData.getRowNo()-1);
		Cell cell = firstRow.getCell(cellData.getColumnNo()-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(cellData.getContent());
		
		OutputStream outputStream = new FileOutputStream(new File(targetExcelPath));
		workbook.write(outputStream);
		
		outputStream.close();
		workbook.close();
		inputStream.close();
	}

	/**
	 * 批量回写
	 * @param sourceExcelPath excel的源路径
	 * @param targetExcelPath 要回写的目标路径
	 * @param sheetIndex sheet索引
	 */
	public static void batchWrite(String sourceExcelPath, String targetExcelPath, int sheetIndex) {
		InputStream inputStream = null;
		Workbook workbook =  null;
		OutputStream outputStream = null;
		try {
			inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);;
			 workbook =  WorkbookFactory.create(inputStream);
			//在第1个表单的第一行的第一列写入：Java13期的同学们大家好
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			//获取全局数据池
			List<CellData> cellDataList = ApiUtils.getCellDataList();
			for (CellData cellData : cellDataList) {
				Row row = sheet.getRow(cellData.getRowNo()-1);
				Cell cell = row.getCell(cellData.getColumnNo()-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(cellData.getContent());
			}
			
			outputStream = new FileOutputStream(new File(targetExcelPath));
			workbook.write(outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook !=null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void batchWrite2(String sourceExcelPath, String targetExcelPath, int sheetIndex) {
		InputStream inputStream = null;
		Workbook workbook =  null;
		OutputStream outputStream = null;
		try {
			inputStream = ExcelUtils.class.getResourceAsStream(sourceExcelPath);;
			 workbook =  WorkbookFactory.create(inputStream);
			//在第1个表单的第一行的第一列写入：Java13期的同学们大家好
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			//获取全局数据池
			List<CellData> cellDataList = ApiUtils.getSqlCellDataList();
			for (CellData cellData : cellDataList) {
				Row row = sheet.getRow(cellData.getRowNo()-1);
				Cell cell = row.getCell(cellData.getColumnNo()-1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cell.setCellType(CellType.STRING);
				cell.setCellValue(cellData.getContent());
			}
			
			outputStream = new FileOutputStream(new File(targetExcelPath));
			workbook.write(outputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workbook !=null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
