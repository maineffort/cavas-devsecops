package com.anchore.jenkins.plugins.anchore;

import com.anchore.jenkins.plugins.anchore.model.ImageVulnerability;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import org.jfree.chart.LegendItemSource;

import java.sql.SQLException;
import java.util.List;

public class Database {
    private static Dao<ImageVulnerability, Integer> dao;
    private static String url = "jdbc:mysql://172.20.8.217:3306/cavas_alerts?serverTimezone=Europe/Berlin";
    private static String username = "cloudlogs";
    private static String password = "cloudlogs";

    private static Dao<ImageVulnerability, Integer> getDao() throws SQLException {
        if (dao == null) {
            ConnectionSource connectionSource = new JdbcConnectionSource(url, username, password);
            dao = DaoManager.createDao(connectionSource, ImageVulnerability.class);
        }
        return dao;
    }

    public static boolean hasImageBeenAnalyzedBefore(String name, String digest) {
        try {
            QueryBuilder<ImageVulnerability, Integer> queryBuilder = getDao().queryBuilder();

            Where where = queryBuilder.where();
            where.and(where.eq(ImageVulnerability.getImageNameColumn(), name), where.eq(ImageVulnerability.getImageDigestColumn(), digest));

            return queryBuilder.countOf() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ImageVulnerability> getVulnerabilitiesOfImage(String name, String digest) throws SQLException {
        QueryBuilder<ImageVulnerability, Integer> queryBuilder = getDao().queryBuilder();

        Where where = queryBuilder.where();
        where.and(where.eq(ImageVulnerability.getImageNameColumn(), name), where.eq(ImageVulnerability.getImageDigestColumn(), digest));

        return queryBuilder.query();
    }

    public static void writeVulnerabilities(List<ImageVulnerability> vulnerabilities) {
        try {
            getDao().create(vulnerabilities);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
