import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTacToe implements ActionListener {
	
	public static final Random rand = new Random();
	
	private JFrame frame;
	private Tile[][] tiles;
	private char[][] board;
	
	private char firstPlayer;
	private char currentTurn;
	private char player1;
	private char player2;
	
	private JButton startGame;
	private JButton resetGame;
		
	private JustAnotherTicTacToeAI jatttai;
	
	public TicTacToe() {
		frame = new JFrame("Ang Ganda Ni Ma'am Kat TicTacToe Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		
		JPanel tilePanel = new JPanel(new GridLayout(3, 3, 1, 1));
		tilePanel.setPreferredSize(new Dimension(400, 400));
		board = new char[][] {
			{ ' ', ' ', ' ' },
			{ ' ', ' ', ' ' },
			{ ' ', ' ', ' ' }
		};
		tiles = new Tile[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j] = new Tile(i, j);
				tiles[i][j].setText(String.valueOf(board[i][j]));
				tiles[i][j].addActionListener(this);
				tiles[i][j].setEnabled(false);
				tilePanel.add(tiles[i][j]);
			}
		}
		c.add(tilePanel, BorderLayout.CENTER);
		
		JPanel topButtonPanel = new JPanel();
		startGame = new JButton("Start");
		startGame.setActionCommand("start");
		startGame.addActionListener(this);
		topButtonPanel.add(startGame);
		c.add(topButtonPanel, BorderLayout.NORTH);
		
		JPanel lowerButtonPanel = new JPanel();
		resetGame = new JButton("Reset");
		resetGame.setActionCommand("reset");
		resetGame.addActionListener(this);
		resetGame.setEnabled(false);
		lowerButtonPanel.add(resetGame);
		c.add(lowerButtonPanel, BorderLayout.SOUTH);
		
		player1 = 'X';
		player2 = 'O';
		
		jatttai = new JustAnotherTicTacToeAI(player2, player1);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void start() {
		// reset game before starting
		int first = JOptionPane.showConfirmDialog (frame, "Would you like to go first?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		firstPlayer = currentTurn = first == JOptionPane.YES_OPTION ? player1 : player2;		
		reset();
		
		if(currentTurn == player2) {
			move(rand.nextInt(3), rand.nextInt(3));
		}
	}
	
	public void update() {
		// update screen
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				tiles[i][j].setText(String.valueOf(board[i][j]));
			}
		}
	}
		
	public void move(int x, int y) {
		board[x][y] = currentTurn;
		tiles[x][y].setEnabled(false);
		tiles[x][y].setText(String.valueOf(currentTurn));
		currentTurn = currentTurn == player1 ? player2 : player1;
	}
	
	private char evaluate() {
		// horizontals
		if(board[0][0] != ' ' && board[0][1] != ' ' && board[0][2] != ' ') {
			if(board[0][0] ==  board[0][1] && board[0][1] == board[0][2]) {
				return board[0][0];
			}
		}
		
		if(board[1][0] != ' ' && board[1][1] != ' ' && board[1][2] != ' ') {
			if(board[1][0] ==  board[1][1] && board[1][1] == board[1][2]) {
				return board[1][0];
			}
		}
		
		if(board[2][0] != ' ' && board[2][1] != ' ' && board[2][2] != ' ') {
			if(board[2][0] ==  board[2][1] && board[2][1] == board[2][2]) {
				return board[2][0];
			}
		}
		
		// verticals
		if(board[0][0] != ' ' && board[1][0] != ' ' && board[2][0] != ' ') {
			if(board[0][0] ==  board[1][0] && board[1][0] == board[2][0]) {
				return board[0][0];
			}
		}
		
		if(board[0][1] != ' ' && board[1][1] != ' ' && board[2][1] != ' ') {
			if(board[0][1] ==  board[1][1] && board[1][1] == board[2][1]) {
				return board[0][1];
			}
		}
		
		if(board[0][2] != ' ' && board[1][2] != ' ' && board[2][2] != ' ') {
			if(board[0][2] ==  board[1][2] && board[1][2] == board[2][2]) {
				return board[0][2];
			}
		}
		
		// diagonals
		if(board[0][0] != ' ' && board[1][1] != ' ' && board[2][2] != ' ') {
			if(board[0][0] ==  board[1][1] && board[1][1] == board[2][2]) {
				return board[0][0];
			}
		}
		
		if(board[0][2] != ' ' && board[1][1] != ' ' && board[2][0] != ' ') {
			if(board[0][2] ==  board[1][1] && board[1][1] == board[2][0]) {
				return board[0][2];
			}
		}
		
		// check for tie
		if(board[0][0] != ' ' && board[0][1] != ' ' && board[0][2] != ' ' &&
           board[1][0] != ' ' && board[1][1] != ' ' && board[1][2] != ' ' &&
		   board[2][0] != ' ' && board[2][1] != ' ' && board[2][2] != ' '
		) {
			return '-';
		}
		
		return ' ';
	}
	
	private void reset() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = ' ';
				tiles[i][j].setText("");
				tiles[i][j].setEnabled(true);
			}
		}
		currentTurn = firstPlayer;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getActionCommand().equals("tile")) {
			char winner;
			Tile src = (Tile) ae.getSource();
			move(src.x, src.y);
			winner = evaluate();
			if(winner != ' ') {
				if(winner == '-') {
					JOptionPane.showMessageDialog(frame, "It's a tie!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Player " + winner + " wins!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
				}
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						tiles[i][j].setEnabled(false);
					}
				}
				startGame.setEnabled(true);
				resetGame.setEnabled(false);
				return;
			}
			
			Point move = jatttai.getBestMove(board);
			move(move.x, move.y);
			winner = evaluate();
			if(winner != ' ') {
				if(winner == '-') {
					JOptionPane.showMessageDialog(frame, "It's a tie!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(frame, "Player " + winner + " wins!", "Game Over!", JOptionPane.PLAIN_MESSAGE);
				}
				for(int i = 0; i < 3; i++) {
					for(int j = 0; j < 3; j++) {
						tiles[i][j].setEnabled(false);
					}
				}
				startGame.setEnabled(true);
				resetGame.setEnabled(false);
			}
		} else if(ae.getActionCommand().equals("reset")) {
			reset();
			currentTurn = firstPlayer;
			if(currentTurn == player2) move(rand.nextInt(3), rand.nextInt(3));
		} else if(ae.getActionCommand().equals("start")) {
			startGame.setEnabled(false);
			resetGame.setEnabled(true);
			start();
		}
	}
	
}