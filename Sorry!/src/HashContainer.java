import java.util.HashMap;


public class HashContainer {
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap2;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap3;
	private static HashMap<SorryFrame.Coordinate, Integer> coordsMap1;

	public HashMap<SorryFrame.Coordinate, Integer> getmap(int i){
		switch (i) {
		case 0:
			return coordsMap;
		case 1:
			return coordsMap1;
		case 2:
			return coordsMap2;
		case 3:
			return coordsMap3;
		}
		return new HashMap<SorryFrame.Coordinate, Integer>();
		
	}
	
	
	public static void populateCoordsMap() {
		coordsMap = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap1 = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap2 = new HashMap<SorryFrame.Coordinate, Integer>();
		coordsMap3 = new HashMap<SorryFrame.Coordinate, Integer>();
		populateCorners();
		populateStartZones();
		populateHomeZones();
		populateSafeZones();
		populateSideLines();
	}

	private static void populateSideLines() {
		mapInsert(14, 15, 1);
		mapInsert(13, 15, 2);
		mapInsert(12, 15, 9);
		mapInsert(11, 15, 10);

		mapInsert(0, 14, 23);
		mapInsert(0, 13, 24);
		mapInsert(0, 12, 31);
		mapInsert(0, 11, 32);

		mapInsert(1, 0, 45);
		mapInsert(2, 0, 46);
		mapInsert(3, 0, 53);
		mapInsert(4, 0, 54);

		mapInsert(15, 1, 67);
		mapInsert(15, 2, 68);
		mapInsert(15, 3, 75);
		mapInsert(15, 4, 76);

		mapInsert1(14, 15, 23);
		mapInsert1(13, 15, 24);
		mapInsert1(12, 15, 31);
		mapInsert1(11, 15, 32);

		mapInsert1(0, 14, 45);
		mapInsert1(0, 13, 46);
		mapInsert1(0, 12, 53);
		mapInsert1(0, 11, 54);

		mapInsert1(1, 0, 67);
		mapInsert1(2, 0, 68);
		mapInsert1(3, 0, 75);
		mapInsert1(4, 0, 76);

		mapInsert1(15, 1, 1);
		mapInsert1(15, 2, 2);
		mapInsert1(15, 3, 9);
		mapInsert1(15, 4, 10);

		mapInsert2(14, 15, 45);
		mapInsert2(13, 15, 46);
		mapInsert2(12, 15, 53);
		mapInsert2(11, 15, 54);

		mapInsert2(0, 14, 67);
		mapInsert2(0, 13, 68);
		mapInsert2(0, 12, 75);
		mapInsert2(0, 11, 76);

		mapInsert2(1, 0, 1);
		mapInsert2(2, 0, 2);
		mapInsert2(3, 0, 9);
		mapInsert2(4, 0, 10);

		mapInsert2(15, 1, 23);
		mapInsert2(15, 2, 24);
		mapInsert2(15, 3, 31);
		mapInsert2(15, 4, 32);

		mapInsert3(14, 15, 67);
		mapInsert3(13, 15, 68);
		mapInsert3(12, 15, 75);
		mapInsert3(11, 15, 76);

		mapInsert3(0, 14, 1);
		mapInsert3(0, 13, 2);
		mapInsert3(0, 12, 9);
		mapInsert3(0, 11, 10);

		mapInsert3(1, 0, 23);
		mapInsert3(2, 0, 24);
		mapInsert3(3, 0, 31);
		mapInsert3(4, 0, 32);

		mapInsert3(15, 1, 45);
		mapInsert3(15, 2, 46);
		mapInsert3(15, 3, 53);
		mapInsert3(15, 4, 54);

	}

