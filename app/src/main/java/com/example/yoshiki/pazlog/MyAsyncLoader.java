package com.example.yoshiki.pazlog;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.example.yoshiki.pazlog.constants.GachaHistoryConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyAsyncLoader extends AsyncTaskLoader<String> {

    private final String url;
    private static final MediaType TEXT = MediaType.parse("text/plain;");

    public MyAsyncLoader(Context context, String url){
        super(context);
        this.url = url;
    }

    @Override
    public String loadInBackground() {

        JSONArray jsonArray = new JSONArray();

        try {

            List<Gacha_history> lists = new Select().from(Gacha_history.class).where("Status <> ?", GachaHistoryConstants.SYNC).execute();
            for (Gacha_history list : lists) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", list.getId());
                jsonObject.put("name", list.MonsterName);
                jsonObject.put("egg_type",list.MonsterName);
                jsonObject.put("got_at", list.GotAt);
                jsonObject.put("status", list.Status);
                jsonArray.put(jsonObject);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(TEXT, jsonArray.toString());

        Request request = new Request.Builder()
                .url(this.url)
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        String result;
        try{
            Response response = client.newCall(request).execute();
            result = response.body().string();
            //Log.d("mytag", String.valueOf(result));
            if(response.isSuccessful()){
                new Update(Gacha_history.class)
                        .set("Status = ?", GachaHistoryConstants.SYNC)
                        .where("Status <> ? " , GachaHistoryConstants.SYNC)
                        .execute();
            }
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;

//        try{
//            url = new URL(this.url);
//        }catch (MalformedURLException e){
//            e.printStackTrace();
//            return null;
//        }
//
//        HttpURLConnection conn = null;
//        try{
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Connection", "close");
//            conn.setFixedLengthStreamingMode(0);
//
//            conn.connect();
//
//            int code = conn.getResponseCode();
//            if(code != 200){
//                Log.e("mytag", "HTTP GET Error : code = " + code);
//                return null;
//            }
//
//            String content = readContent(conn);
//
//            return TextUtils.isEmpty(content) ? null : new JSONObject(content);
//        } catch (IOException e){
//            e.printStackTrace();
//            return null;
//        } catch (JSONException e){
//            e.printStackTrace();
//            return null;
//        } finally {
//            if(conn != null){
//                try{
//                    conn.disconnect();
//                }catch(Exception ignore){
//                }
//            }
//        }
//
        }
//
//    private String readContent(HttpURLConnection conn) throws IOException{
//        String charsetName;
//
//        String contentType = conn.getContentType();
//        if(!TextUtils.isEmpty(contentType)){
//            int idx = contentType.indexOf("charset=");
//            if(idx != -1){
//                charsetName = contentType.substring(idx + "charset=".length());
//            }else{
//                charsetName = "UTF-8";
//            }
//        }else{
//            charsetName = "UTF-8";
//        }
//
//        // getInputStream : 通信ソケットから受信するバイトストリームを取得
//        // BufferedInputStream : 引数にバッファリングの対象となるバイトストリームを指定
//        //   readメソッドが呼ばれるタイミングでバッファからデータを読み込む
//        BufferedInputStream is = new BufferedInputStream(conn.getInputStream());
//
//        int length = conn.getContentLength();
//        ByteArrayOutputStream os = length > 0 ? new ByteArrayOutputStream(length) : new ByteArrayOutputStream();
//
//        byte[] buff = new byte[10240];
//        int readLen;
//        while ((readLen = is.read(buff)) != -1){
//            if(isReset()){
//                return null;
//            }
//
//            if(readLen > 0){
//                os.write(buff, 0, readLen);
//            }
//        }
//
//        return new String(os.toByteArray(), charsetName);
//    }
}
