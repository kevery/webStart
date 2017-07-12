package com.kever.web.help;

import com.kever.web.util.PropsUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DBHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBHelper.class);

    private static final QueryRunner QUERY_RUNNER;


    private static final ThreadLocal<Connection> CONNECTION_THREAD_LOCAL = new ThreadLocal<>();

    private static final BasicDataSource DATA_SOURCE;


    static {
        QUERY_RUNNER = new QueryRunner();

        Properties properties = PropsUtil.loadProps("dbconfig.properties");
        String driver = properties.getProperty("jdbc.driver");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.user");
        String password = properties.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }


    public static Connection getConnection() {
        Connection connection = CONNECTION_THREAD_LOCAL.get();
        if (connection == null) {
            try {
                connection = DATA_SOURCE.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error("can not get jdbc connection", e);
            } finally {
                CONNECTION_THREAD_LOCAL.set(connection);
            }
        }

        return connection;
    }

//    public static void closeConnection() {
//        Connection connection = CONNECTION_THREAD_LOCAL.get();
//        if (connection != null) {
//            try {
//                connection.close();
//            } catch (SQLException e) {
//                LOGGER.error("can not close jdbc connection", e);
//            } finally {
//                CONNECTION_THREAD_LOCAL.remove();
//            }
//        }
//    }


    /**
     * 查询实体List
     *
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList;
        try {
            Connection connection = getConnection();
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("query entity list fail", e);
            throw new RuntimeException(e);
        }
        return entityList;
    }

    /**
     * 查询个体
     * @param entityClass
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        T entity;

        try {
            Connection connection = getConnection();
            entity = QUERY_RUNNER.query(connection, sql, new BeanHandler<>(entityClass), params);
        } catch (Exception e) {
            LOGGER.error("query entity list fail", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 查询语句
     *
     * @param sql
     * @param params
     * @return
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        List<Map<String, Object>> result;

        Connection connection = getConnection();
        try {
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("execute query fail" + e);
            throw new RuntimeException();
        }
        return result;
    }

    /**
     * 更新语句
     *
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate(String sql, Object... params) {
        int rows = 0;

        try {
            Connection connection = getConnection();
            rows = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("execute update fail" + e);
        }
        return rows;
    }

    /**
     * 插入实体
     *
     * @param entityClass
     * @param field
     * @param <T>
     * @return
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> field) {

        StringBuilder sql = new StringBuilder("INSERT INTO " + getTableName(entityClass));
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : field.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?,");
        }

        columns.replace(columns.lastIndexOf(","), columns.length(), ")");
        values.replace(values.lastIndexOf(","), values.length(), ")");

        sql = sql.append(columns).append(" VALUE ").append(values);

        Object[] params = field.values().toArray();
        return executeUpdate(sql.toString(), params) == 1;
    }


    /**
     * 更新实体
     *
     * @param entityClass
     * @param field
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean updateEntity(Class<T> entityClass, Map<String, Object> field, long id) {

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";

        StringBuilder columns = new StringBuilder();
        for (String fieldName : field.keySet()) {
            columns.append(fieldName).append("=?, ");
        }

        sql += columns.substring(0, columns.lastIndexOf(", ")) + " WHERE id=?";


        List<Object> paramsList = new ArrayList<>();
        paramsList.addAll(field.values());
        paramsList.add(id);
        Object[] paramss = paramsList.toArray();


        return executeUpdate(sql, paramss) == 1;
    }


    /**
     * 删除实体
     *
     * @param entityClass
     * @param id
     * @param <T>
     * @return
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }

    public static void executeSqlFile(String filePath) {
        InputStream sqlStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        BufferedReader bf = new BufferedReader(new InputStreamReader(sqlStream));

        String sql;
        try {
            while ((sql = bf.readLine()) != null) {
                DBHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("execute sql file fail" + e);
            throw new RuntimeException();
        }
    }

    /**
     * 获取表名(小写)
     * 例如 Customer.class  customer
     *
     * @param entityClass
     * @return
     */
    private static String getTableName(Class<?> entityClass) {
        return entityClass.getSimpleName().toLowerCase();
    }
}
