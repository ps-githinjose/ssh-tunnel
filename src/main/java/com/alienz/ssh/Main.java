package com.alienz.ssh;

import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.apache.sshd.common.util.security.bouncycastle.BouncyCastleGeneratorHostKeyProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.forward.AcceptAllForwardingFilter;
import org.apache.sshd.server.forward.ForwardingFilter;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.shell.ProcessShellFactory;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        System.out.println("hello ssh");
        SshServer sshd = SshServer.setUpDefaultServer();
        sshd.setPort(1235);
        sshd.setShellFactory(new ProcessShellFactory("cmd.exe"));
        sshd.setKeyPairProvider(new BouncyCastleGeneratorHostKeyProvider(Paths.get("ssh.pem")));
        sshd.setPasswordAuthenticator(((username, password, session) -> {
            System.out.println(session.getClientAddress().toString());
            return true;
        }));
        // allow forwarding
        sshd.setForwardingFilter(AcceptAllForwardingFilter.INSTANCE);
        try {
            sshd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(sshd.isStarted()){

        }
    }
}
