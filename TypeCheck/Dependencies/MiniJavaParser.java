package TypeCheck.Dependencies;

import syntaxtree.*;


public class MiniJavaParser implements MiniJavaParserConstants {

    static final public Goal Goal() throws ParseException {
        MainClass n0;
        NodeListOptional n1 = new NodeListOptional();
        TypeDeclaration n2;
        NodeToken n3;
        Token n4;
        n0 = MainClass();
        label_1:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.CLASS:
                    ;
                    break;
                default:
                    jj_la1[0] = jj_gen;
                    break label_1;
            }
            n2 = TypeDeclaration();
            n1.addNode(n2);
        }
        n1.nodes.trimToSize();
        n4 = jj_consume_token(0);
        n4.beginColumn++; n4.endColumn++;
        n3 = JTBToolkit.makeNodeToken(n4);
        {if (true) return new Goal(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public MainClass MainClass() throws ParseException {
        NodeToken n0;
        Token n1;
        Identifier n2;
        NodeToken n3;
        Token n4;
        NodeToken n5;
        Token n6;
        NodeToken n7;
        Token n8;
        NodeToken n9;
        Token n10;
        NodeToken n11;
        Token n12;
        NodeToken n13;
        Token n14;
        NodeToken n15;
        Token n16;
        NodeToken n17;
        Token n18;
        NodeToken n19;
        Token n20;
        Identifier n21;
        NodeToken n22;
        Token n23;
        NodeToken n24;
        Token n25;
        NodeListOptional n26 = new NodeListOptional();
        VarDeclaration n27;
        NodeListOptional n28 = new NodeListOptional();
        Statement n29;
        NodeToken n30;
        Token n31;
        NodeToken n32;
        Token n33;
        n1 = jj_consume_token(MiniJavaParserConstants.CLASS);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Identifier();
        n4 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n3 = JTBToolkit.makeNodeToken(n4);
        n6 = jj_consume_token(MiniJavaParserConstants.PUBLIC);
        n5 = JTBToolkit.makeNodeToken(n6);
        n8 = jj_consume_token(MiniJavaParserConstants.STATIC);
        n7 = JTBToolkit.makeNodeToken(n8);
        n10 = jj_consume_token(MiniJavaParserConstants.VOID);
        n9 = JTBToolkit.makeNodeToken(n10);
        n12 = jj_consume_token(MiniJavaParserConstants.MAIN);
        n11 = JTBToolkit.makeNodeToken(n12);
        n14 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n13 = JTBToolkit.makeNodeToken(n14);
        n16 = jj_consume_token(MiniJavaParserConstants.STRING);
        n15 = JTBToolkit.makeNodeToken(n16);
        n18 = jj_consume_token(MiniJavaParserConstants.LSQPAREN);
        n17 = JTBToolkit.makeNodeToken(n18);
        n20 = jj_consume_token(MiniJavaParserConstants.RSQPAREN);
        n19 = JTBToolkit.makeNodeToken(n20);
        n21 = Identifier();
        n23 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n22 = JTBToolkit.makeNodeToken(n23);
        n25 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n24 = JTBToolkit.makeNodeToken(n25);
        label_2:
        while (true) {
            if (jj_2_1(2)) {
                ;
            } else {
                break label_2;
            }
            n27 = VarDeclaration();
            n26.addNode(n27);
        }
        n26.nodes.trimToSize();
        label_3:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.LBRACE:
                case MiniJavaParserConstants.IF:
                case MiniJavaParserConstants.WHILE:
                case MiniJavaParserConstants.PRINT:
                case MiniJavaParserConstants.IDENTIFIER:
                    ;
                    break;
                default:
                    jj_la1[1] = jj_gen;
                    break label_3;
            }
            n29 = Statement();
            n28.addNode(n29);
        }
        n28.nodes.trimToSize();
        n31 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n30 = JTBToolkit.makeNodeToken(n31);
        n33 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n32 = JTBToolkit.makeNodeToken(n33);
        {if (true) return new MainClass(n0,n2,n3,n5,n7,n9,n11,n13,n15,n17,n19,n21,n22,n24,n26,n28,n30,n32);}
        throw new Error("Missing return statement in function");
    }

    static final public TypeDeclaration TypeDeclaration() throws ParseException {
        NodeChoice n0;
        ClassDeclaration n1;
        ClassExtendsDeclaration n2;
        if (jj_2_2(3)) {
            n1 = ClassDeclaration();
            n0 = new NodeChoice(n1, 0);
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.CLASS:
                    n2 = ClassExtendsDeclaration();
                    n0 = new NodeChoice(n2, 1);
                    break;
                default:
                    jj_la1[2] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {if (true) return new TypeDeclaration(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public ClassDeclaration ClassDeclaration() throws ParseException {
        NodeToken n0;
        Token n1;
        Identifier n2;
        NodeToken n3;
        Token n4;
        NodeListOptional n5 = new NodeListOptional();
        VarDeclaration n6;
        NodeListOptional n7 = new NodeListOptional();
        MethodDeclaration n8;
        NodeToken n9;
        Token n10;
        n1 = jj_consume_token(MiniJavaParserConstants.CLASS);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Identifier();
        n4 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n3 = JTBToolkit.makeNodeToken(n4);
        label_4:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.BOOLEAN:
                case MiniJavaParserConstants.INTEGER:
                case MiniJavaParserConstants.IDENTIFIER:
                    ;
                    break;
                default:
                    jj_la1[3] = jj_gen;
                    break label_4;
            }
            n6 = VarDeclaration();
            n5.addNode(n6);
        }
        n5.nodes.trimToSize();
        label_5:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.PUBLIC:
                    ;
                    break;
                default:
                    jj_la1[4] = jj_gen;
                    break label_5;
            }
            n8 = MethodDeclaration();
            n7.addNode(n8);
        }
        n7.nodes.trimToSize();
        n10 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n9 = JTBToolkit.makeNodeToken(n10);
        {if (true) return new ClassDeclaration(n0,n2,n3,n5,n7,n9);}
        throw new Error("Missing return statement in function");
    }

    static final public ClassExtendsDeclaration ClassExtendsDeclaration() throws ParseException {
        NodeToken n0;
        Token n1;
        Identifier n2;
        NodeToken n3;
        Token n4;
        Identifier n5;
        NodeToken n6;
        Token n7;
        NodeListOptional n8 = new NodeListOptional();
        VarDeclaration n9;
        NodeListOptional n10 = new NodeListOptional();
        MethodDeclaration n11;
        NodeToken n12;
        Token n13;
        n1 = jj_consume_token(MiniJavaParserConstants.CLASS);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Identifier();
        n4 = jj_consume_token(MiniJavaParserConstants.EXTENDS);
        n3 = JTBToolkit.makeNodeToken(n4);
        n5 = Identifier();
        n7 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n6 = JTBToolkit.makeNodeToken(n7);
        label_6:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.BOOLEAN:
                case MiniJavaParserConstants.INTEGER:
                case MiniJavaParserConstants.IDENTIFIER:
                    ;
                    break;
                default:
                    jj_la1[5] = jj_gen;
                    break label_6;
            }
            n9 = VarDeclaration();
            n8.addNode(n9);
        }
        n8.nodes.trimToSize();
        label_7:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.PUBLIC:
                    ;
                    break;
                default:
                    jj_la1[6] = jj_gen;
                    break label_7;
            }
            n11 = MethodDeclaration();
            n10.addNode(n11);
        }
        n10.nodes.trimToSize();
        n13 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n12 = JTBToolkit.makeNodeToken(n13);
        {if (true) return new ClassExtendsDeclaration(n0,n2,n3,n5,n6,n8,n10,n12);}
        throw new Error("Missing return statement in function");
    }

    static final public VarDeclaration VarDeclaration() throws ParseException {
        Type n0;
        Identifier n1;
        NodeToken n2;
        Token n3;
        n0 = Type();
        n1 = Identifier();
        n3 = jj_consume_token(MiniJavaParserConstants.SEMICOLON);
        n2 = JTBToolkit.makeNodeToken(n3);
        {if (true) return new VarDeclaration(n0,n1,n2);}
        throw new Error("Missing return statement in function");
    }

    static final public MethodDeclaration MethodDeclaration() throws ParseException {
        NodeToken n0;
        Token n1;
        Type n2;
        Identifier n3;
        NodeToken n4;
        Token n5;
        NodeOptional n6 = new NodeOptional();
        FormalParameterList n7;
        NodeToken n8;
        Token n9;
        NodeToken n10;
        Token n11;
        NodeListOptional n12 = new NodeListOptional();
        VarDeclaration n13;
        NodeListOptional n14 = new NodeListOptional();
        Statement n15;
        NodeToken n16;
        Token n17;
        Expression n18;
        NodeToken n19;
        Token n20;
        NodeToken n21;
        Token n22;
        n1 = jj_consume_token(MiniJavaParserConstants.PUBLIC);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Type();
        n3 = Identifier();
        n5 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MiniJavaParserConstants.BOOLEAN:
            case MiniJavaParserConstants.INTEGER:
            case MiniJavaParserConstants.IDENTIFIER:
                n7 = FormalParameterList();
                n6.addNode(n7);
                break;
            default:
                jj_la1[7] = jj_gen;
                ;
        }
        n9 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n8 = JTBToolkit.makeNodeToken(n9);
        n11 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n10 = JTBToolkit.makeNodeToken(n11);
        label_8:
        while (true) {
            if (jj_2_3(2)) {
                ;
            } else {
                break label_8;
            }
            n13 = VarDeclaration();
            n12.addNode(n13);
        }
        n12.nodes.trimToSize();
        label_9:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.LBRACE:
                case MiniJavaParserConstants.IF:
                case MiniJavaParserConstants.WHILE:
                case MiniJavaParserConstants.PRINT:
                case MiniJavaParserConstants.IDENTIFIER:
                    ;
                    break;
                default:
                    jj_la1[8] = jj_gen;
                    break label_9;
            }
            n15 = Statement();
            n14.addNode(n15);
        }
        n14.nodes.trimToSize();
        n17 = jj_consume_token(MiniJavaParserConstants.RETURN);
        n16 = JTBToolkit.makeNodeToken(n17);
        n18 = Expression();
        n20 = jj_consume_token(MiniJavaParserConstants.SEMICOLON);
        n19 = JTBToolkit.makeNodeToken(n20);
        n22 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n21 = JTBToolkit.makeNodeToken(n22);
        {if (true) return new MethodDeclaration(n0,n2,n3,n4,n6,n8,n10,n12,n14,n16,n18,n19,n21);}
        throw new Error("Missing return statement in function");
    }

    static final public FormalParameterList FormalParameterList() throws ParseException {
        FormalParameter n0;
        NodeListOptional n1 = new NodeListOptional();
        FormalParameterRest n2;
        n0 = FormalParameter();
        label_10:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 47:
                    ;
                    break;
                default:
                    jj_la1[9] = jj_gen;
                    break label_10;
            }
            n2 = FormalParameterRest();
            n1.addNode(n2);
        }
        n1.nodes.trimToSize();
        {if (true) return new FormalParameterList(n0,n1);}
        throw new Error("Missing return statement in function");
    }

    static final public FormalParameter FormalParameter() throws ParseException {
        Type n0;
        Identifier n1;
        n0 = Type();
        n1 = Identifier();
        {if (true) return new FormalParameter(n0,n1);}
        throw new Error("Missing return statement in function");
    }

    static final public FormalParameterRest FormalParameterRest() throws ParseException {
        NodeToken n0;
        Token n1;
        FormalParameter n2;
        n1 = jj_consume_token(47);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = FormalParameter();
        {if (true) return new FormalParameterRest(n0,n2);}
        throw new Error("Missing return statement in function");
    }

    static final public Type Type() throws ParseException {
        NodeChoice n0;
        ArrayType n1;
        BooleanType n2;
        IntegerType n3;
        Identifier n4;
        if (jj_2_4(3)) {
            n1 = ArrayType();
            n0 = new NodeChoice(n1, 0);
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.BOOLEAN:
                    n2 = BooleanType();
                    n0 = new NodeChoice(n2, 1);
                    break;
                case MiniJavaParserConstants.INTEGER:
                    n3 = IntegerType();
                    n0 = new NodeChoice(n3, 2);
                    break;
                case MiniJavaParserConstants.IDENTIFIER:
                    n4 = Identifier();
                    n0 = new NodeChoice(n4, 3);
                    break;
                default:
                    jj_la1[10] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {if (true) return new Type(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public ArrayType ArrayType() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeToken n2;
        Token n3;
        NodeToken n4;
        Token n5;
        n1 = jj_consume_token(MiniJavaParserConstants.INTEGER);
        n0 = JTBToolkit.makeNodeToken(n1);
        n3 = jj_consume_token(MiniJavaParserConstants.LSQPAREN);
        n2 = JTBToolkit.makeNodeToken(n3);
        n5 = jj_consume_token(MiniJavaParserConstants.RSQPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        {if (true) return new ArrayType(n0,n2,n4);}
        throw new Error("Missing return statement in function");
    }

    static final public BooleanType BooleanType() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.BOOLEAN);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new BooleanType(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public IntegerType IntegerType() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.INTEGER);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new IntegerType(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public Statement Statement() throws ParseException {
        NodeChoice n0;
        Block n1;
        AssignmentStatement n2;
        ArrayAssignmentStatement n3;
        IfStatement n4;
        WhileStatement n5;
        PrintStatement n6;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MiniJavaParserConstants.LBRACE:
                n1 = Block();
                n0 = new NodeChoice(n1, 0);
                break;
            default:
                jj_la1[11] = jj_gen;
                if (jj_2_5(2)) {
                    n2 = AssignmentStatement();
                    n0 = new NodeChoice(n2, 1);
                } else if (jj_2_6(2)) {
                    n3 = ArrayAssignmentStatement();
                    n0 = new NodeChoice(n3, 2);
                } else {
                    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                        case MiniJavaParserConstants.IF:
                            n4 = IfStatement();
                            n0 = new NodeChoice(n4, 3);
                            break;
                        case MiniJavaParserConstants.WHILE:
                            n5 = WhileStatement();
                            n0 = new NodeChoice(n5, 4);
                            break;
                        case MiniJavaParserConstants.PRINT:
                            n6 = PrintStatement();
                            n0 = new NodeChoice(n6, 5);
                            break;
                        default:
                            jj_la1[12] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
        {if (true) return new Statement(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public Block Block() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeListOptional n2 = new NodeListOptional();
        Statement n3;
        NodeToken n4;
        Token n5;
        n1 = jj_consume_token(MiniJavaParserConstants.LBRACE);
        n0 = JTBToolkit.makeNodeToken(n1);
        label_11:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.LBRACE:
                case MiniJavaParserConstants.IF:
                case MiniJavaParserConstants.WHILE:
                case MiniJavaParserConstants.PRINT:
                case MiniJavaParserConstants.IDENTIFIER:
                    ;
                    break;
                default:
                    jj_la1[13] = jj_gen;
                    break label_11;
            }
            n3 = Statement();
            n2.addNode(n3);
        }
        n2.nodes.trimToSize();
        n5 = jj_consume_token(MiniJavaParserConstants.RBRACE);
        n4 = JTBToolkit.makeNodeToken(n5);
        {if (true) return new Block(n0,n2,n4);}
        throw new Error("Missing return statement in function");
    }

    static final public AssignmentStatement AssignmentStatement() throws ParseException {
        Identifier n0;
        NodeToken n1;
        Token n2;
        Expression n3;
        NodeToken n4;
        Token n5;
        n0 = Identifier();
        n2 = jj_consume_token(MiniJavaParserConstants.ASSIGN);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = Expression();
        n5 = jj_consume_token(MiniJavaParserConstants.SEMICOLON);
        n4 = JTBToolkit.makeNodeToken(n5);
        {if (true) return new AssignmentStatement(n0,n1,n3,n4);}
        throw new Error("Missing return statement in function");
    }

    static final public ArrayAssignmentStatement ArrayAssignmentStatement() throws ParseException {
        Identifier n0;
        NodeToken n1;
        Token n2;
        Expression n3;
        NodeToken n4;
        Token n5;
        NodeToken n6;
        Token n7;
        Expression n8;
        NodeToken n9;
        Token n10;
        n0 = Identifier();
        n2 = jj_consume_token(MiniJavaParserConstants.LSQPAREN);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = Expression();
        n5 = jj_consume_token(MiniJavaParserConstants.RSQPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        n7 = jj_consume_token(MiniJavaParserConstants.ASSIGN);
        n6 = JTBToolkit.makeNodeToken(n7);
        n8 = Expression();
        n10 = jj_consume_token(MiniJavaParserConstants.SEMICOLON);
        n9 = JTBToolkit.makeNodeToken(n10);
        {if (true) return new ArrayAssignmentStatement(n0,n1,n3,n4,n6,n8,n9);}
        throw new Error("Missing return statement in function");
    }

    static final public IfStatement IfStatement() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeToken n2;
        Token n3;
        Expression n4;
        NodeToken n5;
        Token n6;
        Statement n7;
        NodeToken n8;
        Token n9;
        Statement n10;
        n1 = jj_consume_token(MiniJavaParserConstants.IF);
        n0 = JTBToolkit.makeNodeToken(n1);
        n3 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n2 = JTBToolkit.makeNodeToken(n3);
        n4 = Expression();
        n6 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n5 = JTBToolkit.makeNodeToken(n6);
        n7 = Statement();
        n9 = jj_consume_token(MiniJavaParserConstants.ELSE);
        n8 = JTBToolkit.makeNodeToken(n9);
        n10 = Statement();
        {if (true) return new IfStatement(n0,n2,n4,n5,n7,n8,n10);}
        throw new Error("Missing return statement in function");
    }

    static final public WhileStatement WhileStatement() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeToken n2;
        Token n3;
        Expression n4;
        NodeToken n5;
        Token n6;
        Statement n7;
        n1 = jj_consume_token(MiniJavaParserConstants.WHILE);
        n0 = JTBToolkit.makeNodeToken(n1);
        n3 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n2 = JTBToolkit.makeNodeToken(n3);
        n4 = Expression();
        n6 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n5 = JTBToolkit.makeNodeToken(n6);
        n7 = Statement();
        {if (true) return new WhileStatement(n0,n2,n4,n5,n7);}
        throw new Error("Missing return statement in function");
    }

    static final public PrintStatement PrintStatement() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeToken n2;
        Token n3;
        Expression n4;
        NodeToken n5;
        Token n6;
        NodeToken n7;
        Token n8;
        n1 = jj_consume_token(MiniJavaParserConstants.PRINT);
        n0 = JTBToolkit.makeNodeToken(n1);
        n3 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n2 = JTBToolkit.makeNodeToken(n3);
        n4 = Expression();
        n6 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n5 = JTBToolkit.makeNodeToken(n6);
        n8 = jj_consume_token(MiniJavaParserConstants.SEMICOLON);
        n7 = JTBToolkit.makeNodeToken(n8);
        {if (true) return new PrintStatement(n0,n2,n4,n5,n7);}
        throw new Error("Missing return statement in function");
    }

    static final public Expression Expression() throws ParseException {
        NodeChoice n0;
        AndExpression n1;
        CompareExpression n2;
        PlusExpression n3;
        MinusExpression n4;
        TimesExpression n5;
        ArrayLookup n6;
        ArrayLength n7;
        MessageSend n8;
        PrimaryExpression n9;
        if (jj_2_7(2147483647)) {
            n1 = AndExpression();
            n0 = new NodeChoice(n1, 0);
        } else if (jj_2_8(2147483647)) {
            n2 = CompareExpression();
            n0 = new NodeChoice(n2, 1);
        } else if (jj_2_9(2147483647)) {
            n3 = PlusExpression();
            n0 = new NodeChoice(n3, 2);
        } else if (jj_2_10(2147483647)) {
            n4 = MinusExpression();
            n0 = new NodeChoice(n4, 3);
        } else if (jj_2_11(2147483647)) {
            n5 = TimesExpression();
            n0 = new NodeChoice(n5, 4);
        } else if (jj_2_12(2147483647)) {
            n6 = ArrayLookup();
            n0 = new NodeChoice(n6, 5);
        } else if (jj_2_13(2147483647)) {
            n7 = ArrayLength();
            n0 = new NodeChoice(n7, 6);
        } else if (jj_2_14(2147483647)) {
            n8 = MessageSend();
            n0 = new NodeChoice(n8, 7);
        } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case MiniJavaParserConstants.LPAREN:
                case MiniJavaParserConstants.NOT:
                case MiniJavaParserConstants.FALSE:
                case MiniJavaParserConstants.NEW:
                case MiniJavaParserConstants.THIS:
                case MiniJavaParserConstants.TRUE:
                case MiniJavaParserConstants.INTEGER_LITERAL:
                case MiniJavaParserConstants.IDENTIFIER:
                    n9 = PrimaryExpression();
                    n0 = new NodeChoice(n9, 8);
                    break;
                default:
                    jj_la1[14] = jj_gen;
                    jj_consume_token(-1);
                    throw new ParseException();
            }
        }
        {if (true) return new Expression(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public AndExpression AndExpression() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.AND);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        {if (true) return new AndExpression(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public CompareExpression CompareExpression() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.LT);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        {if (true) return new CompareExpression(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public PlusExpression PlusExpression() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.PLUS);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        {if (true) return new PlusExpression(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public MinusExpression MinusExpression() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.MINUS);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        {if (true) return new MinusExpression(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public TimesExpression TimesExpression() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(48);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        {if (true) return new TimesExpression(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public ArrayLookup ArrayLookup() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        PrimaryExpression n3;
        NodeToken n4;
        Token n5;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.LSQPAREN);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = PrimaryExpression();
        n5 = jj_consume_token(MiniJavaParserConstants.RSQPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        {if (true) return new ArrayLookup(n0,n1,n3,n4);}
        throw new Error("Missing return statement in function");
    }

    static final public ArrayLength ArrayLength() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        NodeToken n3;
        Token n4;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.DOT);
        n1 = JTBToolkit.makeNodeToken(n2);
        n4 = jj_consume_token(MiniJavaParserConstants.LENGTH);
        n3 = JTBToolkit.makeNodeToken(n4);
        {if (true) return new ArrayLength(n0,n1,n3);}
        throw new Error("Missing return statement in function");
    }

    static final public MessageSend MessageSend() throws ParseException {
        PrimaryExpression n0;
        NodeToken n1;
        Token n2;
        Identifier n3;
        NodeToken n4;
        Token n5;
        NodeOptional n6 = new NodeOptional();
        ExpressionList n7;
        NodeToken n8;
        Token n9;
        n0 = PrimaryExpression();
        n2 = jj_consume_token(MiniJavaParserConstants.DOT);
        n1 = JTBToolkit.makeNodeToken(n2);
        n3 = Identifier();
        n5 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MiniJavaParserConstants.LPAREN:
            case MiniJavaParserConstants.NOT:
            case MiniJavaParserConstants.FALSE:
            case MiniJavaParserConstants.NEW:
            case MiniJavaParserConstants.THIS:
            case MiniJavaParserConstants.TRUE:
            case MiniJavaParserConstants.INTEGER_LITERAL:
            case MiniJavaParserConstants.IDENTIFIER:
                n7 = ExpressionList();
                n6.addNode(n7);
                break;
            default:
                jj_la1[15] = jj_gen;
                ;
        }
        n9 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n8 = JTBToolkit.makeNodeToken(n9);
        {if (true) return new MessageSend(n0,n1,n3,n4,n6,n8);}
        throw new Error("Missing return statement in function");
    }

    static final public ExpressionList ExpressionList() throws ParseException {
        Expression n0;
        NodeListOptional n1 = new NodeListOptional();
        ExpressionRest n2;
        n0 = Expression();
        label_12:
        while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case 47:
                    ;
                    break;
                default:
                    jj_la1[16] = jj_gen;
                    break label_12;
            }
            n2 = ExpressionRest();
            n1.addNode(n2);
        }
        n1.nodes.trimToSize();
        {if (true) return new ExpressionList(n0,n1);}
        throw new Error("Missing return statement in function");
    }

    static final public ExpressionRest ExpressionRest() throws ParseException {
        NodeToken n0;
        Token n1;
        Expression n2;
        n1 = jj_consume_token(47);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Expression();
        {if (true) return new ExpressionRest(n0,n2);}
        throw new Error("Missing return statement in function");
    }

    static final public PrimaryExpression PrimaryExpression() throws ParseException {
        NodeChoice n0;
        IntegerLiteral n1;
        TrueLiteral n2;
        FalseLiteral n3;
        Identifier n4;
        ThisExpression n5;
        ArrayAllocationExpression n6;
        AllocationExpression n7;
        NotExpression n8;
        BracketExpression n9;
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case MiniJavaParserConstants.INTEGER_LITERAL:
                n1 = IntegerLiteral();
                n0 = new NodeChoice(n1, 0);
                break;
            case MiniJavaParserConstants.TRUE:
                n2 = TrueLiteral();
                n0 = new NodeChoice(n2, 1);
                break;
            case MiniJavaParserConstants.FALSE:
                n3 = FalseLiteral();
                n0 = new NodeChoice(n3, 2);
                break;
            case MiniJavaParserConstants.IDENTIFIER:
                n4 = Identifier();
                n0 = new NodeChoice(n4, 3);
                break;
            case MiniJavaParserConstants.THIS:
                n5 = ThisExpression();
                n0 = new NodeChoice(n5, 4);
                break;
            default:
                jj_la1[17] = jj_gen;
                if (jj_2_15(3)) {
                    n6 = ArrayAllocationExpression();
                    n0 = new NodeChoice(n6, 5);
                } else {
                    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                        case MiniJavaParserConstants.NEW:
                            n7 = AllocationExpression();
                            n0 = new NodeChoice(n7, 6);
                            break;
                        case MiniJavaParserConstants.NOT:
                            n8 = NotExpression();
                            n0 = new NodeChoice(n8, 7);
                            break;
                        case MiniJavaParserConstants.LPAREN:
                            n9 = BracketExpression();
                            n0 = new NodeChoice(n9, 8);
                            break;
                        default:
                            jj_la1[18] = jj_gen;
                            jj_consume_token(-1);
                            throw new ParseException();
                    }
                }
        }
        {if (true) return new PrimaryExpression(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public IntegerLiteral IntegerLiteral() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.INTEGER_LITERAL);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new IntegerLiteral(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public TrueLiteral TrueLiteral() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.TRUE);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new TrueLiteral(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public FalseLiteral FalseLiteral() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.FALSE);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new FalseLiteral(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public Identifier Identifier() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.IDENTIFIER);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new Identifier(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public ThisExpression ThisExpression() throws ParseException {
        NodeToken n0;
        Token n1;
        n1 = jj_consume_token(MiniJavaParserConstants.THIS);
        n0 = JTBToolkit.makeNodeToken(n1);
        {if (true) return new ThisExpression(n0);}
        throw new Error("Missing return statement in function");
    }

    static final public ArrayAllocationExpression ArrayAllocationExpression() throws ParseException {
        NodeToken n0;
        Token n1;
        NodeToken n2;
        Token n3;
        NodeToken n4;
        Token n5;
        Expression n6;
        NodeToken n7;
        Token n8;
        n1 = jj_consume_token(MiniJavaParserConstants.NEW);
        n0 = JTBToolkit.makeNodeToken(n1);
        n3 = jj_consume_token(MiniJavaParserConstants.INTEGER);
        n2 = JTBToolkit.makeNodeToken(n3);
        n5 = jj_consume_token(MiniJavaParserConstants.LSQPAREN);
        n4 = JTBToolkit.makeNodeToken(n5);
        n6 = Expression();
        n8 = jj_consume_token(MiniJavaParserConstants.RSQPAREN);
        n7 = JTBToolkit.makeNodeToken(n8);
        {if (true) return new ArrayAllocationExpression(n0,n2,n4,n6,n7);}
        throw new Error("Missing return statement in function");
    }

    static final public AllocationExpression AllocationExpression() throws ParseException {
        NodeToken n0;
        Token n1;
        Identifier n2;
        NodeToken n3;
        Token n4;
        NodeToken n5;
        Token n6;
        n1 = jj_consume_token(MiniJavaParserConstants.NEW);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Identifier();
        n4 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n3 = JTBToolkit.makeNodeToken(n4);
        n6 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n5 = JTBToolkit.makeNodeToken(n6);
        {if (true) return new AllocationExpression(n0,n2,n3,n5);}
        throw new Error("Missing return statement in function");
    }

    static final public NotExpression NotExpression() throws ParseException {
        NodeToken n0;
        Token n1;
        Expression n2;
        n1 = jj_consume_token(MiniJavaParserConstants.NOT);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Expression();
        {if (true) return new NotExpression(n0,n2);}
        throw new Error("Missing return statement in function");
    }

    static final public BracketExpression BracketExpression() throws ParseException {
        NodeToken n0;
        Token n1;
        Expression n2;
        NodeToken n3;
        Token n4;
        n1 = jj_consume_token(MiniJavaParserConstants.LPAREN);
        n0 = JTBToolkit.makeNodeToken(n1);
        n2 = Expression();
        n4 = jj_consume_token(MiniJavaParserConstants.RPAREN);
        n3 = JTBToolkit.makeNodeToken(n4);
        {if (true) return new BracketExpression(n0,n2,n3);}
        throw new Error("Missing return statement in function");
    }

    static private boolean jj_2_1(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_1(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(0, xla); }
    }

    static private boolean jj_2_2(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_2(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(1, xla); }
    }

    static private boolean jj_2_3(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_3(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(2, xla); }
    }

    static private boolean jj_2_4(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_4(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(3, xla); }
    }

    static private boolean jj_2_5(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_5(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(4, xla); }
    }

    static private boolean jj_2_6(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_6(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(5, xla); }
    }

    static private boolean jj_2_7(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_7(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(6, xla); }
    }

    static private boolean jj_2_8(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_8(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(7, xla); }
    }

    static private boolean jj_2_9(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_9(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(8, xla); }
    }

    static private boolean jj_2_10(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_10(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(9, xla); }
    }

    static private boolean jj_2_11(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_11(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(10, xla); }
    }

    static private boolean jj_2_12(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_12(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(11, xla); }
    }

    static private boolean jj_2_13(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_13(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(12, xla); }
    }

    static private boolean jj_2_14(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_14(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(13, xla); }
    }

    static private boolean jj_2_15(int xla) {
        jj_la = xla; jj_lastpos = jj_scanpos = token;
        try { return !jj_3_15(); } catch(LookaheadSuccess ls) { return true; } finally { jj_save(14, xla); }
    }

    static private boolean jj_3_15() {
        if (jj_3R_20()) return true;
        return false;
    }

    static private boolean jj_3R_20() {
        if (jj_scan_token(MiniJavaParserConstants.NEW)) return true;
        if (jj_scan_token(MiniJavaParserConstants.INTEGER)) return true;
        if (jj_scan_token(MiniJavaParserConstants.LSQPAREN)) return true;
        if (jj_3R_42()) return true;
        if (jj_scan_token(MiniJavaParserConstants.RSQPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_26() {
        if (jj_3R_36()) return true;
        return false;
    }

    static private boolean jj_3R_53() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LT)) return true;
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_40() {
        if (jj_scan_token(MiniJavaParserConstants.BOOLEAN)) return true;
        return false;
    }

    static private boolean jj_3R_25() {
        if (jj_3R_19()) return true;
        return false;
    }

    static private boolean jj_3_1() {
        if (jj_3R_13()) return true;
        return false;
    }

    static private boolean jj_3R_16() {
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.ASSIGN)) return true;
        return false;
    }

    static private boolean jj_3R_24() {
        if (jj_3R_35()) return true;
        return false;
    }

    static private boolean jj_3R_58() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.DOT)) return true;
        if (jj_scan_token(MiniJavaParserConstants.LENGTH)) return true;
        return false;
    }

    static private boolean jj_3R_23() {
        if (jj_3R_34()) return true;
        return false;
    }

    static private boolean jj_3R_22() {
        if (jj_3R_33()) return true;
        return false;
    }

    static private boolean jj_3R_15() {
        if (jj_scan_token(MiniJavaParserConstants.INTEGER)) return true;
        if (jj_scan_token(MiniJavaParserConstants.LSQPAREN)) return true;
        if (jj_scan_token(MiniJavaParserConstants.RSQPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_18() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_22()) {
            jj_scanpos = xsp;
            if (jj_3R_23()) {
                jj_scanpos = xsp;
                if (jj_3R_24()) {
                    jj_scanpos = xsp;
                    if (jj_3R_25()) {
                        jj_scanpos = xsp;
                        if (jj_3R_26()) {
                            jj_scanpos = xsp;
                            if (jj_3_15()) {
                                jj_scanpos = xsp;
                                if (jj_3R_27()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_28()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_29()) return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    static private boolean jj_3R_52() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.AND)) return true;
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_36() {
        if (jj_scan_token(MiniJavaParserConstants.THIS)) return true;
        return false;
    }

    static private boolean jj_3_3() {
        if (jj_3R_13()) return true;
        return false;
    }

    static private boolean jj_3R_57() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LSQPAREN)) return true;
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.RSQPAREN)) return true;
        return false;
    }

    static private boolean jj_3_14() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.DOT)) return true;
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LPAREN)) return true;
        return false;
    }

    static private boolean jj_3_13() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.DOT)) return true;
        if (jj_scan_token(MiniJavaParserConstants.LENGTH)) return true;
        return false;
    }

    static private boolean jj_3R_19() {
        if (jj_scan_token(MiniJavaParserConstants.IDENTIFIER)) return true;
        return false;
    }

    static private boolean jj_3R_51() {
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_32() {
        if (jj_3R_19()) return true;
        return false;
    }

    static private boolean jj_3_12() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LSQPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_31() {
        if (jj_3R_41()) return true;
        return false;
    }

    static private boolean jj_3R_50() {
        if (jj_3R_59()) return true;
        return false;
    }

    static private boolean jj_3R_39() {
        if (jj_scan_token(MiniJavaParserConstants.LPAREN)) return true;
        if (jj_3R_42()) return true;
        if (jj_scan_token(MiniJavaParserConstants.RPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_63() {
        if (jj_scan_token(47)) return true;
        if (jj_3R_42()) return true;
        return false;
    }

    static private boolean jj_3R_30() {
        if (jj_3R_40()) return true;
        return false;
    }

    static private boolean jj_3_11() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(48)) return true;
        return false;
    }

    static private boolean jj_3R_49() {
        if (jj_3R_58()) return true;
        return false;
    }

    static private boolean jj_3_4() {
        if (jj_3R_15()) return true;
        return false;
    }

    static private boolean jj_3_10() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.MINUS)) return true;
        return false;
    }

    static private boolean jj_3R_48() {
        if (jj_3R_57()) return true;
        return false;
    }

    static private boolean jj_3R_35() {
        if (jj_scan_token(MiniJavaParserConstants.FALSE)) return true;
        return false;
    }

    static private boolean jj_3R_56() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(48)) return true;
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_21() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3_4()) {
            jj_scanpos = xsp;
            if (jj_3R_30()) {
                jj_scanpos = xsp;
                if (jj_3R_31()) {
                    jj_scanpos = xsp;
                    if (jj_3R_32()) return true;
                }
            }
        }
        return false;
    }

    static private boolean jj_3_9() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.PLUS)) return true;
        return false;
    }

    static private boolean jj_3R_47() {
        if (jj_3R_56()) return true;
        return false;
    }

    static private boolean jj_3R_14() {
        if (jj_scan_token(MiniJavaParserConstants.CLASS)) return true;
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LBRACE)) return true;
        return false;
    }

    static private boolean jj_3_8() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LT)) return true;
        return false;
    }

    static private boolean jj_3R_62() {
        if (jj_3R_63()) return true;
        return false;
    }

    static private boolean jj_3R_46() {
        if (jj_3R_55()) return true;
        return false;
    }

    static private boolean jj_3R_38() {
        if (jj_scan_token(MiniJavaParserConstants.NOT)) return true;
        if (jj_3R_42()) return true;
        return false;
    }

    static private boolean jj_3_6() {
        if (jj_3R_17()) return true;
        return false;
    }

    static private boolean jj_3_7() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.AND)) return true;
        return false;
    }

    static private boolean jj_3R_45() {
        if (jj_3R_54()) return true;
        return false;
    }

    static private boolean jj_3R_61() {
        if (jj_3R_42()) return true;
        Token xsp;
        while (true) {
            xsp = jj_scanpos;
            if (jj_3R_62()) { jj_scanpos = xsp; break; }
        }
        return false;
    }

    static private boolean jj_3R_34() {
        if (jj_scan_token(MiniJavaParserConstants.TRUE)) return true;
        return false;
    }

    static private boolean jj_3_5() {
        if (jj_3R_16()) return true;
        return false;
    }

    static private boolean jj_3R_44() {
        if (jj_3R_53()) return true;
        return false;
    }

    static private boolean jj_3R_55() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.MINUS)) return true;
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_43() {
        if (jj_3R_52()) return true;
        return false;
    }

    static private boolean jj_3R_42() {
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_43()) {
            jj_scanpos = xsp;
            if (jj_3R_44()) {
                jj_scanpos = xsp;
                if (jj_3R_45()) {
                    jj_scanpos = xsp;
                    if (jj_3R_46()) {
                        jj_scanpos = xsp;
                        if (jj_3R_47()) {
                            jj_scanpos = xsp;
                            if (jj_3R_48()) {
                                jj_scanpos = xsp;
                                if (jj_3R_49()) {
                                    jj_scanpos = xsp;
                                    if (jj_3R_50()) {
                                        jj_scanpos = xsp;
                                        if (jj_3R_51()) return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    static private boolean jj_3R_33() {
        if (jj_scan_token(MiniJavaParserConstants.INTEGER_LITERAL)) return true;
        return false;
    }

    static private boolean jj_3R_60() {
        if (jj_3R_61()) return true;
        return false;
    }

    static private boolean jj_3R_37() {
        if (jj_scan_token(MiniJavaParserConstants.NEW)) return true;
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LPAREN)) return true;
        if (jj_scan_token(MiniJavaParserConstants.RPAREN)) return true;
        return false;
    }

    static private boolean jj_3_2() {
        if (jj_3R_14()) return true;
        return false;
    }

    static private boolean jj_3R_13() {
        if (jj_3R_21()) return true;
        if (jj_3R_19()) return true;
        return false;
    }

    static private boolean jj_3R_17() {
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LSQPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_59() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.DOT)) return true;
        if (jj_3R_19()) return true;
        if (jj_scan_token(MiniJavaParserConstants.LPAREN)) return true;
        Token xsp;
        xsp = jj_scanpos;
        if (jj_3R_60()) jj_scanpos = xsp;
        if (jj_scan_token(MiniJavaParserConstants.RPAREN)) return true;
        return false;
    }

    static private boolean jj_3R_54() {
        if (jj_3R_18()) return true;
        if (jj_scan_token(MiniJavaParserConstants.PLUS)) return true;
        if (jj_3R_18()) return true;
        return false;
    }

    static private boolean jj_3R_29() {
        if (jj_3R_39()) return true;
        return false;
    }

    static private boolean jj_3R_41() {
        if (jj_scan_token(MiniJavaParserConstants.INTEGER)) return true;
        return false;
    }

    static private boolean jj_3R_28() {
        if (jj_3R_38()) return true;
        return false;
    }

    static private boolean jj_3R_27() {
        if (jj_3R_37()) return true;
        return false;
    }

    static private boolean jj_initialized_once = false;
    /**
     * Generated TypeCheck.Token Manager.
     */
    static public MiniJavaParserTokenManager token_source;
    static JavaCharStream jj_input_stream;
    /** Current token. */
    static public Token token;
    /** Next token. */
    static public Token jj_nt;
    static private int jj_ntk;
    static private Token jj_scanpos, jj_lastpos;
    static private int jj_la;
    static private int jj_gen;
    static final private int[] jj_la1 = new int[19];
    static private int[] jj_la1_0;
    static private int[] jj_la1_1;

    static {
        jj_la1_init_0();
        jj_la1_init_1();
    }

    private static void jj_la1_init_0() {
        jj_la1_0 = new int[] {0x1000000,0x60002000,0x1000000,0x80800000,0x0,0x80800000,0x0,0x80800000,0x60002000,0x0,0x80800000,0x2000,0x60000000,0x60002000,0x10400200,0x10400200,0x0,0x10000000,0x400200,};
    }

    private static void jj_la1_init_1() {
        jj_la1_1 = new int[] {0x0,0x1200,0x0,0x1000,0x8,0x1000,0x8,0x1000,0x1200,0x8000,0x1000,0x0,0x200,0x1200,0x1984,0x1984,0x8000,0x1980,0x4,};
    }

    static final private JJCalls[] jj_2_rtns = new JJCalls[15];
    static private boolean jj_rescan = false;
    static private int jj_gc = 0;

    /** Constructor with InputStream. */
    public MiniJavaParser(java.io.InputStream stream) {
        this(stream, null);
    }

    /** Constructor with InputStream and supplied encoding */
    public MiniJavaParser(java.io.InputStream stream, String encoding) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser.  ");
            System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("       during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
        token_source = new MiniJavaParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /** Reinitialise. */
    static public void ReInit(java.io.InputStream stream) {
        ReInit(stream, null);
    }

    /** Reinitialise. */
    static public void ReInit(java.io.InputStream stream, String encoding) {
        try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /** Constructor. */
    public MiniJavaParser(java.io.Reader stream) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("       during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        jj_input_stream = new JavaCharStream(stream, 1, 1);
        token_source = new MiniJavaParserTokenManager(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /** Reinitialise. */
    static public void ReInit(java.io.Reader stream) {
        jj_input_stream.ReInit(stream, 1, 1);
        token_source.ReInit(jj_input_stream);
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /** Constructor with generated TypeCheck.Token Manager. */
    public MiniJavaParser(MiniJavaParserTokenManager tm) {
        if (jj_initialized_once) {
            System.out.println("ERROR: Second call to constructor of static parser. ");
            System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
            System.out.println("       during parser generation.");
            throw new Error();
        }
        jj_initialized_once = true;
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    /** Reinitialise. */
    public void ReInit(MiniJavaParserTokenManager tm) {
        token_source = tm;
        token = new Token();
        jj_ntk = -1;
        jj_gen = 0;
        for (int i = 0; i < 19; i++) jj_la1[i] = -1;
        for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
    }

    static private Token jj_consume_token(int kind) throws ParseException {
        Token oldToken;
        if ((oldToken = token).next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        if (token.kind == kind) {
            jj_gen++;
            if (++jj_gc > 100) {
                jj_gc = 0;
                for (int i = 0; i < jj_2_rtns.length; i++) {
                    JJCalls c = jj_2_rtns[i];
                    while (c != null) {
                        if (c.gen < jj_gen) c.first = null;
                        c = c.next;
                    }
                }
            }
            return token;
        }
        token = oldToken;
        jj_kind = kind;
        throw generateParseException();
    }

    static private final class LookaheadSuccess extends java.lang.Error { }

    static final private LookaheadSuccess jj_ls = new LookaheadSuccess();

    static private boolean jj_scan_token(int kind) {
        if (jj_scanpos == jj_lastpos) {
            jj_la--;
            if (jj_scanpos.next == null) {
                jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
            } else {
                jj_lastpos = jj_scanpos = jj_scanpos.next;
            }
        } else {
            jj_scanpos = jj_scanpos.next;
        }
        if (jj_rescan) {
            int i = 0; Token tok = token;
            while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
            if (tok != null) jj_add_error_token(kind, i);
        }
        if (jj_scanpos.kind != kind) return true;
        if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
        return false;
    }


    /** Get the next TypeCheck.Token. */
    static final public Token getNextToken() {
        if (token.next != null) token = token.next;
        else token = token.next = token_source.getNextToken();
        jj_ntk = -1;
        jj_gen++;
        return token;
    }

    /** Get the specific TypeCheck.Token. */
    static final public Token getToken(int index) {
        Token t = token;
        for (int i = 0; i < index; i++) {
            if (t.next != null) t = t.next;
            else t = t.next = token_source.getNextToken();
        }
        return t;
    }

    static private int jj_ntk() {
        if ((jj_nt=token.next) == null)
            return (jj_ntk = (token.next=token_source.getNextToken()).kind);
        else
            return (jj_ntk = jj_nt.kind);
    }

    static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
    static private int[] jj_expentry;
    static private int jj_kind = -1;
    static private int[] jj_lasttokens = new int[100];
    static private int jj_endpos;

    static private void jj_add_error_token(int kind, int pos) {
        if (pos >= 100) return;
        if (pos == jj_endpos + 1) {
            jj_lasttokens[jj_endpos++] = kind;
        } else if (jj_endpos != 0) {
            jj_expentry = new int[jj_endpos];
            for (int i = 0; i < jj_endpos; i++) {
                jj_expentry[i] = jj_lasttokens[i];
            }
            boolean exists = false;
            for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
                exists = true;
                int[] oldentry = (int[])(it.next());
                if (oldentry.length == jj_expentry.length) {
                    for (int i = 0; i < jj_expentry.length; i++) {
                        if (oldentry[i] != jj_expentry[i]) {
                            exists = false;
                            break;
                        }
                    }
                    if (exists) break;
                }
            }
            if (!exists) jj_expentries.add(jj_expentry);
            if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
        }
    }

    /** Generate TypeCheck.ParseException. */
    static public ParseException generateParseException() {
        jj_expentries.clear();
        boolean[] la1tokens = new boolean[49];
        if (jj_kind >= 0) {
            la1tokens[jj_kind] = true;
            jj_kind = -1;
        }
        for (int i = 0; i < 19; i++) {
            if (jj_la1[i] == jj_gen) {
                for (int j = 0; j < 32; j++) {
                    if ((jj_la1_0[i] & (1<<j)) != 0) {
                        la1tokens[j] = true;
                    }
                    if ((jj_la1_1[i] & (1<<j)) != 0) {
                        la1tokens[32+j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < 49; i++) {
            if (la1tokens[i]) {
                jj_expentry = new int[1];
                jj_expentry[0] = i;
                jj_expentries.add(jj_expentry);
            }
        }
        jj_endpos = 0;
        jj_rescan_token();
        jj_add_error_token(0, 0);
        int[][] exptokseq = new int[jj_expentries.size()][];
        for (int i = 0; i < jj_expentries.size(); i++) {
            exptokseq[i] = jj_expentries.get(i);
        }
        return new ParseException(token, exptokseq, MiniJavaParserConstants.tokenImage);
    }

    /** Enable tracing. */
    static final public void enable_tracing() {
    }

    /** Disable tracing. */
    static final public void disable_tracing() {
    }

    static private void jj_rescan_token() {
        jj_rescan = true;
        for (int i = 0; i < 15; i++) {
            try {
                JJCalls p = jj_2_rtns[i];
                do {
                    if (p.gen > jj_gen) {
                        jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
                        switch (i) {
                            case 0: jj_3_1(); break;
                            case 1: jj_3_2(); break;
                            case 2: jj_3_3(); break;
                            case 3: jj_3_4(); break;
                            case 4: jj_3_5(); break;
                            case 5: jj_3_6(); break;
                            case 6: jj_3_7(); break;
                            case 7: jj_3_8(); break;
                            case 8: jj_3_9(); break;
                            case 9: jj_3_10(); break;
                            case 10: jj_3_11(); break;
                            case 11: jj_3_12(); break;
                            case 12: jj_3_13(); break;
                            case 13: jj_3_14(); break;
                            case 14: jj_3_15(); break;
                        }
                    }
                    p = p.next;
                } while (p != null);
            } catch(LookaheadSuccess ls) { }
        }
        jj_rescan = false;
    }

    static private void jj_save(int index, int xla) {
        JJCalls p = jj_2_rtns[index];
        while (p.gen > jj_gen) {
            if (p.next == null) { p = p.next = new JJCalls(); break; }
            p = p.next;
        }
        p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
    }

    static final class JJCalls {
        int gen;
        Token first;
        int arg;
        JJCalls next;
    }

}

class JTBToolkit {
    static NodeToken makeNodeToken(Token t) {
        return new NodeToken(t.image.intern(), t.kind, t.beginLine, t.beginColumn, t.endLine, t.endColumn);
    }
}
