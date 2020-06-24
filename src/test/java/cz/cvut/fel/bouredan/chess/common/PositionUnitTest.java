package cz.cvut.fel.bouredan.chess.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PositionUnitTest {

    /**
     * Test of getting Position object from its notation
     */
    @Test
    public void positionNotationConstructor_constructingPositionFromNotation_correctPositionConstructed() {
        // Arrange
        Position expectedPosition = new Position(4, 3);

        // Act
        Position position = new Position("e4");

        // Assert
        assertEquals(expectedPosition, position);
    }
}
