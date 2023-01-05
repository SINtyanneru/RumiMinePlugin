package com.rumisystem.rumimineplugin.discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import com.rumisystem.rumimineplugin.Config.*;

import static com.rumisystem.rumimineplugin.Config.*;
import static com.rumisystem.rumimineplugin.RumiMinePlugin.GetPlayerCount;

public class Discord {
    public static JDA jda = null;   //JDA
    public static String[] Discord_Channel = new String[5];   //Discordのチャンネルをすべてここに

    public static String BOT_TOKEN = "";   //DiscordBOTのトークン

    public static void main() {
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                System.out.println(BOT_TOKEN);
                //されている！
                jda = JDABuilder.createDefault(BOT_TOKEN, GatewayIntent.GUILD_MESSAGES)
                        .setRawEventsEnabled(true)
                        .addEventListeners(new Discord_Event()) //追加部分
                        .setActivity(Activity.playing("鯖起動済み"))
                        .setStatus(OnlineStatus.valueOf("ONLINE"))
                        .build();

                jda.awaitReady();
                Discord discord = new Discord();
                discord.StartDiscord();

                /*
                for (int i = 0; i < 10; i++) {

                    jda.getPresence().setActivity(Activity.playing(GetPlayerCount() + "人がログイン中"));
                    System.out.println("タスクが実行されました。");

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                */
            }
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //「プロキシサーバーが起動しました！」と送信
    public void StartDiscord(){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                // Create the EmbedBuilder instance
                EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                eb.setTitle("サーバー", null);     //タイトル
                eb.setColor(new Color(0x0A41F5));   //色設定
                eb.setDescription(String.format(CONFIG_MSG_PROXYSTART));

                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }
    //「プロキシサーバーが停止しました」と送信
    public void StopDiscord(){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                // Create the EmbedBuilder instance
                EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                eb.setTitle("サーバー", null);     //タイトル
                eb.setColor(new Color(0xF50A0A));   //色設定
                eb.setDescription(String.format(CONFIG_MSG_PROXYEXIT));

                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    public void SendDiscord(String TEXT, String MCID){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage("<" + MCID + ">" + TEXT).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    public void JoinServerDiscord(String MCID){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                // Create the EmbedBuilder instance
                EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                eb.setTitle("アクティビティ", null);     //タイトル
                eb.setColor(new Color(0x0AF5AB));   //色設定
                eb.setDescription(String.format(CONFIG_MSG_SERVERLOGIN, MCID));

                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    public void LeaveServerDiscord(String MCID){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                // Create the EmbedBuilder instance
                EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                eb.setTitle("アクティビティ", null);     //タイトル
                eb.setColor(new Color(0x0AF5AB));   //色設定
                eb.setDescription(String.format(CONFIG_MSG_SERVERLEAVE, MCID));

                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    public void SwitchServerDiscord(String MCID, String FS){
        try {
            if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
                //されている！
                // Create the EmbedBuilder instance
                EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                eb.setTitle("アクティビティ", null);     //タイトル
                eb.setColor(new Color(0x0AF5AB));   //色設定
                eb.setDescription(String.format(CONFIG_MSG_SERVERSWITCH, MCID, FS));

                //Discordのチャンネル全てに送信
                for (String s : Discord_Channel) {
                    if(s != null){
                        jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                    }
                }
            }
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }
}