package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties("server")
public class ServerConfiguration {
    private InetAddress address;
    private int port;
    private final Servlet servlet = new Servlet();
    private final Compression compression = new Compression();
    private DataSize maxHttpHeaderSize;

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Servlet getServlet() {
        return servlet;
    }

    public Compression getCompression() {
        return compression;
    }

    public DataSize getMaxHttpHeaderSize() {
        return maxHttpHeaderSize;
    }

    public void setMaxHttpHeaderSize(DataSize maxHttpHeaderSize) {
        this.maxHttpHeaderSize = maxHttpHeaderSize;
    }

    @Override
    public String toString() {
        return "ServerConfiguration{" +
                "address=" + address +
                ", port=" + port +
                ", servlet=" + servlet +
                ", max-http-header-size=" + maxHttpHeaderSize +
                ", compression=" + compression +
                '}';
    }

    public static class Servlet {
        String contextPath;

        private final Session session = new Session();

        public Session getSession() {
            return session;
        }

        public String getContextPath() {
            return contextPath;
        }

        public void setContextPath(String contextPath) {
            this.contextPath = contextPath;
        }

        public static class Session {
            boolean persistent;

            public boolean isPersistent() {
                return persistent;
            }

            public void setPersistent(boolean persistent) {
                this.persistent = persistent;
            }
        }

        @Override
        public String toString() {
            return "Servlet{" +
                    "contextPath='" + contextPath + '\'' +
                    ", session persistent=" + session.isPersistent() +
                    '}';
        }
    }

    public static class Compression {
        private List<String> mimeTypes = new ArrayList<>();

        public List<String> getMimeTypes() {
            return mimeTypes;
        }

        public void setMimeTypes(List<String> mimeTypes) {
            this.mimeTypes = mimeTypes;
        }

        @Override
        public String toString() {
            return "Compression{" +
                    "mimeTypes=" + mimeTypes +
                    '}';
        }
    }
}
