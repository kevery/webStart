package com.kever.web.service;

import com.kever.web.model.Customer;
import com.kever.web.util.PropsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties properties = PropsUtil.loadProps("dbconfig.properties");
        DRIVER = properties.getProperty("jdbc.driver");
        URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.user");
        PASSWORD = properties.getProperty("jdbc.password");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LOGGER.error("can not load jdbc driver", e);
        }
    }

    public List<Customer> getCustomList(String keyword) {
        Connection connection = null;
        List<Customer> customers = new ArrayList<>();
        String sql = "select * from customer";
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setContact(resultSet.getString("contact"));
                customer.setTelephone(resultSet.getString("telephone"));
                customer.setEmail(resultSet.getString("email"));
                customer.setRemark(resultSet.getString("remark"));
                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("can not get jdbc connection", e);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("jdbc connection close fail", e);
                }
            }
        }
        return customers;
    }


    public Customer getCustomer(long id) {
        // TODO: 2017/7/9 0009
        return null;
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        // TODO: 2017/7/9 0009
        return false;
    }


    public boolean updateCustom(long id, Map<String, Object> fieldMap) {
        // TODO: 2017/7/9 0009
        return false;
    }

    public boolean deleteCustomer(long id) {
        // TODO: 2017/7/9 0009
        return false;
    }
}
