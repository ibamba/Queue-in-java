import java.util.*;
import java.util.Optional;
import java.util.function.*;

public class ALTBQueue<E extends Comparable<E>> extends AbstractQueue<E> implements QueueExt<E> {

    private ArrayList<E> tableau;

    public ALTBQueue() {
	/**
	   Constructeur de ALTBQueue.
	   @arg "tableau" est la liste des éléments du tas
	*/
	this.tableau = new ArrayList<>();
    }

    public int indPere(int ind){
	/**indice du père :
	   le père de l'elt à l'indice ind se trouve à l'indice (ind-1)/2
	   @param l'indice de l'elt
	   @return l'indice du père dans la liste
	*/
	return (ind-1)/2;
    }

    public int indFilsG(int ind){
	/**indice du fils gauche :
	   le fils gauche de l'elt à l'indice ind se trouve à l'indice 2*ind+1
	   @param l'indice de l'elt
	   @return l'indice du fils gauche dans la liste
	*/
	return 2*ind+1;
    }

    public int indFilsD(int ind){
	/**indice du fils droit :
	   le fils droit de l'elt à l'indice ind se trouve à l'indice 2*ind+2
	   @param l'indice de l'elt
	   @return l'indice du fils droit dans la liste
	*/
	return 2*ind+2;
    }

    public void echange(int ind1, int ind2){
	/**
	   Echange deux elts du tas
	   @param l'indice des deux elts
	*/
	Collections.swap(tableau, ind1, ind2);
    }

    public void percolationHaut(int ind){
	/**
	   Percolation vers le haut (tamisage): de sorte à retrouver un tas
	   lorsqu'on ajoute un element dans le tas
	   @param l'indice de l'elt dans la liste représentant le tas
	*/
	while(ind > 0){//l'element n'est pas la racine
	    if(tableau.get(indPere(ind)).compareTo(tableau.get(ind))<0){
		echange(indPere(ind), ind);
		ind=indPere(ind);
	    }
	    else
		break;
	}
    }

    public void percolationBas(int ind){
	/**
	   Percolation vers le bas (tamisage): de sorte à retrouver un tas
	   lorsqu'on supprime un element du tas
	   @param l'indice de l'elt dans la liste représentant le tas
	*/
	while(indFilsD(ind) < size() && indFilsG(ind) < size()){
	    if(tableau.get(indFilsD(ind)).compareTo(tableau.get(ind))>0 || tableau.get(indFilsG(ind)).compareTo(tableau.get(ind))>0){
		//on fait monter le max des deux fils
		if(tableau.get(indFilsD(ind)).compareTo(tableau.get(indFilsG(ind)))>0){
		    echange(indFilsD(ind), ind);
		    ind=indFilsD(ind);
		}
		else{
		    echange(indFilsG(ind), ind);
		    ind=indFilsG(ind);
		}
	    }
	    else
		break;
	}
    }

    @Override
    public boolean offer(E e){
	tableau.add(e);
	percolationHaut(size() - 1);
	return true;
    }

    @Override
    public E peek(){
	return tableau.get(0);
    }

    @Override
    public E poll()
	throws NoSuchElementException{
	if(tableau.isEmpty())
	    throw new NoSuchElementException("La pile est vide\n"); 
	E res = tableau.get(0);
	tableau.set(0, tableau.get(size()-1)); //On fait monter le dernier element à la racine
	tableau.remove(size()-1); //On le supprime ensuite du tas
	percolationBas(0); //On tamise l'arbre obtenu pour obtenir un tas
	return res;
    }

    @Override
    public int size(){
	return tableau.size();
    }

    @Override
    public Iterator<E> iterator() {
	return tableau.iterator();
    }
    
    @Override
    public QueueExt<E> filtre(Predicate<E> cond){
	QueueExt<E> res = new ALTBQueue<>();
	for(E x :this)
	    if(cond.test(x)) res.offer(x);
	return res;
    }

    @Override
    public <U extends Comparable<U>> QueueExt<U> map(Function<E, U> f){
	QueueExt<U> res = new ALTBQueue<>();
	for(E x :this) {
	    res.offer(f.apply(x));
	}
	return res;
    }

    @Override
    public Optional<E> trouve(Predicate<E> cond){
	for(E x :this) {
	    if(cond.test(x))
		return Optional.of(x);
	}
	return Optional.empty();
    }

    @Override
    public <U> U reduit(U z, BiFunction<U, E, U> f){
	U acc = z;
	for(E x : this) {
	    acc = f.apply(acc, x);
	}
	return acc;
    }    
    
    public void afficheTas(int hauteur){
	/**
	   Affiche le tas.
	   @param la hauteur du tas à afficher
	*/
	System.out.println("\nTas binaire ArrayList :\n");
	//Affichage de la racine
	for(int i=0; i<(int)Math.pow(2, hauteur)-1; i++)
	    System.out.print(" ");
	System.out.print(tableau.get(0).toString());
	//Affichage des autres noeuds
	int prof=0;
	int nelts=1;
	int ielt=1;
	int nbsp=(int)Math.pow(2, hauteur)-1;
	int nbspsup=(int)Math.pow(2, hauteur)-1;
	for(int i=1; i<tableau.size(); i++){
	    if(ielt<tableau.size() && nelts<tableau.size()){
		if(ielt>=nelts){
		    ielt=0;
		    nelts*=2;
		    prof++;
		    nbspsup=nbsp;
		    nbsp=(int)Math.pow(2, (hauteur-prof))-1;
		    System.out.println();
		    for(int j=0; j<nbsp; j++)
			System.out.print(" ");
		    System.out.print(tableau.get(i).toString());
		}
		else{
		    for(int j=0; j<nbspsup; j++)
			System.out.print(" ");
		    System.out.print(tableau.get(i).toString());
		}
		ielt++;
	    }else break;
	}
	System.out.println();
	System.out.print("\nreprésenté par la liste : "+tableau.toString());
    }
    
}
