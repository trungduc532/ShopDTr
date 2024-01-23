package com.shopdtr.web.backend.user.exporter;

import com.shopdtr.web.backend.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
    }

    /**
     * Create style and header in the xlsx file
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Users");
        XSSFRow row = sheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(15);
        cellStyle.setFont(font);

        //Create header in the file
        createCell(row, 0, "User ID", cellStyle);
        createCell(row, 1, "First Name", cellStyle);
        createCell(row, 2, "Last Name", cellStyle);
        createCell(row, 3, "E-mail", cellStyle);
        createCell(row, 4, "Roles", cellStyle);
        createCell(row, 5, "Enable", cellStyle);
    }

    /**
     * Set value for cell
     *
     * @param row
     * @param columnIndex
     * @param value
     * @param cellStyle
     */
    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
        XSSFCell cell = row.createCell(columnIndex);
        sheet.autoSizeColumn(columnIndex);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(cellStyle);
    }

    /**
     * Export excel file
     *
     * @param listUsers
     * @param response
     * @throws IOException
     */
    public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");

        writeHeaderLine();
        writeDataLines(listUsers);

        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writeDataLines(List<User> listUsers) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(13);
        cellStyle.setFont(font);

        Integer rowIndex = 1;
        for (User user : listUsers) {
            Integer columnIndex = 0;
            XSSFRow row = sheet.createRow(rowIndex++);

            createCell(row, columnIndex++, user.getId(), cellStyle);
            createCell(row, columnIndex++, user.getFirstName(), cellStyle);
            createCell(row, columnIndex++, user.getLastName(), cellStyle);
            createCell(row, columnIndex++, user.getEmail(), cellStyle);
            createCell(row, columnIndex++, user.getRoles().toString(), cellStyle);
            createCell(row, columnIndex, user.isEnable(), cellStyle);
        }
    }
}
