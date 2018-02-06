package odu.handson.classquiz.utility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgudipati on 10/6/2016.
 */
public class ServiceCaller {

    public static String startPostService(String resourcepath,String quizIdTag,String studentQuizId, String Tag, String jsonString)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        int responseCode=0;
        try
        {
            System.out.println("json string is "+jsonString);
            URL url = new URL(resourcepath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair(quizIdTag,studentQuizId));
            params.add(new BasicNameValuePair(Tag,jsonString));
            System.out.println(getQuery(params));
            wr.writeBytes(getQuery(params));
            wr.flush();
            wr.close();
            responseCode = connection.getResponseCode();
            System.out.println("response code quiz submit " + responseCode);
            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));

            String line="";
            while ((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String startQuestionPostService(String resourcepath,String userIdTag,String userId,String quizIdTag,String quizId,String longitude,String latitude)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        int responseCode=0;
//        double ulong=Double.parseDouble(longitude);
//        double ulat=Double.parseDouble(latitude);
        try
        {
            URL url = new URL(resourcepath);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            List<NameValuePair> params=new ArrayList<>();
            params.add(new BasicNameValuePair(userIdTag,userId));
            params.add(new BasicNameValuePair(quizIdTag,quizId));
            params.add(new BasicNameValuePair("longitude",longitude));
            params.add(new BasicNameValuePair("latitude",latitude));
            System.out.println(getQuery(params));
            wr.writeBytes(getQuery(params));
            wr.flush();
            wr.close();
            responseCode = connection.getResponseCode();
            System.out.println("response code quiz details " + responseCode);
            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));

            String line="";
            while ((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }
    public static String startGetService(String resourcepath,String userIdTag,String userId)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        int responseCode=0;
        try
        {
            URL url = new URL(resourcepath+"?"+userIdTag+"="+userId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
            System.out.println("response code quiz details " + responseCode);
            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));
            String line="";
            while ((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return buffer.toString();
    }
    public static String startGetQuestionService(String resourcepath,String userIdTag,String userId,String quizIdTag,String quizId)
    {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer("");
        int responseCode=0;
        try
        {
            URL url = new URL(resourcepath+"?"+userIdTag+"="+userId+"&"+quizIdTag+"="+quizId);
           // System.out.println(resourcepath+"?"+userIdTag+"="+userId+"&"+quizIdTag+"="+quizId);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            responseCode = connection.getResponseCode();
            System.out.println("response code quiz details " + responseCode);
            InputStream stream=connection.getInputStream();
            reader=new BufferedReader(new InputStreamReader(stream));
            String line="";
            while ((line= reader.readLine())!=null)
            {
                buffer.append(line);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
