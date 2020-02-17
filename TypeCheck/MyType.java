package TypeCheck;


import syntaxtree.Identifier;
import syntaxtree.Node;
import syntaxtree.NodeToken;
import syntaxtree.Type;

import java.util.Objects;

import static java.lang.System.exit;

class MyType implements Node {

    String vid;
    int value;
    NodeToken f0;
    String identifierName;
    boolean isFieldVar = false;

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    public MyType(String newvid, NodeToken mtype, int mvalue) {
        vid = newvid;
        value = mvalue;
        f0 = mtype;
    }

    public MyType(NodeToken nodeToken) {
        this.f0 = nodeToken;
    }

    public MyType() {

    }

    //    construct with type name
    public MyType(String typeName) {
        f0 = new NodeToken(typeName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyType)) return false;

        if (this.toString().equals(o.toString())) {
            return true;
        } else {
            return false;
        }
    }

    static public MyType toMyType(Type n) {                         // TODO: 1/27/2020 check
        MyType mt = new MyType("defualt");
        switch(n.f0.which){
            case 0:
                mt.f0.tokenImage = "int[]";
                break;
            case 1:
                mt.f0.tokenImage = "boolean";
                break;
            case 2:
                mt.f0.tokenImage = "int";
                break;
            case 3:
                Identifier id = (Identifier)n.f0.choice;                // TODO: 1/31/2020 check id case
                mt.f0.tokenImage = id.f0.tokenImage;
                break;
            default:
                System.out.println("error when type to mytype");
                exit(-1);
        }
        //System.out.println("here!!!!!!!!type is "+mt.toString());
        return mt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }

    @Override
    public String toString() {
        if (f0 == null) {
            return "";
        }
        return f0.toString();
    }

    public void accept(visitor.Visitor v) {
        v.visit(this.f0);
    }
    public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
        return v.visit(this.f0,argu);
    }
    public <R> R accept(visitor.GJNoArguVisitor<R> v) {
        return v.visit(this.f0);
    }
    public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
        v.visit(this.f0,argu);
    }


}