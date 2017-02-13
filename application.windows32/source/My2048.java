import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class My2048 extends PApplet {

PFont myFont;
int arr[][]=new int[4][4];
int direction=-1;
int cntScore=0,bestScore=0;
boolean flag;
int succeed;
int newX,newY,newWidth,newHeight;
public void setup()
{
  size(600,700);
  background(250,248,239);
  noStroke();
  Init();
  myFont=createFont("MicrosoftPhagsPa-Bold-48",32);
  drawInformationBox(50,70);
  newGameOrTryAgain(450,85,135,40);
}
public void draw()
{
   size(600,700);
  drawRect(50,150,500,500);
  drawGrid(50,150,500);
  CursorChanged(450,85);
  drawScores(400,5,80,50);
  responseAfterGameOver(50,150,500,500);
  responseAfterSucceed(50,150,500,500);
}

public void drawRect(int x1,int y1,int Width,int Height)
{
  fill(187,173,160);
  rect(x1,y1,Width,Height);
}
public void Init()
{
  for(int i=0;i<4;i++)
    for(int j=0;j<4;j++)
      arr[i][j]=-1;
  GenerateNewNumber();
  GenerateNewNumber();
  flag=false;
  succeed=0;
}
public void drawGrid(int x,int y,int Width)
{
  for(int i=0;i<4;i++)
  {
    for(int j=x,k=0;k<4;j+=100,k++)
    {
      j+=20;
      drawNumbers(x,y,Width);
    }
  }
}
public void drawNumbers(int x,int y,int Width)
{
   for(int i=0;i<4;i++)
  {
    for(int j=x,k=0;k<4;j+=100,k++)
    {
      j+=20;
     if(arr[i][k]==-1) fill(204,192,179);
     else if(arr[i][k]==2) fill(238,228,218);
     else if(arr[i][k]==4) fill(237,224,200);
     else if(arr[i][k]==8) fill(242,177,121);
     else if(arr[i][k]==16) fill(245,149,99);
     else if(arr[i][k]==32) fill(246,124,95);
     else if(arr[i][k]==64) fill(246,94,59);
     else if(arr[i][k]==128) fill(237,204,114);
     else if(arr[i][k]==256) fill(237,204,97);
     else if(arr[i][k]==512) fill(237,200,80);
     else if(arr[i][k]==1024) fill(230,212,75);
     else if(arr[i][k]==2048) fill(223,220,60);
     else if(arr[i][k]==4096) fill(217,230,50);
     else fill(210,240,40);
     rect(j,y+i*100+20*(i+1),100,100);
     if(arr[i][k]!=-1)
     {
       if(arr[i][k]<128) {fill(119,110,101); textSize(45); text(arr[i][k],j+30,y+i*100+20*(i+1)+65);}
       else {fill(249,246,242); textSize(35);text(arr[i][k],j+10,y+i*100+20*(i+1)+65);}
     }
    }
  }
}
public boolean isGameOver()
{
  for(int i=0;i<4;i++)
  {
    for(int j=0;j<4;j++)
    {
      if(arr[i][j]==-1) return false;
      if(j!=3) if(arr[i][j]==arr[i][j+1]) return false;  
      if(i!=3) if(arr[i][j]==arr[i+1][j]) return false;
    }
  }
  return true;
}
public void GenerateNewNumber()
{
  if(!isGameOver())
  {
    int Num=0;
    for(int i=0;i<4;i++)
      for(int j=0;j<4;j++)
        if(arr[i][j]!=-1) ++Num;
    if(Num==16) return;
    int x=(int)random(0,4),y=(int)random(0,4);
    while(arr[x][y]!=-1)
    {
      x=(int)random(0,4);y=(int)random(0,4);
    }
    float num=random(0,100);
    if(num<=90) arr[x][y]=2;
    else arr[x][y]=4;
  }
}

public void Union()
{
  if(direction==1) toUp();
  else if(direction==2) toDown();
  else if(direction==3) toRight();
  else if(direction==4) toLeft();
  direction=-1;
}

public void drawInformationBox(int x,int y)
{
  textSize(60);
  fill(119,110,101);
  text("2048",x+5,y+10);
  textSize(20);
  text("Made by Joker!",x+5,y+40);
  text("Join the numbers and get to the 2048 tile!",x+5,y+70);
}
public void newGameOrTryAgain(int x,int y,int Width,int Height)
{
  newX=x;newY=y;newWidth=Width;newHeight=Height;
  fill(0x8f,0x7a,0x66);
  rect(x+3,y-10,Width,Height);
  textSize(25);
  fill(242,237,238);
  if(flag) text("Try again",x+5,y+15);
  else text("New Game",x+5,y+15);
 
}
public void CursorChanged(int x,int y)
{
   if(mouseX>=x+3&&mouseX<=x+133&&mouseY>=y-10&&mouseY<=y+50||(mouseX>=newX+160&&mouseX<=newX+295&&mouseY>=newY&&mouseY<=newY+newHeight)||(mouseX>=newX&&mouseY>=newY&&mouseX<=newX+newWidth&&mouseY<=newY+newHeight))
    cursor(HAND);
   else cursor(ARROW);
}
public void drawScores(int x,int y,int Width,int Height)
{
  fill(0xbb,0xad,0xa0);
  rect(x+2,y+10,Width,Height);
  rect(x+100,y+10,Width,Height);
  fill(245);
  textSize(15);
  text("SCORE",x+15,y+30);
  text("BEST",x+118,y+30);
  text(cntScore,x+20,y+50);
  bestScore=bestScore>cntScore?bestScore:cntScore;
  text(bestScore,x+120,y+50);
}
public void responseAfterGameOver(int x,int y,int Width,int Height)
{
  if(!flag) return;
  fill(240,123);
  rect(x,y,Width,Height);
  textSize(45);
  fill(119,110,110);
  text("Game Over!",x+150,y+270);
  newGameOrTryAgain(x+120,y+330,135,40);
  
  fill(0x8f,0x7a,0x66);
  rect(x+280,y+320,135,40);
  textSize(25);
  fill(240);
  text("Quit!",x+300,y+345);
}
public void judgeSuceess()
{
   for(int i=0;i<4;i++)
  {
    for(int j=0;j<4;j++)
      if(arr[i][j]==2048) succeed++; 
  }
  println("The Value Of Succeed Is Eqaul To "+succeed);
}
public void responseAfterSucceed(int x,int y,int Width,int Height)
{
  if(succeed==1)
  {
  fill(240,123);
  rect(x,y,Width,Height);
  textSize(45);
  fill(119,110,110);
  text("Congratulations!",x+100,y+270);
  newGameOrTryAgain(x+120,y+330,135,40);
  
  fill(0x8f,0x7a,0x66);
  rect(x+280,y+320,135,40);
  textSize(25);
  fill(240);
  text("Quit!",x+300,y+345);
  //succeed=0;
  }
}

public void toUp()
{
 for(int i=0;i<4;i++)
 {
   int k=0;int temp[]=new int[4];
   for(int j=0;j<4;j++) temp[j]=-1;
   for(int j=0;j<4;j++)
   {
      if(arr[j][i]!=-1) temp[k++]=arr[j][i]; 
      arr[j][i]=-1;
   }
   for(int j=1;j<k;j++)
   {
     if(temp[j]==-1) continue;
     if(temp[j]==temp[j-1]&&temp[j]>-1)
     {
        temp[j-1]*=2;
        temp[j]=-1;
        cntScore+=temp[j-1];
     }
   }
   int l=0;
   for(int j=0;j<k;j++)
     if(temp[j]!=-1) arr[l++][i]=temp[j]; 
 }
}
public void toDown()
{
  for(int i=0;i<4;i++)
  {
   int k=0;int temp[]=new int[4];
   for(int j=0;j<4;j++) temp[j]=-1;
   for(int j=0;j<4;j++)
   {
      if(arr[j][i]!=-1) temp[k++]=arr[j][i]; 
      arr[j][i]=-1;
   }
   for(int j=k-1;j>=1;j--)
   {
     if(temp[j]==-1) continue;
     if(temp[j]==temp[j-1]&&temp[j]>-1)
     {
        temp[j]*=2;
        temp[j-1]=-1;
        cntScore+=temp[j];
     }
   }
   int l=3;
   for(int j=k-1;j>=0;j--)
     if(temp[j]!=-1) arr[l--][i]=temp[j]; 
  }
}
public void toRight()
{
   for(int i=0;i<4;i++)
  {
   int k=0;int temp[]=new int[4];
   for(int j=0;j<4;j++) temp[j]=-1;
   for(int j=0;j<4;j++)
   {
      if(arr[i][j]!=-1) temp[k++]=arr[i][j]; 
      arr[i][j]=-1;
   }
   for(int j=k-1;j>=1;j--)
   {
     if(temp[j]==-1) continue;
     if(temp[j]==temp[j-1]&&temp[j]>-1)
     {
        temp[j]*=2;
        temp[j-1]=-1;
        cntScore+=temp[j];
     }
   }
   int l=3;
   for(int j=k-1;j>=0;j--)
     if(temp[j]!=-1) arr[i][l--]=temp[j]; 
  }
}
public void toLeft()
{
   for(int i=0;i<4;i++)
  {
   int k=0;int temp[]=new int[4];
   for(int j=0;j<4;j++) temp[j]=-1;
   for(int j=0;j<4;j++)
   {
      if(arr[i][j]!=-1) temp[k++]=arr[i][j]; 
      arr[i][j]=-1;
   }
   for(int j=0;j<k-1;j++)
   {
     if(temp[j]==-1) continue;
     if(temp[j]==temp[j+1]&&temp[j]>-1)
     {
        temp[j]*=2;
        temp[j+1]=-1;
        cntScore+=temp[j];
     }
   }
   int l=0;
   for(int j=0;j<k;j++)
     if(temp[j]!=-1) arr[i][l++]=temp[j]; 
  }
}

public void keyPressed()
{
  
  if(key == CODED)
  {
    judgeSuceess();
    if(keyCode==UP) direction=1;
    else if(keyCode==DOWN) direction=2;
    else if(keyCode==RIGHT) direction=3;
    else if(keyCode==LEFT) direction=4;
    else direction=-1;
    if(!isGameOver()) GenerateNewNumber();
    else flag=true;
    judgeSuceess();
    Union();
  }
}
public void mousePressed()
{
   if(mousePressed&&mouseButton==LEFT)
  {
    if(mouseX>=newX&&mouseY>=newY&&mouseX<=newX+newWidth&&mouseY<=newY+newHeight)
      {
        Init();
      }
      if(!flag&&(succeed!=1)) return;
      if(mouseX>=newX+160&&mouseX<=newX+295&&mouseY>=newY&&mouseY<=newY+newHeight)
        exit();
  }
}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "My2048" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
