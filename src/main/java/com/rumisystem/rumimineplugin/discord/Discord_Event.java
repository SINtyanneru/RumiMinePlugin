package com.rumisystem.rumimineplugin.discord;

import com.rumisystem.rumimineplugin.RumiMinePlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.awt.*;

import static com.rumisystem.rumimineplugin.Config.*;
import static com.rumisystem.rumimineplugin.RumiMinePlugin.GetPlayerCount;
import static com.rumisystem.rumimineplugin.discord.Discord.Discord_Channel;
import static com.rumisystem.rumimineplugin.discord.Discord.jda;
import static javax.sql.rowset.spi.SyncFactory.getLogger;

public class Discord_Event extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(CONFIG_FUNCTION_DISCORD){//DiscordBOTが有効化されているか
            //されている！
            String msg = e.getMessage().getContentRaw(); //入力されたメッセージを取得
            for (String ch : Discord_Channel) {
                if(ch != null){
                    if(ch.equals(e.getChannel().getId())) {//送信されたチャンネルが、設定されているものか
                        if(!e.getAuthor().equals(jda.getSelfUser())) {  //送信されたメッセージがBOTによるものではないか
                            //Да～
                            switch (msg){
                                case "rmp.list"://サーバー人数
                                    try {
                                        // Create the EmbedBuilder instance
                                        EmbedBuilder eb = new EmbedBuilder();   //埋め込みのやつを簡単に作れるツール(Discord.JSにはない！！神！！JAVA先生一生ついていきます！！)
                                        eb.setTitle("サーバー", null);     //タイトル
                                        eb.setColor(new Color(0xF50A0A));   //色設定
                                        eb.setDescription(String.format(String.format(CONFIG_MSG_COMMAND_SERVERLIST, GetPlayerCount())));

                                        //Discordのチャンネル全てに送信
                                        for (String s : Discord_Channel) {
                                            if(s != null){
                                                jda.getTextChannelById(s).sendMessage(eb.build()).queue();
                                            }
                                        }
                                    }catch (Exception ex){
                                        System.out.println("[ ERROR ]" + ex.getMessage());
                                    }
                                    break;

                                default:
                                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                                        player.sendMessage(new TextComponent("[ " + e.getGuild().getName() + "|" + e.getChannel().getName() + " ]<" + e.getAuthor().getName() + ">" + msg));
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}
