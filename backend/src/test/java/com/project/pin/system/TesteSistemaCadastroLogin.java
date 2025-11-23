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
 * Implementa testes de Caixa Preta (Black Box) usando Selenium e JUnit.
 * O código usa seletores robustos para componentes Material-UI (MUI).
 */
public class TesteSistemaCadastroLogin {

    private WebDriver driver;
    // Timeout de 30 segundos para dar tempo ao Backend processar (evita TimeoutException).
    private static final int TIMEOUT_SECS = 30;

    // Variável para garantir unicidade em testes de sucesso (evita conflitos no CT01)
    private String uniqueUser;

    // --- DADOS FIXOS PARA TESTES DE LOGIN (PRECISAM EXISTIR NO BANCO) ---
    // O usuário 'autoteste' deve ter sido criado manualmente no banco de dados com a senha "teste123123".
    private final String USUARIO_LOGIN_EXISTENTE = "autoteste";
    private final String SENHA_LOGIN_EXISTENTE = "teste123123";
    private final String MENSAGEM_ERRO_CADASTRO = "Erro ao cadastrar Usuario";
    // --------------------------------------------------------------------

    // --- CONFIGURE SUAS URLs AQUI ---
    private final String URL_BASE = "http://localhost:5173";
    private final String URL_CADASTRO = URL_BASE + "/cadastro";
    private final String URL_LOGIN = URL_BASE + "/login";

