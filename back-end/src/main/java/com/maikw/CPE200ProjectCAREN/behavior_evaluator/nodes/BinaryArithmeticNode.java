package com.maikw.CPE200ProjectCAREN.behavior_evaluator.nodes;

public class BinaryArithmeticNode implements Node {
    protected Node left, right;
    protected String op;

    public BinaryArithmeticNode(Node left, String op, Node right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public double evaluate() throws ArithmeticException {
        double lv = left.evaluate();
        double rv = right.evaluate();
        if (op.equals("+"))
            return lv + rv;
        if (op.equals("-"))
            return lv - rv;
        if (op.equals("*"))
            return lv * rv;
        if (op.equals("/")){
            if(rv == 0){
                throw new ArithmeticException("divide by zero");
            }
            return Math.floor(lv / rv);
        }
        if (op.equals("%")){
            if(rv == 0){
                throw new ArithmeticException("modulo by zero");
            }
            return lv % rv;
        }
        if (op.equals("^")){
            return Math.pow(lv, rv);
        }  
        throw new ArithmeticException("unknown op: " + op);
    }

    @Override
    public void print(int depth){
        for(int i = 0 ; i < depth; ++i){
            System.out.print("   ");
        }
        left.print(0);
        System.out.print(" " + op + " ");
        right.print(0);
    }
}
