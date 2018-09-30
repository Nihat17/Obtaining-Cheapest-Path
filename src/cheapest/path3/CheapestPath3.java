/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheapest.path3;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author marshall
 */
public class CheapestPath3 {

    /**
     Task: Finding the cheapest path in a given matrix of data
     * Group members: Nihat Allahverdiyev (46219), Giorgi Merabishvili (46344)
     * Used algorithm Dijkstra's algorithm
     */
    static double total = 0;    
    static int initPosX = 0, initPosY = 0, lastPosX = 0, lastPosY = 0;
    public static void main(String[] args) throws FileNotFoundException {
               
        Scanner scan = new Scanner(System.in);
        String data = "", contents = "";
        double [][] matrix= null;
        boolean tryCatch = false;
        do{
          
          tryCatch = false;
          try{
                System.out.println("Please enter the location of txt file:");
                data = scan.nextLine();
                File file = new File(data);   /* Here it creates file object and gets the location of the file that contains numbers */
                contents = new Scanner(file).useDelimiter("\\Z").next();
            }
        
          catch(Exception e){
            
            tryCatch = true;
            JOptionPane.showMessageDialog(null, "Please choose correct txt file location.");
          }
        
        }while(tryCatch == true);
        
        
        int x = 0;        /* These variables are the dimensions */                                     
        int y = 0;        
        if(tryCatch == false){
       
         
           tryCatch = false;
           
           try{
                System.out.print("Please enter initialize position:");
                initPosX = scan.nextInt() - 1;  /* Receives the location of initalize position in matrix by user */
                initPosY = scan.nextInt() - 1;  
                
                System.out.print("Please enter positins of the last point:");
                lastPosX = scan.nextInt() - 1;  /* Receives the last point that given by user */
                lastPosY = scan.nextInt() - 1;
              }
           catch(Exception d){
              tryCatch = true;
              JOptionPane.showMessageDialog(null, "Please insert numbers as regularly as written on documentation");                              
             } 
           
        if(tryCatch == false){
            
        String [] input = new String[contents.length()];  /* This file is filled with string numbers 
                                                           which received from the txt file given by user */            
        int j = 0;                       
        int i = 0;
        
        /*Through this loop 
        * it determine what are the dimensions of the matrix
        * and while cleaning spaces and semicolons 
        * it stores them into string array which is called input 
        */
        
              
        
        try{
            
        boolean r = false;
        while(i < contents.length()){          
          if(i == contents.length()){               
              break;
          }
            
          r = false;
          
          while(j != 1000){
            if(r == false) input[j] = contents.substring(i , ++i);
            if(r == true) input[j] += contents.substring(i , ++i);
                           
              if((contents.substring(i, i + 1).contains(" ") == true)||
                  (contents.substring(i, i + 1).contains(";") == true)){                  
                   
                if((contents.substring(i, i + 1).contains(" ")) && (x == 0)){
                    y++;
                }
                 if(contents.substring(i, i + 1).contains(";")){
                    x++;
                    if(i == contents.length() - 2 || i == contents.length() - 1)
                        i++;
                  }
                                        
                 j++;
                 i++;
                 break;
               }
               r = true;
           }
             
         }
        matrix = new double[x][++y];  /* This is the array which will be stored all the numbers  */                       
        j = 0;    
        boolean ch = false;
         /* Through this loops it converts from string to double 
            and stores the numbers into array called matrix */
        
        for( i = 0; i < x; i++){        
            for(int c = 0; c < y; c++){ 
                if(input[j] == null){
                    ch = true;
                    break;
                }
                matrix[i][c] = Double.parseDouble(input[j++]);
            }
            if(ch == true) break;
        }
        }
        
        catch(Exception e){
            tryCatch = true;
            JOptionPane.showMessageDialog(null, "Please enter the matrix into txt file as regularly as written on documentation and repeat the process");
        }
        
        if(tryCatch == false){
            
           String output = "";
           output = solution(y,x,initPosY,initPosX,lastPosY,lastPosX,matrix);
        
           String[][] outMatr = new String[x][y];
           outMatr = convertOutput(output, x, y, initPosX, initPosY,lastPosX,lastPosY);
           boolean f = false;
           
           for(int k = 0; k < x; k++){      
            
             f = true;
             for(int z = 0; z < y; z++){
                
               if(z == y - 1){
                  System.out.println(outMatr[k][z]);
                  break;
                }
               System.out.print(outMatr[k][z]);
              
               }
            }
           System.out.println("Total cost:" +total);
        }
    }
    }
    }
     static int currPointY, currPointX;                     //After getting initialize positions I stored them into                                 
     static List<Hashtable> costAndStrCon = new ArrayList<>();
     
