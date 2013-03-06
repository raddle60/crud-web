package com.raddle.crud.tomcat;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Embedded;

public class CrudWebTomcat {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Embedded tomcat = new Embedded();
        String baseDir = new File(".").getAbsolutePath();
        // tomcat home自己新建一个，里面就只建一个webapps空目录
        // 数据源配在context.xml里，可以从普通tomcat里拷贝过来
        tomcat.setCatalinaHome(baseDir + "/src/test/resources/tomcat-home");
        Engine engine = tomcat.createEngine();
        engine.setName("Catalina");
        // webapps建个空目录就可以
        Host host = tomcat.createHost("localhost", tomcat.getCatalinaHome() + "/webapps");
        host.setAutoDeploy(false);
        engine.addChild(host);
        engine.setDefaultHost(host.getName());
        // 要在tomcat里启动的应用
        Context ctxtRoot = tomcat.createContext("/crud-web", baseDir + "/src/main/webapp");
        host.addChild(ctxtRoot);
        tomcat.addEngine(engine);
        // http
        Connector httpConnector = tomcat.createConnector((String) null, 8082, false);
        tomcat.addConnector(httpConnector);
        tomcat.start();
        while (true) {
            Thread.sleep(1000 * 3600);
        }
    }

}
