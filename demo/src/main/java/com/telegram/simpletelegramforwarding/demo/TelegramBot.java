package com.telegram.simpletelegramforwarding.demo;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final String botToken;
    private final String botName;
    private final String botUsername;
    private final String developerId;
    private final String botTestChannelId;
    private final String botProdChannelId;
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() || update.getMessage().hasVideo()){
            logger.info("Mensagem de vídeo recebida!");
            Long senderId = update.getMessage().getFrom().getId();
            if (senderId.equals(Long.valueOf(developerId))){
                try {
                    SendVideo sendVideo = new SendVideo();
                    sendVideo.setChatId(botProdChannelId);
                    // sendVideo.setChatId(botTestChannelId);
                    sendVideo.setVideo(new InputFile(update.getMessage().getVideo().getFileId()));
                    sendVideo.setCaption("#TikTok");

                    execute(sendVideo);
                    logger.info("Vídeo enviado com sucesso!");
                } catch (TelegramApiException e) {
                    logger.error("Erro ao enviar vídeo: " + e);
                }
            } else {
                logger.info("Mensagem enviada por um usuário comum!");
            }
        }

    }
}
