import java.util.*;
import java.util.Optional;
import java.util.function.*;
import java.util.Queue;

public class TBDQueue<E extends Comparable<E>> extends AbstractQueue<E> implements QueueExt<E>{
    
    private LinkedList<E[]> listTableaux;
    private int niveauCourant;
    private int sizeCourant;
    private int size;

    @SuppressWarnings("unchecked")
    public TBDQueue(){
	/**
	   Constructeur de TBDQueue.
	   @arg "listTableau" est la liste de tableau pour chaque niveau du tas
	   @arg "niveauCourant" est le niveau où on se trouve dans le tas. 
	        La racine est au niveau 0
	   @arg "sizeCourant" est le nb d'elts dans le tableau du niveau courant
	   @arg "size" est le nb d'elts total dans le tas
	*/
	listTableaux = new LinkedList<>();
	niveauCourant=0; //niveau de la racine
	listTableaux.add((E[])(new Comparable[1])); //Le tableau au niveau 0
	sizeCourant=0;
	size=0;
    }

    public int indPere(int ind){
	/**indice du père :
	   le père de l'elt à l'indice ind se trouve dans le tableau 
	   du niveau inférieur(niv-1), à l'indice ind/2
	   @param l'indice de l'elt
	   @return l'indice du père dans le tableau de niveau niv(ind)-1
	*/
	return ind/2;
    }

    public int indFilsG(int ind){
	/**indice du fils gauche :
	   le fils gauche de l'elt à l'indice ind se trouve dans le tableau 
	   du niveau supérieur(niv+1), à l'indice 2*ind
	   @param l'indice de l'elt
	   @return l'indice du fils gauche dans le tableau de niveau niv(ind)+1
	*/
	return 2*ind;
    }

    public int indFilsD(int ind){
	/**indice du fils droit :
	   le fils droit de l'elt à l'indice ind se trouve dans le tableau 
	   du niveau supérieur(niv+1), à l'indice 2*ind+1
	   @param l'indice de l'elt
	   @return l'indice du fils droit dans le tableau de niveau niv(ind)+1
	*/
	return 2*ind+1;
    }
    
    public void percolationHaut(int niv, int ind){
	/**
	   Percolation vers le haut (tamisage): de sorte à retrouver un tas
	   lorsqu'on ajoute un element dans le tas
	   @param le niveau de l'elt
	   @param l'indice de l'elt dans son tableau
	*/
	while(niv > 0){//l'element n'est pas la racine
	    if((listTableaux.get(niv-1))[indPere(ind)].compareTo((listTableaux.get(niv))[ind])<0){
		echange(niv-1, indPere(ind), niv, ind);
		ind=indPere(ind);
		niv--;
	    }
	    else break;
	}
    }

    public void percolationBas(int niv, int ind){
	/**
	   Percolation vers le bas (tamisage): de sorte à retrouver un tas
	   lorsqu'on supprime un element du tas
	   @param le niveau de l'elt
	   @param l'indice de l'elt dans son tableau
	*/
	while(niv < niveauCourant-1 || (niv==(niveauCourant-1) && indFilsD(ind) < sizeCourant && indFilsG(ind) < sizeCourant)){
	    if((listTableaux.get(niv+1))[indFilsD(ind)].compareTo((listTableaux.get(niv))[ind])>0
	       || (listTableaux.get(niv+1))[indFilsG(ind)].compareTo((listTableaux.get(niv))[ind])>0){
		//on fait monter le max des deux fils
		if((listTableaux.get(niv+1))[indFilsD(ind)].compareTo((listTableaux.get(niv+1))[indFilsG(ind)])>0){
		    echange(niv+1, indFilsD(ind), niv, ind);
		    ind=indFilsD(ind);
		}
		else{
		    echange(niv+1, indFilsG(ind), niv, ind);
		    ind=indFilsG(ind);
		}
		niv++;
	    }
	    else
		break;
	}
    }
    
