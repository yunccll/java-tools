package com.chenglun.net;

import com.chenglun.net.Client;
import com.chenglun.net.ClientBuilder;

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
