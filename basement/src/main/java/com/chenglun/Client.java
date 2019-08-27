package com.chenglun;

import java.io.*;
import java.net.Socket;

public class Client implements  AutoCloseable{
    private String remoteIp;
    private int port;
    private Socket s;
    private BufferedReader br;
    private BufferedWriter wr;
    public Client(final String remoteIp, final int port) throws IOException {
        this.remoteIp = remoteIp;
        this.port = port;
        s = new Socket(remoteIp, port);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        wr = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    public Client writeLine(final String str) throws IOException {
        wr.write(str + "\n");
        wr.flush();
        return this;
    }

    public String readLine() throws IOException {
        return br.readLine();
    }

    @Override
    public void close() throws Exception {
        if(s != null && wr != null){
            wr.flush();
            s.close();
        }
    }
}
