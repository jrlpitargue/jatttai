import java.awt.Point;

import java.util.ArrayList;

public class JustAnotherTicTacToeAI {
    
    private char symbol;
    private char opponent;
    
    private final static int CUTOFF = 2;
    
    public JustAnotherTicTacToeAI(char symbol, char opponent) {
        this.symbol = symbol;
        this.opponent = opponent;
    }
    
    public Point getBestMove(char[][] initial) {
        ArrayList<Point> moves = generatePossibleActions(initial);
        int[] scores = new int[moves.size()];
        
        //printBoard(initial);
        
        for(int i = 0; i < scores.length; i++) {
            //printBoard(result(initial, moves.get(i), this.symbol));
            scores[i] = minimaxAlphaBetaPruning(result(initial, moves.get(i), this.symbol), 1, Integer.MIN_VALUE, Integer.MAX_VALUE, this.opponent);
            //System.out.println("MOVE: " + moves.get(i).x + " " + moves.get(i).y + " (" + scores[i] + ")");
        }
        
        int max = Integer.MIN_VALUE, best = 0;
        for(int i = 0; i < scores.length; i++) {
            if(scores[i] > max) {
                max = scores[i];
                best = i;
            }
        }
        
        //System.out.println("BEST MOVE: " + moves.get(best).x + " " + moves.get(best).y);
        return moves.get(best);
    }
    
    public ArrayList<Point> generatePossibleActions(char[][] board) {
        ArrayList<Point> actions = new ArrayList<Point>();
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i][j] == ' ') {
                    actions.add(new Point(i, j));
                }
            }
        }
        
        return actions;
    }
    
    public void printBoard(char[][] board) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                System.out.print("[ " + board[i][j] + " ] ");
            }
            System.out.println();
        }
    }
    
    public char[][] result(char[][] state, Point action, char player) {
        char[][] newState = new char[3][3];
        for(int i = 0; i < 3; i++) {
            System.arraycopy(state[i], 0, newState[i], 0, state[i].length);
        }
        
        newState[action.x][action.y] = player;
        
        return newState;
    }
    
    public int utility(char[][] state, int depth) {
        char winner = evaluateBoard(state);
        
        return winner == '-' ? depth : winner == this.symbol ? 10 - depth  : depth - 10;
    }
    
    public int minimaxAlphaBetaPruning(char[][] initial, int depth, int alpha, int beta, char player) {
        int best, child;
        char status = evaluateBoard(initial);
        if(depth == CUTOFF) return estimateValue(initial, player, player == this.symbol ? this.opponent : this.symbol);
        if(status != ' ') {
            //printBoard(initial);
            return utility(initial, depth);
        }
        
        if(player == this.symbol) {
            best = alpha;
            for(Point action : generatePossibleActions(initial)) {
                best = Math.max(best, minimaxAlphaBetaPruning(result(initial, action, this.symbol), depth+1, alpha, beta, this.opponent));
                if(best > beta) {
                    return best;
                }
                alpha = Math.max(alpha, best);
            }
            return best;
        } else {
            best = beta;
            for(Point action : generatePossibleActions(initial)) {
                best = Math.min(best, minimaxAlphaBetaPruning(result(initial, action, this.opponent), depth+1, alpha, beta, this.symbol));
                if(best < alpha) {
                    return best;
                }
                beta = Math.min(beta, best);
            }
            return best;
        }
    }
    
    public char evaluateBoard(char[][] board) {
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
    
    private int estimateValue(char[][] board, char player, char opponent) {
        int score = 0;
        
        for(int i = 0; i < 3; i++) {
            score += lineScore(board[i], player, opponent);
            score += lineScore(new char[] { board[0][i], board[1][i], board[2][i]}, player, opponent);
        }
        
        score += lineScore(new char[] { board[0][0], board[1][1], board[2][2]}, player, opponent);
        score += lineScore(new char[] { board[0][2], board[1][1], board[2][0]}, player, opponent);
        
        return score;
    }
    
    private int lineScore(char[] line, char player, char opponent) {
        int score = 0, playerCount = 0, opponentCount = 0;
        
        for(int i = 0; i < 3; i++) {
            if(line[i] == player) {
                playerCount++;
            } else if(line[i] == opponent) {
                opponentCount++;
            }
        }
        
        if(playerCount == 3) {
            score = 10;
        } else if(opponentCount == 3) {
            score = -10;
        } else if((playerCount == 2 || playerCount == 1) && opponentCount == 0) {
            score = playerCount == 2 ? 5 : 1;
        } else if((opponentCount == 2 || opponentCount == 1) && playerCount == 0) {
            score = opponentCount == 2 ? -5 : -1;
        }
        
        return score;
    }
    
}