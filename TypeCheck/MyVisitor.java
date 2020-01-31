package TypeCheck;

import syntaxtree.*;
import visitor.*;

import java.util.HashMap;
import java.util.Stack;

import static java.lang.System.exit;

//import TypeCheck.*;
public class MyVisitor extends GJNoArguDepthFirst<MyType> {
	public Stack<Env> envStack;
	public HashMap<String, Env> envTable;
	public HashMap<String, String> typeTable;
	public int MAXCLASSNUM = 1024;
	public void initialize(){
		typeTable.put("int",null);
		typeTable.put("boolean",null);
		typeTable.put("int[]",null);
	}

	// TODO: 1/30/2020 check tostring
	// TODO: 1/30/2020 class.method

	public boolean isChild(MyType child, MyType parent){
		String s = null;
		String middle = child.toString();
		String[] str = new String[MAXCLASSNUM];
		int i=0;
		do{
			if( (s = typeTable.get(middle)) == null)
				break;
			str[i] = s;
			i++;
			middle = s;
			if(i>MAXCLASSNUM){
				System.out.println("In isChild, it loops for too many times!");
				exit(-1);
			}
		}while(true);
		while(i>=0){
			if(str[i].equals(parent.toString()))
				return true;
			i--;
		}
		return false;
	}

	public MyType checkIdentifier(Identifier n) {                    //check whether the identifier n is in the env
		MyType _ret = null;
		if (envStack.empty()) return null;
		Env A = envStack.pop();
		if (A.isMethod) {
			Env C = envStack.peek();
			if (C.getVariableType(n) != null) {
				envStack.push(A);
				_ret = C.getVariableType(n);
				return _ret;
			}
		}
		envStack.push(A);
		if (A.getVariableType(n) != null) {
			_ret = A.getVariableType(n);
			return _ret;
		}
		return null;
	}

	public void addParameter(FormalParameter n) {            //add parameter to method env
		if (envStack.empty()) {
			System.out.println("When adding the Parameter, the stack is empty");
			exit(-1);
		}
		Env A = envStack.peek();
		A.add(n);
	}


	public void addVar(Type f0, Identifier f1) {                //add varible to env
		if (envStack.empty()) {
			System.out.println("At addVar, the stack is empty");
			exit(-1);
		}
		Env A = envStack.peek();
		A.addVar(f0, f1);
	}

	public void fieldExtends(String id, String idM) {            //put parent class fields to child
		Env childclass = envTable.get(id);
		Env parentclass = envTable.get(idM);
		for (HashMap.Entry<String, MyType> entry : parentclass.varTable.entrySet()) {
			if (childclass.varTable.containsKey(entry.getKey())) {
				System.out.println("When extending the class, has the same field");
				exit(-1);        //Env.has(string(id))
			}
			childclass.varTable.entrySet().add(entry);
		}
	}

