package com.teamtbd.nextbusml.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by crejaud on 12/8/16.
 */

public class BusStops {
    public static final List<String> BUSCH_STOPS =
            Collections.unmodifiableList(
                    Arrays.asList("Stadium", "Werblin Main Entrance", "Visitor Center", "Library of Science", "Werblin Back Entrance", "Buell Apartments", "Busch Suites", "Busch Campus Center", "Science Building", "Davidson Hall", "Allison Road Classrooms"));

    public static final List<String> LIVVY_STOPS =
            Collections.unmodifiableList(
                    Arrays.asList("Livingston Plaza", "Livingston Student Center", "Quads"));
    public static final List<String> COOK_STOPS =
            Collections.unmodifiableList(
                    Arrays.asList("Train Station", "Bravo Supermarket", "Public Safety Building South", "Cabaret Theatre", "Red Oak Lane", "Rockoff Hall", "College Hall", "Lipman Hall", "Paterson Street", "Public Safety Building North", "Henderson", "Food Sciences Building", "Liberty Street", "Biel Road", "Gibbons", "Katzenbach", "Red Oak Lane"));
    public static final List<String> CAC_STOPS =
            Collections.unmodifiableList(
                    Arrays.asList("Scott Hall", "Rutgers Student Center", "Student Activities Center", "Train Station", "Zimmerli Arts Museum", "Paterson Street"));
}