	private static void mapInsert(int x, int y, int pos) {
		coordsMap.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert1(int x, int y, int pos) {
		coordsMap1.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert2(int x, int y, int pos) {
		coordsMap2.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void mapInsert3(int x, int y, int pos) {
		coordsMap3.put(new SorryFrame.Coordinate(x, y), pos);
	}

	private static void populateSafeZones() {
		int redZone = 2;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(13, i), redZone++);

		int blueZone = 24;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(i, 13), blueZone++);

		int yellowZone = 46;
		for (int i = 0; i <= 5; i++)
			coordsMap.put(new SorryFrame.Coordinate(2, i), yellowZone++);

		int greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap.put(new SorryFrame.Coordinate(i, 2), greenZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap1.put(new SorryFrame.Coordinate(13, i), blueZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap1.put(new SorryFrame.Coordinate(i, 13), yellowZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap1.put(new SorryFrame.Coordinate(2, i), greenZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap1.put(new SorryFrame.Coordinate(i, 2), redZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap2.put(new SorryFrame.Coordinate(13, i), yellowZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap2.put(new SorryFrame.Coordinate(i, 13), greenZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap2.put(new SorryFrame.Coordinate(2, i), redZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap2.put(new SorryFrame.Coordinate(i, 2), blueZone++);

		redZone = 2;
		blueZone = 24;
		yellowZone = 46;
		greenZone = 68;
		for (int i = 15; i >= 10; i--)
			coordsMap3.put(new SorryFrame.Coordinate(13, i), greenZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap3.put(new SorryFrame.Coordinate(i, 13), redZone++);
		for (int i = 0; i <= 5; i++)
			coordsMap3.put(new SorryFrame.Coordinate(2, i), blueZone++);
		for (int i = 15; i >= 10; i--)
			coordsMap3.put(new SorryFrame.Coordinate(i, 2), yellowZone++);
	}

	private static void populateHomeZones() {
		int redHome = 8;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), redHome);

		int blueHome = 30;
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap.put(new SorryFrame.Coordinate(i, j), blueHome);

		int yellowHome = 52;
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowHome);

		int greenHome = 74;
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap.put(new SorryFrame.Coordinate(i, j), greenHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), blueHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), yellowHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap1.put(new SorryFrame.Coordinate(i, j), redHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), yellowHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), redHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap2.put(new SorryFrame.Coordinate(i, j), blueHome);

		redHome = 8;
		blueHome = 30;
		yellowHome = 52;
		greenHome = 74;
		for (int i = 14; i >= 12; i--)
			for (int j = 9; j >= 7; j--)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), greenHome);
		for (int i = 6; i <= 8; i++)
			for (int j = 14; j >= 12; j--)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), redHome);
		for (int i = 1; i <= 3; i++)
			for (int j = 6; j <= 8; j++)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), blueHome);
		for (int i = 7; i <= 9; i++)
			for (int j = 1; j <= 3; j++)
				coordsMap3.put(new SorryFrame.Coordinate(i, j), yellowHome);
	}

	private static void populateStartZones() {
		int redStart = 11;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), redStart);
			}
		}

		int blueStart = 33;
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(j, i), blueStart);
			}
		}

		int yellowStart = 55;
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap.put(new SorryFrame.Coordinate(i, j), yellowStart);
			}
		}

		int greenStart = 77;
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap.put(new SorryFrame.Coordinate(j, i), greenStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(i, j), blueStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(j, i), yellowStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap1.put(new SorryFrame.Coordinate(i, j), greenStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap1.put(new SorryFrame.Coordinate(j, i), redStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(i, j), yellowStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(j, i), greenStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap2.put(new SorryFrame.Coordinate(i, j), redStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap2.put(new SorryFrame.Coordinate(j, i), blueStart);
		}

		redStart = 11;
		blueStart = 33;
		yellowStart = 55;
		greenStart = 77;
		for (int i = 10; i <= 12; i++) {
			for (int j = 12; j <= 14; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(i, j), greenStart);
			}
		}
		for (int i = 10; i <= 12; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(j, i), redStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 1; j <= 3; j++) {
				coordsMap3.put(new SorryFrame.Coordinate(i, j), blueStart);
			}
		}
		for (int i = 3; i <= 5; i++) {
			for (int j = 12; j <= 14; j++)
				coordsMap3.put(new SorryFrame.Coordinate(j, i), yellowStart);
		}
	}

	private static void populateCorners() {
		int redSide = 12;
		int blueSide = 34;

		for (int i = 10; i >= 0; i--) {
			coordsMap.put(new SorryFrame.Coordinate(i, 15), redSide++);
			coordsMap.put(new SorryFrame.Coordinate(0, i), blueSide++);
		}

		int greenSide = 78;
		int yellowSide = 56;
		for (int i = 5; i <= 15; i++) {
			coordsMap.put(new SorryFrame.Coordinate(i, 0), yellowSide++);
			coordsMap.put(new SorryFrame.Coordinate(15, i), greenSide++);
		}
		coordsMap.put(new SorryFrame.Coordinate(15, 15), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap1.put(new SorryFrame.Coordinate(i, 15), blueSide++);
			coordsMap1.put(new SorryFrame.Coordinate(0, i), yellowSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap1.put(new SorryFrame.Coordinate(i, 0), greenSide++);
			coordsMap1.put(new SorryFrame.Coordinate(15, i), redSide++);
		}
		coordsMap1.put(new SorryFrame.Coordinate(15, 0), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap2.put(new SorryFrame.Coordinate(i, 15), yellowSide++);
			coordsMap2.put(new SorryFrame.Coordinate(0, i), greenSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap2.put(new SorryFrame.Coordinate(i, 0), redSide++);
			coordsMap2.put(new SorryFrame.Coordinate(15, i), blueSide++);
		}
		coordsMap2.put(new SorryFrame.Coordinate(0, 15), 0);

		redSide = 12;
		blueSide = 34;
		greenSide = 78;
		yellowSide = 56;
		for (int i = 10; i >= 0; i--) {
			coordsMap3.put(new SorryFrame.Coordinate(i, 15), greenSide++);
			coordsMap3.put(new SorryFrame.Coordinate(0, i), redSide++);
		}
		for (int i = 5; i <= 15; i++) {
			coordsMap3.put(new SorryFrame.Coordinate(i, 0), blueSide++);
			coordsMap3.put(new SorryFrame.Coordinate(15, i), yellowSide++);
		}
		coordsMap3.put(new SorryFrame.Coordinate(15, 15), 0);

	}
}
