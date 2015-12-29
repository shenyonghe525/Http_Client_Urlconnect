package com.towway.http.yongheshen.http_client_urlconnect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 作者： shenyonghe689 on 15/12/29.
 */
public class HttpURLConnectionRequest
{
    /**
     * httpurlconnecton get请求
     *
     * @param urlPath    请求的地址
     * @param headers    请求头列表
     * @return 返回的值
     */
    public static String doHttpURLGet(String urlPath,List<HttpParameterDto> headers)
    {
        HttpURLConnection conn = null;
        try
        {
            URL url = new URL(urlPath);
            //利用HttpURLConnection对象从网络中获取网页数据
            conn = (HttpURLConnection) url.openConnection();
            //设置请求超时
            conn.setConnectTimeout(6 * 1000);

            if ((headers != null) && headers.size() > 0)
            {
                for (int i = 0; i < headers.size(); i++)
                {
                    conn.setRequestProperty(headers.get(i).getKey(), headers.get(i).getValue());
                }
            }

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String response = "";
                String readLine = null;
                while ((readLine = br.readLine()) != null)
                {
                    //response = br.readLine();
                    response = response + readLine;
                }
                is.close();
                br.close();
                conn.disconnect();
                return response;
            } else
            {
                System.out.println("请求url失败");
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;

        } finally
        {
            conn.disconnect();
        }
    }

    /**
     * httpurlconnecton post请求
     *发送POST请求必须设置允许输出
     *不要使用缓存,容易出现问题.
     * @param urlPath    请求地址
     * @param parameters 请求参数列表
     * @param headers    请求头列表
     * @return 返回值
     */
    public static String doHttpURLPost(String urlPath, List<HttpParameterDto> parameters,
                                       List<HttpParameterDto> headers)
    {
        HttpURLConnection conn = null;
        try
        {

            URL url = new URL(urlPath);
            //利用HttpURLConnection对象从网络中获取网页数据
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(false);
            //设置请求超时
            conn.setConnectTimeout(6 * 1000);

            if ((headers != null) && headers.size() > 0)
            {
                for (int i = 0; i < headers.size(); i++)
                {
                    conn.setRequestProperty(headers.get(i).getKey(), headers.get(i).getValue());
                }
            }
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            String Params = "";
            if ((parameters != null) && parameters.size() > 0)
            {
                for (int i = 0; i < parameters.size(); i++)
                {
                    if (i == 0)
                    {
                        Params = parameters.get(i).getKey() + "=" + parameters.get(i)
                                .getValue();
                    } else
                    {
                        Params = Params + "&" + parameters.get(i).getKey() + "=" + parameters
                                .get(i).getValue();
                    }
                }

            }
            out.writeBytes(Params);
            out.flush();
            out.close();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String response = "";
                String readLine = null;
                while ((readLine = br.readLine()) != null)
                {
                    //response = br.readLine();
                    response = response + readLine;
                }
                is.close();
                br.close();
                conn.disconnect();
                return response;
            } else
            {
                System.out.println("请求url失败");
                return null;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;

        } finally
        {
            conn.disconnect();
        }
    }
}
