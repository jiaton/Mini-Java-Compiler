import syntaxtree.Goal;
import syntaxtree.Node;
import TypeCheck.*;
public class Main {
	public static void main(String[] args) {
		try {
			Node goal = new MiniJavaParser(System.in).Goal();
			System.out.println("visit starting");
			goal.accept(new TypeCheckingVisitor());
		} catch (ParseException e) {
			e.printStackTrace();
		}


	}
}
