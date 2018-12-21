/* Oscar Rodas

   WebHistory.java */

// A menu driven program that displays the websites users visited and other data.

import java.util.*; 
import java.text.*;
import java.io.*;

public class WebHistory extends Myq
{
	public static Scanner keyboard = new Scanner(System.in);
	public static final int SIZE = 20;						//max number of sites to visit
	public static String[] website = new String[SIZE];		//all websites visited, including duplicates
	public static int numSites = 0;							//number of sites visited
	public static final int SIZE2 = 5;						//max number of users
	public static String [][] info = new String[SIZE2][2];	//registered users
	public static int userCount = 0;						//number of users registered
	
	public static void main(String[] args)
	{
		prompt();
	}
	
	public static void prompt()							//main menu
	{
		String webs[] = new String[SIZE];
		String choice;
		choice= "4";
		//System.out.println("Welcome, new user. Please register your account.");//didn't add this because I thought it was better to not force the user to log in at the start.
		//register();
		while(!choice.equals("8"))
		{
			System.out.println("Enter an option from the menu:\n 1. Register User and create User login\n 2. Login existing user\n 3. Visit a website\n" +
			"4.Display complete browsing history of websites visited in chronological order\n 5. Check the history of websites visited in reverse chronological order\n 6. Number of times each website is visited\n 7. Most visited website\n 8. Logout\n");
			choice = keyboard.next();
			switch(choice)
			{
				case "1":
					register();
					stack1.reset();//resets data
					website = webs;//resets array
					numSites = 0;//resets counter
					System.out.println("");
					break;
				case "2":
					System.out.println("Enter your username");
					String user = keyboard.next();
					System.out.println("Enter your password");
					String pass = keyboard.next();
					login(user, pass);
					stack1.reset();//resets data
					website = webs;//resets array
					numSites = 0;//resets counter
					System.out.println("");
					break;
				case "3":
					stack1.popqueue();//
					stack1.dequeue();//makes sure that the data is not empty
					browse();
					System.out.println("");
					break;
				case "4":
					System.out.println("From left to right: ");
					stack1.popqueue();//
					stack1.dequeue();//makes sure that the data is not empty
					stack1.display();
					System.out.println("");
					break;
				case "5":
					System.out.println("From left to right: ");
					stack1.dequeue();//
					stack1.popqueue();//makes sure that the data is not empty
					stack1.display2(0);
					System.out.println("");
					break;
				case "6":
					printSites(0);
					System.out.println("");
					break;
				case "7":
					if(numSites > 0)
						printSites(1);
					System.out.println("");
					break;
				case "8":
					System.out.println("exited successfully");
					break;
				default:
					break;
			}
		}
	}
	
	public static String register()						//registers a user
	{
		String name1, name2, username, newUsername, passwd;
		int i, j, rand, temp = 0, exists = 0;
		
		if(userCount >= SIZE2)
		{
			System.out.println("too many users exist");
			return null;
		}
		
		System.out.println("Enter your first name:");
		name1 = keyboard.next();
		System.out.println("Enter your last name:");
		name2 = keyboard.next();
		System.out.println("Enter a username:");
		username = keyboard.next();
		
		for(i = 0; i<SIZE2; i++)
		{
			if(username.equals(info[i][0]) && i<SIZE2)//checks if the username has already been taken
				exists++;
			else if(i == SIZE2-1 && exists == 0)// if no match is found, username is registered
			{
				info[userCount][0] = username;//username is stored
				userCount++;
			}
			if((i == SIZE2-1) && (exists > 0))//else, user can not register
			{
				System.out.println(username + " already exists");
				System.out.println("Enter a username:");
				username = keyboard.next();
			}
		}
		
		if(exists > 0)
		{
			exists = 0;
			for(i = 0; i<SIZE2; i++)
			{
				if(username.equals(info[i][0]) && i<SIZE2)//checks if the username has already been taken
					exists++;
				else if(i == SIZE2-1 && exists == 0)// if no match is found, username is registered
				{
					info[userCount][0] = username;//username is stored
					userCount++;
				}
				if((i == SIZE2-1) && (exists > 0))//else, user can not register
				{
					System.out.println(username + " already exists");
					rand = randomUser();//random int
					rand = rand / (int)(1*Math.pow(10, name2.length()-1));
					newUsername = name2 + rand;//new username
					username = newUsername;
					info[userCount][0] = username;//username is stored
					userCount++;
					System.out.println("your randomly generated username is: " + username);
					exists = 0;
				}
			}
		}

		if(exists == 0)
		{
			System.out.println("Enter a password:");
			passwd = keyboard.next();
			while(passwd.length() < 8)//checks for correct length
			{
				System.out.println("password must be at least 8 characters long");
				System.out.println("Enter a password:");
				passwd = keyboard.next();
			}
			info[userCount-1][1] = passwd;//password is stored
		}
		return null;
	}
	
