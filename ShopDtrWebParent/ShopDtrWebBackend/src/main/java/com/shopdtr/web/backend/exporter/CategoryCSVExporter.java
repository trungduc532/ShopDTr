package com.shopdtr.web.backend.exporter;

import com.shopdtr.web.backend.entity.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCSVExporter extends AbstractExporter{
    public void export(List<Category> listCategory, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "Category Name", "Enable"};
        String[] fieldMapping = {"id", "name", "enable"};
        csvBeanWriter.writeHeader(csvHeader);

        for (Category category : listCategory) {
            csvBeanWriter.write(category, fieldMapping);
        }
        csvBeanWriter.close();
    }
}
