import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class OrderFormTest {


    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldSubmitFormTest() {
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldEmptyNameField() {

        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldHyphenInNameField() {

        $("[data-test-id=name] input").setValue("Николай Миклухо-Маклай");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldLatinLettersInNameField() {

        $("[data-test-id=name] input").setValue("Ivanov Ivan");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldEmptyPhoneField() {

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldPhoneWithoutPlusSymbol() {

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("89111111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldMoreElevenCharactersInPhone() {

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+791111111119");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldLessElevenCharactersInPhone() {

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+7911111111");
        $("[data-test-id=agreement]").click();
        $("[class='button__text']").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldCheckboxNotMarked() {

        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79111111111");
        $("[class='button__text']").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(text("Я соглашаюсь"));
    }
}