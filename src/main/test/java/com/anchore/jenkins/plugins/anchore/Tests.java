package com.anchore.jenkins.plugins.anchore;

import com.anchore.jenkins.plugins.anchore.model.ImageVulnerability;
import com.anchore.jenkins.plugins.anchore.model.security.AnchoreVulnerabilities;
import com.anchore.jenkins.plugins.zap.TimeoutException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import jnr.ffi.annotations.In;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class Tests {
    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    @Test
    public void testCorrelation() throws IOException {
        InputStream stream = Tests.class.getClassLoader().getResourceAsStream("vulnerabilities.json");
        String contents = IOUtils.toString(stream, StandardCharsets.UTF_8);
        JSONObject json = JSONObject.fromObject(contents);

        List<ImageVulnerability> imageVulnerabilities = VulnerabilityCorrelator.correlateVulnerabilitiesFromJson(json, "");
        AnchoreVulnerabilities vulnerabilities = new AnchoreVulnerabilities(imageVulnerabilities);
        String asString = new ObjectMapper().writeValueAsString(vulnerabilities);
        System.out.println(asString);

        Database.writeVulnerabilities(imageVulnerabilities);
    }

    @Test
    public void testGit() throws IOException {
        Repository repository = new FileRepositoryBuilder().setGitDir(new File("simple-maven-project-with-tests/.git")).build();
        RevCommit commit = new RevWalk(repository).parseCommit(repository.resolve(Constants.HEAD));
        String abbreviated = commit.abbreviate(7).name();
        System.out.println(commit);
    }

    @Test
    public void testOrmLite() throws SQLException {
        ConnectionSource source  = new JdbcConnectionSource(
                "jdbc:mysql://172.20.8.217:3306/cavas_slingshot?serverTimezone=Europe/Berlin",
                "cloudlogs",
                "cloudlogs");
        Dao<ImageVulnerability, Integer> dao = DaoManager.createDao(source, ImageVulnerability.class);

        long count = dao.queryBuilder().countOf();
        System.out.println(count);
    }

    @Test
    public void testQueries() throws SQLException {
        boolean res = Database.hasImageBeenAnalyzedBefore("debian:latest", "");
        List<ImageVulnerability> vulnerabilities = Database.getVulnerabilitiesOfImage("debian:latest", "");

        System.out.println(res);
    }

    @Test
    public void testIfTheHttpClientActuallySucks() // spoiler: It does!
     throws IOException, InterruptedException {
        // This way it works. Therefore: create a new Instance of HttpClient for each request and don't reuse them
        do {
             HttpClient client = HttpClientBuilder.create().build();
             HttpGet request = new HttpGet("http://172.20.8.218:8761/app/9bb46bf/status");
             request.addHeader("Content-Type", "application/json");
             HttpResponse response = client.execute(request);

             Thread.sleep(500);
         } while (true);


        // But it doesn't work that way (which is pretty annoying because the Thread will simply suspend)
        /*HttpClient client = HttpClientBuilder.create().build();
        do {

            HttpGet request = new HttpGet("http://172.20.8.218:8761/app/9bb46bf/status");
            request.addHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);

            Thread.sleep(500);
        } while (true);*/
    }
}
