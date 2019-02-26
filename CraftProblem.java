/*
Course:			COMP 124
Lecturer:		David Jackson
Title:			Assignment 2 - Craft Problem
Name: 			Huba Ferenc Benzar
Student ID:		201262833
Email:			H.Benzar@student.Liverpool.ac.uk
Deadline: 		27/04/2018 - 1700
*/
public class CraftProblem{											//Class CraftProblem initiates
	public static void main (String args[]){						//main method begins
		//Thread Creation
		Shelf b = new Shelf();										//creates new thread Shelf as b
		potter_Harry creaftA = new potter_Harry(b);					//creates new thread potter_Harry as creaftA
		potter_Beatrix prodB = new potter_Beatrix(b);				//creates new thread potter_Harry as creaftA
		packer_Macca consM = new packer_Macca(b);					//creates new thread packer_Macca as consM
		creaftA.start(); prodB.start(); consM.start();				//Starts the created Threads
	}
} 
//PRODUCERS - Could have merged the two and changed the ms difference to make it more compact.
	//THREAD HARRY
class potter_Harry extends Thread{									//potter_Harry extends the Thread
	private Shelf b;												//private Shelf b is initialised
	public potter_Harry (Shelf buf){								//potter_Harry constructor gives back Shelf buf.
		b = buf;													//b is buf
	}
	public void run(){												//run method
		System.out.println("Potter 1 (Harry) has started");			//Message - Thread started
		for(int i = 0; i < 10; i++){								//Loops contents 10 times as it needs to create 10 pots
			try{													//Try
				potter_Harry.sleep(500);							//Makes the thread potter_Harry sleep for 500ms
			}
			catch (InterruptedException e) {}						//Catch Exception
			System.out.println("Harry has made a pot.");			//Message - Shelf got one added to shelf
			b.insert();												//Inserts into b meaning that it adds one on to shelf
		}
		System.out.println("Potter Harry has finished.");			//Message - Thread is done
	}
}

	//THREAD BEATRIX
class potter_Beatrix extends Thread{								//potter_Beatrix extends the Thread
	private Shelf b;												//private Shelf b is initialised
	public potter_Beatrix (Shelf buf){								//potter_Beatrix constructor gives back Shelf buf.
		b = buf;													//b is buf
	}
	public void run(){												//run method
		System.out.println("Potter 2 (Beatrix) has started.");		//Message - Thread started
		for(int i = 0; i < 10; i++){								//Loops contents 10 times as it needs to create 10 pots
			try{													//Try
				potter_Beatrix.sleep(600);							//Makes the thread potter_Beatrix sleep for 600ms
			} 
			catch (InterruptedException e) {}						//Catch Exception
			System.out.println("Beatrix has made a pot.");			//Message - Shelf got one added to shelf
			b.insert();												//Inserts into b meaning that it adds one on to shelf
		}
		System.out.println("Potter Beatrix has finished.");			//Message - Thread is done
	}
}
//CONSUMER
	//THREAD MACCA
class packer_Macca extends Thread{									//packer_Macca extends the Thread
	private Shelf b;												//private Shelf b is initialised
	public packer_Macca (Shelf buf){								//packer_Macca constructor gives back Shelf buf.
		b = buf;													//b is buf
	}
	public void run(){												//run method
			System.out.println("The Packer (Macca) has started\n");	//Message - Thread started
			for(int i = 0; i < 20; i++){							//Loops contents 20 times as it needs to pack 20 pots from both producers
				try{												//Try
					packer_Macca.sleep(400);						//Makes the thread potter_Beatrix sleep for 400ms
					System.out.println("Macca is ready to pack.");	//Message - Macca is ready to pack
				} 
				catch (InterruptedException e) {}					//Catch Exception
				System.out.println("Macca has packed a pot.");		//Message - Shelf got one taken away
				b.remove();											//remove from b meaning that it takes one from shelf
			}
			System.out.println("Packer Macca has finished.");		//Message - Thread is done
	}
}
//Shelf
	//THREAD SHELF
class Shelf{														//Buffer Shelf thread
	private volatile int shelfCounter = 0;							//private volatile int shelfCounter is set to 0
	//Insert
	public synchronized void insert(){								//public Synchronized void insert method - The producers end up here
		while (shelfCounter >= 5){									//While there are 5 or more pots on the shelf 
			try{													//Try
				wait();												//pauses, meaning that the potters cant stack on the shelf until they are removed.
			}
			catch (InterruptedException e) {}						//Catch Exception
		}
		shelfCounter++;												//if the loop is passed it increments shelfCounter
		System.out.println("Inserting pot. There are now "+ shelfCounter +" pots on the shelf");	//Message about insert pot and number of pots on the shelf.
		notify();													//Notifies the Threads to unblock the queue
		if(shelfCounter >= 5){										//If shelf is 5 or more pots on the shelf 
			System.out.println("Shelf is full. Waiting for packer . . .");	//Message about shelf being full. This message could have been shorter if it was placed higher up, but the order wouldn't make sense when printing out.
		}
	}
	//Remove
	public synchronized void remove(){								//public Synchronized void remove method - The consumber ends up here
		while (shelfCounter <= 0){									//While there are 0 or less pots on the shelf
			try{													//Try
				wait();												//pauses, meaning that the potters cant stack on the shelf until they are removed.
			}
			catch (InterruptedException e) {}						//Catch Exception
		}
		shelfCounter--;												//if the loop is passed it decrements shelfCounter
		System.out.println("Removing pot. There are now "+ shelfCounter +" pots on the shelf");	//Message about removing a pot and number of pots on the shelf.
		notify();													//Notifies the Threads to unblock the queue
		if(shelfCounter <= 0){										//If shelf is 5 or more pots on the shelf 
			System.out.println("Shelf is empty. Waiting for potter . . .");	//Message about shelf being full. This message could have been shorter if it was placed higher up, but the order wouldn't make sense when printing out.
		}
	}
}