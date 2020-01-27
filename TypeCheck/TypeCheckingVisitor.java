package TypeCheck;

import syntaxtree.*;
import visitor.GJNoArguDepthFirst;

import java.util.HashMap;
import java.util.Stack;
public class TypeCheckingVisitor extends GJNoArguDepthFirst<MyType> {

    Stack<Context> contextStack = new Stack<Context>(); // record the current class and method
//    HashMap<Context, HashMap<String, Type>> envTable = new HashMap<Context, HashMap<String, Type>>(); // record the environment of each context
	HashMap<Context, Env> envTable = new HashMap<Context,Env>();

	/*
	* Given context and id, check if it has been declared.
	* */
	boolean checkDeclaration(Context c, String id) {
		Env env = envTable.get(c);
		MyType myType = env.getOrDefault(id,null);
		if (myType == null) {
			return false;
		}
		return true;
	}

	void addType(Context c, String id, MyType type) {
		Env env = envTable.get(c);
		env.put(id,type);
	}
	boolean checkDeclaration =false;

	/*
	* Each time we meet a new context, we must establish a new environment
	* */
	void addNewContext(Context context) {
		contextStack.push(context);
		envTable.put(context,new Env());
	}
    @Override
    public MyType visit(NodeList n) {
        return super.visit(n);
    }

    @Override
    public MyType visit(NodeListOptional n) {
        return super.visit(n);
    }

    @Override
    public MyType visit(NodeOptional n) {
        return super.visit(n);
    }

    @Override
    public MyType visit(NodeSequence n) {
        return super.visit(n);
    }

    @Override
    public MyType visit(NodeToken n) {
        return new MyType(n);
    }

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     *
     * @param n
     */
    @Override
    public MyType visit(Goal n) {
        return super.visit(n);
    }

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
     *
     * @param n
     */
    @Override
    public MyType visit(MainClass n) {
    	addNewContext(new Context(n.f1.f0.toString())); //get the Identifier name, method is null
        return super.visit(n); // traverse and return null
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     *
     * @param n
     */
    @Override
    public MyType visit(TypeDeclaration n) {
        return super.visit(n);
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     *
     * @param n
     */
    @Override
    public MyType visit(ClassDeclaration n) {
        return super.visit(n);
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
     *
     * @param n
     */
    @Override
    public MyType visit(ClassExtendsDeclaration n) {
        return super.visit(n);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     *
     * @param n
     */
    @Override
    public MyType visit(VarDeclaration n) {
        return super.visit(n);
    }

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
     *
     * @param n
     */
    @Override
    public MyType visit(MethodDeclaration n) {
        return super.visit(n);
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     *
     * @param n
     */
    @Override
    public MyType visit(FormalParameterList n) {
        return super.visit(n);
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     *
     * @param n
     */
    @Override
    public MyType visit(FormalParameter n) {
        return super.visit(n);
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     *
     * @param n
     */
    @Override
    public MyType visit(FormalParameterRest n) {
        return super.visit(n);
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     *
     * @param n
     */
    @Override
    public MyType visit(Type n) {
        return super.visit(n);
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     *
     * @param n
     */
    @Override
    public MyType visit(ArrayType n) {
        return super.visit(n);
    }

    /**
     * f0 -> "boolean"
     *
     * @param n
     */
    @Override
    public MyType visit(BooleanType n) {
        return super.visit(n);
    }

    /**
     * f0 -> "int"
     *
     * @param n
     */
    @Override
    public MyType visit(IntegerType n) {
        return super.visit(n);
    }

    /**
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     *
     * @param n
     */
    @Override
    public MyType visit(Statement n) {
        return super.visit(n);
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     *
     * @param n
     */
    @Override
    public MyType visit(Block n) {
        return super.visit(n);
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     *
     * @param n
     */
    @Override
    public MyType visit(AssignmentStatement n) {
        return super.visit(n);
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     *
     * @param n
     */
    @Override
    public MyType visit(ArrayAssignmentStatement n) {
        return super.visit(n);
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     *
     * @param n
     */
    @Override
    public MyType visit(IfStatement n) {
        return super.visit(n);
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     *
     * @param n
     */
    @Override
    public MyType visit(WhileStatement n) {
        return super.visit(n);
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     *
     * @param n
     */
    @Override
    public MyType visit(PrintStatement n) {
        return super.visit(n);
    }

    /**
     * f0 -> AndExpression()
     * | CompareExpression()
     * | PlusExpression()
     * | MinusExpression()
     * | TimesExpression()
     * | ArrayLookup()
     * | ArrayLength()
     * | MessageSend()
     * | PrimaryExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(Expression n) {
        return super.visit(n);
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

        if (t1.equals(t2) && t1.equals(new MyType("boolean"))) {
            return new MyType("boolean");
        } else {
            System.out.println("Method: "+this.getClass().getName()+" error");
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
        return super.visit(n);
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
        return super.visit(n);
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
        return super.visit(n);
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
        return super.visit(n);
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
        return super.visit(n);
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
        return super.visit(n);
    }

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
        return super.visit(n);
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     *
     * @param n
     */
    @Override
    public MyType visit(ExpressionList n) {
        return super.visit(n);
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     *
     * @param n
     */
    @Override
    public MyType visit(ExpressionRest n) {
        return super.visit(n);
    }

    /**
     * f0 -> IntegerLiteral()
     * | TrueLiteral()
     * | FalseLiteral()
     * | Identifier()
     * | ThisExpression()
     * | ArrayAllocationExpression()
     * | AllocationExpression()
     * | NotExpression()
     * | BracketExpression()
     *
     * @param n
     */
    @Override
    public MyType visit(PrimaryExpression n){
        // TODO: 2020/1/25 ¼ÌÐøÖ´ÐÐ
        return super.visit(n);
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     *
     * @param n
     */
    @Override
    public MyType visit(IntegerLiteral n) {
        return super.visit(n);
    }

    /**
     * f0 -> "true"
     *
     * @param n
     * @return MyType
     */
    @Override
    public MyType visit(TrueLiteral n) {

        MyType _ret=null;
        n.f0.accept(this);
        return new MyType(n.f0);

    }

    /**
     * f0 -> "false"
     *
     * @param n
     */
    @Override
    public MyType visit(FalseLiteral n) {
        return super.visit(n);
    }

    /**
     * f0 -> <IDENTIFIER>
     *
     * @param n
     */
    @Override
    public MyType visit(Identifier n) {
        return super.visit(n);
    }

    /**
     * f0 -> "this"
     *
     * @param n
     */
    @Override
    public MyType visit(ThisExpression n) {
        return super.visit(n);
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
        return super.visit(n);
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
        return super.visit(n);
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     *
     * @param n
     */
    @Override
    public MyType visit(NotExpression n) {
        return super.visit(n);
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
        return super.visit(n);
    }
}
