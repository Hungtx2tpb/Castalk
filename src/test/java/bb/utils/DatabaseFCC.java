package bb.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bb.utils.Utilities;

public class DatabaseFCC {

    public static Connection createConnect() throws SQLException {
        String url = "", user = "", pass = "";
        String ENV = Utilities.getConfigValue("serenity.api.environment");
        LoggerUtil.logInfo("ENV: " + ENV);
        switch (ENV) {
            case "UAT":
                url = Utilities.getConfigValue("environments.default.fccUrl");
                user = Utilities.getConfigValue("environments.default.fccUser");
                pass = Utilities.getConfigValue("environments.default.fccPass");
                break;
            case "SIT":
                url = Utilities.getConfigValue("environments.android_SIT.fccUrl");
                user = Utilities.getConfigValue("environments.android_SIT.fccUser");
                pass = Utilities.getConfigValue("environments.android_SIT.fccPass");
                break;
        }
        return DriverManager.getConnection(url, user, pass);
    }

    public static Connection createConnect(String url, String user, String pass) throws SQLException {
        Connection connect = DriverManager.getConnection(url, user, pass);
        connect.setAutoCommit(false);
        return connect;
    }

    public static ResultSet getAccountInfo(String accountNumber) {
        String sql = "";
        try {
            Thread.sleep(1500);
            Connection connect = createConnect();
            Statement st = connect.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            return resultSet;
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException("Error while fetching account info for accountNumber: " + accountNumber, e);
        }
    }

    public static String getCurrentBalance(String accountNumber) {
        try {
            ResultSet rs = getAccountInfo(accountNumber);
            if (rs.next()) {
                String availableBalance = rs.getString("");
                String overdraftLimit = rs.getString("");
                if (overdraftLimit == null) {
                    return availableBalance;
                } else {
                    return String.valueOf(Long.parseLong(availableBalance) + Long.parseLong(overdraftLimit));
                }
            } else {
                throw new RuntimeException("Account number not found: " + accountNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching Current Balance for accountNumber: " + accountNumber, e);
        }
    }

    public static HashMap<String, String> getVATAndSRVOfStatementTransaction(String accountNumber, String refCode) {
        HashMap<String, String> vatAndSrv = new HashMap<>();
        String sql = "";
        LoggerUtil.logInfo("sqlFCC: " + sql);
        try {
            Thread.sleep(1500);
            Connection connect = createConnect("", "", "");
            Statement st = connect.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String lcyAmount = resultSet.getString("");
                String trnCode = resultSet.getString("");
                vatAndSrv.put(trnCode, lcyAmount);
            }
            resultSet.close();
            st.close();
            connect.close();
        } catch (SQLException | InterruptedException e) {
            throw new RuntimeException("Error while fetching brand code for accountNumber" + e);
        }
        return vatAndSrv;
    }

    public static List<String> getBrandCode(String refCode) {
        List<String> listBrandCode = new ArrayList<>();
        String sql = "";
        try {
            Connection connect = createConnect("", "", "");
            Statement st = connect.createStatement();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String brandCode = resultSet.getString("");
                listBrandCode.add(brandCode);
            }
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching brand code for accountNumber" + e);
        }
        return listBrandCode;
    }
}
