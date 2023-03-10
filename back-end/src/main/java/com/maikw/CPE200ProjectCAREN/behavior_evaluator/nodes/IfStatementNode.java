package com.maikw.CPE200ProjectCAREN.behavior_evaluator.nodes;

public class IfStatementNode implements Node {

    Node expressionNode;
    Node ifTrueStatementNode;
    Node ifFalseStatementNode;

    public IfStatementNode(Node expressionNode, Node ifTrueStatementNode, Node ifFalseStatementNode) {
        this.expressionNode = expressionNode;
        this.ifTrueStatementNode = ifTrueStatementNode;
        this.ifFalseStatementNode = ifFalseStatementNode;
    }
    
    @Override
    public double evaluate() {
        if(expressionNode.evaluate() > 0){
            ifTrueStatementNode.evaluate();
        }else{
            ifFalseStatementNode.evaluate();
        }
        return 0;
    }
    
    @Override
    public void print(int depth){
        for(int i = 0 ; i < depth; ++i){
            System.out.print("   ");
        }
        System.out.print(" |---If ");
        expressionNode.print(0);
        System.out.println();
        ifTrueStatementNode.print(depth+1);
        ifFalseStatementNode.print(depth+1);
    }
}
