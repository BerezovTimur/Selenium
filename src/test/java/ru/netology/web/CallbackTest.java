package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class CallbackTest {

    @Test
    void shouldSubmitCorrect() {
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Теркин");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79110000000");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=order-success]").shouldHave(Condition.exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    void shouldSubmitUncheckedBox(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Теркин");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79110000000");
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=agreement].input_invalid").shouldHave(Condition.exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));

    }

    @Test
    void shouldSubmitIfNoName(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79110000000");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitIfNoPhone(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Теркин");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitIfLatinLatter(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Ivan Brovkin");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79110000000");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSubmitEmpty(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldSubmitIfNotValidSymbolsInName(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("111 111");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79110000000");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=name] .input__sub").shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldSubmitIfNoPlusInNumber(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Теркин");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("79110000000");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldSubmitIfNotValidSymbolsInNumber(){
        open("http://localhost:9999");
        SelenideElement form = $("[action]");
        form.$(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Теркин");
        form.$(By.cssSelector("[data-test-id=phone] input")).sendKeys("Василий");
        form.$(By.cssSelector("[data-test-id=agreement]")).click();
        form.$(By.cssSelector("[role=button]")).click();
        $("[data-test-id=phone] .input__sub").shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
}
