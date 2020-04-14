import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;                    //These all are the header files

class Food implements Serializable
{
    int itemno,quantity;              //Declaring the variables   
    float price;
    Food(int itemno,int quantity)
    {
        this.itemno=itemno;          //Using this keyword to intialise the value
        this.quantity=quantity;      //to the instance variables as variable name are same
        switch(itemno)
        {
            case 1:price=quantity*50;
                break;
            case 2:price=quantity*60;
                break;
            case 3:price=quantity*70;
                break;
            case 4:price=quantity*30;
                break;
        }
    }
}
class Luxuryroom implements Serializable     //we are using Serializable as it will help to store my object
{                                            //it is just a marker interface so that why there is no declarative 
    					     //that is we donnot have to implement anything
    String name,contact,gender;        
    ArrayList<Food> food =new ArrayList<>();   
    Luxuryroom()
    {
        this.name="";
    }
    Luxuryroom(String name,String contact,String gender)
    {
        this.name=name;
        this.contact=contact;
        this.gender=gender;
    }
}
class NotAvailable extends Exception
{
    public String toString()
    {
        return "Not Available !";
    }
}
class holder implements Serializable
{
    Luxuryroom arr1[]=new Luxuryroom[10]; //LuxuryRoom
}
class Hotel
{
    static holder ob=new holder();
    static Scanner sc = new Scanner(System.in);
    static void CustDetails(int rn)
    {
        String name, contact, gender;
        System.out.print("\nEnter customer name: ");
        name = sc.next();
        System.out.print("Enter contact number: ");
        contact=sc.next();
        System.out.print("Enter gender: ");
        gender = sc.next();       
	ob.arr1[rn]=new Luxuryroom(name,contact,gender);
    }
    static void bookroom()
    {
        int j;
        int rn;
        System.out.println("\nChoose room number from : ");
                for(j=0;j<ob.arr1.length;j++)
                {
                    if(ob.arr1[j]==null)
                    {
                        System.out.print(j+1+",");
                    }
                }
                System.out.print("\nEnter room number: ");
                try{
                rn=sc.nextInt();
                rn--;
                if(ob.arr1[rn]!=null)
                    throw new NotAvailable();
                CustDetails(rn);
                }
                catch(Exception e)
                {
                    System.out.println("Invalid Option");
                    return;
                }
        System.out.println("Room Booked");
    }
    static void features()
    {
	System.out.println("Number of double beds : 1\nAC : Yes\nFree breakfast : Yes\nCharge per day:4000 ");
    }
    static void availability()
    {
      int j,count=0;
                for(j=0;j<10;j++)
                {
                    if(ob.arr1[j]==null)
                        count++;
                }
        System.out.println("Number of rooms available : "+count);
    }
    static void bill(int rn,int rtype)
    {
        double amount=0;
        String list[]={"Sandwich","Pasta","Noodles","Coke"};
        System.out.println("\n*******");
        System.out.println(" Bill:-");
        System.out.println("*******");
                amount+=4000;
                    System.out.println("\nRoom Charge - "+4000);
                    System.out.println("\n===============");
                    System.out.println("Food Charges:- ");
                    System.out.println("===============");
                     System.out.println("Item   Quantity    Price");
                    System.out.println("-------------------------");
                    for(Food obb:ob.arr1[rn].food)
                    {
                        amount+=obb.price;
                        String format = "%-10s%-10s%-10s%n";
                        System.out.printf(format,list[obb.itemno-1],obb.quantity,obb.price );
                    }               
        System.out.println("\nTotal Amount- "+amount);
    }
    static void deallocate(int rn,int rtype)
    {
        int j;
        char w;               
                if(ob.arr1[rn]!=null)
                    System.out.println("Room used by "+ob.arr1[rn].name);                
                else 
                {    
                    System.out.println("Empty Already");
                        return;
                }
                System.out.println("Do you want to checkout ?(y/n)");
                 w=sc.next().charAt(0);
                if(w=='y'||w=='Y')
                {
                    bill(rn,rtype);
                    ob.arr1[rn]=null;
                    System.out.println("Deallocated succesfully");
                }
		//System.out.println("\nPlease enter your valuable feedback");
		//String feed=sc.next();
    }    
    static void order(int rn,int rtype)
    {
        int i,q;
        char wish;
         try{
             System.out.println("\n==========\n   Menu:  \n==========\n\n1.Sandwich\tRs.50\n2.Pasta\t\tRs.60\n3.Noodles\tRs.70\n4.Coke\t\tRs.30\n");
        do
        {
            i = sc.nextInt();
            System.out.print("Quantity- ");
            q=sc.nextInt();
		ob.arr1[rn].food.add(new Food(i,q));                                               
              System.out.println("Do you want to order anything else ? (y/n)");
              wish=sc.next().charAt(0); 
        }while(wish=='y'||wish=='Y');  
        }
         catch(NullPointerException e)
            {
                System.out.println("\nRoom not booked");
            }
         catch(Exception e)
         {
             System.out.println("Cannot be done");
         }
    }
}
class write implements Runnable
{
    holder ob;
    write(holder ob)
    {
        this.ob=ob;
    }
    public void run() {
          try{
        FileOutputStream fout=new FileOutputStream("backup");
        ObjectOutputStream oos=new ObjectOutputStream(fout);
        oos.writeObject(ob);
        }
        catch(Exception e)
        {
            System.out.println("Error in writing "+e);
        }            
    }
}
class Project3 {
    public static void main(String[] args)throws IOException{       
        try
        {           
        File f = new File("backup");
        if(f.exists())
        {
            FileInputStream fin=new FileInputStream(f);
            ObjectInputStream ois=new ObjectInputStream(fin);
            Hotel.ob=(holder)ois.readObject();
        }
        Scanner sc = new Scanner(System.in);
        int ch,ch2;
        char wish;
        x:
        do{
        System.out.println("\nEnter your choice :\n1.Display room details\n2.Display room availability \n3.Book\n4.Order food\n5.Checkout\n6.Write Review\n7.Exit\n8.Read Review");
        ch = sc.nextInt();
        switch(ch){
            case 1: Hotel.features();
                break;
            case 2: Hotel.availability();
                break;
            case 3: Hotel.bookroom();                     
                break;
            case 4:
                 System.out.print("Room Number -");
                     ch2 = sc.nextInt();
                     if(ch2>10)
                         System.out.println("Room doesn't exist");
                     else if(ch2>0)
                         Hotel.order(ch2-1,1);
                     else
                         System.out.println("Room doesn't exist");
                     break;
            case 5:                 
                System.out.print("Room Number -");
                     ch2 = sc.nextInt();
                     if(ch2>10)
                         System.out.println("Room doesn't exist");
                     else if(ch2>0)
                         Hotel.deallocate(ch2-1,1);
                     else
                         System.out.println("Room doesn't exist");          
break;
	    case 6:
		System.out.println("CUSTOMER REVIEWS");
                oute();

		break;
   
            case 7:break x;  
            case 8:
        		    FileInputStream fp=null;
                    fp=new FileInputStream("D:/review.txt");
        int c=0;
        while(c!=-1)
        {
        c=fp.read();
        if(c==-1)
        break;
        System.out.print((char)c);
        }
        }           
            System.out.println("\nContinue : (y/n)");
            wish=sc.next().charAt(0); 
            if(!(wish=='y'||wish=='Y'||wish=='n'||wish=='N'))
            {
                System.out.println("Invalid Option");
                System.out.println("\nContinue : (y/n)");
                wish=sc.next().charAt(0); 
            }            
        }while(wish=='y'||wish=='Y');    
        
        Thread t=new Thread(new write(Hotel.ob));
        t.start();
        }        
            catch(Exception e)
            {
                System.out.println("Not a valid input");
            }
    }
static void oute() throws IOException
{
try{
Scanner sc=new Scanner(System.in);
System.out.println("enter ");
FileOutputStream fout=null;
fout=new FileOutputStream("D:/review.txt",true);
String str=sc.nextLine();		 
byte b[]=str.getBytes();
fout.write(b); 
if(fout!=null)
fout.close();
}
catch(IOException e)
{
	System.out.println("Invalid Input");
}

}
}