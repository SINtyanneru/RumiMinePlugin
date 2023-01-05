package com.rumisystem.rumimineplugin;

import com.rumisystem.rumimineplugin.discord.Discord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import static com.rumisystem.rumimineplugin.discord.Discord.Discord_Channel;

public class Config{
    public static String Config_Path = null;

    public static String CONFIG_MSG_PROXYSTART = null;
    public static String CONFIG_MSG_PROXYEXIT = null;
    public static String CONFIG_MSG_SERVERLEAVE = null;
    public static String CONFIG_MSG_SERVERLOGIN = null;
    public static String CONFIG_MSG_SERVERSWITCH = null;
    public static String CONFIG_MSG_COMMAND_SERVERLIST = null;
    public static boolean CONFIG_FUNCTION_DISCORD = false;
    public static boolean CONFIG_FUNCTION_HUBCOM = false;

    public static String CONFIG_HUBCOM_HUB = null;
    public static String CONFIG_HUBCOM_MSG = null;

    public static void main(){
        config_ConfLoad();
        msg_ConfLoad();
        DiscordCh_ConfLoad();
        hubcom_ConfLoad();
    }

    private static void DiscordCh_ConfLoad(){
        try {
            //Discordの設定ファイルを読み込み
            if(CONFIG_FUNCTION_DISCORD) {//DiscordBOTが有効化されているか
                //されている！
                FileInputStream fis = new FileInputStream(Config_Path + "/DiscordCh.txt");
                BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                String content;
                while((content = bf.readLine()) != null) {
                    int count = 0;
                    for (String s : content.split("/")) {
                        System.out.println(s);
                        if(!s.equals(null)){
                            Discord_Channel[count] = s;
                            count++;
                        }
                    }
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void msg_ConfLoad(){
        try {
            ObjectMapper  objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/msg.json").toFile());
            System.out.println("[ OK ]LoadConfig:" + json);
            System.out.println("[ *** ]Setting Config...");

            CONFIG_MSG_PROXYSTART = json.get("PROXY_START").textValue();//起動メッセージ
            CONFIG_MSG_PROXYEXIT = json.get("PROXY_EXIT").textValue();//終了メッセージ
            CONFIG_MSG_SERVERLOGIN = json.get("SERVER_LOGIN").textValue();//ログインメッセージ
            CONFIG_MSG_SERVERLEAVE = json.get("SERVER_LEAVE").textValue();//ログアウトメッセージ
            CONFIG_MSG_SERVERSWITCH = json.get("SERVER_SWITCH").textValue();//移動メッセージ
            CONFIG_MSG_COMMAND_SERVERLIST = json.get("COMMAND_SERVER_LIST").textValue();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void config_ConfLoad(){
        try {
            ObjectMapper  objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/config.json").toFile());
            System.out.println("[ OK ]LoadConfig:" + json);
            System.out.println("[ *** ]Setting Config...");

            CONFIG_FUNCTION_DISCORD = Boolean.valueOf(json.get("DiscordBOT").textValue());//機能の有効無効化
            CONFIG_FUNCTION_HUBCOM = Boolean.valueOf(json.get("HubCom").textValue());//機能の有効無効化
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void hubcom_ConfLoad(){
        try {
            ObjectMapper  objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/hubcom.json").toFile());
            System.out.println("[ OK ]LoadConfig:" + json);
            System.out.println("[ *** ]Setting Config...");

            CONFIG_HUBCOM_HUB = json.get("HUB").textValue();//HUb鯖を指定
            CONFIG_HUBCOM_MSG = json.get("MSG").textValue();//メッセージを設定
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
}
