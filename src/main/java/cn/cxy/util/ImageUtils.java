package cn.cxy.util;

import cn.cxy.entity.InstantImage;
import cn.cxy.value.Constants;
import cn.cxy.value.WeChatMsgTypes;
import cn.cxy.value.WeChatUrl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;

/**
 * post上传图片临时文件获得media_id
 * Created by lenovo on 2016/11/2.
 */
public class ImageUtils {

    public static void handleImage(String path,String type){
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            String url = WeChatUrl.POST_IMAGE.replace("ACCESS_TOKEN",Constants.INSTANT_ACCESS_TOKEN).replace("TYPE", type);
            HttpPost httpPost = new HttpPost(url);
            //TODO POST表单处理待上传的文件信息
            FileBody fileBody = new FileBody(new File(path));
            HttpEntity httpEntity = MultipartEntityBuilder.create().addPart("media", fileBody).build();
            httpPost.setEntity(httpEntity);

            response = client.execute(httpPost);
            int code = response.getStatusLine().getStatusCode();
            if (200 == code){
                HttpEntity entity = response.getEntity();
                String s = EntityUtils.toString(entity);
                InstantImage image = JsonUtils.deserialize(s, InstantImage.class);
                System.err.println(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (client != null){
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
