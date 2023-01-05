package com.rumisystem.rumimineplugin.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static com.rumisystem.rumimineplugin.Config.*;

public class hub extends Command {
    public hub(){
        //コマンドとパーミッション
        super("hub");
    }

    public void execute(CommandSender sender, String[] arg){
        if(CONFIG_FUNCTION_HUBCOM){
            if ((sender instanceof ProxiedPlayer)) {
                ProxiedPlayer p = (ProxiedPlayer)sender;
                p.connect(ProxyServer.getInstance().getServerInfo(CONFIG_HUBCOM_HUB));
                if(!CONFIG_HUBCOM_MSG.equals("NONE")){
                    p.sendMessage(new TextComponent(CONFIG_HUBCOM_MSG));
                }
            }
        }
    }
}
