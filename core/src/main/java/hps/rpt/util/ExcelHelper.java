package hps.rpt.util;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 将数据源封装为excel
 * 
 * @author dezhi.shen@hand-china.com
 * @date 2016/08/25
 */
public class ExcelHelper {

	public static final int MAX_ROWS = 65536;

	/**
	 * @param sheetName
	 * @param sheetSize
	 * @param list
	 *            数据源 key : value :key对应的值
	 * @param colname
	 *            key:数据源取值的key value :列名
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream exportExcle(String sheetName, int sheetSize, List<Map<String, Object>> list,
			Map<String, String> colname) throws Exception {
		HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
		HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
		workbook.setSheetName(0, sheetName);// 设置工作表的名称.
		HSSFRow row;
		HSSFCell cell;// 产生单元格
		row = sheet.createRow(0);
		Iterator<?> it = colname.entrySet().iterator();
		int index = 0;
		while (it.hasNext()) {
			cell = row.createCell(index);
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry) it.next();
			Object value = entry.getValue();
			cell.setCellValue(value.toString());
			index++;
		}
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> datasource = list.get(i);
			row = sheet.createRow(i + 1);
			it = colname.entrySet().iterator();
			index = 0;
			while (it.hasNext()) {
				cell = row.createCell(index);
				@SuppressWarnings("rawtypes")
				Map.Entry entry = (Map.Entry) it.next();
				Object key = entry.getKey();
				cell.setCellValue(datasource.get(key) == null ? null : datasource.get(key).toString());
				index++;
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		workbook.close();
		return out;
	}
}
