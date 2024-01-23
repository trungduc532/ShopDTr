package com.shopdtr.web.backend.user.exporter;

import com.shopdtr.web.backend.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCsvExporter extends AbstractExporter{
    public void export(List<User> listsUser, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "text/csv", ".csv");
        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"ID", "First Name", "Last Name", "E-mail", "Roles", "Enable"};
        String[] fieldMapping = {"id", "firstName", "lastName", "email", "roles", "enable"};
        csvBeanWriter.writeHeader(csvHeader);

        for (User user : listsUser) {
            csvBeanWriter.write(user, fieldMapping);
        }
        csvBeanWriter.close();
    }
}
