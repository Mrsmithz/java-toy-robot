package unit

import org.example.service.implement.FileReaderServiceImpl
import spock.lang.Specification

class FileReaderServiceImplTest extends Specification {

    def underTest = new FileReaderServiceImpl()

    def "when get buffered reader should return buffered reader"() {
        when:
        def reader = underTest.getBufferedReader("test.txt")

        then:
        reader != null
    }
}
