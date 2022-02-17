package byow.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** A list that only contains unique rooms. **/
public class RoomList extends ArrayList<Room>{
    private List<Room> listOfRooms;
    private Set<Room> setOfRooms;

    RoomList() {
        this.listOfRooms = new ArrayList<>();
        this.setOfRooms = new HashSet<>();
    }

    /** Make a copy of another roomList. */
    RoomList(RoomList r) {
        this.listOfRooms = r.getRoomList();
        this.setOfRooms = new HashSet<>(this.listOfRooms);
    }

    /**
     *  Add Room r to the list if it's not already in the list. Otherwise, do nothing.
      * @param r A Room object.
     */
    public void addRoom(Room r) {
        if (this.setOfRooms.contains(r)) {
            return;
        }
        this.listOfRooms.add(r);
        this.setOfRooms.add(r);
    }

    /** Returns the list of rooms. */
    public ArrayList<Room> getRoomList() {
        return new ArrayList<>(this.listOfRooms);
    }

    /** Set the list of rooms. */
    public void setListOfRooms(ArrayList<Room> otherList) {
        this.listOfRooms = otherList;
        this.setOfRooms = new HashSet<>(this.listOfRooms);
    }


}
