package cmpe150;
import java.util.*;
import java.io.*;

public class Assigment3 {
	public static void main(String[] args)throws FileNotFoundException{
		char[][] board=new char[6][6];
		Scanner console=new Scanner(System.in);
		Scanner input=new Scanner(new File("input.txt"));
		Random rand=new Random();
		//This array keeps 16 move coordinates
		String[] move=new String[16];
		System.out.println("Do you want to play a new game(N) or continue an ongoing game(O)?");
		//This if statement fills board from input.txt
		if(console.nextLine().equalsIgnoreCase("o")){
			for(int i=0;i<6;i++){
				for(int j=0;j<6;j++){
					board[i][j]=(input.next()).charAt(0);
				}
			}
		}
		//This else statement fills the board with B
		else{
			for(int i=0;i<6;i++){
				for(int j=0;j<6;j++){
					board[i][j]='B';
				}
			}
		}
		print(board);
		System.out.println("To make a change enter C after that enter coordinate; to make a move enter M after that enter coordinate."
				+ "For example \"C 0 0\" or \"M 0 0 0 5\"");
		System.out.println("Enter a coordinate");
		//i is used for playing turns
		int i=0;
		while(i<2){
			//when i==0 user plays
			if(i==0){
				//Action is refers to coordinate
				String action=console.nextLine();
				//This if statement responsible for doing change and move actions
				if(action.charAt(0)=='C'||action.charAt(0)=='c'){
					if(change(action,'X',board)){
						print(board);
						i++;
					}else{
						System.out.println("Wrong coordinate.Enter a new coordinate again.");
					}
					if(win(board)){
						i=2;
					}
				}
				else if(action.charAt(0)=='M'||action.charAt(0)=='m'){
					if(move(action,board,'X')){
						print(board);
						i++;
					}
					else{
						System.out.println("Wrong coordinate.Enter a new coordinate again.");
					}
					if(win(board)){
						i=2;
					}
				}
				else{
					System.out.println("Wrong coordinate.Enter a new coordinate again.");
				}
			}

			//computers turn
			else{
				int action=rand.nextInt(2);
				//if action is 0 program will change the one characters. Otherwise it will move the one "O" character 
				if(action==0){
					int coordinate1=rand.nextInt(6);
					String coordinate="";
					int coordinate2=0;
					if(coordinate1==0||coordinate1==5){
						coordinate2=rand.nextInt(6);
						coordinate="  "+coordinate1 +" "+coordinate2;
					}
					else{
						int z=rand.nextInt(2);
						if(z==0){
							coordinate2=0;
							coordinate="  "+coordinate1 +" "+coordinate2;
						}
						else{
							coordinate2=5;
							coordinate="  "+coordinate1 +" "+coordinate2;
						}
					}
					
					if(change(coordinate,'O',board)){
						System.out.println("C"+coordinate+"\n");
						print(board);
						i--;
					}

					if(win(board)){
						i=2;
					}
				}
				// it will move the one "O" character 
				else if(action==1){
					String coordinate="";
					int coordinate11=rand.nextInt(6);
					int coordinate12=rand.nextInt(2);
					int coordinate21=0;
					int coordinate22=0;
					if(coordinate12==0){
						coordinate12=0;
					}else{
						coordinate12=5;
					}

					if((coordinate11==0&&coordinate12==0)||(coordinate11==5&&coordinate12==5)){
						coordinate21=rand.nextInt(2);
						if(coordinate21==0){
							coordinate22=5;
							coordinate="  "+coordinate11+" "+coordinate12+" "+coordinate21+" "+coordinate22;
						}else{
							coordinate21=5;
							coordinate22=0;
							coordinate="  "+coordinate11+" "+coordinate12+" "+coordinate21+" "+coordinate22;
						}
					}else if((coordinate11==0&&coordinate12==5)||(coordinate11==5&&coordinate12==0)){
						coordinate21=rand.nextInt(2);
						if(coordinate21==0){
							coordinate22=0;
							coordinate="  "+coordinate11+" "+coordinate12+" "+coordinate21+" "+coordinate22;
						}else{
							coordinate21=5;
							coordinate22=5;
							coordinate="  "+coordinate11+" "+coordinate12+" "+coordinate21+" "+coordinate22;
						}
					}else{
						move[0]="  1 0 1 5";
						move[1]="  1 5 1 0";
						move[2]="  2 0 2 5";
						move[3]="  2 5 2 0";
						move[4]="  3 0 3 5";
						move[5]="  3 5 3 0";
						move[6]="  4 0 4 5";
						move[7]="  4 5 4 0";
						move[8]="  0 1 5 1";
						move[9]="  5 1 0 1";
						move[10]="  0 2 5 2";
						move[11]="  5 2 0 2";
						move[12]="  0 3 5 3";
						move[13]="  5 3 0 3";
						move[14]="  0 4 5 4";
						move[15]="  5 4 0 4";
						int h=rand.nextInt(16);
						coordinate=move[h];
					}					
					if(move(coordinate,board,'O')){
						System.out.println("M"+coordinate+"\n");
						print(board);
						i--;
					}
					if(win(board)){
						i=2;
					}
				}
			}
		}
	}
	//This method is used for making change and returns boolean.if coordinate is not on the outer square it returns false
	public static boolean change(String coordinate,char a,char[][] board){
		int row=coordinate.charAt(2)-'0';
		int column=coordinate.charAt(4)-'0';
		if(((row==0||row==5)||(row>0&&row<5&&(column==0||column==5)))&&(board[row][column]=='B')){
			board[row][column]=a;
			return true;
		}
		else{
			return false;

		}
	}
	//This method used for making move and returns boolean.if coordinate is not suitable it returns false
	public static boolean move(String coordinate,char[][] board,char k){
		int row1=coordinate.charAt(2)-'0';
		int column1=coordinate.charAt(4)-'0';
		int row2=coordinate.charAt(6)-'0';
		int column2=coordinate.charAt(8)-'0';
		if((row1==0||row1==5||(row1>0&&row1<5&&(column1==0||column1==5)))
				&&(row2==0||row2==5||(row2>0&&row2<5&&(column2==0||column2==5)))&&(board[row1][column1]==k)){

			if(row1==row2){
				char a=board[row1][column1];
				for(int i=0;i<5;i++){
					board[row1][(column1+i)%6]=board[row1][(column1+i+1)%6];
				}
				board[row2][column2]=a;
			}
			else if(column1==column2){
				char a=board[row1][column1];
				for(int i=0;i<5;i++){
					board[(row1+i)%6][column1]=board[(row1+i+1)%6][column1];
				}
				board[row2][column2]=a;	

			}
			return true;
		}
		else{
			return false;
		}
	}

	//This method used for printing board
	public static void print(char[][] board){
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}
	//This method is used to determining winner and returns boolean. when game was finished it returns true
	public static boolean win(char[][] board){
		int i=0;
		while(i<6){
			int count1=0;
			int count2=0;
			for(int j=0;j<5;j++){
				if(board[i][j]==board[i][j+1]){
					count1++;
				}
				if(board[j][i]==board[j+1][i]){
					count2++;
				}

				if((count1==5&&board[i][j]!='B')||(count2==5&&board[j][i]!='B')){
					System.out.print("player "+board[i][j]+" win");
					i=6;
					return true;
				}
			}
			i++;
		}
		return false;
	}
}
