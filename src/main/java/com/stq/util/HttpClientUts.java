package com.stq.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;

/**
 * HTTP工具类
 */
public class HttpClientUts {
    //发送一个GET请求
    public static String get(String path) throws Exception{
        HttpURLConnection httpConn=null;
        BufferedReader in=null;
        try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            //读取响应
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
                throw new Exception("请求出现了问题!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            in.close();
            httpConn.disconnect();
        }
        return null;
    }
    //发送一个POST请求
    public static String post(String path,String params) throws Exception{
        HttpURLConnection httpConn=null;
        BufferedReader in=null;
        PrintWriter out=null;
        try {
            URL url=new URL(path);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestProperty("Content-Type", "application/Json; charset=UTF-8");
            httpConn.setConnectTimeout(30000); //30秒连接超时
            httpConn.setReadTimeout(30000);    //30秒读取超时
            //发送post请求参数
            out=new PrintWriter(httpConn.getOutputStream());
            out.println(params);
            out.flush();
            //读取响应
            if(httpConn.getResponseCode()==HttpURLConnection.HTTP_OK){
                StringBuffer content=new StringBuffer();
                String tempStr="";
                in=new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
                while((tempStr=in.readLine())!=null){
                    content.append(tempStr);
                }
                return content.toString();
            }else{
                throw new Exception("请求出现了问题!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            in.close();
            out.close();
            httpConn.disconnect();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
    	JSONObject cred   = new JSONObject();
    	cred.put("keyword","西游记");
    	cred.put("startDate","2017-10-01");
    	cred.put("endDate","2017-10-02");
        String resMessage=HttpClientUts.post("http://127.0.0.1/stq/api/v1/words/addIvstWordsTask", cred.toString());
        System.out.println(resMessage);
        String resMessageGet=HttpClientUts.get("http://127.0.0.1/stq/api/v1/words/findIvstTaskStatusByPrimaryId?primaryId=10");
        System.out.println(resMessageGet);
    }
}
