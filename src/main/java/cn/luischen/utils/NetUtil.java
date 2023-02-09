package cn.luischen.utils;

import java.net.*;
import java.util.Enumeration;

public class NetUtil {
    public static String getIp(){
        String ip = null;
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static String getLocalIPv6Address() {
        InetAddress inetAddress = null;
        Enumeration<NetworkInterface> networkInterfaces = null;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAds = networkInterfaces.nextElement().getInetAddresses();
            while (inetAds.hasMoreElements()) {
                inetAddress = inetAds.nextElement();
                //Check if it's ipv6 address and reserved address
                if (inetAddress instanceof Inet6Address) {
                    System.out.println(inetAddress);
                }
            }
        }
        return inetAddress.getHostAddress();
    }

    public static void main(String[] args) {
        getIp();
    }
}
