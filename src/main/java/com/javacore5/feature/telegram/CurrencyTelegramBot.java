package com.javacore5.feature.telegram;

import com.javacore5.feature.currency.CurrencyService;
import com.javacore5.feature.currency.PrivateBankCurrencyService;
import com.javacore5.feature.currency.dto.Currency;
import com.javacore5.feature.telegram.command.StartCommand;
import com.javacore5.feature.ui.PrettyPrintCurrencyService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {

    private CurrencyService currencyService;
    private PrettyPrintCurrencyService prettyPrintCurrencyService;
    public CurrencyTelegramBot() {
        currencyService = new PrivateBankCurrencyService();
        prettyPrintCurrencyService = new PrettyPrintCurrencyService();
        register(new StartCommand());
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            Currency currency = Currency.valueOf(callbackQuery);

            double currencyRate = currencyService.getRate(currency);

            String prettyText = prettyPrintCurrencyService.convert(currencyRate, currency);

            SendMessage responseMessage = new SendMessage();
            responseMessage.setText(prettyText);
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            responseMessage.setChatId(Long.toString(chatId));
            try {
                execute(responseMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }

            System.out.println("callbackQuery " + callbackQuery);
        }
            if(update.hasMessage()){
                String message = update.getMessage().getText();

                String responseText = "You wrote: " + message;

                SendMessage sendMessage = new SendMessage();
                sendMessage.setText(responseText);
                sendMessage.setChatId(Long.toString(update.getMessage().getChatId()));
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
    }

    @Override
    public String getBotUsername() {
        return BotConstants.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotConstants.BOT_TOKEN;
    }


}
