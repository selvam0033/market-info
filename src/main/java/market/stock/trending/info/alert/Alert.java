package market.stock.trending.info.alert;
import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Alert {
    public static void play(String name,String info) {
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String filePath = desktopPath+"\\stock\\media\\siren-alert-96052.mp3";
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Player player = new Player(fis);
            player.play();
            System.out.println("Music played for "+info+" "+name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
