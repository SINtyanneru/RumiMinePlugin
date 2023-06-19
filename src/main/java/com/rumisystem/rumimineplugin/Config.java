package com.rumisystem.rumimineplugin;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.gson.JsonArray;
import com.rumisystem.rumimineplugin.discord.Discord;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import static com.rumisystem.rumimineplugin.discord.Discord.Discord_Channel;
import static com.rumisystem.rumimineplugin.discord.Discord.BOT_TOKEN;

public class Config{
    public static String Config_Path = null;

    public static String CONFIG_MSG_PROXYSTART = null;
    public static String CONFIG_MSG_PROXYEXIT = null;
    public static String CONFIG_MSG_SERVERLEAVE = null;
    public static String CONFIG_MSG_SERVERLOGIN = null;
    public static String CONFIG_MSG_SERVERSWITCH = null;
    public static String CONFIG_MSG_COMMAND_SERVERLIST = null;
    public static String CONFIG_MSG_COMMAND_CUSTOMCHAT = null;
    public static String CONFIG_MSG_COMMAND_CUSTOMCHATBLOCK = null;
    public static boolean CONFIG_FUNCTION_DISCORD = false;
    public static boolean CONFIG_FUNCTION_HUBCOM = false;
    public static boolean CONFIG_FUNCTION_CUSTOMCHAT = false;

    public static String CONFIG_HUBCOM_HUB = null;
    public static String CONFIG_HUBCOM_MSG = null;

    public static List<Replace_ChatDATA> CONFIG_CUSTOMCHAT_REPLASECHAT = null;
    public static List<Block_ChatDATA> CONFIG_CUSTOMCHAT_BLOCKCHAT = null;

