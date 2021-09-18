package bearmaps.proj2ab;

import java.util.Comparator;

public interface ComparePoints<T> {

    public int compare(T x, T y);

    /** The dimension to compare after this comparison. **/
    public ComparePoints<Point> nextComparator();
}
