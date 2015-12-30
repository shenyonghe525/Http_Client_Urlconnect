package com.towway.http.yongheshen.http_client_urlconnect;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 作者： shenyonghe689 on 15/12/30.
 */
public class IsInternetConnect
{
    /**
     * 判断是否联网（普通情况下可以）
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        if (cm != null)
        {
            return true;
        }
        return false;
    }

    /**
     * 但是，有时候我们连接上一个没有外网连接的WiFi或者需要输入账号和密码才能链接外网的网络，就会出现虽然网络可用，
     * 但是外网却不可以访问。针对这种情况，一般的解决办法就是ping一个外网，如果能ping通就说明可以真正上网
     * @return    是否联网
     */
    public static  boolean isConnectByPing()
    {

        String result = null;
        try
        {
            String ip = "www.baidu.com";// ping 的地址，可以换成任何一种可靠的外网
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping网址3次
            // 读取ping的内容，可以不加
            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer stringBuffer = new StringBuffer();
            String content = "";
            while ((content = in.readLine()) != null)
            {
                stringBuffer.append(content);
            }
            Log.d("------ping-----", "result content : " + stringBuffer.toString());
            // ping的状态
            int status = p.waitFor();
            if (status == 0)
            {
                result = "success";
                return true;
            } else
            {
                result = "failed";
            }
        } catch (IOException e)
        {
            result = "IOException";
        } catch (InterruptedException e)
        {
            result = "InterruptedException";
        } finally
        {
            Log.d("----result---", "result = " + result);
        }
        return false;
    }

}
