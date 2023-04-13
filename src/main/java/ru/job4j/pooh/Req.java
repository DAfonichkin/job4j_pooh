package ru.job4j.pooh;

import java.util.Scanner;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        Scanner sc = new Scanner(content).useDelimiter(System.lineSeparator());
        String header = sc.next();
        String[] paramsArray = header.split("/");
        String httpReqType = paramsArray[0].trim();
        String poohMode = paramsArray[1];
        String sourceName = paramsArray[2].replaceAll(" HTTP", "");
        String param = "";
        if (paramsArray.length == 5) {
            param = paramsArray[3].replaceAll(" HTTP", "");
        } else {
            while (sc.hasNext()) {
                param = sc.next();
            }
        }
        return new Req(httpReqType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}