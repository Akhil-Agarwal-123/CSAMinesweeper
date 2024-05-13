package Testing;

import Testing.UnitTests.Hexagon.HexagonBuildAWallUnitTest;
import Testing.UnitTests.UnitTest;

/**
 * Class to run unit tests from
 */
public class Test {
    /**
     * Driver method
     * @param args command line arguments
     * @author Akhil
     */
    public static void main(String[] args) {
        UnitTest test = new HexagonBuildAWallUnitTest();
        test.printBoard(true);
        test.playUntilEnd(true);
    }
}
