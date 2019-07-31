import java.util.*;
import java.util.Optional;
import java.util.function.*;
import java.util.Queue;

public class TBQueue<E extends Comparable<E>> extends AbstractQueue<E> implements QueueExt<E>{
    
    private E[] tableau;
    private final int taille;
    private int size;

    @SuppressWarnings("unchecked")
    public TBQueue(int n){
	/**
	   Constructeur de TBQueue.
	   @arg "tableau" est le tableau contenant les elements du tas
	   @arg "taille" est la capacité maximale(nombre d'element max) du tableau
	   @arg "size" est le nb de case remplie du tableau
	*/
	taille=n;
	tableau = (E[])(new Comparable[taille]);
	size=0;
    }
    
    public int indPere(int ind){
	/**indice du père :
	   le père de l'elt à l'indice ind se trouve à l'indice (ind-1)/2
	   @param l'indice de l'elt
	   @return l'indice du père dans le tableau
	*/
	return (ind-1)/2;
    }

    public int indFilsG(int ind){
	/**indice du fils gauche :
	   le fils gauche de l'elt à l'indice ind se trouve à l'indice 2*ind+1
	   @param l'indice de l'elt
	   @return l'indice du fils gauche dans le tableau
	*/
	return 2*ind+1;
    }

    public int indFilsD(int ind){
	/**indice du fils droit :
	   le fils droit de l'elt à l'indice ind se trouve à l'indice 2*ind+2
	   @param l'indice de l'elt
	   @return l'indice du fils droit dans le tableau
	*/
	return 2*ind+2;
    }
    
    public void percolationHaut(int ind){
	/**
	   Percolation vers le haut (tamisage): de sorte à retrouver un tas
	   lorsqu'on ajoute un element dans le tas
	   @param l'indice de l'elt dans le tableau représentant le tas
	*/
	while(ind > 0){//l'element n'est pas la racine
	    if(tableau[indPere(ind)].compareTo(tableau[ind])<0){
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
	   @param l'indice de l'elt dans le tableau représentant le tas
	*/
	while(indFilsD(ind) < size && indFilsG(ind) < size){
	    if(tableau[indFilsD(ind)].compareTo(tableau[ind])>0 || tableau[indFilsG(ind)].compareTo(tableau[ind])>0){
		//on fait monter le max des deux fils
		if(tableau[indFilsD(ind)].compareTo(tableau[indFilsG(ind)])>0){
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
    
    public void echange(int ind1, int ind2){
	/**
	   Echange deux elts du tas
	   @param l'indice des deux elts
	*/
	E tmp = tableau[ind1];
	tableau[ind1] = tableau[ind2];
	tableau[ind2] = tmp;
    }
    
    @Override
    public boolean offer(E e){
	if(size >= taille)
	    return false;
	tableau[size] = e;
	percolationHaut(size);
	size++;
	return true;
    }

    @Override
    public E peek(){
	if(size==0)
	    throw new NoSuchElementException("La pile est vide\n"); 
	return tableau[0];
    }

    @Override
    public E poll()
	throws NoSuchElementException{
	if(size==0)
	    throw new NoSuchElementException("La pile est vide\n"); 
	E res = tableau[0];
	tableau[0] = tableau[size-1]; //On fait monter le dernier element à la racine
	size--; //On le supprime ensuite du tas
	percolationBas(0); //On tamisse l'arbre obtenu pour obtenir un tas
	return res;
    }

    @Override
    public int size(){
	return size;
    }

    @Override
    public Iterator<E> iterator(){
	
	return new Iterator<E>(){
	    //On utilisera le parcours en largeur sur les elts du tas
	    
	    private int pos=0;
	    
	    @Override
	    public boolean hasNext(){
		return pos<size;
	    }
	    
	    @Override
	    public E next() throws UnsupportedOperationException{
		if(hasNext())
		    return tableau[pos++];
		throw new UnsupportedOperationException("Il n'y a plus d'element suivant dans le tas");
	    }

	    @Override
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
	
    }

    @Override
    public QueueExt<E> filtre(Predicate<E> cond){
	QueueExt<E> res = new TBQueue<>(taille);
	for(E x :this)
	    if(cond.test(x)) res.offer(x);
	return res;
    }

    @Override
    public <U extends Comparable<U>> QueueExt<U> map(Function<E, U> f){
	QueueExt<U> res = new TBQueue<>(taille);
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
    
    public void afficheTab(){
	/**
	   Affiche le tableau représentant le tas
	*/
	System.out.print("[");
	for(int i=0; i<size-1; i++)
	    System.out.print(tableau[i].toString()+" ");
	System.out.println(tableau[size-1].toString()+"]");
    }
    
    public void afficheTas(int hauteur){
	/**
	   Affiche le tas.
	   @param la hauteur du tas à afficher
	*/
	System.out.println("\nTas binaire :\n");
	//Affichage de la racine
	for(int i=0; i<(int)Math.pow(2, hauteur)-1; i++)
	    System.out.print(" ");
	System.out.print(tableau[0].toString());
	//Affichage des autres noeuds
	int prof=0;
	int nelts=1;
	int ielt=1;
	int nbsp=(int)Math.pow(2, hauteur)-1;
	int nbspsup=(int)Math.pow(2, hauteur)-1;
	for(int i=1; i<size; i++){
	    if(ielt<size && nelts<size){
		if(ielt>=nelts){
		    ielt=0;
		    nelts*=2;
		    prof++;
		    nbspsup=nbsp;
		    nbsp=(int)Math.pow(2, (hauteur-prof))-1;
		    System.out.println();
		    for(int j=0; j<nbsp; j++)
			System.out.print(" ");
		    System.out.print(tableau[i].toString());
		}
		else{
		    for(int j=0; j<nbspsup; j++)
			System.out.print(" ");
		    System.out.print(tableau[i].toString());
		}
		ielt++;
	    }else break;
	}
	System.out.println();
	System.out.print("\nreprésenté par le tableau : ");
	afficheTab();
    }
    
}