    @Before
    public void setUp() {
        // Gera um ID único baseado no timestamp para isolamento de dados
        String timestamp = String.valueOf(System.currentTimeMillis()).substring(5);
        this.uniqueUser = "auto_teste_" + timestamp;

        // --- CONFIGURAÇÃO HEADLESS (SEM JANELA) ---
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

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
     * Helper para criar uma espera explícita com o timeout definido.
     */
    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT_SECS));
    }

    /**
     * Localiza o elemento INPUT de um campo usando o texto exato do seu rótulo (Label).
     * Essencial para lidar com IDs dinâmicos do Material-UI.
     * @param labelText O texto do rótulo visível e exato (ex: "Username").
     * @return O elemento input encontrado.
     */
    private WebElement findInputByLabel(String labelText) {
        // XPath mais robusto para Material-UI.
        String xpath = "//label[normalize-space(text())='" + labelText + "']/ancestor::div[contains(@class, 'MuiFormControl-root')]//input";

        return getWait().until(
                ExpectedConditions.presenceOfElementLocated(By.xpath(xpath))
        );
    }

    /**
     * Método auxiliar para preencher os dados de cadastro e submeter o formulário.
     */
    private void cadastrarUsuario(String username, String senha, String cpf, String email) {
        // Nota: Deixar campos vazios aqui causará a validação nativa do navegador (HTML5).
        findInputByLabel("Username").sendKeys(username);
        findInputByLabel("Senha").sendKeys(senha);
        findInputByLabel("Nome").sendKeys("Clara Teste");
        findInputByLabel("CPF").sendKeys(cpf);
        findInputByLabel("Endereço").sendKeys("Rua do Teste, 120");
        findInputByLabel("Bairro").sendKeys("Centro");
        findInputByLabel("Cidade").sendKeys("Florianópolis");
        findInputByLabel("CEP").sendKeys("88000000");
        findInputByLabel("Estado").sendKeys("SC");
        findInputByLabel("Telefone").sendKeys("48999998888");
        findInputByLabel("Email").sendKeys(email);

        // Clica no botão "Salvar" (tipo submit no formulário)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Pequeno delay após o clique para dar tempo de iniciar a requisição.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    /**
     * ----------------------------------------
     * TESTES DE CADASTRO (RegisterForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT01: Valida se o sistema efetua o cadastro de um usuário de forma correta.
     * Resultado Esperado (Alinhado ao React): Redirecionamento para a tela de login.
     */
    @Test
    public void testCadastroComSucesso_CT01() {
        driver.get(URL_CADASTRO);

        // Gera dados totalmente únicos para o cadastro de sucesso
        String username = uniqueUser + "_ct01";
        String cpf = "111222333" + uniqueUser.substring(13);
        String email = uniqueUser + "@sucesso.com";

        cadastrarUsuario(username, "minhaSenha123", cpf, email);

        // VALIDAÇÃO: Espera o redirecionamento para /login.
        getWait().until(ExpectedConditions.urlContains("/login"));

        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /login após o cadastro.", urlAtual.contains("/login"));
    }

    /**
     * CT02: Valida se o sistema impede a criação de usuários com campos vazios.
     * Resultado Esperado: Permanência na tela /cadastro (devido à validação nativa do navegador).
     */
    @Test
    public void testCadastroCamposVazios_CT02() {
        driver.get(URL_CADASTRO);

        // 1. Preenche todos os campos, EXCETO um (Username), que é obrigatório.
        String cpf = "444555666" + uniqueUser.substring(13);

        findInputByLabel("Username").sendKeys(""); // VAZIO (Ativa validação HTML5)
        findInputByLabel("Senha").sendKeys("minhaSenha123");
        findInputByLabel("Nome").sendKeys("Clara Teste");
        findInputByLabel("CPF").sendKeys(cpf);
        findInputByLabel("Endereço").sendKeys("Rua X");
        findInputByLabel("Bairro").sendKeys("Bairro Y");
        findInputByLabel("Cidade").sendKeys("Cidade Z");
        findInputByLabel("CEP").sendKeys("00000000");
        findInputByLabel("Estado").sendKeys("XX");
        findInputByLabel("Telefone").sendKeys("11999990000");
        findInputByLabel("Email").sendKeys("vazio@test.com");

        // 2. Clica no botão "Salvar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 3. VALIDAÇÃO: Verifica se o navegador bloqueou o envio e se o URL NÃO mudou.
        try {
            // Tenta esperar por um redirecionamento de sucesso (que não deve acontecer)
            getWait().withTimeout(Duration.ofSeconds(1)).until(ExpectedConditions.urlContains("/login"));
            // Se o código chegar aqui, significa que houve redirecionamento indevido.
            Assert.fail("O formulário foi enviado com campos vazios e houve redirecionamento indevido para /login.");
        } catch (Exception e) {
            // Se houver exceção (o mais provável, pois o redirecionamento foi bloqueado), verifica o URL.
            String urlAtual = driver.getCurrentUrl();
            Assert.assertTrue("Deveria ter permanecido na tela de cadastro devido à validação. URL atual: " + urlAtual, urlAtual.contains("/cadastro"));
        }
    }

    /**
     * CT03: Verifica se o sistema impede o cadastro com um username ou cpf já cadastrados.
     * Resultado Esperado: Mensagem de erro genérica do catch (requer comunicação com o backend).
     */
    @Test
    public void testCadastroDuplicado_CT03() {
        driver.get(URL_CADASTRO);

        // Tenta cadastrar com o username fixo existente 'autoteste' (que causa duplicidade)
        cadastrarUsuario(USUARIO_LOGIN_EXISTENTE, "outraSenha123", "00000000000", "duplicado_" + uniqueUser + "@test.com");

        // VALIDAÇÃO: Espera a mensagem de erro genérica do catch do React, que aparece após a rejeição do Backend.
        WebElement msgErro = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + MENSAGEM_ERRO_CADASTRO + "')]"))
        );

        Assert.assertEquals("A mensagem de erro de duplicidade ('" + MENSAGEM_ERRO_CADASTRO + "') não foi encontrada.", MENSAGEM_ERRO_CADASTRO, msgErro.getText());
    }


    /**
     * ----------------------------------------
     * TESTES DE LOGIN (LoginForm.tsx)
     * ----------------------------------------
     */

    /**
     * CT04: Valida se o sistema efetua a autenticação de forma correta.
     * Resultado Esperado: Redirecionamento para /main.
     */
    @Test
    public void testLoginComSucesso_CT04() {
        // Usa o usuário fixo 'autoteste'
        driver.get(URL_LOGIN);

        findInputByLabel("Username").sendKeys(USUARIO_LOGIN_EXISTENTE);
        findInputByLabel("Senha").sendKeys(SENHA_LOGIN_EXISTENTE);

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera o redirecionamento para /main.
        getWait().until(ExpectedConditions.urlContains("/main"));

        String urlAtual = driver.getCurrentUrl();
        Assert.assertTrue("Deveria ter sido redirecionado para /main. URL atual: " + urlAtual, urlAtual.contains("/main"));
    }

    /**
     * CT05: Valida se o sistema impede a autenticação com senha incorreta.
     * Resultado Esperado: Mensagem "Usuário ou senha inválidos.".
     */
    @Test
    public void testLoginComSenhaIncorreta_CT05() {
        // Usa o usuário fixo 'autoteste'
        driver.get(URL_LOGIN);

        findInputByLabel("Username").sendKeys(USUARIO_LOGIN_EXISTENTE);
        findInputByLabel("Senha").sendKeys("senhaTotalmenteErrada"); // Senha incorreta

        // Clica no botão "Entrar" (submit)
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // VALIDAÇÃO: Espera a mensagem específica de erro de login.
        WebElement msgErro = getWait().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Usuário ou senha inválidos.')]"))
        );

        Assert.assertEquals("A mensagem de erro de login incorreto não foi encontrada.", "Usuário ou senha inválidos.", msgErro.getText());
    }
}