     /*
      * 
      */
     public static String previous_path_returner(Double key){
         String strPath = "";
        
         for(Hashtable e : costAndStrCon){
            if(e.containsKey(key)){
              strPath = (String) e.get(key);
            }         
         } 
         return strPath;
     }

    static List<Double> sameCostAdded = new ArrayList<>();
    /*
     * In every iteration before inserting cost number and the direction of it
     * to hashtable called costAndStr it checks through this method whether that
     * same cost added number exists in that hashtable, if does so, it gets 
     * the directions of both into strings and compare them according to their locations
     * and after choosing it inserts the chosen cost added number and it's directions
     * into the hashtable
     */
    public static Hashtable checkSameness_returnCloser(double sameKeyHash,String currStr){
         int [] locOfFirst = new int[2];
         int [] locOfSecond = new int[2];
         
         boolean ch = false, firOrSec = false;
         
         String output, strOfFir = "", strOfSec = "";
         int i = 0;
         
         Hashtable replace = new Hashtable();
         Hashtable delete = new Hashtable();
         
         String replaceStr = "";
         delete.put(sameKeyHash, currStr);
         
         for(Hashtable e : costAndStrCon){
             if(e.containsKey(sameKeyHash)){
                 locOfFirst = locationOut((String) e.get(sameKeyHash));
                 strOfFir = (String) e.get(sameKeyHash);
                 delete = e;
             }
         }
                  
         strOfSec = currStr;
         locOfSecond = locationOut(strOfSec);
         replace = delete;
                
          if((!strOfFir.equalsIgnoreCase("")) && (!strOfFir.equalsIgnoreCase(currStr))){
            firOrSec = seek_for_closer(locOfFirst, locOfSecond);     
            if(firOrSec == true){ 
              replace.put(sameKeyHash, currStr);
              costAndStrCon.remove(delete);
              replace.remove(delete);        
            }
          }
          
         output = (firOrSec == false)? strOfFir : strOfSec;
         return replace;
     }
    
