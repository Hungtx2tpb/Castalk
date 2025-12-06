package bb.utils;

import bb.utils.LoggerUtil;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.di.SerenityInfrastructure;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DataUtils {

    public static final String PATH_RESOURCE;
    public static final String PATH_RESOURCE_DATA;
    public static final EnvironmentVariables ENV;

    static {
        PATH_RESOURCE = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources";
        PATH_RESOURCE_DATA = PATH_RESOURCE + File.separator + "data";
        ENV = SystemEnvironmentVariables.createEnvironmentVariables();
    }

    public DataUtils() {
    }

    public static String getValueConf(String config) {
        EnvironmentVariables environmentVariables = SerenityInfrastructure.getEnvironmentVariables();
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(config);
    }

    public static String getPropertiesValue(String key) {
        Properties props = new Properties();
        String lang = getValueConf("lang");
        InputStream is = FileUtils.class.getClassLoader().getResourceAsStream("language" + File.separator + lang + ".properties");

        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            props.load(isr);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return props.getProperty(key);
    }

    public static String getTestDataByCol(String colName) {
        String id = Serenity.sessionVariableCalled("id");
        Map<String, String> rowData = Serenity.sessionVariableCalled(id);
        return rowData.get(colName);
    }

    public static Map<String, String> getTestDataRow() {
        String id = Serenity.sessionVariableCalled("id");
        return Serenity.sessionVariableCalled(id);
    }

    public static Map<String, String> getTestDataRowWithId(String id) {
        return Serenity.sessionVariableCalled(id);
    }

    public static List<Map<String, String>> getTestDataListWithId(String id) {
        return Serenity.sessionVariableCalled(id);
    }

    public static Document getDocument(String document) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(document.getBytes(StandardCharsets.UTF_8));
        return builder.parse(stream);
    }

    public static List<String> evaluateXPath(Document document, String xpathExpression) throws Exception {
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        List<String> values = new ArrayList<>();

        try {
            XPathExpression expr = xpath.compile(xpathExpression);
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodes.getLength(); ++i) {
                values.add(nodes.item(i).getNodeValue());
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return values;
    }

    public static String getTextOfXml(String xml, String xpath) {
        String text = "";
        Document doc = convertStringToXMLDocument(xml);
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xp = xpathFactory.newXPath();

        try {
            XPathExpression expr = xp.compile(xpath);
            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            text = nodes.item(0).getNodeValue();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return text;
    }

    public static Document convertStringToXMLDocument(String xmlString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, String> getCredentialsByRole(String filePath, String role) {
        HashMap<String, String> credentials = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            LoggerUtil.logError("File không tồn tại tại đường dẫn: " + filePath);
            return credentials;
        }

        boolean roleFound = false;

        try (FileInputStream fileInputStream = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell roleCell = row.getCell(0);
                if (roleCell != null && roleCell.getCellType() == CellType.STRING &&
                        roleCell.getStringCellValue().trim().equals(role.trim())) {

                    Cell usernameCell = row.getCell(1);
                    Cell passwordCell = row.getCell(2);

                    String username = usernameCell != null ? usernameCell.getStringCellValue().trim() : "";
                    String password = passwordCell != null ? passwordCell.getStringCellValue().trim() : "";

                    credentials.put("username", username);
                    credentials.put("password", password);
                    roleFound = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!roleFound) {
            throw new RuntimeException("Role '" + role + "' không tồn tại trong file: " + filePath);
        }

        return credentials;
    }

    public static String getDataFile(String type, String excelFilePath) {
        String message = null;

        try (FileInputStream fis = new FileInputStream(new File(excelFilePath));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell typeCell = row.getCell(0);
                Cell messageCell = row.getCell(1);

                if (typeCell != null && messageCell != null) {
                    String cellType = typeCell.getStringCellValue().trim();
                    if (cellType.equals(type)) {
                        message = messageCell.getStringCellValue();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            LoggerUtil.logError("Error reading the Excel file: " + e.getMessage());
        }

        return message;
    }
}
