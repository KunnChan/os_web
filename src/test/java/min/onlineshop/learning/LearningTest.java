package min.onlineshop.learning;

import min.onlineshop.domains.CardType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

public class LearningTest {

    @DisplayName("My Repeated Test")
    @RepeatedTest(value = 5)
    void repeatedTest(){
    }

    @DisplayName("My Repeated Test with DI")
    @RepeatedTest(value = 5)
    void repeatedTestDI(TestInfo testInfo, RepetitionInfo repetitionInfo){
        System.out.println(testInfo.getDisplayName() + " : " + repetitionInfo.getCurrentRepetition());
    }

    @DisplayName("My Parameterized - Value Source Test")
    @ParameterizedTest
    @ValueSource(strings = {"Min", "Kunn", "Chan"})
    void valueSourceTest(String value){
        System.out.println(value);
    }

    @DisplayName("My Parameterized - Enum Source Test")
    @ParameterizedTest
    @EnumSource(CardType.class)
    void enumSourceTest(CardType value){
        System.out.println(value);
    }

    @DisplayName("My Parameterized - CSV Source Test")
    @ParameterizedTest
    @CsvSource({
            "Min, 12, 22",
            "Kunn, 22, 33",
            "Chan, 44, 55"
    })
    void csvSourceTest(String name, String math, String eng){
        System.out.println(name + " got mark in math : "+ math + ", english : "+ eng);
    }

    @DisplayName("My Parameterized - CSV Source File Test")
    @ParameterizedTest
    @CsvFileSource(resources = "/student.csv", numLinesToSkip = 1)
    void csvSourceFileTest(String name, String math, String eng){
        System.out.println(name + " got mark in math : "+ math + ", english : "+ eng);
    }

    @DisplayName("My Parameterized - Method Source Test")
    @ParameterizedTest
    @MethodSource("getArgs")
    void methodSourceTest(String name, Integer math, Integer eng){
        System.out.println(name + " got mark in math : "+ math + ", english : "+ eng);
    }

    static Stream<Arguments> getArgs(){
        return Stream.of(
                Arguments.of("Min", 11,22),
                Arguments.of("Kunn", 33,44),
                Arguments.of("Chan", 55,66)
        );
    }

    @DisplayName("My Parameterized - Argument Source Test")
    @ParameterizedTest
    @ArgumentsSource(CustomArgsProvider.class)
    void customProviderTest(String name, Integer math, Integer eng){
        System.out.println(name + " got mark in math : "+ math + ", english : "+ eng);
    }
}
