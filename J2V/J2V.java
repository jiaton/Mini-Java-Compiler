package J2V;

import J2V.Dependencies.MiniJavaParser;
import J2V.Dependencies.ParseException;
import syntaxtree.Node;

import java.io.FileNotFoundException;

public class J2V {
	public static void main(String[] args) throws FileNotFoundException {
//		try {
//			Node goal = new J2V.Dependencies.MiniJavaParser(System.in).Goal();
//
//			goal.accept(new MyVisitor());
//			System.out.println("Program type checked successfully");
//		} catch (J2V.Dependencies.ParseException e) {
//			e.printStackTrace();
//		}
		//InputStream in = null;
		//in = new FileInputStream("prog.g");
		try {

			Node goal = new MiniJavaParser(System.in).Goal();

			goal.accept(new transVisitor());
			//System.out.println("Program type checked successfully");
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
}
