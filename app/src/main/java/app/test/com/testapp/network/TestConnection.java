package app.test.com.testapp.network;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import app.test.com.testapp.utils.Logger;

/**
 * Utility connection class
 *
 * @author omar.brugna
 */
public class TestConnection {

    private static final int CONNECTION_TIMEOUT = 5000;
    private static final int SOCKET_TIMEOUT = 10000;

    private final String mUrl;
    private BufferedInputStream mInputStream;
    private InputStreamReader mInputStreamReader;
    private BufferedReader mBufferedReader;

    public TestConnection(String url) {
        mUrl = url;
    }

    public String initConnection() throws IOException {
        String response = startConnection();
        if (response != null) {
            Logger.i("Connection response" + Logger.DIVIDER + response);
        } else {
            Logger.e("Connection response is null");
        }
        return response;
    }

    private String startConnection() throws IOException {

        Logger.d("Starting connection at..\nUrl" + Logger.DIVIDER + mUrl);

        HttpURLConnection connection = (HttpURLConnection) new URL(mUrl).openConnection();
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(SOCKET_TIMEOUT);
        connection.setUseCaches(false);
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            Logger.e("Unexpected HTTP response: "
                    + connection.getResponseCode() + " "
                    + connection.getResponseMessage());
            return null;
        }

        try {

            // read the response
            mInputStream = new BufferedInputStream(connection.getInputStream());
            mInputStreamReader = new InputStreamReader(mInputStream, "UTF-8");
            mBufferedReader = new BufferedReader(mInputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = mBufferedReader.readLine()) != null)
                stringBuilder.append(line).append("\n");
            return stringBuilder.toString().trim();
        } catch (IOException e) {
            Logger.e("Connection error" + Logger.DIVIDER + e.getMessage());
            e.printStackTrace();
        } finally {
            // clean up
            connection.disconnect();
            try {
                if (mInputStream != null)
                    mInputStream.close();
                if (mInputStreamReader != null)
                    mInputStreamReader.close();
                if (mBufferedReader != null)
                    mBufferedReader.close();
            } catch (IOException ignored) {

            }
        }
        return null;
    }
}
