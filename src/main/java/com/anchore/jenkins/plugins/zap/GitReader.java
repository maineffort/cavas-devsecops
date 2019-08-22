package com.anchore.jenkins.plugins.zap;

import hudson.FilePath;
import hudson.remoting.VirtualChannel;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.jenkinsci.remoting.RoleChecker;

import java.io.File;
import java.io.IOException;

public class GitReader {

    public static String getCurrentCommitId(FilePath workspace) throws IOException, InterruptedException {
        return workspace.act(new FilePath.FileCallable<String>() {
            @Override
            public void checkRoles(RoleChecker roleChecker) throws SecurityException { }

            @Override
            public String invoke(File file, VirtualChannel virtualChannel) throws IOException {
                Repository repository = new FileRepositoryBuilder().setGitDir(new File(".git")).build();
                RevCommit commit = new RevWalk(repository).parseCommit(repository.resolve(Constants.HEAD));
                return commit.abbreviate(7).name();
            }
        });
    }
}
