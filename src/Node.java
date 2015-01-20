import java.util.ArrayList;
import java.util.Comparator;


class Node
{
	int arr[][]=new int [3][3];
	int g;
	int h;
	Node parent;
	ArrayList <Node> children;
	void set_h(int arr[][])
	{

	}
	void set_g(int g)
	{
		this.g=g;
	}
	void update_g()
	{
		g++;
	}
	void printMatrix()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(this.arr[i][j]+" ");
			}
			System.out.println();
		}
	}

	public int invparity()
	{
		int inv=0;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				for(int m=(i*3+j+1);m<9;m++)
				{
					if( arr[m/3][m%3] != 0)
					{
						if(arr[i][j] > arr[m/3][m%3])
							inv ++;
					}
				}
			}
		}
		return inv;
	}
	int getHeuristic4(Node a)
	{
		int dist=0;
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				int s = this.arr[i][j];
				int I=0;
				int J=0;
				for(int k=0;k<3;k++)
				{
					for(int l=0;l<3;l++)
					{
						if(k==0 && l==0) continue;
						
						if(a.arr[k][l]==s)
						{
							I=k;
							J=l;
							break;
						}
					}
				}
				dist+=Math.abs(I-i);
				dist+=Math.abs(J-j);
			}
		}
		return dist; //This is Manhattan distance

	}

	int getHeuristic3(Node a)
	{


		int dist=0;
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if(a.arr[i][j]  == this.arr[i][j])
				{


				}
				else
					dist++;
			}
		}
		return dist;  //This is tile distance

	}
	
	int getHeuristic2(Node a)
	{
		return 0; //Uncomment this to get a BFS

	}

	// Linear-Conflict 
	int getHeuristic(Node a)
	{
		int dist=0;
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				int s = this.arr[i][j];
				int I=0;
				int J=0;
				for(int k=0;k<3;k++)
				{
					for(int l=0;l<3;l++)
					{
						if(a.arr[k][l]==s)
						{
							I=k;
							J=l;
							break;
						}
					}
				}
				dist+=Math.abs(I-i);
				dist+=Math.abs(J-j);
			}
		}

		int arr1[][] = new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				arr1[i][j]=a.arr[i][j];
			}
		}
		int arr2[][] = new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				arr2[i][j]=this.arr[i][j];
			}
		}
		out:for(int i=0;i<3;i++)
		{
			boolean flag=false;
			for(int j=0; j<3; j++)
			{
				for(int j1=0; j1<3; j1++)
				{
					if(arr1[i][j]==arr2[i][j1] && j!=j1)
					{
						if(arr1[i][(j+1)%3] == arr2[i][(j1+1)%3] && ((j-j1)*((j+1)%3-(j1+1)%3)<0)) 
						{
							dist++;
							flag=true;
							continue out;
							//break;
						}
						if(arr1[i][(j+2)%3] == arr2[i][(j1+1)%3] && ((j-j1)*((j+2)%3-(j1+1)%3)<0)) 
						{
							dist++;
							flag=true;
							continue out;
							//break;
						}
						if(arr1[i][(j+1)%3] == arr2[i][(j1+2)%3] && ((j-j1)*((j+1)%3-(j1+2)%3)<0))
						{
							dist++;
							flag=true;
							continue out;
							//break;
						}
						if(arr1[i][(j+2)%3] == arr2[i][(j1+2)%3] && ((j-j1)*((j+2)%3-(j1+2)%3)<0))
						{
							dist++;
							flag=true;
							continue out;
							//break;
						}
					}
				}
			}

		}
		return dist;
	}
	//returns -1 if arr[i][j] is not zero for all entries 

	int getZero()
	{
		int s=-1;
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(this.arr[i][j]==0)
				{
					s=3*i+j;
					break;
				}
			}
		}
		return s;
	}

	//equality of two nodes
	@Override
	public boolean equals(Object a)
	{
		if(a == null)
			return false;
		if (! (a instanceof Node) )
			return false;
		Node ob = (Node) a;

		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(this.arr[i][j]!=ob.arr[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	@Override
	public int hashCode()
	{
		return(this.arr[0][0]*this.arr[1][1]*this.arr[2][2] + this.arr[0][1]*this.arr[1][0]*this.arr[2][0] +
				this.arr[2][0]*this.arr[2][1]*this.arr[1][2] )   ;

	}
}

class nodeComparator implements Comparator<Node>
{
	@Override
	public int compare(Node a, Node b)
	{
		return (a.h + a.g - b.h - b.g );
	}
}
