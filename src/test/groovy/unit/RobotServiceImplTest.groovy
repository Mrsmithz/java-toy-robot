package unit


import org.example.model.Commandable
import org.example.service.FileReaderService
import org.example.service.implement.RobotServiceImpl
import spock.lang.Specification

class RobotServiceImplTest extends Specification {

    def robot = Mock(Commandable)
    def scanner = Mock(Scanner)
    def fileReaderService = Mock(FileReaderService)
    def underTest = new RobotServiceImpl(
            robot,
            scanner,
            fileReaderService
    )

    def "when choose input with CLI and input valid command robot should do commands"() {
        when:
        underTest.start()

        then:
        5 * scanner.nextLine() >>> ["1", "PLACE 0,0,NORTH", "MOVE", "REPORT", "DONE"]

        1 * robot.doCommand(_ as String) >> {
            String command ->
                {
                    verifyAll {
                        command == "PLACE 0,0,NORTH"
                    }
                }
        }

        1 * robot.doCommand(_ as String) >> {
            String command ->
                {
                    verifyAll {
                        command == "MOVE"
                    }
                }
        }

        1 * robot.doCommand(_ as String) >> {
            String command ->
                {
                    verifyAll {
                        command == "REPORT"
                    }
                }
        }
    }

    def "when choose input with CLI and input valid command without place robot first robot should ignore commands before placed"() {
        when:
        underTest.start()

        then:
        6 * scanner.nextLine() >>> ["1", "MOVE", "PLACE 0,0,NORTH", "MOVE", "REPORT", "DONE"]

        1 * robot.doCommand("MOVE")

        1 * robot.doCommand("PLACE 0,0,NORTH")

        1 * robot.doCommand("MOVE")

        1 * robot.doCommand("REPORT")
    }

    def "when choose input with CLI and input invalid place command robot should ignore all commands"() {
        when:
        underTest.start()

        then:
        6 * scanner.nextLine() >>> ["1", "MOVE", "PLACE A,0,NORTH", "MOVE", "REPORT", "DONE"]
    }

    def "when choose input with File and input valid command robot should do commands"() {
        given:
        def reader = Mock(BufferedReader)

        when:
        underTest.start()

        then:
        2 * scanner.nextLine() >>> ["2", "test.txt"]

        1 * fileReaderService.getBufferedReader(_ as String) >> {
            String fileName ->
                {
                    verifyAll {
                        fileName == "test.txt"
                    }

                    return reader
                }
        }

        5 * reader.readLine() >>> ["1", "PLACE 0,0,NORTH", "MOVE", "REPORT", null]

        1 * robot.doCommand("PLACE 0,0,NORTH")

        1 * robot.doCommand("MOVE")

        1 * robot.doCommand("REPORT")
    }

    def "when choose input with File and file not found should throw exception"() {
        when:
        underTest.start()

        then:
        2 * scanner.nextLine() >>> ["2", "test.txt"]

        1 * fileReaderService.getBufferedReader(_ as String) >> {
            String fileName ->
                {
                    verifyAll {
                        fileName == "test.txt"
                    }

                    throw Mock(FileNotFoundException)
                }
        }

        thrown(FileNotFoundException)
    }

    def "when choose input with File and get IO error should throw exception"() {
        given:
        def reader = Mock(BufferedReader)

        when:
        underTest.start()

        then:
        2 * scanner.nextLine() >>> ["2", "test.txt"]

        1 * fileReaderService.getBufferedReader(_ as String) >> {
            String fileName ->
                {
                    verifyAll {
                        fileName == "test.txt"
                    }

                    return reader
                }
        }

        1 * reader.readLine() >> { throw Mock(IOException) }

        thrown(IOException)
    }

    def "when choose invalid input type should throw exception"() {

        when:
        underTest.start()

        then:
        1 * scanner.nextLine() >> "3"

        thrown(IllegalArgumentException)
    }
}
