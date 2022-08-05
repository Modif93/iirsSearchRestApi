package com.connector.iirsSearch.service;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

@PropertySource("classpath:extern.properties")
@Service
public class FtpPutService {
    @Value("${host}")
    private String host = "127.0.0.1";
    @Value("${port}")
    private int port = 21;
    @Value("${user}")
    private String user = "iirs";
    @Value("${pwd}")
    private String pwd = "1q2w3e4r";

    public void FTPUploader(File file) throws IOException {
        try {
            int reply = 0;
            FTPClient ftpClient = new FTPClient();

            ftpClient.connect(host, port);
            reply = ftpClient.getReplyCode();

            if (FTPReply.isPositiveCompletion(reply) == false) {
                ftpClient.disconnect();
            } else {
                Date date = new Date();
                SimpleDateFormat nowDateHHmmss = new SimpleDateFormat("HHmmss");
                SimpleDateFormat nowDateYmd;

                // Login
                ftpClient.login(user, pwd);
                showServerReply(ftpClient);

                // Change to PassiveMode
                ftpClient.enterLocalPassiveMode();
                showServerReply(ftpClient);

                // FTP Put
                FileInputStream fis = new FileInputStream(file);
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                if (ftpClient.storeFile(file.getName(), fis) == true) {
                    System.out.println("FTP Put Success");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("FTP 업로드 파일 없음");
        } catch (SocketException e) {
            System.out.println("Socket 에러");
        }
    }

    private static void showServerReply(FTPClient _ftpClient) {
        String[] replies = _ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println(aReply);
            }
        }
    }
}