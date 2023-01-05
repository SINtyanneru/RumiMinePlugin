package com.rumisystem.rumimineplugin;

import com.rumisystem.rumimineplugin.discord.Discord;
import net.dv8tion.jda.api.entities.Activity;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.Chat;

import static com.rumisystem.rumimineplugin.RumiMinePlugin.GetPlayerCount;
import static com.rumisystem.rumimineplugin.discord.Discord.jda;
import static javax.sql.rowset.spi.SyncFactory.getLogger;

public class Events implements Listener {
    //Server Joined
    @EventHandler
    public void onJoin(PostLoginEvent event) {
        try {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                player.sendMessage(new TextComponent(ChatColor.YELLOW + "「" + event.getPlayer().getName() + "」さん！ようこそ！"));
            }

            Discord discord = new Discord();
            discord.JoinServerDiscord(event.getPlayer().getName());
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        try {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                player.sendMessage(new TextComponent(ChatColor.YELLOW + "「" + e.getPlayer().getName() + "」さんが「" + e.getPlayer().getServer().getInfo().getName() + "」に移動しました！"));
            }

            Discord discord = new Discord();
            discord.SwitchServerDiscord(e.getPlayer().getName(), e.getFrom().getName());

        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    @EventHandler
    public void onProxyLeave(PlayerDisconnectEvent event) {
        try {
            for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                player.sendMessage(new TextComponent(ChatColor.YELLOW + event.getPlayer().getName() + "さんが退出しました！"));
            }

            Discord discord = new Discord();
            discord.LeaveServerDiscord(event.getPlayer().getName());
        }catch (Exception ex){
            System.out.println("[ ERROR ]" + ex.getMessage());
        }
    }

    @EventHandler
    //チャット
    public void onChat(ChatEvent e){
        try {
            if(!e.isCommand()){//これはコマンド？？
                //コマンドじゃないよ！
                Discord discord = new Discord();
                discord.SendDiscord(e.getMessage(), e.getSender().toString());
            }else {
                //コマンドだよ！
                System.out.println("Command Send:<" + e.getSender() + ">" + e.getMessage());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @EventHandler
    public void onPluginMessageEvent(PluginMessageEvent e){
        System.out.println(e.toString());
    }
}
