package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

	// É o principal do sistema, onde tem as regras.
	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check; // Começa com false por padrão
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	// O certo seria ChessPiece, mas como nn faz tanta diferencia pra faciliar
	// deixou Piece
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable(){
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;	
	}

	// Esse método vai retornar uma matriz de peças para a partida
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getColumns()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				// Pra cada peça ele ta pegando a Piece e transformando em ChessPiece
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}

		return mat;
	}

	// Isso aqui ta pegando os trues do array e ta transformando para poder imprimir
	// dps
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);

		return board.piece(position).possibleMoves();
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); // Ta convertendo pra posição da matriz.
		Position target = targetPosition.toPosition();

		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);

		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You can't put yourself on check.");
		}

		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		
		//SPECIAL MOVE PROMOTION
		promoted = null;
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		// Se o teste de check do seu oponente voltar como true a variavel Check vira
		// true, se não continua false.
		check = (testCheck(opponent(currentPlayer))) ? true : false;

		// Se o teste de checkmate vltar cmo verdadeiro ele não deixa ir pro próximo
		// tunro
		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}
		
		//#SPECIAL MOVE ENPASSAN
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow()-2 || target.getRow() == source.getRow()+2)) {
			enPassantVulnerable = movedPiece;
		} else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece) capturedPiece;
	}

	public ChessPiece replacePromotedPiece(String type) {
		if (promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")) {
			return promoted;
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}

	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board, color);
		if (type.equals("N")) return new Knight(board, color);
		if (type.equals("Q")) return new Queen(board, color);
		return new Rook(board, color);
	}
	
	private Piece makeMove(Position source, Position target) {
		ChessPiece p = (ChessPiece) board.removePiece(source); // Vai remover a peça na posição de origem
		// Fez o downcast de cima só pra deixar P acessar o increave movecount.
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); // Se tiver uma peça na posição de destino ela tbm será
															// removida
		board.placePiece(p, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// #MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			// Aqui ta mandando a torre pro lugar do lado do Rei
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// #MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			// Aqui ta mandando a torre pro lugar do lado do Rei
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		// #SPECIA MOVE EN PASSAN
		if(p instanceof Pawn) {
			//Se o peão andou na diagonal e nn capturou ngm então foi um enpassan
			if(source.getColumn() != target.getColumn() && capturedPiece == null) {
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					//Se foi uma peça branca, a peça a ser capturada está em baixo dela
					pawnPosition = new Position(target.getRow() +1, target.getColumn());
				} else {
					//Se for preto ela captura a que acima dela.
					pawnPosition = new Position(target.getRow() -1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}

		return capturedPiece;
	}

	// Pra desfazer a jogada caso o jogador cause um auto check
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// #MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			// Aqui ta mandando a torre pro lugar do lado do Rei
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); 
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// #MOVIMENTO ESPECIAL ROQUE PEQUENO
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			// Aqui ta mandando a torre pro lugar do lado do Rei
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// #SPECIA MOVE EN PASSAN
		if(p instanceof Pawn) {
			//Se o peão andou na diagonal e nn capturou ngm então foi um enpassan
			if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)board.removePiece(target);		
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) {
					//Se foi uma peça branca, a peça a ser capturada está em baixo dela
					pawnPosition = new Position(3, target.getColumn());
				} else {
					//Se for preto ela captura a que acima dela.
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}

	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece in origin position");
		}
		// Se o jogador atual for não for da cor da peça que ele ta tentando mexer da
		// erro
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
			throw new ChessException("The chosen piece is not yours.");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		// Se a pessa tiver com true no alvo.
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		// Se o jogador for brnaco ele vira preto, e vice e versa.
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private Color opponent(Color color) {
		// Retorna a cor oposta a do jogador atual, para saber qm é oponente
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) { // Ta dando downcast aqui pra que x tenha getColor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}

		throw new IllegalStateException("There is no " + color + " King on the board.");
	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

		// Ele vai rodar por todas as peças do oponente (cor oposta a do jogador atual)
		for (Piece p : opponentPieces) {
			// Criou uma matriz para ver os movimentos possíveis de todas da lista.
			boolean[][] mat = p.possibleMoves();
			// Se uma peça tiver um movimento possível como TRUE no quadrado do Rei o check
			// volta como verdadeiro
			if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		// Fazendo lista de todas um dessa cor
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					// A matriz ta cheia de movimentos true or false, que as peças podem ou não
					// fazer
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition(); // Nn da pra pegar a posição
																							// por estr em outro pacote,
																							// então faz assim.
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);

						// Essa lógica ta fazendo o computador testar todos os movimentos possíveis.
						boolean testCheck = testCheck(color);
						// Após testar ele desfaz os movimentos.
						undoMove(source, target, capturedPiece);
						// Se ele achar um movimento que faça o check acabar (falso) então ele não
						// considera checkmate.
						if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		// Se depois de circular a lista INTEIRA de movimentos das peças não tiver
		// nenhuma que tira do checkmate, ele vira true.
		return true;
	}

	// Esse método coloca a peça e 'traduz' a posição do xadrez para a posição da
	// matirz
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this)); // Esse this ta passando a própria partida dessa
																	// calsse
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));

	}

}
