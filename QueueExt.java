import java.util.Optional;
import java.util.function.*;
import java.util.Queue;

interface QueueExt<E> extends Queue<E>{

    public QueueExt<E> filtre(Predicate<E> cond);
    <U extends Comparable<U>> QueueExt<U> map(Function<E, U> f);
    Optional<E> trouve(Predicate<E> cond);
    <U> U reduit(U z, BiFunction<U, E, U> f);
    public void afficheTas(int hauteur);
}
