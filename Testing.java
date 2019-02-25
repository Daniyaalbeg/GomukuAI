
import java.awt.Color;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) {
        GomokuPlayer player = new Player160983966();
        Scanner scanner = new Scanner(System.in);
        while(true){
            Color[][] pieces = new Color[8][8];
            for(int i = 0; i < 8; i++){
                String line = scanner.nextLine();
                for(int j = 0; j < 8; j++){
                    if(line.charAt(j) == '.')
                        pieces[i][j] = Color.BLACK;
                    else if(line.charAt(j) == 'X')
                        pieces[i][j] = Color.WHITE;
                }
            }
            System.out.println(player.chooseMove(pieces, Color.WHITE));
        }
    }
}