	public static int randomUser()									//randomly generates an int
	{
		Random randy = new Random();
		int randomNum = randy.nextInt(1000000)+1000000;
		return randomNum;
	}
	
	public static String login(String u, String p)					//logs in a user
	{
		int userMatches = 0, passwdMatches = 0;
		if(userCount == 0 || p.length()<8)//if havent registered or password is too short, do not bother checking
		{
			System.out.println("username and password conbination does not exist");
			return null;
		}
		
		for(int i = 0; i<userCount; i++)//if both username and password match, log in successful
		{
			if(u.equals(info[i][0]))
			{
				userMatches = 1;
				if(p.equals(info[i][1]))
				{
					passwdMatches = 1;
					System.out.println("login successful. Welcome back");
					return null;
				}
			}
		}
		
		if(	 userMatches == 0 || passwdMatches == 0 )//else login error
			System.out.println("username and password combination not found");	
		return null;
	}
	
	public static void browse()										//lets user enter sites
	{
		int i = 0;
		String con = new String();
		
		while(!con.equals("stop") && i< SIZE)
		{
			System.out.println("Enter a website you want to visit (Enter 'stop' to stop):");
			con = keyboard.next();
			if(!con.equals("stop"))//stores the sites
			{
				website[numSites] = con;
				stack1.enqueue(website[numSites]);
				numSites++;
				i++;
			}
		}
	}
	
	public static void printSites(int option)					//prints the number of times each site was visited
	{								
		String[] temp = new String[numSites];//for a purged array
		String[] purgedSites;//for a purged and downsized array
		int[] repeats = new int[numSites];
		int i,j, k,l, num = 0;
		
        for(i = 0; i <= numSites-1; i++)
		{
			for(j = 0; j<i; j++)
			{
				if(website[i].equals(website[j]))//the website is a duplicate, mark it as one
					repeats[i] +=1;
			}
			if( (repeats[i] <= 0))//else store it in a purged array
			{
				temp[num] = website[i];
				num++;
			}
		}
		
		purgedSites = new String[num];
		int[] duplicates = new int[num];
		
		for(i = 0; i <= num-1; i++)//copy the unique elements to a downsized array
		{
				purgedSites[i] = temp[i];
		}
		
		for(k = 0; k <= num-1; k++)
		{
			for(l = 0; l<= numSites-1; l++)
			{
				if(purgedSites[k].equals(website[l]))
				{
					duplicates[k]++;
				}
			}
			if(option!=1)//only prints for the number each site was visited
				System.out.println("The number of times " + purgedSites[k] + " was visited is " + duplicates[k]);
		}
		
		if(option==1)//only called for the most visited site
			{
				sortSites(duplicates, purgedSites);
			}
	}
	
	public static void sortSites(int array[], String a[]) 					// sorts the sites and displays the results
	{
			for (int i = (array.length - 1); i >= 0; i--)
			{
				for (int j = 0; j < i; j++)
				{
					if (array[j] < array[j+1])
					{
						int temp = array[j]; // sorting the number of repeats
						array[j] = array[j+1];
						array[j+1] = temp;
						String tempS = a[j]; // sorting the sites along with their corresponding repetitions
						a[j] = a[j+1];
						a[j+1] = tempS;
					}	 
				} 
			}
		System.out.println("The most visited site is " + a[0] + "; times it was visited: " +  array[0]);
	}	
}


