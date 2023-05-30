package lox;

import lox.Expr.Binary;
import lox.Expr.Grouping;
import lox.Expr.Literal;
import lox.Expr.Unary;

public class PrinterPolishNotation implements Expr.Visitor<String> {

	public String print(Expr expr) {
		return expr.accept(this);
	}

	private String rpn(String name, Expr... exprs) {
		StringBuilder builder = new StringBuilder();

		for (Expr expr : exprs) {
			builder.append(expr.accept(this));
			builder.append(" ");
		}

		builder.append(name);

		return builder.toString();
	}

	@Override
	public String visitBinaryExpr(Binary expr) {
		return rpn(expr.operator.lexeme, expr.left, expr.right);
	}

	@Override
	public String visitGroupingExpr(Grouping expr) {
		return rpn("group", expr.expression);
	}

	@Override
	public String visitLiteralExpr(Literal expr) {
		if (expr.value == null) {
			return "nil";
		}
		return expr.value.toString();
	}

	@Override
	public String visitUnaryExpr(Unary expr) {
		return rpn(expr.operator.lexeme, expr.right);
	}

	public static void main(String[] args) {
		var printer = new PrinterPolishNotation();
		Expr expression1 = 
				new Binary(
				new Expr.Binary(
					new Expr.Literal(1),
					new Token(TokenType.PLUS, "+", null, 1), 
					new Expr.Literal(2)
				),
				new Token(TokenType.STAR, "*", null, 1), 
					new Expr.Binary(
						new Expr.Literal(4),
						new Token(TokenType.STAR, "-", null, 1), 
						new Expr.Literal(3)
					)
				);
		
		System.out.println(printer.print(expression1));
	}
}
