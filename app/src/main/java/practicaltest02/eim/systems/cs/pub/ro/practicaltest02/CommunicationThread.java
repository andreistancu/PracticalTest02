package practicaltest02.eim.systems.cs.pub.ro.practicaltest02;
//
///**
// * Created by student on 22.05.2018.
// */
//
//import android.util.Log;
//
import org.json.JSONException;
import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import cz.msebera.android.httpclient.NameValuePair;
//import cz.msebera.android.httpclient.client.HttpClient;
//import cz.msebera.android.httpclient.client.ResponseHandler;
//import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
//import cz.msebera.android.httpclient.client.methods.HttpPost;
//import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
//import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
//import cz.msebera.android.httpclient.message.BasicNameValuePair;
//import cz.msebera.android.httpclient.protocol.HTTP;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.Constants;
import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.ServerThread;
import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.Utilities;
import practicaltest02.eim.systems.cs.pub.ro.practicaltest02.Information;



public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket == null) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
            return;
        }
        try {
            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader == null || printWriter == null) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Buffered Reader / Print Writer are null!");
                return;
            }
            Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type!");
            String city = bufferedReader.readLine();
            String informationType = bufferedReader.readLine();
            if (city == null || city.isEmpty() || informationType == null || informationType.isEmpty()) {
                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type!");
                return;
            }
            String data = serverThread.getData();
            Information info = null;
//            HashMap<String, WeatherForecastInformation> data = serverThread.getData();
//            WeatherForecastInformation weatherForecastInformation = null;

                Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS + data);
                List<NameValuePair> params = new ArrayList<>();
                params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                httpPost.setEntity(urlEncodedFormEntity);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String pageSourceCode = httpClient.execute(httpPost, responseHandler);
                if (pageSourceCode == null) {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error getting the information from the webservice!");
                    return;
                }
//                Document document = Jsoup.parse(pageSourceCode);
//                Element element = document.child(0);
//                Elements elements = element.getElementsByTag(Constants.SCRIPT_TAG);
//                for (Element script: elements) {
//                    String scriptData = script.data();
//                    if (scriptData.contains(Constants.SEARCH_KEY)) {
//                        int position = scriptData.indexOf(Constants.SEARCH_KEY) + Constants.SEARCH_KEY.length();
//                        scriptData = scriptData.substring(position);
//                        JSONObject content = new JSONObject(scriptData);
//                        JSONObject currentObservation = content.getJSONObject(Constants.CURRENT_OBSERVATION);
//                        String city = currentObservation.getString("name");
//
//                        info = new Information(data);
//                        serverThread.setData(city);
//                        break;
//                    }
//                }

//            if (weatherForecastInformation == null) {
//                Log.e(Constants.TAG, "[COMMUNICATION THREAD] Weather Forecast Information is null!");
//                return;
//            }
//            String result = null;
//            switch(informationType) {
//                case Constants.ALL:
//                    result = weatherForecastInformation.toString();
//                    break;
//                default:
//                    result = "[COMMUNICATION THREAD] Wrong information type (all / temperature / wind_speed / condition / humidity / pressure)!";
//            }
//            printWriter.println(result);
//            printWriter.flush();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
//        } catch (JSONException jsonException) {
//            Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());
//            if (Constants.DEBUG) {
//                jsonException.printStackTrace();
//            }
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ioException) {
                    Log.e(Constants.TAG, "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                    if (Constants.DEBUG) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

}