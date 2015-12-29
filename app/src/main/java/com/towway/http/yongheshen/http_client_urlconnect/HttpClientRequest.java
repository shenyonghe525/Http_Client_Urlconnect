package com.towway.http.yongheshen.http_client_urlconnect;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： shenyonghe689 on 15/12/29.
 */
public class HttpClientRequest
{

    public static String doHttpClientGet(String uri,List<HttpParameterDto> list)
    {
        try {
            //得到HttpClient对象
            HttpClient getClient = new DefaultHttpClient();
            //得到HttpGet对象
            HttpGet request = new HttpGet(uri);

            //设置请求头信息
            if ((list!=null) && list.size() > 0)
            {
                for (int i = 0;i<list.size();i++){
                    request.addHeader(list.get(i).getKey(),list.get(i).getValue());
                }
            }
            //客户端使用GET方式执行请教，获得服务器端的回应response
            HttpResponse response = getClient.execute(request);
            //判断请求是否成功
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                System.out.println("请求服务器端成功");
                //获得输入流
                InputStream inStrem = response.getEntity().getContent();
                String tmp = "";
                int result = inStrem.read();
                while (result != -1){
                    tmp = tmp + (char)result;
                    System.out.print((char)result);
                    result = inStrem.read();
                }
                //关闭输入流
                inStrem.close();
                return tmp;
            }else {
                System.out.println("请求服务器端失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String doHttpClientPost(String urlPath, List<HttpParameterDto> parameters,
                                        List<HttpParameterDto> headers)
    {

        BufferedReader in = null;
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(urlPath);

            //添加请求头信息
            if ((headers!=null) && headers.size() > 0)
            {
                for (int i = 0;i<headers.size();i++){
                    request.addHeader(headers.get(i).getKey(),headers.get(i).getValue());
                }
            }

            //使用NameValuePair来保存要传递的Post参数
            List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            //添加要传递的参数
            if ((parameters!=null) && parameters.size() > 0)
            {
                for (int i = 0;i<parameters.size();i++)
                {
                    postParameters.add(new BasicNameValuePair(parameters.get(i).getKey(), parameters.get(i).getValue()));
                }
            }
            //实例化UrlEncodedFormEntity对象
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
                    postParameters);

            //使用HttpPost对象来设置UrlEncodedFormEntity的Entity
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK)
            {
                in = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent()));

                StringBuffer string = new StringBuffer("");
                String lineStr = "";
                while ((lineStr = in.readLine()) != null)
                {
                    string.append(lineStr + "\n");
                }
                in.close();

                String resultStr = string.toString();
                System.out.println(resultStr);
                return resultStr;
            }
            else {
                System.out.println("请求url失败");
                return null;
            }
        } catch(Exception e) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }

}
