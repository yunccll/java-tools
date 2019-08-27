package com.chenglun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements  AutoCloseable, Runnable{

    final static Logger logger = LoggerFactory.getLogger(Server.class);

    private ServerSocket ss;
    private int port;
    public Server(final int port){
        this.port = port;
    }

    @Override
    public void close() throws Exception {
        System.out.println("close...port:(" + port + ")...");
        if(ss != null){
            ss.close();
        }
    }

    class Session implements  AutoCloseable {
        private Socket s;
        public Socket getSocket(){return this.s;}
        private BufferedReader br;
        private BufferedWriter wr;

        public Session(Socket s) throws IOException {
            if(s == null) throw new NullPointerException("socket is null");
            this.s = s;
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            wr = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        }

        public String readLine() throws IOException {
            return br.readLine();
        }
        public Session writeLine(String str) throws IOException {
            wr.write(str+"\n");
            wr.flush();
            return this;
        }

        @Override
        public void close() throws Exception {
            if(wr != null) {
                wr.flush();
            }
            if(s != null && !s.isClosed()){
                this.s.close();
            }
        }

        @Override
        public String toString() {
            return s.getLocalAddress().toString() + ":" + s.getLocalPort();
        }
    }


    @Override
    public void run() {
        System.out.println("start to runn......");
        try {
            ss = new ServerSocket(port, 1);

            Session ses = null;
            try {
                ses = new Session(ss.accept());
                System.out.println("session connected:" + ses.toString());
                String str = ses.readLine();
                System.out.println("ReadMessage:" + str);
                ses.writeLine(str + " ===append by server ");
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
            finally {
                try {
                    if (ses != null)
                        ses.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                this.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
