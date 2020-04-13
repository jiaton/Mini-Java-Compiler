package TypeCheck;

import TypeCheck.Dependencies.MiniJavaParser;
import TypeCheck.Dependencies.ParseException;
import syntaxtree.Node;

public class Typecheck {
	public static void main(String[] args) {
		try {
			Node goal = new MiniJavaParser(System.in).Goal();

			goal.accept(new MyVisitor());
			System.out.println("Program type checked successfully");
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}
