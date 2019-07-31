import java.util.*;
import java.util.function.*;
import java.util.Optional;

class Tests{
    private Queue<Integer> queue;
    private int nbelts;
    public Tests(Queue<Integer> q){
	queue=q;
	nbelts=1000000;
    }

    public long testOffer(){
	/**
	   Teste la méthode offer sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	for(int i=1; i<=nbelts; i++){
	    if(!queue.offer(i)){
		System.out.print("!!! Test stoppé au "+i+"-ème elements : tas plein !!!\n"+
				   "                                     --> ");
		break;
	    }
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }

    public long testAdd(){
	/**
	   Teste la méthode add sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	for(int i=1; i<=nbelts; i++){
	    try{
		queue.add(i);
	    }catch(IllegalStateException e){
		System.out.print("!!! Test stoppé au "+i+"-ème elements : tas plein !!!\n"+
				 "                                     --> ");
		break;
	    }
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }
    
    public long testPeek(){
	/**
	   Teste la méthode peek sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	try{
	    queue.peek();
	}catch(NoSuchElementException e){
	    System.out.print("!!! Test stoppé : tas vide !!!");
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }

    public long testRemove(){
	/**
	   Teste la méthode remove sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	try{
	    queue.remove();
	}catch(NoSuchElementException e){
	    System.out.print("!!! Test stoppé : tas vide !!!");
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }
    
    public long testPoll(){
	/**
	   Teste la méthode poll sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	try{
	    queue.poll();
	}catch(NoSuchElementException e){
	    System.out.print("!!! Test stoppé : tas vide !!!");
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }

    public long testElement(){
	/**
	   Teste la méthode element sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	queue.element();
	long end = System.currentTimeMillis();
	return end-debut;
    }

    public long testIterator(){
	/**
	   Teste la méthode iterator sur le tas et renvoie 
	   le temps d'exécution
	*/
	long debut = System.currentTimeMillis();
	for(Integer x : queue){
	    x--;
	}
	long end = System.currentTimeMillis();
	return end-debut;
    }
}
