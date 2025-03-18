package unit

import org.example.constant.Direction
import org.example.model.Robot
import spock.lang.Specification

class RobotTest extends Specification {

    def underTest = new Robot()

    def "when input valid PLACE command robot should be placed"() {
        when:
        underTest.doCommand("PLACE 0,0,NORTH")

        then:
        verifyAll(underTest) {
            x == 0
            y == 0
            direction == Direction.NORTH
        }
    }

    def "when input invalid PLACE command robot should do nothing"() {
        when:
        underTest.doCommand("PLACE A,B,NORTH")

        then:
        verifyAll(underTest) {
            x == null
            y == null
            direction == null
        }
    }

    def "when input MOVE command robot and robot is not placed then robot should not moved"() {
        when:
        underTest.doCommand("MOVE")

        then:
        verifyAll(underTest) {
            x == null
            y == null
            direction == null
        }
    }

    def "when input TURN command robot and robot is not placed then robot should not TURNED"() {
        when:
        underTest.doCommand("LEFT")

        then:
        verifyAll(underTest) {
            x == null
            y == null
            direction == null
        }
    }

    def "when input REPORT command robot and robot is not placed then robot should not REPORT"() {
        when:
        underTest.doCommand("REPORT")

        then:
        verifyAll(underTest) {
            x == null
            y == null
            direction == null
        }
    }

    def "when place robot outside of the table robot should ignore command"() {
        when:
        underTest.doCommand("PLACE 15,10,NORTH")
        underTest.doCommand("MOVE")
        underTest.doCommand("PLACE 15,10,SOUTH")
        underTest.doCommand("MOVE")
        underTest.doCommand("PLACE 15,10,WEST")
        underTest.doCommand("MOVE")
        underTest.doCommand("PLACE 15,10,EAST")
        underTest.doCommand("MOVE")

        then:
        verifyAll(underTest) {
            x == 15
            y == 10
            direction == Direction.EAST
        }
    }

    def "when place robot in the table robot should do command"() {
        when:
        underTest.doCommand("PLACE 1,0,NORTH")
        underTest.doCommand("MOVE")
        underTest.doCommand("MOVE")
        underTest.doCommand("LEFT")
        underTest.doCommand("MOVE")
        underTest.doCommand("LEFT")
        underTest.doCommand("MOVE")
        underTest.doCommand("LEFT")
        underTest.doCommand("MOVE")
        underTest.doCommand("RIGHT")
        underTest.doCommand("MOVE")
        underTest.doCommand("REPORT")

        then:
        verifyAll(underTest) {
            x == 1
            y == 0
            direction == Direction.SOUTH
        }
    }

    def "when place robot and turn left 4 times direction should be NORTH again"() {
        when:
        underTest.doCommand("PLACE 1,0,NORTH")
        underTest.doCommand("LEFT")
        underTest.doCommand("LEFT")
        underTest.doCommand("LEFT")
        underTest.doCommand("LEFT")

        then:
        verifyAll(underTest) {
            x == 1
            y == 0
            direction == Direction.NORTH
        }
    }

    def "when place robot and turn right 4 times direction should be NORTH again"() {
        when:
        underTest.doCommand("PLACE 1,0,NORTH")
        underTest.doCommand("RIGHT")
        underTest.doCommand("RIGHT")
        underTest.doCommand("RIGHT")
        underTest.doCommand("RIGHT")

        then:
        verifyAll(underTest) {
            x == 1
            y == 0
            direction == Direction.NORTH
        }
    }

    def "when place robot in 5,5 robot should ignore command if go NORTH or EAST"() {
        when:
        underTest.doCommand("PLACE 5,5,NORTH")
        underTest.doCommand("MOVE")
        underTest.doCommand("RIGHT")
        underTest.doCommand("MOVE")

        then:
        verifyAll(underTest) {
            x == 5
            y == 5
            direction == Direction.EAST
        }
    }

    def "when place robot in 5,5 robot should do command if go WEST or SOUTH"() {
        when:
        underTest.doCommand("PLACE 5,5,SOUTH")
        underTest.doCommand("MOVE")
        underTest.doCommand("RIGHT")
        underTest.doCommand("MOVE")

        then:
        verifyAll(underTest) {
            x == 4
            y == 4
            direction == Direction.WEST
        }
    }
}
