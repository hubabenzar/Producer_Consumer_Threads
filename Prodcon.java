class Producer extends Thread{
	private Buffer b;
	public Producer (Buffer buf){
		b = buf;
	}
	public void run(){
		int m;
		b.insert(m);
	}
}

public class Prodcon{
	public static void main (String args[]){ // Create buffer, producer & consumer
		Buffer b = new Buffer();
		Producer p = new Producer(b);
		Consumer c = new Consumer(b);
		p.start(); c.start(); 
	}
}

class Buffer{ 
	private int v;
	private volatile boolean empty=true;
	public synchronized void insert (int x){
		while (!empty){ 
			try{ 
				wait();
			}
			catch (InterruptedException e) {}
		}
		empty = false;
		v = x;
		notify();
	}
	// Similarly for remove()
}