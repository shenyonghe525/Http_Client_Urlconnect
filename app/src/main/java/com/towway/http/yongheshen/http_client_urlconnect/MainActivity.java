package com.towway.http.yongheshen.http_client_urlconnect;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener
{

    private TextView textView;

    private Handler mHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            textView.setText(msg.getData().getString("result"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews()
    {
        textView = (TextView) findViewById(R.id.tv_info);
        Button urlGet, urlPost, clientGet, clientPost;
        urlGet = (Button) findViewById(R.id.btn_urlGet);
        urlPost = (Button) findViewById(R.id.btn_urlPost);
        clientGet = (Button) findViewById(R.id.btn_clientGet);
        clientPost = (Button) findViewById(R.id.btn_clientPost);
        urlGet.setOnClickListener(this);
        urlPost.setOnClickListener(this);
        clientGet.setOnClickListener(this);
        clientPost.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v)
    {

        final List<HttpParameterDto> parameterDtos = new ArrayList<>();
        final List<HttpParameterDto> headers = new ArrayList<>();
        HttpParameterDto parameterDto1 = new HttpParameterDto("appID", "1001");
        HttpParameterDto parameterDto2 = new HttpParameterDto("platform", "Android");
        HttpParameterDto parameterDto3 = new HttpParameterDto("appVersion", "1.0.0");
        HttpParameterDto header = new HttpParameterDto("Charset", "UTF-8");
        parameterDtos.add(parameterDto1);
        parameterDtos.add(parameterDto2);
        parameterDtos.add(parameterDto3);
        headers.add(header);

        new Thread(new Runnable()
        {

            String result = null;

            @Override
            public void run()
            {
                switch (v.getId())
                {
                    case R.id.btn_urlGet:

                        result = HttpURLConnectionRequest.doHttpURLGet("http://192.168.57" +
                                ".1:8080/MyWebAPI/H5UpdateServlet?appID=1000&platform=Android" +
                                "&appVersion=1.0", headers);
                        break;

                    case R.id.btn_urlPost:

                        result = HttpURLConnectionRequest.doHttpURLPost("http://192.168.57" +
                                ".1:8080/MyWebAPI/H5UpdateServlet", parameterDtos, headers);
                        break;

                    case R.id.btn_clientGet:

                        result = HttpClientRequest.doHttpClientGet("http://192.168.57" +
                                ".1:8080/MyWebAPI/H5UpdateServlet?appID=1000&platform=Android" +
                                "&appVersion=1.0", headers);
                        break;

                    case R.id.btn_clientPost:

                        result = HttpURLConnectionRequest.doHttpURLPost("http://192.168.57" +
                                ".1:8080/MyWebAPI/H5UpdateServlet", parameterDtos, headers);
                        break;
                    default:
                        break;
                }

                sendMessage(result);
            }
        }).start();

    }

    private void sendMessage(String result)
    {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString("result", result);
        System.out.println("<=====Http请求结果====>" +
                result);
        message.setData(bundle);
        mHandler.sendMessage(message);
    }
}
