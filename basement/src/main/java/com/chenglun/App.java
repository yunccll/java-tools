package com.chenglun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 */
public class App 
{
    final static Logger logger = LoggerFactory.getLogger(App.class);

    public void number(){
        {
            Integer i = 1245; 
            Integer j = 1245;
            System.out.println(i == j); //false
            System.out.println(i.equals(j)); //true
        }

        {
            int i = 1245;
            int j = 1245;
            System.out.println(i == j); //true
        }

        {
            //String  --> int ==> String ->integer -> int
            String istr = "12345";
            int i = Integer.valueOf(istr).intValue();
            int j = Integer.parseInt(istr); //better
        }
    }
    public void charF(){
        // char --> int  auto-convert
        {
            System.out.println('a' + 0);
            System.out.println('程' + 0);
        }

        //int -> char  force-convert
        {
            System.out.println((char)97);
            System.out.println((char)31243);
        }

        System.out.println(Character.BYTES);
    }

    public void stringBuilder(){
        StringBuilder sb = new StringBuilder();
        sb.append("hello world\n").append("world");
        System.out.println(sb.toString());
    }
    public void date(){
        java.util.Date dt = new java.util.Date();
        System.out.printf("date:%1$tF %1$tT, %1$tr\n", dt);
    }
    public void logger(){
        logger.info("hello world");
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        org.apache.log4j.BasicConfigurator.configure();

        App app = new App();
        app.number();
        app.charF();
        app.stringBuilder();
        app.date();
        app.logger();
    }
}
