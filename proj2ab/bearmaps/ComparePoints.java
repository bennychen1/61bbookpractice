package bearmaps;

import java.util.Comparator;

public interface ComparePoints<T> extends Comparator<Point> {
    /** The dimension to compare after this comparison. **/
    public ComparePoints<Point> nextComparator();
}
