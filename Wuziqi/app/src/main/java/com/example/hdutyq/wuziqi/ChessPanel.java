package com.example.hdutyq.wuziqi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessPanel extends View {
    private int row=10;
    private int column=10;
    private int myPaneWith;
    private float myLineHeight;
    private int maxLine = 10;
    private Paint myPaint;
    private Bitmap myWhitePice;
    private Bitmap myBlackPice;
    private float ratioPieceofLineHeight = 3 * 1.0f / 4;
    private boolean isGameover=false;
    private boolean isWhite = true;
    private List<Point> myWhiteArray = new ArrayList<>();
    private List<Point> myBlackArray = new ArrayList<>();
    private String [][]    number=new String[10][10];
    private Integer[][]     quan=new Integer[10][10];
    int AI=0;
    public static HashMap<String,Integer>   map=new HashMap<>();
    static {
        map.put("01", 17);
        map.put("02", 12);
        map.put("001", 17);
        map.put("002", 12);
        map.put("0001", 17);
        map.put("0002", 12);

        map.put("0102",17);
        map.put("0201",12);
        map.put("0012",15);
        map.put("0021",10);
        map.put("01002",19);
        map.put("02001",14);
        map.put("00102",17);
        map.put("00201",12);
        map.put("00012",15);
        map.put("00021",10);

        map.put("01000",21);
        map.put("02000",16);
        map.put("00100",19);
        map.put("00200",14);
        map.put("00010",17);
        map.put("00020",12);
        map.put("00001",15);
        map.put("00002",10);
        map.put("0101",65);
        map.put("0202",60);
        map.put("0110",65);
        map.put("0220",60);
        map.put("011",65);
        map.put("022",60);
        map.put("0011",65);
        map.put("0022",60);
        map.put("01012",65);
        map.put("02021",60);
        map.put("01102",65);
        map.put("02201",60);
        map.put("00112",65);
        map.put("00221",60);

        map.put("01010",75);
        map.put("02020",70);
        map.put("01100",75);
        map.put("02200",70);
        map.put("00110",75);
        map.put("00220",70);
        map.put("00011",75);
        map.put("00022",70);


        map.put("0111",150);
        map.put("0222",140);

        map.put("01112",150);
        map.put("02221",140);

        map.put("01101",1000);
        map.put("02202",800);
        map.put("01011",1000);
        map.put("02022",800);
        map.put("01110", 1000);
        map.put("02220", 800);
        map.put("01111",3000);
        map.put("02222",3500);

    }
    public Integer unionWeight(Integer a,Integer b ) {
        if((a==null)||(b==null)) return 0;

        else if((a>=10)&&(a<=25)&&(b>=10)&&(b<=25)) return 60;
        else if(((a>=10)&&(a<=25)&&(b>=60)&&(b<=80))||((a>=60)&&(a<=80)&&(b>=10)&&(b<=25))) return 800;
        else if(((a>=10)&&(a<=25)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=10)&&(b<=25))||((a>=60)&&(a<=80)&&(b>=60)&&(b<=80)))
            return 3000;
        else if(((a>=60)&&(a<=80)&&(b>=140)&&(b<=1000))||((a>=140)&&(a<=1000)&&(b>=60)&&(b<=80))) return 3000;
        else return 0;
    }


    public ChessPanel(Context context) {
        this(context, null);
    }

    public ChessPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        myPaint = new Paint();
        myPaint.setColor(0x88000000);
        myPaint.setAntiAlias(true);
        myPaint.setDither(true);
        myPaint.setStyle(Paint.Style.STROKE);
        myWhitePice = BitmapFactory.decodeResource(getResources(), R.drawable.bq);
        myBlackPice = BitmapFactory.decodeResource(getResources(), R.drawable.hq);
        isGameover=true;
        for (int i=0;i<10;i++){
            for (int    j=0;j<10;j++){
                number[i][j]="0";
            }
        }
    }
    private Point getVaLidPiont(int x , int y){
        return new Point((int)(x/myLineHeight),(int)(y/myLineHeight));
    }
    private void putchess(){
            for (int i=0;i<10;i++){
                for (int    j=0;j<10;j++){
                    quan[i][j]=0;
                }
            }
            for (int    i=0;i<10;i++){
                for (int    j=0;j<10;j++){
                    if(number[i][j]=="0"){
                        String ConnectType="0";
                        int jmin=Math.max(0, j-4);
                        for(int positionj=j-1;positionj>=jmin;positionj--) {
                            ConnectType=ConnectType+number[i][positionj];
                        }
                        Integer valueleft=map.get(ConnectType);
                        if(valueleft!=null) quan[i][j]+=valueleft;
                        ConnectType="0";
                        jmin=Math.min(9, j+4);
                        for(int positionj=j+1;positionj<=jmin;positionj++) {
                            ConnectType=ConnectType+number[i][positionj];
                        }
                        Integer valueright=map.get(ConnectType);
                        if(valueright!=null) quan[i][j]+=valueright;
                       quan[i][j]+=unionWeight(valueleft,valueright);
                        ConnectType="0";
                        jmin=Math.min(9, i+4);
                        for(int positionj=i+1;positionj<=jmin;positionj++) {
                            ConnectType=ConnectType+number[positionj][j];
                        }
                        Integer valuedown=map.get(ConnectType);
                        if(valuedown!=null) quan[i][j]+=valuedown;
                        ConnectType="0";
                        jmin=Math.max(0, i-4);
                        for(int positionj=i-1;positionj>=jmin;positionj--) {
                            ConnectType=ConnectType+number[positionj][j];
                        }
                        Integer valueup=map.get(ConnectType);
                        if(valueup!=null) quan[i][j]+=valueup;
                        quan[i][j]+=unionWeight(valueup,valuedown);
                        ConnectType="0";
                        for(int position=-1;position>=-4;position--) {
                            if((i+position>=0)&&(i+position<=9)&&(j+position>=0)&&(j+position<=9))
                                ConnectType=ConnectType+number[i+position][j+position];
                        }
                        Integer valueLeftUp=map.get(ConnectType);
                        if(valueLeftUp!=null) quan[i][j]+=valueLeftUp;

                        ConnectType="0";
                        for(int position=1;position<=4;position++) {
                            if((i+position>=0)&&(i+position<=9)&&(j+position>=0)&&(j+position<=9))
                                ConnectType=ConnectType+number[i+position][j+position];
                        }
                        Integer valueRightDown=map.get(ConnectType);
                        if(valueRightDown!=null) quan[i][j]+=valueRightDown;
                        quan[i][j]+=unionWeight(valueLeftUp,valueRightDown);
                        ConnectType="0";
                        for(int position=1;position<=4;position++) {
                            if((i+position>=0)&&(i+position<=9)&&(j-position>=0)&&(j-position<=9))
                                ConnectType=ConnectType+number[i+position][j-position];
                        }
                        Integer valueLeftDown=map.get(ConnectType);
                        if(valueLeftDown!=null) quan[i][j]+=valueLeftDown;


                        ConnectType="0";
                        for(int position=1;position<=4;position++) {
                            if((i-position>=0)&&(i-position<=9)&&(j+position>=0)&&(j+position<=9))
                                ConnectType=ConnectType+number[i-position][j+position];
                        }
                        Integer valueRightUp=map.get(ConnectType);
                        if(valueRightUp!=null) quan[i][j]+=valueRightUp;
                        quan[i][j]+=unionWeight(valueRightUp,valueLeftDown);

                    }
                }
            }
            int AIi=0,AIj=0;
            int weightmax=0;
            for(int i=0;i<row;i++) {
                for(int j=0;j<column;j++) {
                    if(weightmax<quan[i][j]) {
                        weightmax=quan[i][j];
                        AIi=i;
                        AIj=j;
                    }
                }
            }
            number[AIi][AIj]="2";
            Point   p=new Point(AIi,AIj);
            myBlackArray.add(p);

    }
    public boolean  onTouchEvent(MotionEvent    event) {

        if (isGameover) {
            return false;
        }
        if (AI == 0) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                Point p = getVaLidPiont(x, y);
                if (myWhiteArray.contains(p) || myBlackArray.contains(p)) {
                    return false;
                }
                if (isWhite) {
                    number[p.x][p.y] = "1";
                    myWhiteArray.add(p);
                } else {
                    number[p.x][p.y] = "2";
                    myBlackArray.add(p);
                }
                invalidate();
                isWhite = !isWhite;
            }
        } else {
            if (isWhite) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_UP) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    Point p = getVaLidPiont(x, y);
                    if (myWhiteArray.contains(p)|| myBlackArray.contains(p)) {
                        return false;
                    }
                    number[p.x][p.y] = "1";
                    myWhiteArray.add(p);
                    putchess();
                    invalidate();
                }
            }
        }
        return true;
    }

    protected void onMeasure(int    widthMeasureSpec,int    heightMeasureSpec){
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width=Math.min(widthSize,heightSize);
        if(widthMode==MeasureSpec.UNSPECIFIED){
            width=heightSize;
        }else {
            if(heightMode==MeasureSpec.UNSPECIFIED){
                width=widthSize;
            }
        }
        setMeasuredDimension(width,width);
    }
    protected void onSizeChanged(int    w,int   h,int   oldew,int   oldh){
        myPaneWith=w;
        myLineHeight=myPaneWith*1.0f/maxLine;
        int pieceWidth = (int) (myLineHeight*ratioPieceofLineHeight);
        myWhitePice=Bitmap.createScaledBitmap(myWhitePice,pieceWidth,pieceWidth,false);
        myBlackPice = Bitmap.createScaledBitmap(myBlackPice, pieceWidth, pieceWidth, false);
    }
    protected void onDraw(Canvas    canvas){
        super.onDraw(canvas);
        drawBroad(canvas);
        drawPiece(canvas);
        checkGameOver();
    }
    private void drawBroad(Canvas   canvas){
        int w=myPaneWith;
        float   lineHeight=myLineHeight;
        int startX=(int)(lineHeight/2);
        int endX=(int)(w-lineHeight/2);
        for (int    i=0;i<maxLine;i++){
            int y=(int)((i+0.5)*lineHeight);
            canvas.drawLine(startX,y,endX,y,myPaint);
            canvas.drawLine(y,startX,y,endX,myPaint);

        }
    }
    private void drawPiece(Canvas   canvas){
        int n1 = myWhiteArray.size();
        int n2 = myBlackArray.size();
        for(int i =0; i< n1 ;i++){
            Point whitePoint = myWhiteArray.get(i);
            canvas.drawBitmap(myWhitePice, (whitePoint.x+(1-ratioPieceofLineHeight)/2)*myLineHeight,(whitePoint.y+(1-ratioPieceofLineHeight)/2)*myLineHeight, null);
        }
     for(int i =0; i< n2 ;i++){
            Point blackPoint = myBlackArray.get(i);
            canvas.drawBitmap(myBlackPice, (blackPoint.x+(1-ratioPieceofLineHeight)/2)*myLineHeight, (blackPoint.y+(1-ratioPieceofLineHeight)/2)*myLineHeight, null);
        }
    }
    private void checkGameOver(){
        boolean whiteWin=checkFiveInLine(myWhiteArray);
        boolean blackWin = checkFiveInLine(myBlackArray);
        if (whiteWin ) {
            isGameover = true;
            Dialog("白棋获胜");
        }else {
            if(blackWin){
                isGameover = true;
                Dialog("黑棋获胜");
            }
        }
        if(myWhiteArray.size()+myBlackArray.size()==100){
            Dialog("平局哦");
            isGameover=true;
        }
    }
    protected void Dialog(String    str){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(getContext());
        alertDialog.setTitle("游戏结束");
        alertDialog.setMessage(str);
        alertDialog.setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)getContext()).finish();
            }
        });
        alertDialog.setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                restartGame();
            }
        });
        alertDialog.setCancelable(false).create();
        alertDialog.show();

    }
    private boolean checkFiveInLine(List<Point> myArray){
        for (Point  p:myArray){
            int x=p.x;
            int y=p.y;
            boolean win_flag=checkHorizontal(x,y,myArray)||checkVertical(x,y,myArray)||checkLeft(x,y,myArray)||checkRight(x,y,myArray);
            if(win_flag){
                return true;
            }
        }
        return false;
    }
    private boolean checkHorizontal(int x,int   y,List<Point>myArray){
        int count = 1;
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x+i,y))) {
                count++;
            }else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x-i,y))) {
                count++;
            }else {
                break;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }
    private boolean checkVertical(int x ,int y ,List<Point> myArray){
        int count = 1;
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x,y+i))) {
                count++;
            }else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x,y-i))) {
                count++;
            }else {
                break;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }
    private boolean checkLeft(int x ,int y ,List<Point> myArray){
        int count = 1;
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x-i,y+i))) {
                count++;
            }else {
                break;
            }
        }
        if (count == 5) {
            return true;
        }
        for(int i = 1;i < 5; i++){
            if (myArray.contains(new Point(x+i,y-i))) {
                count++;
            }else {
                break;
            }
            if (count == 5) {
                return true;
            }
        }
        return false;
    }
    private boolean checkRight(int x ,int y ,List<Point> myArray){
       int count = 1;
       for(int i = 1;i < 5; i++){
           if (myArray.contains(new Point(x-i,y-i))) {
               count++;
           }else {
               break;
           }
       }
       if (count == 5) {
           return true;
       }
       for(int i = 1;i < 5; i++){
           if (myArray.contains(new Point(x+i,y+i))) {
               count++;
           }else {
               break;
           }
           if (count == 5) {
               return true;
           }
       }
       return false;
    }
    protected void restartGame(){
        myWhiteArray.clear();
        myBlackArray.clear();
        isGameover=false;
        isWhite=true;
        for (int i=0;i<10;i++){
            for (int    j=0;j<10;j++){
                number[i][j]="0";
            }
        }
        invalidate();
    }
    public void regret() {
        if(AI==0){
            if (myBlackArray.size() > 0 || myWhiteArray.size() > 0) {
                if (isWhite) {
                    Point   p=myBlackArray.get(myBlackArray.size()-1);
                    number[p.x][p.y]="0";
                    myBlackArray.remove(myBlackArray.size() - 1);
                    isWhite = !isWhite;
                } else {
                    Point   p=myWhiteArray.get(myWhiteArray.size()-1);
                    number[p.x][p.y]="0";
                    myWhiteArray.remove(myWhiteArray.size() - 1);
                    isWhite = !isWhite;
                }
                invalidate();
            }
        }else {
            if (myBlackArray.size() > 0 || myWhiteArray.size() > 0) {

                Point p = myBlackArray.get(myBlackArray.size()-1);
                number[p.x][p.y] = "0";
                myBlackArray.remove(myBlackArray.size() - 1);
                p = myWhiteArray.get(myWhiteArray.size()-1);
                number[p.x][p.y] = "0";
                myWhiteArray.remove(myWhiteArray.size() - 1);
                invalidate();
            }
        }

    }
    public void start(){
        myWhiteArray.clear();
        myBlackArray.clear();
        isGameover=false;
        isWhite=true;
        for (int i=0;i<10;i++){
            for (int    j=0;j<10;j++){
                number[i][j]="0";
            }
        }
        invalidate();
    }
}

