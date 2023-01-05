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

import static com.rumisystem.rumimineplugin.Config.*;
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
            if(CONFIG_FUNCTION_CUSTOMCHAT){//カスタムチャットが有効かぁ？
                e.setCancelled(true);//チャットをなかったコトに(プロキシだけで処理されるようするため)
            }
            if(!e.isCommand()){//これはコマンド？？
                //コマンドじゃないよ！
                Discord discord = new Discord();
                discord.SendDiscord(e.getMessage(), e.getSender().toString());  //Discordに送信

                if(CONFIG_FUNCTION_CUSTOMCHAT){//カスタムチャットが有効かぁ？
                    //有効！！
                    String TEXT = e.getMessage();

                    //文字列のブロック
                    for (Block_ChatDATA blockchat : CONFIG_CUSTOMCHAT_BLOCKCHAT){
                        if(TEXT.contains(blockchat.WORD)){//ブロックされた文字列が含まれているか？
                            ProxiedPlayer p = (ProxiedPlayer) e.getSender();//PLAYER取得
                            p.sendMessage(new TextComponent(ChatColor.RED + CONFIG_MSG_COMMAND_CUSTOMCHATBLOCK));//その文字列はブロックされているとチャットに送信
                            return;//終了
                        }
                    }

                    //文字列の置き換え
                    for (Replace_ChatDATA replacechat : CONFIG_CUSTOMCHAT_REPLASECHAT){
                        TEXT = TEXT.replace(replacechat.OLD, replacechat.NEW);//文字列を置き換える
                    }

                    //チャットに送信
                    for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
                        //全プレイヤーに送信
                        player.sendMessage(new TextComponent(String.format(CONFIG_MSG_COMMAND_CUSTOMCHAT, e.getSender(), TEXT)));
                    }
                }
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
