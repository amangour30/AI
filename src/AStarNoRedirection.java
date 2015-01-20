import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.*;
public class AStarNoRedirection
{
	//inversions
	//Fischer Yates
	public static void shuffle(int arr[][]) 
	{
		for(int i=2; i>=0; i--)
		{
			for(int j=2;j>=0;j--)
			{
				int ind=i+j;
				Random randob = new Random();
				int rnd = randob.nextInt(ind+1);
				//SWAP
				int temp = arr[i][j];
				arr[i][j]=arr[rnd%3][rnd/3];
				arr[rnd%3][rnd/3] = temp;
			}
		}
	}



	public static void main(String args[])
	{
		//System.out.println("Hello World");
		//ArrayList <Node> ol = new ArrayList<Node>();

		Node goal = new Node();
		for(int i=0; i<3; i++)
		{
			for(int j=0;j<3;j++)
			{
				goal.arr[i][j]=((3*i)+j+1)%9;
			}
		}

		Node start = new Node();
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				start.arr[i][j]=3*i+j;
			}
		}
		/*	start.arr[0][0]=8;
		start.arr[0][1]=6;
		start.arr[0][2]=7;
		start.arr[1][0]=2;
		start.arr[1][1]=5;
		start.arr[1][2]=4;
		start.arr[2][0]=3;
		start.arr[2][1]=0;
		start.arr[2][2]=1;*/

		//Test 2

		/*	start.arr[0][0]=7;
		start.arr[0][1]=2;
		start.arr[0][2]=4;
		start.arr[1][0]=5;
		start.arr[1][1]=0;
		start.arr[1][2]=6;
		start.arr[2][0]=8;
		start.arr[2][1]=3;
		start.arr[2][2]=1;*/

		//Test 3


		start.arr[0][0]=1;
		start.arr[0][1]=2;
		start.arr[0][2]=3;
		start.arr[1][0]=0;
		start.arr[1][1]=4;
		start.arr[1][2]=5;
		start.arr[2][0]=6;
		start.arr[2][1]=7;
		start.arr[2][2]=8;
		try{	
			BufferedReader br = new BufferedReader(new FileReader("randomPerms.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				
				Comparator<Node> cmp = new nodeComparator();
				PriorityQueue <Node> ol = new PriorityQueue<Node>(10,cmp);
				Map	<Node, Node> cl = new HashMap<Node, Node>();
				int nodesExpanded = 0;
				
				//System.out.println();
				String[] splited = line.split("\\s+");
				if(splited[0].equals("end")) break;
				for(int i=0;i<splited.length;i++)
					start.arr[i/3][i%3]=Integer.parseInt(splited[i]);

				//start.printMatrix();

				//System.out.println("Start inversions "+start.invparity()+" final inversions "+ goal.invparity() );
				boolean term;
				if ((start.invparity() - goal.invparity() )%2 == 0)
				{
					//System.out.println("Goal reachable from start state");
					term = true;
				}
				else
				{
					System.out.println(line + " unreachable");
					term = false;
					//System.exit(0);
				}

				//reachability test ends here 


				start.g=0;
				start.h=start.getHeuristic4(goal);
				//System.out.println(start.h);

				ol.add(start);

				Node expand=new Node();
				expand = start;
				//System.out.println("Start inversions "+start.invparity()+" final inversions "+ goal.invparity() );
				if(term)
				{
					while(!ol.isEmpty())
					{
						//System.out.println("Start inversions "+start.invparity()+" final inversions "+ goal.invparity() );
						//System.out.println("CL size "+cl.size() +" ol size "+ol.size());

						ol.remove(expand);
						nodesExpanded++;
						cl.put(expand, expand);

						int J=expand.getZero()%3;
						int I=expand.getZero()/3; //position of blank tile

						for(int i=0;i<3;i++)
						{
							for(int j=0;j<3;j++)
							{
								int k=Math.abs(I-i)+Math.abs(J-j);// Distance from tile 0
								if(k==1)
								{
									//System.out.println(3*i+j);
									Node child=new Node();
									for(int m=0;m<3;m++)
									{
										for(int n=0;n<3;n++)
										{
											child.arr[m][n]=expand.arr[m][n];
										}
									}

									child.arr[i][j]=0;
									child.arr[I][J]=expand.arr[i][j];
									child.g=expand.g+1;
									child.h=child.getHeuristic4(goal);
									child.parent=expand;

									if (!(cl.containsKey(child))) // MARKER 1
									{
										//check whether the node exist in open list
										boolean flag1 = true;
										for (Node e : ol)
										{
											//if it does then change update the 
											if(e.equals(child) && e.g > child.g)
											{	
												e.g = child.g;
												e.parent = child.parent;
												flag1 = false;
												break;

											}
										}
										if(flag1)ol.add(child);
									}
									/* following code removes an element from close list and add backs to open list
									 * */
									//~ else 
									//~ {
										//~ //System.out.print("cl contains child");
										//~ if(cl.get(child).g > child.g)
										//~ {
											//~ cl.remove(child);
											//~ ol.add(child);
										//~ }
									//~ }

								}

							}
						}


						if(ol.isEmpty())
						{
							//System.out.println("failure goal can't be reached -- ol empty");
							break;
						}
						expand = ol.peek();// retrieve  min elt
						//expand.printMatrix();
						//System.out.println();
						boolean flag=true;
						for(int i=0;i<3;i++)
						{
							for(int j=0;j<3;j++)
							{
								if(expand.arr[i][j]!=goal.arr[i][j])
									flag = false;
							}
						}
						if(flag)
						{
							//System.out.println(expand.getZero()+" coming to the end");
							break;
						}
					}

					//System.out.println(cl.size());

					// get the path back to the start 
					int C=0;
					while(expand!=start)
					{
						C++;
						//expand.printMatrix();
						//System.out.println();
						expand=expand.parent;
					}
					System.out.print(line + " Nodes Expanded " + nodesExpanded);
					System.out.println(" " + C + " steps");
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