     public static boolean seek_for_closer(int[] fArr, int [] sArr){
         int diff1X = (fArr[0] > lastPosX)? fArr[0] - lastPosX : lastPosX - fArr[0];
         int diff1Y = (fArr[1] > lastPosY)? fArr[1] - lastPosY : lastPosY - fArr[1];
         int diff2X = (sArr[0] > lastPosX)? sArr[0] - lastPosX : lastPosX - sArr[0];
         int diff2Y = (sArr[1] > lastPosY)? sArr[1] - lastPosY : lastPosY - sArr[1];
         
         boolean output = ((diff1X + diff1Y) < (diff2X + diff2Y))? false : true;   // Means if first one is closer send false or vice versa
         return output;
     }                              
    /*
     * In this method on first step it checks all 8 directions
     * and if that location is not out of matrix, by means it can't be 
     * negative and bigger than X and Y, then it adds the costs into array 
     * which will be used for comparison, and it also adds direction
     * and cost into hashtable called costAndStr.
     * On second step it compare all the costs which were added to array before
     * and choose the least one, and stores it into visited list
     * to not repeat that step in further iterations. Then it grabs the direction
     * by using the chosen cost added number from hashtable,
     * and by using that direction and locationOut it gets the current location 
     * in the matrix, and stores it into variables called currPointX, currPointY
     * in order to proceed next iteration.
     */                                                      
     public static String solution( int Y, int X,int initPosY,int initPosX, int lastPosY,
        int lastPosX, double matrix[][]){
       
        int m = 0;       
        int[][] idMatrix = new int[X][Y]; 
        
        for(int i = 0; i < X; i++){
            for(int c = 0; c < Y; c++)
                idMatrix[i][c] = m++;
        }
        
        m = 0;
        double nextPoints [] = new double[10000000];
        
        for(int i = 0; i < nextPoints.length; i++)      //Here I decided to store this array with -1 first
            nextPoints[i] = -1;                        // Due to comparison. -1 won't make any confusion coz we don't use negative numbers        
                      
        Hashtable trackTable = new Hashtable();         /* we'll use two doubles (nextNode, nextNodeC) one as a root
                                                           And the other for cost added number */
        ArrayList<Integer> listOfVisited = new ArrayList<>();     /* This hashtable contains the visited nodes */
        
        listOfVisited.add(idMatrix[initPosX][initPosY]);        
      
        String road1 = "";                
        String mainRoad = null;
        currPointY = initPosY;                          // After getting initialize positions it stores them into
        currPointX = initPosX;                          // Current point Y axes and X axes and these values will be changed
                                                        // Simultaneously in order to check the points around
        double nextNode = 0;                            // This is the node which the agent will move next
        double nextNodeC = 0;                           // This value will be the cost which is the least in comparison with others        
        Hashtable costAndStr;                           // This hashtable contains cost added value as key and the direction strings                       
        Hashtable replacer;        
        do{
                                                                                                                          
        int copyCurrPointX = currPointX, copyCurrPointY = currPointY;        
        boolean found = false;
        String direction = "";
        
        for(int i = 1; i < 9; i++){    
          switch(i){
              
              case 1: 
                  copyCurrPointX = currPointX - 1;
                  copyCurrPointY = currPointY - 1;
                  direction = "U-L";                  
                  break;
                  
              case 2: 
                  copyCurrPointX = currPointX - 1;
                  copyCurrPointY = currPointY;
                  direction = "U";
                  break;    
                  
              case 3: 
                  copyCurrPointX = currPointX - 1;
                  copyCurrPointY = currPointY + 1;
                  direction = "U-R";
                  break;    
                  
              case 4: 
                  copyCurrPointX = currPointX;
                  copyCurrPointY = currPointY + 1;
                  direction = "R";
                  break;    
                  
              case 5: 
                  copyCurrPointX = currPointX + 1;
                  copyCurrPointY = currPointY + 1;
                  direction = "D-R";
                  break;    
                  
              case 6: 
                  copyCurrPointX = currPointX + 1;
                  copyCurrPointY = currPointY;
                  direction = "D";
                  break;    
              
              case 7: 
                  copyCurrPointX = currPointX + 1;
                  copyCurrPointY = currPointY - 1;
                  direction = "D-L";
                  break;    
                  
              case 8: 
                  copyCurrPointX = currPointX;
                  copyCurrPointY = currPointY - 1;
                  direction = "L";
                  break;    
          }
    
        
          if((copyCurrPointX >= 0) && (copyCurrPointY >= 0) && 
                  (copyCurrPointX < X) && (copyCurrPointY < Y)){  
              
            if(!listOfVisited.contains(idMatrix[copyCurrPointX][copyCurrPointY])){                                             
               if((copyCurrPointX == lastPosX) && (copyCurrPointY == lastPosY)){     // It checks whether next point is the final point or not                                                            
                                                                                     // If it's it will stopped the iteration and gives the output                                          
                    found = true;
                    break;                                                                            
              }
               
               nextPoints[m]++;
               nextPoints[m++] = nextNodeC + matrix[copyCurrPointX][copyCurrPointY];  // It stores all the cost added numbers into this array
               nextPoints[m - 1] = round(nextPoints[m - 1],2);                      
                          
               costAndStr = new Hashtable();     
                                       
               replacer = new Hashtable();
             
               replacer = checkSameness_returnCloser(nextPoints[m - 1], 
                     previous_path_returner(round(nextPoints[m - 1] - 
                        matrix[copyCurrPointX][copyCurrPointY],2)) + direction);
            
               if(!costAndStrCon.contains(replacer))
                  costAndStrCon.add(replacer);
                         
               trackTable.put(nextPoints[m - 1], matrix[copyCurrPointX][copyCurrPointY]);   // This hashtable contains the cost added number                                  
              }                                                                             // And the cost itself            
           }
       }
       if(found == true)
           break;
        
       boolean check = false;
       int h = 0;
       /* Through this loops it compares the cost added numbers
          and picks the least one */
       
        for(int i = 0; i < nextPoints.length; i++){   
            check = false;                            
            for(int c = 0; c < nextPoints.length; c++){                
                if(i == c)
                    continue;
                
                if(nextPoints[i] > nextPoints[c] && nextPoints[c] > 0.0){                    
                    break;
                }
                                                                    
                if(c == nextPoints.length - 1 && nextPoints[i] > 0){                                                            
                    nextNodeC = nextPoints[i];                          
                    nextPoints[i] = -1;        
                    h = i;
                    total = nextNodeC;
                    check = true;
                    break;
                }
            }             
            
            if(check == true) break;
        }
        
        int [] arrayLocation = new int[2];
        nextNode = (double) trackTable.get(nextNodeC);        //by using trackTable it tracks what is the genuine number of cost added number
        
        for(Hashtable e : costAndStrCon){
            if(e.containsKey(nextNodeC)){
                
                arrayLocation = locationOut((String) e.get(nextNodeC));      
                road1 = (String) e.get(nextNodeC);
                break;
            }
        }               
        
        currPointX = arrayLocation[0];
        currPointY = arrayLocation[1];
        check = false;
                          
        if(check == true)               
          nextPoints[h] = -1;              
               
        listOfVisited.add(idMatrix[currPointX][currPointY]);          // it adds all the nodes to list and doesn't repeat that route       
                            
      }while(mainRoad == null);
        return road1;
    
     }
     
