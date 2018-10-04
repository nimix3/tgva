
package ir.telegramp;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import ir.telegramp.JarPath;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class HttpURLConnectionExample {
    private final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)";

    public String ComputeHash(String plainText, String salt) throws NoSuchAlgorithmException {
        byte[] plainTextBytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
        HashMap<String, Integer> items = new HashMap<String, Integer>();
        items.put("A", 10);
        items.forEach((k, v) -> System.out.println(" : "));
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] plainHash = digest.digest(plainTextBytes);
        byte[] concat = new byte[plainHash.length + saltBytes.length];
        System.arraycopy(saltBytes, 0, concat, 0, saltBytes.length);
        System.arraycopy(plainHash, 0, concat, saltBytes.length, plainHash.length);
        byte[] tHashBytes = digest.digest(concat);
        String hashValue = Base64.encode(tHashBytes);
        return hashValue;
    }

    public String getcpuid() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            return sc.next();
        }
        catch (Exception ex) {
            return "None";
        }
    }

    public String GetBoardMaker() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "baseboard", "get", "serialnumber"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            return sc.next();
        }
        catch (Exception ex) {
            return "None";
        }
    }

    public String GetBiosSerial() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "bios", "get", "serialnumber"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            return sc.next();
        }
        catch (Exception ex) {
            return "None";
        }
    }

    public String getMacAddress() {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "nic", "where", "NetEnabled='true'", "get", "MACAddress"});
            process.getOutputStream().close();
            Scanner sc = new Scanner(process.getInputStream());
            String property = sc.next();
            return sc.next();
        }
        catch (Exception ex) {
            return "None";
        }
    }

    public boolean sendPost() throws Exception {
        try {
            String inputLine;
            String urlParameters;
            String lic;
            String serial = this.ComputeHash(this.GetBiosSerial() + this.GetBoardMaker() + this.getMacAddress().replace(":", ""), this.getcpuid());
            String url = "http://saremco.ir/activation2.php";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String RND = "" + Math.abs(new Random().nextInt()) + "";
            try {
                lic = new String(Files.readAllBytes(Paths.get(JarPath.GetPath() + "/licence.txt", new String[0])));
                System.out.print(lic);
                urlParameters = "serial=" + lic + "&Machine=" + URLEncoder.encode(serial, "UTF-8") + "&RND=" + RND;
            }
            catch (Exception ex) {
                return false;
            }
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            if (responseCode == 200 && response.toString().equalsIgnoreCase(this.ComputeHash(serial, RND + "ok" + lic))) {
                System.out.println("ok");
                return true;
            }
            return false;
        }
        catch (Exception ex) {
            return false;
        }
    }
}