    public static void main(){
        config_ConfLoad();
        msg_ConfLoad();
        DiscordCh_ConfLoad();
        Discord_ConfLoad();
        hubcom_ConfLoad();
        replase_chat_ConfLoad();
        block_chat_ConfLoad();
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

    private static void Discord_ConfLoad(){
        try {
            File CFILE = new File(Config_Path + "/Discord.json");
            if(CFILE.exists()){
                System.out.println(Paths.get(Config_Path + "/Discord.json").toFile());

                ObjectMapper  objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/Discord.json").toFile());
                System.out.println("[ OK ]LoadConfig Discord:" + json);
                System.out.println("[ *** ]Setting Config...");

                BOT_TOKEN = json.get("TOKEN").textValue();//トークン
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("{\n" +
                            "\t\"TOKEN\":\"BOT TOKEN HERE\"\n" +
                            "}");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void msg_ConfLoad(){
        try {
            File CFILE = new File(Config_Path + "/msg.json");
            if(CFILE.exists()){
                ObjectMapper  objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/msg.json").toFile());
                System.out.println("[ OK ]LoadConfig MSG:" + json);
                System.out.println("[ *** ]Setting Config...");

                CONFIG_MSG_PROXYSTART = json.get("PROXY_START").textValue();//起動メッセージ
                CONFIG_MSG_PROXYEXIT = json.get("PROXY_EXIT").textValue();//終了メッセージ
                CONFIG_MSG_SERVERLOGIN = json.get("SERVER_LOGIN").textValue();//ログインメッセージ
                CONFIG_MSG_SERVERLEAVE = json.get("SERVER_LEAVE").textValue();//ログアウトメッセージ
                CONFIG_MSG_SERVERSWITCH = json.get("SERVER_SWITCH").textValue();//移動メッセージ
                CONFIG_MSG_COMMAND_SERVERLIST = json.get("COMMAND_SERVER_LIST").textValue();
                CONFIG_MSG_COMMAND_CUSTOMCHAT = json.get("CUSTOM_CHAT").textValue();
                CONFIG_MSG_COMMAND_CUSTOMCHATBLOCK = json.get("CUSTOM_CHAT_BLOCK").textValue();
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("{\n" +
                            "\t\"PROXY_START\":\":ballot_box_with_check: プロキシサーバーが起動しました！\",\n" +
                            "\t\"PROXY_EXIT\":\":no_entry_sign: プロキシサーバーが停止しました\",\n" +
                            "\t\"SERVER_LOGIN\":\"「** %1$s **」さんがログインしました！\",\n" +
                            "\t\"SERVER_LEAVE\":\"「** %1$s **」さんが鯖に退出しました！\",\n" +
                            "\t\"SERVER_SWITCH\":\":twisted_rightwards_arrows: 「** %1$s **」さんが「** %2$s **」に移動しました\",\n" +
                            "\t\"COMMAND_SERVER_LIST\":\"現在の人数は、「%1$s」人です！\",\n" +
                            "\t\"CUSTOM_CHAT\":\"<%1$s>%2$s\",\n" +
                            "\t\"CUSTOM_CHAT_BLOCK\":\"その文字列は禁止されています！\"\n" +
                            "}");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void config_ConfLoad(){
        try {
            File CFILE = new File(Config_Path + "/config.json");
            if(CFILE.exists()){
                ObjectMapper  objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/config.json").toFile());
                System.out.println("[ OK ]LoadConfig:" + json);
                System.out.println("[ *** ]Setting Config...");

                CONFIG_FUNCTION_DISCORD = Boolean.valueOf(json.get("DiscordBOT").textValue());//機能の有効無効化
                CONFIG_FUNCTION_HUBCOM = Boolean.valueOf(json.get("HubCom").textValue());//機能の有効無効化
                CONFIG_FUNCTION_CUSTOMCHAT = Boolean.valueOf(json.get("CustomChat").textValue());//機能の有効無効化
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("{\n" +
                            "\t\"DiscordBOT\":\"false\",\n" +
                            "\t\"HubCom\":\"false\",\n" +
                            "\t\"CustomChat\":\"false\"\n" +
                            "}");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void hubcom_ConfLoad(){
        try {
            File CFILE = new File(Config_Path + "/hubcom.json");
            if(CFILE.exists()){
                ObjectMapper  objectMapper = new ObjectMapper();
                JsonNode json = objectMapper.readTree(Paths.get(Config_Path + "/hubcom.json").toFile());
                System.out.println("[ OK ]LoadConfig HubCom:" + json);
                System.out.println("[ *** ]Setting Config...");

                CONFIG_HUBCOM_HUB = json.get("HUB").textValue();//HUb鯖を指定
                CONFIG_HUBCOM_MSG = json.get("MSG").textValue();//メッセージを設定
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("{\n" +
                            "\t\"HUB\":\"lobby\",\n" +
                            "\t\"MSG\":\"NONE\"\n" +
                            "}");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void replase_chat_ConfLoad(){
        ObjectMapper objectMapper = new ObjectMapper();

        List<Replace_ChatDATA> replace_chatDATA = null;
        try {
            File CFILE = new File(Config_Path + "/replace_chat.json");
            if(CFILE.exists()){
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Config_Path + "/replace_chat.json"), "UTF-8"))) {
                    String string = reader.readLine();
                    while (string != null){
                        builder.append(string + System.getProperty("line.separator"));
                        string = reader.readLine();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                replace_chatDATA = objectMapper.readValue(builder.toString(), new TypeReference<List<Replace_ChatDATA>>() {});

                CONFIG_CUSTOMCHAT_REPLASECHAT = replace_chatDATA;
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("[{\n" +
                            "\t\"OLD\":\"置き換える前の内容\",\n" +
                            "\t\"NEW\":\"置き換える先の内容\"\n" +
                            "}]");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void block_chat_ConfLoad(){
        ObjectMapper objectMapper = new ObjectMapper();

        List<Block_ChatDATA> block_chatDATA = null;
        try {
            File CFILE = new File(Config_Path + "/block_chat.json");
            if(CFILE.exists()){
                StringBuilder builder = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(Config_Path + "/block_chat.json")), "UTF-8"))) {
                    String string = reader.readLine();
                    while (string != null){
                        builder.append(string + System.getProperty("line.separator"));
                        string = reader.readLine();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                block_chatDATA = objectMapper.readValue(builder.toString(), new TypeReference<List<Block_ChatDATA>>() {});

                CONFIG_CUSTOMCHAT_BLOCKCHAT = block_chatDATA;
            }else{
                boolean created = CFILE.createNewFile();
                if (created) {
                    System.out.println("Created config file");
                    FileWriter writer = new FileWriter(CFILE);
                    writer.write("[{\n" +
                            "\t\"WORD\":\"下ネタとか\"\n" +
                            "}]");
                    System.out.println("Writed config file");

                    main();
                } else {
                    System.out.println("ファイルの作成に失敗しました。");
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class Replace_ChatDATA {
    public String OLD;
    public String NEW;
}

class Block_ChatDATA {
    public String WORD;
}

//JAVAでファイル読み込むのむずかしすぎだろ！！readtoend()的なの実装しろ；；