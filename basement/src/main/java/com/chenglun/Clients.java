package com.chenglun;

public class Clients
{
    public static Client createDefault()
    {
        return new ClientBuilder().build();
    }
    public static ClientBuilder custom()
    {
        return new ClientBuilder();
    }

}