class Myq 
{
    public static Myq stack1 = new Myq(); // this is a queue, not a stack. 
	public static Stack stack3 = new Stack(20);//1st stack
	public static Stack stack4 = new Stack(20);//2nd stack
	public static int count = 0;
	
	public void enqueue(String item) 
	{
        stack3.push(item);
		
		if(!stack3.isFull())
			count++;
    }
	
	public String popqueue()							//transfer data between stacks
	{
		String returnvalue = null;
		//if(stack3.isEmpty())
		//{
			//System.out.println("Stack underflow");
			//return returnvalue;
		//}
		
        if (stack4.isEmpty()) //if the 2nd stack is empty, fill it with the 1st stack
		{ 
            while (!stack3.isEmpty()) 
			{
               stack4.push(stack3.pop());
            }
        }
		return returnvalue;
	}

    public String dequeue() 							//transfer data between stacks
	{
		String returnvalue = null;
		//if(stack3.isEmpty())
		//{
			//System.out.println("Stack underflow");
			//return returnvalue;
		//}
		
        if (stack4.isEmpty()) { //if the 2nd stack is empty, fill it with the 1st stack
            while (!stack3.isEmpty()) {
               stack4.push(stack3.pop());
            }
        }
		
		//returnvalue = stack4.pop(); //pop out the front
		if (stack3.isEmpty()) { // fill the 1st stack with the second stack minus the front
            while (!stack4.isEmpty()) {
               stack3.push(stack4.pop());
            }
        }
        return returnvalue;
    }
	
	public String reset() 							//reset data
	{
		String returnvalue = null;
		if(stack3.isEmpty())
		{
			System.out.println("Stack underflow");
			//return returnvalue;
		}
		
        if (stack4.isEmpty()) { //if the 2nd stack is empty, fill it with the 1st stack
            while (!stack3.isEmpty()) {
               stack4.push(stack3.pop());
			   returnvalue = stack4.pop(); //pop out the front
            }
        }
		
		//returnvalue = stack4.pop(); //pop out the front
		if (stack3.isEmpty()) { // fill the 1st stack with the second stack minus the front
            while (!stack4.isEmpty()) {
               stack3.push(stack4.pop());
			   returnvalue = stack3.pop();
            }
        }
        return returnvalue;
    }
	
	public void display() 								//for the order sites are visited with repeats
	{
		stack3.display();
		//System.out.println(count);
	}
		
	public void display2(int include) 					//for the reverse order visited without repeats
	{
		stack4.purge(include);//
		//System.out.println(count);
	}
}

class Stack 
{
    String[] stack1;
	
    int top;
    int size;
    
    public Stack( int arraySize)
	{
        size = arraySize;
        stack1 = new String[size];
		
        top = -1;
    }
    
    public void push (String value)
	{
        if(top == size - 1)
		{
            System.out.println("Stack overflow");
		}
        else
        {
            top = top + 1;
            stack1[top] = value;
        }
    }
    
    public String pop() 
	{
        String returnValue = null;
        if(!isEmpty())
        {
            returnValue = stack1[top];
            top--;
        } 
        else
            System.out.println("Stack underflow");
        return returnValue;
    }
   
    public boolean isEmpty() 
	{
        return top == -1;
    }
	
	public boolean isFull() 
	{
        return top == size - 1;
    }
    
    public void display() 
	{
        for(int i = 0; i <= top; i++)
            System.out.print(stack1[i] + "|");
			
        System.out.println(" "); 
    }
	
	  public void purge(int include) 										//purges the data
	{
		int[] repeats = new int[stack1.length];
		int j, k;
		
        for(int i = 0; i <= top; i++)
		{
			for(j = 0; j<i; j++)
			{
				if(stack1[i].equals(stack1[j]))
					repeats[i] +=1;
			}
			if( (repeats[i] <= 0))
			{
				System.out.print(stack1[i] + "|");
			}
		}
        System.out.println(" "); 
    }
    
    public String peek () 
	{
        String returnValue = null;
        if(!isEmpty())
        {
            returnValue = stack1[top];
            
        } 
        else
            System.out.println("Stack underflow");
        return returnValue;
    }
}