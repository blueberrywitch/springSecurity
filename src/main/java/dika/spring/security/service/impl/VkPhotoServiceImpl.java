package dika.spring.security.service.impl;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.User;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import dika.spring.security.service.PhotoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkPhotoServiceImpl implements PhotoService {

    private final static String accessToken = "vk1.a.G_ZosFrJxngmcdJCrZAXozr4zwo-2JvlBYqFY0mVabde3-TJULSPU07UWmAkooyE7vT8ZQqIOulYJPYjsQVsyz50FbGU0uoLBkYlW5jnnsFEN4SpshgWgmcnHCwrsziVxjD4lVzu3O5BknRQAXAmItogw5c3uIjYHnuFFXq2bRAssRVwK2UX6EF2wP_MPXTHv87QdjcXzJIgdkZ54ZqWSA";

    @Override
    public String getPhoto(String userID) {
        long authUserId = 506705247L;
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
