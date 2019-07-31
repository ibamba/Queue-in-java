import java.io.*;
import java.util.*;
import java.util.function.*;
import java.util.Optional;

class Main{
    public static void main(String []args){
	
	//TESTS DE PERFORMANCE
	Tests testLl = new Tests(new LinkedList<Integer> ());
	Tests testAD = new Tests(new ArrayDeque<Integer> ());
	Tests testTBQ = new Tests(new TBQueue<Integer> (500));
	Tests testTBDQ = new Tests(new TBDQueue<Integer> ());
	Tests testALTBQ = new Tests(new ALTBQueue<Integer> ());
	System.out.println("/************************************************************************************************/\n"+
			   "/                     <<<TESTS DE PERFORMANCE>>>\n"+
			   "/\n        Test type         Queue type --> time(en ms)\n\n"+
			   "/ >>>>>test 'add' :       LinkedList --> "+testLl.testAdd()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testAdd());
	System.out.print("/                         TBQueue    --> ");
	System.out.println(testTBQ.testAdd());
	System.out.println("/                         TBDQueue   --> "+testTBDQ.testAdd()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testAdd());
	
	testLl = new Tests(new LinkedList<Integer> ());
	testAD = new Tests(new ArrayDeque<Integer> ());
	testTBQ = new Tests(new TBQueue<Integer> (500));
	testTBDQ = new Tests(new TBDQueue<Integer> ());
	testALTBQ = new Tests(new ALTBQueue<Integer> ());
	
	System.out.println("/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'offer' :     LinkedList --> "+testLl.testOffer()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testOffer());
	System.out.print("/                         TBQueue    --> ");
	System.out.println(testTBQ.testOffer());
	System.out.println("/                         TBDQueue   --> "+testTBDQ.testOffer()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testOffer()+"\n"+
			   "/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'peek' :      LinkedList --> "+testLl.testPeek()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testPeek()+"\n"+
			   "/                         TBQueue    --> "+testTBQ.testPeek()+"\n"+
			   "/                         TBDQueue   --> "+testTBDQ.testPeek()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testPeek()+"\n"+
			   "/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'poll' :      LinkedList --> "+testLl.testPoll()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testPoll()+"\n"+
			   "/                         TBQueue    --> "+testTBQ.testPoll()+"\n"+
			   "/                         TBDQueue   --> "+testTBDQ.testPoll()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testPoll()+"\n"+
			   "/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'remove' :    LinkedList --> "+testLl.testRemove()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testRemove()+"\n"+
			   "/                         TBQueue    --> "+testTBQ.testRemove()+"\n"+
			   "/                         TBDQueue   --> "+testTBDQ.testRemove()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testRemove()+"\n"+
			   "/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'element' :   LinkedList --> "+testLl.testElement()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testElement()+"\n"+
			   "/                         TBQueue    --> "+testTBQ.testElement()+"\n"+
			   "/                         TBDQueue   --> "+testTBDQ.testElement()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testElement()+"\n"+
			   "/------------------------------------------------------------------------------------------------/\n"+
			   "/ >>>>>test 'iterator' :  LinkedList --> "+testLl.testIterator()+"\n"+
			   "/                         ArrayDeque --> "+testAD.testIterator()+"\n"+
			   "/                         TBQueue    --> "+testTBQ.testIterator()+"\n"+
			   "/                         TBDQueue   --> "+testTBDQ.testIterator()+"\n"+
			   "/                         ALTBQueue  --> "+testALTBQ.testIterator()+"\n"+
			   "/                                                                                                /\n"+
			   " ***********************************************************************************************"
			   );

	
	//TESTS DE FONCTIONNEMENT
	QueueExt<Integer> tbq;
	Scanner sc = new Scanner(System.in);
	System.out.print("\n\nVoulez-vous procéder à des tests sur les différents types de tas ?!\n"+
			 "-1-Oui                                                    -0-Non\n"+
			 ">>Choix : ");
	int rep=sc.nextInt();
	int choix;
	while(rep==1){
	    
	    do{
		System.out.print("\n\n<<<<TESTS SUR LES TAS>>>>\n"+
				 "\n--Menu Principal--\n"+
				 "               Utiliser un :\n"+
				 "                 <-1-> Tas Binaire (aka TBQueue)\n"+
				 "                 <-2-> Tas Binaire Dynamique (aka TBDQueue)\n"+
				 "                 <-3-> Tas Binaire avec array list (aka ALTBQueue)\n"+
				 "                 <-0-> Abandonner\n"+
				 ">>Choix : "
				 );
		choix=sc.nextInt();
	    }while(choix<0 || choix>3);

	    if(choix==0){
		rep=0;
	    }
	    else{
		if(choix==1) tbq = new TBQueue<>(16);
		else if(choix==2) tbq = new TBDQueue<>();
		else tbq = new ALTBQueue<>();
		tbq.offer(10);
		tbq.offer(17);
		tbq.offer(23);
		tbq.offer(34);
		tbq.offer(15);
		tbq.offer(73);
		tbq.offer(37);
		tbq.offer(50);
		tbq.offer(21);
		tbq.offer(3);
		tbq.offer(9);
		tbq.offer(33);
		tbq.offer(77);
		tbq.offer(25);
		tbq.afficheTas(3);
		
		System.out.println("\n-->test Peek : "+tbq.peek().toString());
		tbq.afficheTas(3);
		System.out.println("\n-->test Poll : "+tbq.poll().toString());
		tbq.afficheTas(3);
		Predicate<Integer> cond = i-> i>=30 && i<=70;
		System.out.println("\n-->test filtre : elts compris entre 30 et 70 : "+tbq.filtre(cond).toString());
		tbq.afficheTas(3);
		cond = i-> i>33 && i<37;
		System.out.println("\n-->test trouve : un elt compris entre 33 et 37 : "+tbq.trouve(cond).toString());
		tbq.afficheTas(3);
		Function<Integer, String> fun = (Integer i)-> {return "'"+i+"'";};
		System.out.println("\n-->test map : transformation des entiers en string : ");
		QueueExt<String> tbqmap = tbq.map(fun);
		tbqmap.afficheTas(3);
		BiFunction<Integer, Integer, Integer> bf = (Integer x, Integer y)-> {return x+y;};
		System.out.println("\n-->test reduit : somme des elts de la liste = "+tbq.reduit(0, bf).toString());
		tbq.afficheTas(3);
	    }	
	}
    }
}
