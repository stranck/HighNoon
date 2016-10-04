package it.TetrisReich.com.highnoon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

/**
 * Hello world!
 *
 */
public class App{
	public static int tInt = 0;
	public static int[] n = new int[3];
	public static int[] t = new int[3];
	public static String[] s = new String[3];
	public static String c = "\\";
    public static void main(String[] args) throws IOException{
    	TelegramBot bot = TelegramBotAdapter.build(reader("token"));
    	String chat = reader("chat");
    	try{
    		if(args[0].equalsIgnoreCase("-l")) c = "/";
    	}catch (ArrayIndexOutOfBoundsException e) {}
    	boolean b = true;
    	for(int i=0;i<n.length;i++){
    		utils(i);
    	}
    	System.out.println("Startup done");
    	while(true){
    		System.out.print("Checking date ("+time("HHmm")+"): ");
    		if(highNoon(Integer.parseInt(time("HHmm")))){if(b){
    			SendResponse sp = bot.execute(new SendMessage(chat, "*It's hiiiiiiigh noon*"
    					+ "\n_In " + s[tInt] + "._").parseMode(ParseMode.Markdown));
    			utils(tInt);
        		b = false;
        		System.out.println("true.\n" + sp.toString());}
    		} else {b = true; System.out.println("false");}
    		wait(5000);
    	}
    }
    public static String reader(String path) throws IOException{
        FileReader fileReader;
        fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader( fileReader );
         
        String line = bufferedReader.readLine();
        bufferedReader.close();
        return line;
    }
    public static String time(String format) {
        SimpleDateFormat sdfDate = new SimpleDateFormat(format);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
	public static void wait(int time){
		try{
		    Thread.sleep(time);
		} catch(InterruptedException ex){
		    Thread.currentThread().interrupt();
		}
	}
	public static int random(int i) {
		Random r = new Random();
		return r.nextInt(i);
	}
	public static boolean highNoon(int a){
		//System.out.print("Analazing date");
		for(int i=0;i<t.length;i++){
			//System.out.println(t[i] + "    " + a);
			//wait(1000);
			if(t[i]==a) {tInt = i; return true;}
		}
		//System.out.print("done");
		return false;
	}
	public static void utils(int i) throws NumberFormatException, IOException{
		n[i] = random(4);
		t[i] = Integer.parseInt(reader("time" + c + i).split(";")[n[i]]);
		s[i] = reader("locate" + c + i).split(";")[n[i]];
    	System.out.println(Arrays.toString(n));
    	System.out.println(Arrays.toString(s));
    	System.out.println(Arrays.toString(t));
	}
}