    public void echange(int nivi, int i, int nivj, int j){
	/**
	   Echange deux elts du tas
	   @param les deux elts et leur niveau respectif
	*/
	E tmp = listTableaux.get(nivi)[i];
	listTableaux.get(nivi)[i] = listTableaux.get(nivj)[j];
	listTableaux.get(nivj)[j] = tmp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean offer(E e){
	int len;
	if(sizeCourant >= (len=listTableaux.get(niveauCourant).length)){
	    //le dernier niveau est rempli
	    listTableaux.add((E[])(new Comparable[2*len]));
	    sizeCourant=0;
	    niveauCourant++;
	}
	listTableaux.get(niveauCourant)[sizeCourant] = e;
	percolationHaut(niveauCourant, sizeCourant);
	sizeCourant++;
	size++;
	return true;
    }

    @Override
    public E peek(){
	return listTableaux.get(0)[0];
    }

    @Override
    public E poll()
	throws NoSuchElementException{
	if(size==0)
	    throw new NoSuchElementException("La pile est vide\n"); 
	E res = listTableaux.get(0)[0];
	listTableaux.get(0)[0] = listTableaux.get(niveauCourant)[sizeCourant-1]; //On fait monter le dernier element à la racine
	sizeCourant--; //On le supprime ensuite dans son niveau
	percolationBas(0, 0); //On tamisse l'arbre obtenu pour obtenir un tas
	size--;
	//On gère les tableaux
	if(listTableaux.get(niveauCourant).length==0 && listTableaux.get(niveauCourant-1).length==0)
	    niveauCourant--;
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
	    private int niv=0;
	    
	    @Override
	    public boolean hasNext(){
		return (niv < listTableaux.size()-1 || (niv==niveauCourant && pos < sizeCourant));
	    }
	    
	    @Override
	    public E next() throws UnsupportedOperationException{
		if(hasNext()){
		    if(pos==listTableaux.get(niv).length){
			pos=0;
			niv++;
		    }
		    return listTableaux.get(niv)[pos++];
		}
		throw new UnsupportedOperationException("Plus d'element dans le tas");
	    }

	    @Override
	    public void remove() {
		throw new UnsupportedOperationException();
	    }
	};
		
    }

    @Override
    public QueueExt<E> filtre(Predicate<E> cond){
	QueueExt<E> res = new TBDQueue<>();
	for(E x :this)
	    if(cond.test(x)) res.offer(x);
	return res;
    }

    @Override
    public <U extends Comparable<U>> QueueExt<U> map(Function<E, U> f){
	QueueExt<U> res = new TBDQueue<>();
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
    
    public void afficheList(){
	/**
	   Affiche les elements du tas dans l'ordre stocké dans le tas
	*/
	System.out.print("[");
	int niv=0, ind=0;
	while(niv < listTableaux.size()-1 || (niv==niveauCourant && ind < sizeCourant-1)){
	    if(ind >= listTableaux.get(niv).length){
		ind=0;
		niv++;
	    }
	    System.out.print(listTableaux.get(niv)[ind]+" ");
	    ind++;
	}
	System.out.println(listTableaux.get(niv)[ind]+"]");
    }
    
    public void afficheTas(int hauteur){
	/**
	   Affiche le tas.
	   @param la hauteur du tas à afficher
	*/
	System.out.println("\nTas binaire Dynamique :\n");
	hauteur=niveauCourant;
	//Affichage de la racine
	for(int i=0; i<(int)Math.pow(2, hauteur)-1; i++)
	    System.out.print(" ");
	System.out.print(listTableaux.get(0)[0].toString());
	//Affichage des autres noeuds
	int niv=0;
	int ind=1;
	int nbsp=(int)Math.pow(2, hauteur)-1;
	int nbspsup=(int)Math.pow(2, hauteur)-1;
	while(niv < listTableaux.size()-1 || (niv==niveauCourant && ind < sizeCourant)){
	    if(ind >= listTableaux.get(niv).length){
		ind=0;
		niv++;
		nbspsup=nbsp;
		nbsp=(int)Math.pow(2, (hauteur-niv))-1;
		System.out.println();
		for(int j=0; j<nbsp; j++)
		    System.out.print(" ");
		System.out.print(listTableaux.get(niv)[ind].toString());
	    }else{
		for(int j=0; j<nbspsup; j++)
		    System.out.print(" ");
		System.out.print(listTableaux.get(niv)[ind].toString());
	    }
	    ind++;
	}
	System.out.println();
	System.out.print("\nreprésenté par la liste : ");
	afficheList();
    }
    
}
