package com.towway.http.yongheshen.http_client_urlconnect;

/**
 * 作者： shenyonghe689 on 15/12/29.
 */
public class HttpParameterDto
{
    private String key;

    private String value;

    public HttpParameterDto(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }


}