	public void MethodExtends(String id, String idM) {                    //put parent class methods to child
		Env childclass = envTable.get(id);
		Env parentclass = envTable.get(idM);
		for (HashMap.Entry<String, MethodType> entry : parentclass.methodTable.entrySet()) {
			if (childclass.methodTable.containsKey(entry.getKey())) {
				if (!(childclass.methodTable.get(entry.getKey()).equals(entry.getValue()))) {                // TODO: 1/27/2020 methodtype == methodtype
					System.out.println("When extending the class, method overloading!");
					exit(-1);        //Env.has(string(id))
				}
			} else
				childclass.methodTable.entrySet().add(entry);
		}
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "="
	 * f2 -> Expression()
	 * f3 -> ";"
	 */
	public MyType visit(AssignmentStatement n) {                                //check assignmentStatement
		MyType _ret = null;
		MyType IdentifierType = n.f0.accept(this);
		n.f1.accept(this);
		MyType ExpressionType = n.f2.accept(this);                        // TODO: 1/27/2020 check
		n.f3.accept(this);
		if (IdentifierType == null) {
			System.out.println("At an assignmentstatement the identifier is null!");
			exit(-1);
		}
		if (isChild(IdentifierType, ExpressionType)) {
			System.out.println("At an assignmentstatement the identifiertype >= expressiontype!");
			exit(-1);                            // TODO: 1/27/2020 MyType < MyType
		}
		_ret = IdentifierType;
		return _ret;
	}

	/**
	 * f0 -> <IDENTIFIER>
	 */
	public MyType visit(Identifier n) {
		MyType _ret = null;
		n.f0.accept(this);
		_ret = checkIdentifier(n);
		return _ret;
	}

	public MyType visit(Expression n) {
		MyType _ret = n.f0.accept(this);
		return _ret;
	}

	/**
	 * f0 -> Identifier()
	 * f1 -> "["
	 * f2 -> Expression()
	 * f3 -> "]"
	 * f4 -> "="
	 * f5 -> Expression()
	 * f6 -> ";"
	 */
	public MyType visit(ArrayAssignmentStatement n) {                    //check array assignment statement
		MyType _ret = null;
		MyType IdentifierType = n.f0.accept(this);
		n.f1.accept(this);
		MyType ExpressionType1 = n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		MyType ExpressionType2 = n.f5.accept(this);
		n.f6.accept(this);
		if (IdentifierType == null || IdentifierType.toString() != "int[]") {
			System.out.println("At an arrayassignmentstatement, the identifiertype is null or is not int[]");
			exit(-1);
		}
		if (ExpressionType1.toString() != "int") {
			System.out.println("At an arrayassignmentstatement, the Expressiontype1 is not int");
			exit(-1);
		}
		if (ExpressionType2.toString() != "int") {
			System.out.println("At an arrayassignmentstatement, the Expressiontype2 is not int");
			exit(-1);
		}
		return _ret;
	}


	/**
	 * f0 -> "if"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 * f5 -> "else"
	 * f6 -> Statement()
	 */
	public MyType visit(IfStatement n) {                        //check if statement
		MyType _ret = null;
		n.f0.accept(this);
		n.f1.accept(this);
		MyType ExpressionType = n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		if (ExpressionType.toString() != "boolean") {
			System.out.println("At an if statement, the type of the expression is not a boolean");
			exit(-1);
		}
		return _ret;
	}

	/**
	 * f0 -> "while"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> Statement()
	 */
	public MyType visit(WhileStatement n) {                //check while statement
		MyType _ret = null;
		n.f0.accept(this);
		n.f1.accept(this);
		MyType ExpressionType = n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		if (ExpressionType.toString() != "boolean") {
			System.out.println("At a while statement, the expression type is not boolean!");
			exit(-1);
		}
		return _ret;
	}

	/**
	 * f0 -> "System.out.println"
	 * f1 -> "("
	 * f2 -> Expression()
	 * f3 -> ")"
	 * f4 -> ";"
	 */
	public MyType visit(PrintStatement n) {                            //check print statement
		MyType _ret = null;
		n.f0.accept(this);
		n.f1.accept(this);
		MyType ExpressionType = n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		if (ExpressionType.toString() != "int") {
			System.out.println("At a printstatement the type of the expression is not an int");
			exit(-1);
		}
		return _ret;
	}

	//METHODDELARATION

	/**
	 * f0 -> "public"
	 * f1 -> Type()
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( FormalParameterList() )?
	 * f5 -> ")"
	 * f6 -> "{"
	 * f7 -> ( VarDeclaration() )*
	 * f8 -> ( Statement() )*
	 * f9 -> "return"
	 * f10 -> Expression()
	 * f11 -> ";"
	 * f12 -> "}"
	 */
	public MyType visit(MethodDeclaration n) {                                //check method declaration
		MyType _ret = null;
		Env classenv = envStack.peek();
		if(classenv.isMethod){
			System.out.println("Define method outside the class!");
			exit(-1);
		}
		String methodid = classenv.id+"."+n.f2.f0.toString();
		if (envTable.containsKey(methodid)) {
			System.out.println("At the methodDeclaration, the method has been existed!");
			exit(-1);
		}
		Env env = new Env(methodid, true, envStack.peek().id);        //Env(id, isMethod, classofmethod(or superclass))
		envTable.put(env.id, env);
		envStack.push(env);
		n.f0.accept(this);
		MyType ReturnType = MyType.toMyType(n.f1);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		//FormalParameterList ParameterList = n.f4;	need to be done?
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		//VarDeclaration* DeclarationList = n.f7;
		n.f7.accept(this);                                //VarDeclaration visit need to be set
		n.f8.accept(this);                                //Statement visit need to be set
		n.f9.accept(this);
		MyType ExpressionType = n.f10.accept(this);
		n.f11.accept(this);
		n.f12.accept(this);
		//if (!typeTable.containsKey(n.f1.toString())) {                        // TODO: 1/27/2020 check type()
		//	System.out.println("At the methoddeclaration, the return type is not avaliable");
		//	exit(-1);
		//}
		//if(!Distinct(ParameterList))	exit(-1);
		//if(!Distinct(DeclarationList))	exit(-1);
		if (ReturnType != ExpressionType) {
			System.out.println("At the methoddeclaration, the return type is not the return type");
			exit(-1);
		}
		if (envStack.pop().id != methodid) {
			System.out.println("At the methoddeclaration,the method is not in the stack!");
			exit(-1);
		}
		return _ret;
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 */
	public MyType visit(FormalParameter n) {
		MyType _ret = null;
		n.f0.accept(this);
		MyType IdentifierType = n.f1.accept(this);
		if (!typeTable.containsKey(MyType.toMyType(n.f0).toString())) {
			System.out.println("At the FormalParameter, the type is not avaliable!");
			exit(-1);
		}
		if (IdentifierType != null) {
			System.out.println("At the FormalParameter, identifiertype is token!");
			exit(-1);
		}
		addParameter(n);
		return _ret;
	}

	/**
	 * f0 -> Type()
	 * f1 -> Identifier()
	 * f2 -> ";"
	 */
	public MyType visit(VarDeclaration n) {
		MyType _ret = null;
		n.f0.accept(this);
		MyType IdentifierType = n.f1.accept(this);
		n.f2.accept(this);
		if (!typeTable.containsKey(MyType.toMyType(n.f0).toString())) {
			System.out.println("At the VarDeclaration, the type is not avaliable!");
			exit(-1);
		}
		if (IdentifierType != null) {
			System.out.println("At the VarDeclaration, the id is token!");
			exit(-1);
		}
		addVar(n.f0, n.f1);
		return _ret;
	}

	//TYPE DECLARATIONS

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> ( VarDeclaration() )*
	 * f4 -> ( MethodDeclaration() )*
	 * f5 -> "}"
	 */
	public MyType visit(ClassDeclaration n) {
		MyType _ret = null;
		if (typeTable.containsKey(n.f1.f0.toString())) {
			System.out.println("At classdeclaration, the id is already token!");
			exit(-1);
		}
		typeTable.put(n.f1.f0.toString(), null);
		n.f0.accept(this);
		MyType Identifier = n.f1.accept(this);
		if (Identifier != null) {
			System.out.println("At classdeclaration, something wrong with id");
			exit(-1);
		}
		Env env = new Env(n.f1.f0.toString(), false, null);    //Env(id, isMethod)
		envTable.put(env.id, env);
		envStack.push(env);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		return _ret;
	}

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "extends"
	 * f3 -> Identifier()
	 * f4 -> "{"
	 * f5 -> ( VarDeclaration() )*
	 * f6 -> ( MethodDeclaration() )*
	 * f7 -> "}"
	 */
	public MyType visit(ClassExtendsDeclaration n) {
		MyType _ret = null;
		if (typeTable.containsKey(n.f1.f0.toString())) {
			System.out.println("At classdeclaration (extends), the id is already token!");
			exit(-1);
		}
		typeTable.put(n.f1.f0.toString(), n.f3.f0.toString());                    // TODO: 1/27/2020 check mytype
		n.f0.accept(this);
		MyType Identifier = n.f1.accept(this);
		if (Identifier != null) {
			System.out.println("At classdeclaration (extends), the id is not null!");
			exit(-1);
		}
		Env env = new Env(n.f1.f0.toString(), false, n.f3.f0.toString());    //Env(id, idM)
		envTable.put(env.id, env);
		envStack.push(env);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		fieldExtends(n.f1.f0.toString(), n.f3.f0.toString());                    //VarExtends(id, idM)
		n.f6.accept(this);
		MethodExtends(n.f1.f0.toString(), n.f3.f0.toString());                //MethodExtends(id, idM)
		n.f7.accept(this);
		return _ret;
	}

	//MAIN CLASS

	/**
	 * f0 -> "class"
	 * f1 -> Identifier()
	 * f2 -> "{"
	 * f3 -> "public"
	 * f4 -> "static"
	 * f5 -> "void"
	 * f6 -> "main"
	 * f7 -> "("
	 * f8 -> "String"
	 * f9 -> "["
	 * f10 -> "]"
	 * f11 -> Identifier()
	 * f12 -> ")"
	 * f13 -> "{"
	 * f14 -> ( VarDeclaration() )*
	 * f15 -> ( Statement() )*
	 * f16 -> "}"
	 * f17 -> "}"
	 */
	public MyType visit(MainClass n) {
		MyType _ret = null;
		if (typeTable.containsKey(n.f1.f0.toString())) {
			System.out.println("At mainclass, the id is used!");
			exit(-1);
		}
		typeTable.put(n.f1.f0.toString(), null);
		n.f0.accept(this);
		MyType Identifier = n.f1.accept(this);
		if (Identifier != null) {
			System.out.println("At mainclass, the id is token!");
			exit(-1);
		}
		Env env = new Env(n.f1.f0.toString(), false, n.f1.f0.toString());
		envTable.put(env.id, env);
		envStack.push(env);
		n.f2.accept(this);
		n.f3.accept(this);
		n.f4.accept(this);
		n.f5.accept(this);
		n.f6.accept(this);
		n.f7.accept(this);
		n.f8.accept(this);
		n.f9.accept(this);
		n.f10.accept(this);
		MyType idS = n.f11.accept(this);
		if (idS != null) {
			System.out.println("At mainclass, the ids is token!");
			exit(-1);
		}
		n.f12.accept(this);
		n.f13.accept(this);
		n.f14.accept(this);
		n.f15.accept(this);
		n.f16.accept(this);
		n.f17.accept(this);
		return _ret;
	}

	//GOAL

	/**
	 * f0 -> MainClass()
	 * f1 -> ( TypeDeclaration() )*
	 * f2 -> <EOF>
	 */
	public MyType visit(Goal n) {
		initialize();
		MyType _ret = null;
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		return _ret;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "&&"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(AndExpression n) {
		MyType t1 = n.f0.accept(this);
		MyType t2 = n.f2.accept(this);
		n.f1.accept(this);
		if (t1.equals(t2) && t1.equals(new MyType("boolean"))) {
			return (new MyType("boolean"));
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "<"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(CompareExpression n) {
		MyType t1 = n.f0.accept(this);
		MyType t2 = n.f2.accept(this);
		n.f1.accept(this);
		if (t1.equals(t2) && t2.equals(new MyType("int"))) {
			return new MyType("boolean");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "+"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(PlusExpression n) {
		MyType t1 = n.f0.accept(this);
		n.f1.accept(this);
		MyType t2 = n.f2.accept(this);
		if (t1.equals(t2) && t2.equals(new MyType("int"))) {
			return new MyType("int");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;

	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "-"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(MinusExpression n) {
		MyType t1 = n.f0.accept(this);
		n.f1.accept(this);
		MyType t2 = n.f2.accept(this);
		if (t1.equals(t2) && t2.equals(new MyType("int"))) {
			return new MyType("int");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "*"
	 * f2 -> PrimaryExpression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(TimesExpression n) {
		MyType t1 = n.f0.accept(this);
		n.f1.accept(this);
		MyType t2 = n.f2.accept(this);
		if (t1.equals(t2) && t2.equals(new MyType("int"))) {
			return new MyType("int");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "["
	 * f2 -> PrimaryExpression()
	 * f3 -> "]"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ArrayLookup n) {
		n.f1.accept(this);
		n.f3.accept(this);
		MyType t1 = n.f0.accept(this);
		MyType t2 = n.f2.accept(this);
		if (t1.equals(new MyType("int[]")) && t2.equals(new MyType("int"))) {
			return new MyType("int");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> "length"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ArrayLength n) {

		n.f1.accept(this);
		n.f2.accept(this);
		MyType t1 = n.f0.accept(this);
		if (t1.equals(new MyType("int[]"))) {
			return new MyType("int");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}
	// TODO: 2020/1/30 method call

	/**
	 * f0 -> PrimaryExpression()
	 * f1 -> "."
	 * f2 -> Identifier()
	 * f3 -> "("
	 * f4 -> ( ExpressionList() )?
	 * f5 -> ")"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(MessageSend n) {
		MyType theClass = n.f0.accept(this);
		n.f1.accept(this);
		MyType id = n.f2.accept(this); //get identifier's id
		n.f3.accept(this);
		n.f4.accept(this); //get parameters list
		n.f5.accept(this);
		//get method type
//		MethodType methodType = getMethodType(theClass, id);
		//check each parameter whether it's subtype of required typek
//		return new MethodType(construction);
		return null;
	}

	// TODO: 2020/1/30 constant check

	/**
	 * f0 -> "true"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(TrueLiteral n) {
		MyType t = n.f0.accept(this);
		if (t.equals(new MyType("boolean"))) {
			return new MyType("boolean");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> "false"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(FalseLiteral n) {
		MyType t = n.f0.accept(this);
		if (t.equals(new MyType("boolean"))) {
			return new MyType("boolean");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;
	}

	/**
	 * f0 -> "this"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ThisExpression n) {
		n.f0.accept(this);
		Env env = envStack.peek();
		if (env != null) {
			// TODO: 2020/1/30 return value?
			return null;//return the class name?
		}
	}

	/**
	 * f0 -> "new"
	 * f1 -> "int"
	 * f2 -> "["
	 * f3 -> Expression()
	 * f4 -> "]"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(ArrayAllocationExpression n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		MyType t1 = n.f3.accept(this);
		n.f4.accept(this);
		if (t1.equals(new MyType("int"))) {
			return new MyType("int[]");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;

	}

	/**
	 * f0 -> "new"
	 * f1 -> Identifier()
	 * f2 -> "("
	 * f3 -> ")"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(AllocationExpression n) {
		n.f0.accept(this);
		n.f1.accept(this);
		n.f2.accept(this);
		n.f3.accept(this);
		return new MyType(n.f1.f0.tokenImage);

	}

	/**
	 * f0 -> "!"
	 * f1 -> Expression()
	 *
	 * @param n
	 */
	@Override
	public MyType visit(NotExpression n) {
		n.f0.accept(this);
		MyType t1 = n.f1.accept(this);
		if (t1.equals(new MyType("boolean"))) {
			return new MyType("boolean");
		} else {
			System.out.println("Method: " + this.getClass().getName() + " error");
			System.exit(-1);
		}
		return null;

	}

	/**
	 * f0 -> "("
	 * f1 -> Expression()
	 * f2 -> ")"
	 *
	 * @param n
	 */
	@Override
	public MyType visit(BracketExpression n) {
		n.f0.accept(this);
		MyType t1 = n.f1.accept(this);
		n.f2.accept(this);
		return t1;
	}
}
