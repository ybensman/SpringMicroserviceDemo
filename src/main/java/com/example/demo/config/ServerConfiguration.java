package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.net.InetAddress;
import java.util.List;

@ConfigurationProperties("server")
public record ServerConfiguration(InetAddress address,
                           int port,
                           Servlet servlet,
                           Compression compression,
                           DataSize maxHttpHeaderSize
) {

    public record Servlet(String contextPath,
                          Session session) {

        public record Session(boolean persistent) {
        }
    }

    public record Compression(List<String> mimeTypes) {
    }
}