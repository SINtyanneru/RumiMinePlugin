package com.rumisystem.rumimineplugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class RumiServer {
    public String MCID_GET(String MCID){
        try {
            URL url = new URL("https://rumiserver.com/API/SERVICE/MCID_GET.php?ID=" + MCID);

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            try (BufferedReader bf = new BufferedReader(new InputStreamReader(
                    conn.getInputStream())))
            {
                String line;
                while ((line = bf.readLine()) != null) {
                    Charset charset = StandardCharsets.UTF_8;
                    String result = new String(Base64.getDecoder().decode(line), charset);
                    return result;
                }
            }
        }catch (Exception ex){
            // Finally we have the response
            ex.printStackTrace();
        }
        return MCID;
    }
}
