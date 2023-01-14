package com.github.vbomm.gpt3discordchatbot.listeners;

import com.github.vbomm.gpt3discordchatbot.HashCreator;
import com.theokanning.openai.completion.CompletionRequest;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import com.theokanning.openai.OpenAiService;
import java.security.NoSuchAlgorithmException;

public class MessageListener extends ListenerAdapter {
    private OpenAiService service;
    private HashCreator hashCreator;

    public MessageListener(OpenAiService service, HashCreator hashCreator) {
        this.service = service;
        this.hashCreator = hashCreator;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        long ownId = event.getJDA().getSelfUser().getIdLong();
        User author = event.getAuthor();
        String message = event.getMessage().getContentRaw();

        if (author.isBot()) return;
        if (message.length() == 0) return;
        if (message.charAt(0) != '!') return;
        if (author.getIdLong() == ownId) return;

        CompletionRequest completionRequest;
        try {
            completionRequest = CompletionRequest.builder()
                    .model("text-davinci-003")
                    .maxTokens(256)
                    .prompt(message)
                    .echo(false)
                    .user(hashCreator.createMD5Hash(author.getName()))
                    .build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            var completion = service.createCompletion(completionRequest).getChoices();

            completion.forEach(text -> event.getChannel().sendMessage(text.getText()).queue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
