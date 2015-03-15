
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class EditDistance {
 
    public static int[][] editDistance(String str1,String str2,int mismatchPenalty,int gapPq,int gapPr,int distance[][])
    {            
		int i,j;
		distance[0][0]=0;
        for (i = 1; i <= str1.length(); i++)                                 
            distance[0][i] =gapPq+i*gapPr; 
        //distance[0][i-1]+=gapPq;
        
        for (j = 1; j <= str2.length(); j++)                                 
            distance[j][0] = gapPq+j*gapPr;
       // distance[j-1][0]+=gapPq;      
        
                
        int case1,case2,case3,gaph,gapv;
        
        for (i = 1; i <= str2.length(); i++) 
        {
        	gaph=1;gapv=1;
            for (j = 1; j <= str1.length(); j++)
            {
            	case1=distance[i - 1][j] +(gapv==1? gapPq+gapPr:gapPr);
            	case2=distance[i][j - 1] +(gaph==1? gapPq+gapPr:gapPr);
            	case3=distance[i - 1][j - 1] + ((str1.charAt(j - 1) == str2.charAt(i - 1)) ? 0 : mismatchPenalty);
            	
            	if(case1<case3 && case1<case2)
            	{
            		distance[i][j] =case1;
            		gapv++;
            		gaph=1;
            			
            	}
            	else if(case2<case3 && case2<case1)
            	{
            		distance[i][j] =case2;
            		gaph++;
            		gapv=1;
            	}
            	else
            	{
            		distance[i][j]=case3;
            		gaph=1;gapv=1;
            	}
            	        
            }
        }
        return distance;    
    }
                                                                            
	
 public static void FinalPattern(int distance[][], String str1,String str2, int gapPq,int gapPr,int misP)
    {
    	int row=distance.length-1;
    	int col=distance[0].length-1;
    	int i=row,j=col,gapv=1,gaph=1;
    	Stack<String> stk=new Stack<String>();
    	
    	System.out.println("Edit Distance: "+ distance[row][col]);
    	
    	while(i>0 && j>0)
    		{
    			char s1=str1.charAt(j-1),s2=str2.charAt(i-1);
    			int diff=distance[i][j]-distance[i-1][j-1];
    			//System.out.println(s1+" "+s2+" "+diff+" "+misP);
    			
    			
    			if(s1==s2 && diff==0)
    				{
    				stk.push(s1+" "+s2+" 0");
    				i--; j--;
    				gapv=1;gaph=1;
    				}
    			else 
    				{
    				if(diff==misP)
    					{
    					stk.push(s1+" "+s2+" "+misP);
    					i--; j--;
    					gapv=1;gaph=1;
    					//ed+=misP;
    					}
    				else 
    					{
    						if(distance[i-1][j]>distance[i][j-1])
    						{
    							if(gapv==1)
    								{
    								stk.push(str1.charAt(j-1)+" - "+(gapPq+gapPr));    
    								//ed+=gapPq+gapPr;
    								}
    							else
    								{
    								stk.push(str1.charAt(j-1)+" - "+gapPr);
    								//ed+=gapPr;
    								}
    								
    							j--;
    							gapv++;
    						}
    						else
    						{
    							if(gaph==1)
    								{
    								stk.push("- "+str2.charAt(i-1)+" "+(gapPq+gapPr));    	
    								//ed+=gapPq+gapPr;
    								}
    							else
    								{
    								stk.push("- "+str2.charAt(i-1)+" "+gapPr);
    								//ed+=gapPr;
    								}
    							i--;
    							gaph++;
    						}    							    								   						
    						
    					}
    				}
    				
    		}
 
	    while(j>=1)
	    	{
				stk.push(str1.charAt(j-1)+" - "+(gapPq+gapPr));
				//ed+=gapPq+gapPr;
				j--;
	    	}
    		
	    //System.out.println("ed: "+ed);
    	while(!stk.empty())
    		System.out.println(stk.pop());
    }
    
	public static void main(String args[])throws IOException{
		
		try{
			FileReader fr = new FileReader(args[0]);
		    BufferedReader br = new BufferedReader(fr);
		    String line=br.readLine();
		    String[] penalty=line.split(" ");
			
		    String temp1=br.readLine();
		    String temp2=br.readLine();
		 
		    String str1=temp1.length()>=temp2.length()?temp1:temp2;
		    String str2=temp1.length()<temp2.length()?temp1:temp2;
		    
		   // System.out.println(str1);
		  		    
		    int gapPq=Integer.parseInt(penalty[1]);
		    int gapPr=Integer.parseInt(penalty[2]);
		    int mismatchPenalty=Integer.parseInt(penalty[0]);
		    
		    int[][] distance = new int[str2.length() + 1][str1.length() + 1];
		    
		    distance=editDistance(str1,str2,mismatchPenalty,gapPq,gapPr,distance);
		    //System.out.print(distance[distance.length-1][distance[0].length-1]);
		    /*for(int i=200;i<211;i++)
		    {
		    	System.out.println("");
		    	for(int j=200;j<210;j++)
		    		System.out.print(distance[i][j]+" ");
		    }*/
		    FinalPattern(distance,str1,str2,gapPq,gapPr,mismatchPenalty);
		    
		    br.close();
		    
		}
		catch(Exception io){
			System.out.println(io);
		}
	
	}
}
