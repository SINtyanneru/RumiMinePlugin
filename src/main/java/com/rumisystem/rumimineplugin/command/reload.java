package com.rumisystem.rumimineplugin.command;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class reload extends Command {
    public reload(){
        super("reload", "admin", "rmpr");
    }

    public void execute(CommandSender sender, String[] arg){
        com.rumisystem.rumimineplugin.Config.main();
    }
}
