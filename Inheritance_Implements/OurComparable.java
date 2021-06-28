public interface OurComparable {
    /* Returns negative if this object is less than o, 0 if equal, positive if greater then */
    /* Leave how exactly to compare up the the subclass*/
    public int compareTo(Object o);
}