   /* 
    * In this method it finds the current X and Y in matrix 
    * by using directions such as D (down), L (Left).
    * On first step it gets the initialize points 
    * And string which is the directions for that point 
    * from initilize point.
    */  
   public static int[] locationOut(String str){
              
       int [] out = {initPosX,initPosY};
       String subStr = "";
       
       do{
           if(str.length() > 1){
               subStr = str.substring(str.length() - 1);
               str = str.substring(0,str.length() - 1);
           }
           else{ 
               subStr = str;
               str = "";
           }
           
           switch(subStr){
               case "U-L": 
                 out[0]--;
                 out[1]--;
                 break;
                 
               case "U": 
                 out[0]--;                                  
                 break;
                 
               case "U-R": 
                 out[0]--;
                 out[1]++;                                  
                 break;  
                 
               case "R":                  
                 out[1]++;
                 break;
                 
               case "D-R":                                   
                 out[0]++;
                 out[1]++;
                 break;  
                 
               case "D":                  
                 out[0]++;
                 break;  
                 
               case "D-L":                                   
                 out[0]++;
                 out[1]--;
                 break; 
                 
                case "L":                  
                 out[1]--;
                 break; 
           }
                                 
       }while(str != "");
       return out;
   }  
  /*
   * This method is used to round numbers
   * After every calculation
   */   
  public static double round(double value, int places) {   
      
      if(places < 0) throw new IllegalArgumentException();
      BigDecimal bd = new BigDecimal(value);
        
      bd = bd.setScale(places, RoundingMode.HALF_UP);
      return bd.doubleValue();
    }
    /*
     * In this method it simply converts strings such as D (down)
     * D-R down-right to matrix which contains just + and -
     */
    public static String[][] convertOutput(String output, int X, int Y, int initPosX, int initPosY, int lastPosX, int lastPosY){
 
        String matOut[][] = new String [X][Y];
        
        for(int i = 0; i < X; i++)
            for(int c = 0; c < Y; c++)
                matOut[i][c] = "-";
        
        
        matOut[initPosX][initPosY] = "+";
        matOut[lastPosX][lastPosY] = "+";
        String str = "";
        int j = 0;
        boolean ch = false;
        
        while(j <= output.length() - 1){    
            
           if(ch == true) break;
           
           if(j != output.length() - 1){
               str = output.substring(j, ++j);           
               
            if("-".equals(output.substring(j, j + 1))){
                  str += output.substring(j, j + 2);
                  j+=2;
            }
          }
           
           else{ str = output.substring(output.length() - 1); ch = true;}
           
               switch(str){
                   case "U-L":
                       matOut[--initPosX][--initPosY] = "+"; break;                        
                       
                   case "U":
                        matOut[--initPosX][initPosY] = "+"; break;                   
                        
                   case "U-R":
                        matOut[--initPosX][++initPosY] = "+"; break;     
                        
                   case "R":
                        matOut[initPosX][++initPosY] = "+"; break;    
                        
                   case "D-R":
                        matOut[++initPosX][++initPosY] = "+"; break;
                        
                   case "D":
                        matOut[++initPosX][initPosY] = "+"; break;      
                        
                   case "D-L":
                        matOut[++initPosX][--initPosY] = "+"; break;     
                        
                   case "L":
                        matOut[initPosX][--initPosY] = "+"; break;      
               }
        }
           return matOut;
      }             
}