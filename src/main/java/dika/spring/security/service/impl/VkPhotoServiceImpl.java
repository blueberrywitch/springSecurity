package dika.spring.security.service.impl;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import dika.spring.security.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkPhotoServiceImpl implements PhotoService {

    @Value("${spring.constants.accessToken}")
    private  String accessToken;

    @Value("${spring.constants.authUserId}")
    private  long authUserId;

    @Override
    public String getPhoto(String userID) {
        UserActor actor = new UserActor(authUserId, accessToken);
        VkApiClient vk = new VkApiClient(new HttpTransportClient());

        try {
            List<GetResponse> users = vk.users()
                    .get(actor)
                    .userIds(userID)
                    .fields(Fields.PHOTO_100)
                    .execute();

            User user = users.get(0);
            return (user.getPhoto100()).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
