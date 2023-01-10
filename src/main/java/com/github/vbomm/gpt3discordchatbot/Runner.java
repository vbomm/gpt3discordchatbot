package com.github.vbomm.gpt3discordchatbot;

import javax.security.auth.login.LoginException;

public class Runner
{
    public static void main( String[] args )
    {
        try {
            Bot bot = new Bot();
        } catch (
            LoginException e) {
            System.err.println("discord token invalid");
        }
    }
}
