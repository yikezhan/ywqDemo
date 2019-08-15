package com.yuwuquan.demo.designpatterns.flyweight;

//可以看出，这里W、B、B实际只创建了两个对象W和B。
//此外这里没有保存外蕴状态，外蕴状态由客户端需要时传入。所以对这个例子这里有个疑问？如何判断五子棋的胜利规则呢？
public class TestChess {
    public static void main(String[] args) {
        AbstractChess chessWhite = ChessFactory.getChess("W");
        AbstractChess chessBlack = ChessFactory.getChess("B");
        AbstractChess chessBlack2 = ChessFactory.getChess("B");
        chessWhite.operate(1,2);chessBlack.operate(3,4);chessBlack2.operate(5,6);
        System.out.println(chessWhite.showChessInfo());
        System.out.println(chessBlack.showChessInfo());
        System.out.println(chessBlack2.showChessInfo());
    }
}
