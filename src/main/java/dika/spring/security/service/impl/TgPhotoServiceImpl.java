package dika.spring.security.service.impl;

import dika.spring.security.bot.PhotoTelegramBot;
import dika.spring.security.service.PhotoService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.GetUserProfilePhotos;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.UserProfilePhotos;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Здесь ты не работаешь с API телеграмма, а просто загружаешь страничку и берешь элемент img
@Service
public class TgPhotoServiceImpl implements PhotoService {
    private final PhotoTelegramBot telegramBot;

    public TgPhotoServiceImpl(PhotoTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    // Убери throws и обрабатывай это исключение
    @Override
    public String getPhoto(String userID) {
        Long userId = Long.valueOf(userID);
        GetUserProfilePhotos getPhotosRequest = new GetUserProfilePhotos();
        getPhotosRequest.setUserId(userId);
        getPhotosRequest.setOffset(0);
        getPhotosRequest.setLimit(1);
        try {
            UserProfilePhotos photos = telegramBot.execute(getPhotosRequest);
            if (photos.getTotalCount() > 0) {
                PhotoSize photoSize = photos.getPhotos().get(0).get(0);
                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(photoSize.getFileId());
                File file = telegramBot.execute(getFileRequest);
                return "https://api.telegram.org/file/bot" + telegramBot.getBotToken() + "/" + file.getFilePath();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}

