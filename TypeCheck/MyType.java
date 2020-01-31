package TypeCheck;


import syntaxtree.Node;
import syntaxtree.NodeToken;
import syntaxtree.Type;

import java.util.Objects;

class MyType implements Node {


    NodeToken f0;

    public MyType(NodeToken nodeToken) {
        this.f0 = nodeToken;
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
        MyType mt = new MyType(n.toString());
        return mt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.toString());
    }

    @Override
    public String toString() {
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