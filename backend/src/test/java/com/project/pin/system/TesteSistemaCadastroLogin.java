package com.project.pin.system;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.After;
import org.junit.Before;

import java.time.Duration;

/**
 * Classe de Testes de Sistema para as funcionalidades de Cadastro e Login.
 * ATUALIZAÇÃO: Aumentado o TIMEOUT para 30 segundos para lidar com a lentidão do ambiente local.
 */
public class TesteSistemaCadastroLogin {

    private WebDriver driver;
    // O tempo de espera padrão (em segundos) - AUMENTADO PARA 30s
    private static final int TIMEOUT_SECS = 30;

    // --- CONFIGURE SUAS URLs AQUI ---
    private final String URL_BASE = "http://localhost:5173";
    private final String URL_CADASTRO = URL_BASE + "/cadastro";
    private final String URL_LOGIN = URL_BASE + "/login";

    @Before
    public void setUp() {
        // --- CONFIGURAÇÃO HEADLESS PARA ECONOMIZAR RECURSOS ---
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        // --- FIM CONFIGURAÇÃO HEADLESS ---

        // Inicializa o ChromeDriver com as opções.
        driver = new ChromeDriver(options);
    }


    @After
    public void tearDown() {
        // Fecha o navegador após cada teste
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Helper para usar espera explícita
     * @return Instância de WebDriverWait com timeout padrão.
     */
    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECS));
    }

    /**
     * Localiza um campo de entrada (input) usando o texto visível e exato do seu Label.
     * Este é o seletor mais robusto para componentes Material-UI aninhados.
     * @param labelText O texto do rótulo visível e exato (ex: "Username" ou "Senha").
     * @return O elemento input encontrado.
     */
    private WebElement findInputByLabel(String labelText) {
        // XPath: Procura o elemento LABEL com o texto exato e usa 'ancestor'
        // para subir até o container MuiFormControl-root e buscar o INPUT dentro dele.
        String xpath = "//label[normalize-space(text())='" + labelText + "']/ancestor::div[contains(@class, 'MuiFormControl-root')]//input";

        return getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))
        );
    }

    /**
     * ----------------------------------------
     * TESTES DE CADASTRO (RegisterForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT01: Valida se o sistema efetua o cadastro de um usuário de forma correta.
     * Resultado Esperado (Sistema): Redirecionamento para a tela de login.
     */
    @Test
    public void testCadastroComSucesso_CT01() {
        driver.get(URL_CADASTRO);

        // Preenche os campos usando os dados do CT01
        findInputByLabel("Username").sendKeys("clarabecker");
        findInputByLabel("Senha").sendKeys("minhaSenha123");
        findInputByLabel("Nome").sendKeys("Clara Becker");
        findInputByLabel("CPF").sendKeys("12345678900");
        findInputByLabel("Endereço").sendKeys("Rua das Flores, 120");
        findInputByLabel("Bairro").sendKeys("Centro");
        findInputByLabel("Cidade").sendKeys("Florianópolis");
        findInputByLabel("CEP").sendKeys("88000000");
        findInputByLabel("Estado").sendKeys("SC");
        findInputByLabel("Telefone").sendKeys("48999998888");
        findInputByLabel("Email").sendKeys("clara.becker@email.com");

        // Clica no botão "Salvar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera o redirecionamento para /login.
        getWait().until(ExpectedConditions.urlContains("/login"));

        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /login. URL atual: " + urlAtual, urlAtual.contains("/login"));
    }

    /**
     * CT02: Valida se o sistema impede a criação de usuários com campos vazios.
     * Resultado Esperado (Sistema): Mensagem genérica de erro do catch do React.
     */
    @Test
    public void testCadastroCamposVazios_CT02() {
        driver.get(URL_CADASTRO);

        // Deixa 'Username' e 'Nome' vazios, mas preenche outros (Conforme CT02)
        findInputByLabel("Username").sendKeys("");
        findInputByLabel("Nome").sendKeys("");

        // Preencher outros campos para forçar a submissão
        findInputByLabel("Email").sendKeys("clara.becker@email.com");
        findInputByLabel("Senha").sendKeys("minhaSenha123");
        findInputByLabel("CPF").sendKeys("12345678900");
        findInputByLabel("Endereço").sendKeys("Rua das Flores, 120");
        findInputByLabel("Bairro").sendKeys("Centro");
        findInputByLabel("Cidade").sendKeys("Florianópolis");
        findInputByLabel("CEP").sendKeys("88000000");
        findInputByLabel("Estado").sendKeys("SC");
        findInputByLabel("Telefone").sendKeys("48999998888");

        // Clica no botão "Salvar"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera a mensagem de erro genérica do catch do React.
        WebElement msgErro = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Erro ao cadastrar Usuario')]"))
        );

        Assert.assertEquals("A mensagem de erro esperada ('Erro ao cadastrar Usuario') não foi encontrada.", "Erro ao cadastrar Usuario", msgErro.getText());
    }

    /**
     * CT03: Verifica se o sistema impede o cadastro com um username ou cpf já cadastrados.
     * Resultado Esperado (Sistema): Mensagem genérica de erro do catch do React.
     */
    @Test
    public void testCadastroDuplicado_CT03() {
        driver.get(URL_CADASTRO);

        // Preenche com os dados duplicados (assumidos do CT01)
        findInputByLabel("Username").sendKeys("clarabecker");
        findInputByLabel("Senha").sendKeys("minhaSenha123");
        findInputByLabel("Nome").sendKeys("Clara Becker (Duplicada)");
        findInputByLabel("CPF").sendKeys("12345678900");
        findInputByLabel("Endereço").sendKeys("Rua Duplicada");
        findInputByLabel("Bairro").sendKeys("Centro");
        findInputByLabel("Cidade").sendKeys("Florianópolis");
        findInputByLabel("CEP").sendKeys("88000000");
        findInputByLabel("Estado").sendKeys("SC");
        findInputByLabel("Telefone").sendKeys("48999998888");
        findInputByLabel("Email").sendKeys("duplicada.becker@email.com");

        // Clica no botão "Salvar"
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera a mensagem de erro genérica do catch do React.
        WebElement msgErro = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Erro ao cadastrar Usuario')]"))
        );

        Assert.assertEquals("A mensagem de erro de duplicidade ('Erro ao cadastrar Usuario') não foi encontrada.", "Erro ao cadastrar Usuario", msgErro.getText());
    }


    /**
     * ----------------------------------------
     * TESTES DE LOGIN (LoginForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT04: Valida se o sistema efetua a autenticação de forma correta.
     * Resultado Esperado (Sistema): Redirecionamento para /main.
     */
    @Test
    public void testLoginComSucesso_CT04() {
        driver.get(URL_LOGIN);

        // Preenche os campos usando o texto do rótulo (Label Text)
        findInputByLabel("Username").sendKeys("clarabecker");
        findInputByLabel("Senha").sendKeys("minhaSenha123");

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera o redirecionamento para /main.
        getWait().until(ExpectedConditions.urlContains("/main"));

        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /main. URL atual: " + urlAtual, urlAtual.contains("/main"));
    }

    /**
     * CT05: Valida se o sistema impede a autenticação com senha incorreta.
     * Resultado Esperado (Sistema): Mensagem "Usuário ou senha inválidos.".
     */
    @Test
    public void testLoginComSenhaIncorreta_CT05() {
        driver.get(URL_LOGIN);

        // Preenche os campos usando o texto do rótulo (Label Text)
        findInputByLabel("Username").sendKeys("clarabecker");
        findInputByLabel("Senha").sendKeys("minhaSenha"); // Senha incorreta

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera a mensagem específica de erro de login.
        WebElement msgErro = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Usuário ou senha inválidos.')]"))
        );

        Assert.assertEquals("A mensagem de erro de login incorreto não foi encontrada.", "Usuário ou senha inválidos.", msgErro.getText());
    }
}