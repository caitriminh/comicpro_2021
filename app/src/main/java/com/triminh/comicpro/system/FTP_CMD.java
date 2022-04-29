package com.triminh.comicpro.system;

import android.util.Log;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FTP_CMD {
    /**
     * Đường dẫn FTP_CMD url không bao gồm địa chỉ IP server,
     * Vì IP đã được khai báo khi khởi tạo FTPClient
     */

    public static final String FTP_SERVER_IP = "triminh.xyz";
    public static final String FTP_USERNAME = "gv1mpaj216";
    public static final String FTP_PASSWORD = "Ue77uPjhH3";
    private static final int TIME_OUT = 2000;

    public static boolean makeDir(String url) {
        boolean success = false;
        Log.w("making dir", url + "");
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return false;
        }
        try {
            // nếu đã tồn tại folder rồi thì không cần tạo mới.
            if (client.changeWorkingDirectory(url)) {
                client.disconnect();
                return true;
            }
            success = client.makeDirectory(url);
            if (!success) {
                Log.w("MK dir Err", "unknown reason. dir: " + url);
            }
            client.disconnect();
        } catch (IOException e) {
            Log.w("MK dir Err", e.getMessage() + "");
            Log.w("dir name", url + "");
        }
        return success;
    }

    public static byte[] ReadBinFile(String url) {
        return ReadBinFile(url, null);
    }

    public static byte[] ReadBinFile(String url, IProgressChange progressChangeCallback) {
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return null;
        }

        try {
            client.setKeepAlive(true);
            client.sendCommand("SIZE", url);
            String reply = client.getReplyString();
            Log.w("file size", reply);
            String[] replyArr = reply.split(" ");
            if (replyArr.length == 2) {
                replyArr[1] = replyArr[1].replaceAll("\\D", "");
                if (replyArr[1].matches("\\d+")) {
                    int dataLen = Integer.valueOf(replyArr[1]);
                    return ReadBinFile(client, url, dataLen, progressChangeCallback);
                }
            }
            Log.e("not Number", "request url is not a file " + url);
        } catch (IOException e) {
            Log.e("read file Err", "IOException " + e.getMessage());
        }
        return null;
    }

    public static byte[] ReadBinFile(String url, int dataLen) {
        return ReadBinFile(url, dataLen, null);
    }

    public static byte[] ReadBinFile(String url, int dataLen, IProgressChange progressChangeCallback) {
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return null;
        }
        return ReadBinFile(client, url, dataLen, progressChangeCallback);
    }

    private static byte[] ReadBinFile(FTPClient client, String url, int dataLen, IProgressChange progressChangeCallback) {
        try {
            client.setFileType(FTPClient.BINARY_FILE_TYPE);// important for fast binary transfer
            Log.w("reading file", "url: " + url);
            InputStream ist = client.retrieveFileStream(url);
            byte[] data = readBinFromStream(ist, dataLen, progressChangeCallback);
            client.disconnect();
            return data;
        } catch (IOException e) {
            Log.e("read file Err", "IOException " + e.getMessage());
        }
        return null;
    }

    public static byte[] readBinFromStream(InputStream inStream, int dataLen, IProgressChange progressChangeCallback) {
        if (inStream == null) {
            Log.e("NULL STREAM", "InputStream NULL");
            return null;
        }
        byte[] data = new byte[dataLen];
        int currentOffset = 0;
        int cRead = -1;
        boolean success = false;
        try {
            while ((cRead = inStream.read(data, currentOffset, dataLen - currentOffset)) != -1) {
                currentOffset += cRead;
                if (progressChangeCallback != null) {
                    float progressvalue = (float) 100.0 * currentOffset / dataLen;
                    progressChangeCallback.progressChanged(progressvalue);
                }
                Log.w("read bytes", cRead + ". offset: " + currentOffset + " / " + dataLen);
                if (currentOffset >= dataLen) {
                    break;
                }
            }
            success = true;
        } catch (IOException e) {
            Log.e("read byte err", "IOException " + e.getMessage());
        } finally {
            try {
                inStream.close();
            } catch (IOException e) {
                Log.e("close stream err", "IOException " + e.getMessage());
            }
        }
        if (success) {
            return data;
        } else {
            return null;
        }
    }

    public static boolean DeleteFile(String url) {
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return false;
        }
        try {
            client.deleteFile(url);
            client.disconnect();
            Log.w("deleted ftp file", url);
            return true;
        } catch (IOException e) {
            Log.e("delete error", url + " " + e.getMessage());
        }
        return false;
    }

    public static FTPFile getFileInfo(String url_folder, String... filenameNoExtension) {
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return null;
        }
        try {
            FTPFile result = null;
            client.setKeepAlive(true);
            for (final String testfilename : filenameNoExtension) {
                FTPFile[] listFile = client.listFiles(url_folder, new FTPFileFilter() {
                    @Override
                    public boolean accept(FTPFile ftpFile) {
                        boolean isSuccess = ftpFile.isFile();
                        if (isSuccess) {
                            String trueFilename = ftpFile.getName();
                            int lPos = trueFilename.lastIndexOf(".");
                            if (lPos > -1) {
                                trueFilename = trueFilename.substring(0, lPos);
                            }
                            isSuccess = trueFilename.equalsIgnoreCase(testfilename);
                        }
                        return isSuccess;
                    }
                });
                if (listFile.length > 0) {
                    result = listFile[0];
                    break;
                }
            }
            client.disconnect();
            return result;

        } catch (IOException e) {
            Log.e("get List Err", "IOException " + e.getMessage());
            return null;
        }
    }

    public static boolean uploadFile(String url, byte[] fileData) {
        Log.w("up file", url + "");
        boolean success = false;
        FTPClient client = createSimpleFTPClient();
        if (client == null) {
            return false;
        }
        try {
            client.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
            ByteArrayInputStream fs = new ByteArrayInputStream(fileData);
            success = client.storeFile(url, fs);
            if (!success) {
                Log.w("Cr File Err", "unknown reason. file: " + url);
            }
            client.disconnect();
        } catch (IOException e) {
            Log.w("Cr File Err", e.getMessage() + "");
            Log.w("file name", url + "");
        }
        return success;
    }

    private static FTPClient createSimpleFTPClient() {
        FTPClient client = new FTPClient();
        try {
            //client.setKeepAlive(false);
            //client.setDefaultTimeout(TIME_OUT);
            client.setConnectTimeout(TIME_OUT);
            //client.setSoTimeout(TIME_OUT);

            client.connect(FTP_SERVER_IP);
            client.enterLocalPassiveMode();
            boolean bl = client.login(FTP_USERNAME, FTP_PASSWORD);
            if (bl == false) {
                Log.w("login FTP_CMD fail", FTP_SERVER_IP + "|" + FTP_USERNAME + "|" + FTP_PASSWORD);
                return null;
            }
            return client;
        } catch (IOException e) {
            Log.w("ftp err", e.getMessage() + "");
            return null;
        }
    }


}
