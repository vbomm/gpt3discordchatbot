package com.github.vbomm.gpt3discordchatbot;

import com.github.vbomm.gpt3discordchatbot.listeners.MessageListener;
import com.theokanning.openai.OpenAiService;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import javax.security.auth.login.LoginException;

public class Bot {

    private Dotenv config;
    private ShardManager shardManager;
    public Bot() throws LoginException {
        config = Dotenv.configure().load();
        String discordToken = config.get("DISCORDBOTTOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(discordToken);

        String openAiToken = config.get("OPENAITOKEN");
        OpenAiService service = new OpenAiService(openAiToken);

        builder.enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES);

        HashCreator hashCreator = new HashCreator();

        shardManager = builder.build();
        shardManager.addEventListener(new MessageListener(service, hashCreator));
    }
}