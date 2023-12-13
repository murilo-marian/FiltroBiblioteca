import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Filtro filtro = Filtro.getInstance("blackList.txt", TipoDeFiltro.Asteriscos, true);
        Scanner entrada = new Scanner(System.in);
        while(true) {
            filtro.adicionarBlackList(entrada.nextLine());
        }
    }
}