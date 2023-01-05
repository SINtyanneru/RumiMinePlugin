package com.rumisystem.rumimineplugin;

import com.rumisystem.rumimineplugin.discord.Discord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.GroupedThreadFactory;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.checkerframework.checker.units.qual.C;

import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import static com.rumisystem.rumimineplugin.Config.CONFIG_FUNCTION_HUBCOM;
import static com.rumisystem.rumimineplugin.Config.Config_Path;

public final class RumiMinePlugin extends Plugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        getProxy().getPluginManager().registerListener(this, new Events());

        getLogger().info("==========[RSP]==========");
        getLogger().info("RSP Enabled!!!");
        getLogger().info("RSP V1.0");

        System.out.println(String.format("ロシア人は%1$sと%2$sでできている(%3$s)", "ウォッカ", "コサック", "諸説あり"));//String.Formatのテスト！おぼえた！

        // Create plugin config folder if it doesn't exist
        if (!getDataFolder().exists()) {
            getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        Config_Path = getDataFolder().getPath();
        Config.main();
        Discord.main();

        //コマンド登録
        getProxy().getPluginManager().registerCommand(this, new com.rumisystem.rumimineplugin.command.reload());
        if(CONFIG_FUNCTION_HUBCOM){
            getProxy().getPluginManager().registerCommand(this, new com.rumisystem.rumimineplugin.command.hub());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Discord discord = new Discord();
        discord.StopDiscord();

        getLogger().info("RSP Disabled... GoodBye");
    }

    public static int GetPlayerCount(){
        return ProxyServer.getInstance().getOnlineCount();
    }
}
