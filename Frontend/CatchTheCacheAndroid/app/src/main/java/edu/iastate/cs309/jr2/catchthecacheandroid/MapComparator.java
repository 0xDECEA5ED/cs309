package edu.iastate.cs309.jr2.catchthecacheandroid;


import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by acg
 */

class MapComparator implements Comparator<HashMap<String, String>>
{
    private final String key;
    private final String order;

    public MapComparator(String key, String order)
    {
        this.key = key;
        this.order = order;
    }

    /**
     * method to store chat data
     * @param first
     * @param second
     * @return
     */
    public int compare(HashMap<String, String> first,
                       HashMap<String, String> second)
    {
        // TODO: Null checking, both for maps and values
        String firstValue = first.get(key);
        String secondValue = second.get(key);
        if(this.order.toLowerCase().contentEquals("asc"))
        {
            return firstValue.compareTo(secondValue);
        }else{
            return secondValue.compareTo(firstValue);
        }

    }
}